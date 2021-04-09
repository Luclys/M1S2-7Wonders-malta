package engine.server;

import engine.board.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;

import java.io.PrintStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.logging.Logger;

@Component
@Scope("singleton")
@SpringBootApplication
public class EngineServer {
    private final static Logger log = Logger.getLogger(EngineServer.class.getName());
    static int nbPlayers = 3;
    static int nbGames = 1;
    static boolean boolPrint = false;
    private boolean connectToServer = false;
    private boolean gameStarted = false;


    @Autowired
    EngineServerController ctrl;

    HashMap<Integer, String> mapPlayerID_URL;
    private String serverURL;


    public static void main(String... args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        SpringApplication.run(EngineServer.class, args);
    }

    @Bean
    public CommandLineRunner runner() {
        return args -> {
            System.out.println("***************** EngineServer running... ******************");
            serverURL = args.length >= 1 ? "http://" + args[0] + ":8080" : "http://127.0.0.1:8080";
            nbPlayers = args.length >= 2 ? Integer.parseInt(args[1]) : 3;
            nbGames = args.length >= 3 ? Integer.parseInt(args[2]) : 1;

            this.mapPlayerID_URL = new HashMap<>(nbPlayers);

            System.out.println("StatsServer IP : " + serverURL);
            System.out.println("EngineServer IP : " + InetAddress.getLocalHost().getHostAddress());
            connectToStatsServer();
        };
    }

    private void startGamesEngine() throws Exception {
        ctrl.sendNumberOfPlayers(nbPlayers);

        for (int i = 1; i <= nbGames; i++) {
            // au lieu de playList -> urlList
            for (Integer k: mapPlayerID_URL.keySet()){
                System.out.println("ENGINE SERVER >  *** MAP PLAYER > player id "+k+" url "+mapPlayerID_URL.get(k));
            }

            Board board = new Board(this.mapPlayerID_URL, boolPrint,ctrl);
            board.play(i);

            if (i != nbGames) {
                System.out.printf("ENGINE SERVER > [7WONDERS - MALTA] Progress : %d / %d.\r", i, nbGames);
            } else {
                System.out.printf("ENGINE SERVER > [7WONDERS - MALTA] Execution finished : %d games played.\n", nbGames);
            }

            ctrl.sendStats(board.getResults());
        }
       System.out.println("ENGINE SERVER > \n"+ctrl.showStats());

        try {
            ctrl.disconnectStatsServer();
        } catch (ResourceAccessException ignored) {
        }

        mapPlayerID_URL.values().forEach(url -> {
            try {
                System.out.println("ENGINE SERVER > Disconnect request send.");
                ctrl.disconnectPlayer(url);
            } catch (ResourceAccessException ignored) {
            }
        });


        System.exit(0);
    }

    private void connectToStatsServer() {
        System.out.println("ENGINE SERVER > ***************** Connect Engine Server to Stats Server ******************");
        connectToServer = ctrl.connectToStatsServer(serverURL);
        System.out.println("ENGINE SERVER > Connection accepted ? " + connectToServer);
    }

    int addPlayerURL(String url) {
        // Si accès concurrent : BOOM
        this.mapPlayerID_URL.put(mapPlayerID_URL.size(), url);
        return this.mapPlayerID_URL.size() - 1;
    }

    public HashMap<Integer, String> getUrls()  {
        return this.mapPlayerID_URL;
    }

    public String getUrl() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }

    public void testStart() {
        if (!gameStarted && nbPlayers <= mapPlayerID_URL.size()) {
            System.out.println("ENGINE SERVER > " + mapPlayerID_URL.size() + " players ready, initialising games.");
            Thread launchGame = new Thread(() -> {
                try {
                    startGamesEngine();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                gameStarted = true;
            });
            launchGame.start();
        } else
            if(!gameStarted){
                System.out.println("ENGINE SERVER > " + (mapPlayerID_URL.size() - nbPlayers) + " missing players. Waiting...");
            }
    }

    public boolean isConnectToServer() {
        return connectToServer;
    }
}
