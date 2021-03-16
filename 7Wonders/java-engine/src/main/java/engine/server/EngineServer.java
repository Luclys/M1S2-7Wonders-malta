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
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Component
@Scope("singleton")
@SpringBootApplication
public class EngineServer {
    private final static Logger log = Logger.getLogger(EngineServer.class.getName());

    @Autowired
    EngineServerController ctrl;

    static int nbPlayers = 3;
    static int nbGames = 5;
    static boolean boolPrint = false;
    List<String> playersURLList;
    private String serverURL;


    public static void main(String... args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        SpringApplication.run(EngineServer.class, args);
    }

    @Bean
    public CommandLineRunner runner() {
        return args -> {
            System.out.println("***************** EngineServer running... ******************");
            this.playersURLList = new ArrayList<>(10);
            serverURL = args.length == 1 ? "http://" + args[0] + ":8080" : "http://localhost:8080";
        };
    }

    private void startGamesEngine() throws Exception {
        System.out.println("***************** Connect ClientEngine to StatsServer ******************");
        Boolean val = ctrl.connectToStatsServer(serverURL);
        System.out.println("clientEngine > Connection accepted ? " + val);
/*
        if (args.length >= 3) {
            nbPlayers = Integer.parseInt(args[0]);
            nbGames = Integer.parseInt(args[1]);
            boolPrint = Boolean.parseBoolean(args[2]);
        }
*/

        ctrl.sendNumberOfPlayers(nbPlayers);

        for (int i = 1; i <= nbGames; i++) {
            // au lieu de playList -> urlList
            Board board = new Board(this.playersURLList, boolPrint);
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

    int addPlayerURL(String url){
        this.playersURLList.add(url);
        return this.playersURLList.size();
    }

    public String getUrl() {
        return "http://localhost:8081";
    }

    public void testStart() throws Exception {
        if (nbPlayers <= playersURLList.size()) {
            System.out.println("clientEngine > "+playersURLList.size()+" players ready, initialising games.");
            startGamesEngine();
        } else {
            System.out.println("clientEngine > " + (playersURLList.size()-nbPlayers) + " missing players. Waiting...");
        }
    }
}
