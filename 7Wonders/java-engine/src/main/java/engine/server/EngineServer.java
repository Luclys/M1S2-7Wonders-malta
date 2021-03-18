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
    static int nbGames = 5;
    static boolean boolPrint = false;

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
            this.mapPlayerID_URL = new HashMap<>(7);
            serverURL = args.length == 1 ? "http://" + args[0] + ":8080" : "http://localhost:8080";
            System.out.println(serverURL);
            connectToStatsServer();
        };
    }

    private void startGamesEngine() throws Exception {
        ctrl.sendNumberOfPlayers(nbPlayers);

        for (int i = 1; i <= nbGames; i++) {
            // au lieu de playList -> urlList
            Board board = new Board(this.mapPlayerID_URL, boolPrint);
            board.play(i);

            if (i != nbGames) {
                System.out.printf("[7WONDERS - MALTA] Progress : %d / %d.\r", i, nbGames);
            } else {
                System.out.printf("[7WONDERS - MALTA] Execution finished : %d games played.\n", nbGames);
            }

            ctrl.sendStats(board.getResults());
        }
        ctrl.showStats();

        try {
            ctrl.disconnect();
        } catch (ResourceAccessException ignored) {
        }

        System.exit(0);
    }

    private void connectToStatsServer() {
        System.out.println("***************** Connect ClientEngine to StatsServer ******************");
        Boolean val = ctrl.connectToStatsServer(serverURL);
        System.out.println("clientEngine > Connection accepted ? " + val);
    }

    int addPlayerURL(String url) {
        // Si accès concurrent : BOOM
        this.mapPlayerID_URL.put(mapPlayerID_URL.size(), url);
        return this.mapPlayerID_URL.size() - 1;
    }

    public String getUrl() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }

    public void testStart() throws Exception {
        if (nbPlayers <= mapPlayerID_URL.size()) {
            System.out.println("clientEngine > " + mapPlayerID_URL.size() + " players ready, initialising games.");
            startGamesEngine();
        } else {
            System.out.println("clientEngine > " + (mapPlayerID_URL.size() - nbPlayers) + " missing players. Waiting...");
        }
    }
}
