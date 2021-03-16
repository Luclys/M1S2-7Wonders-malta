package engine.server;

import engine.board.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.ResourceAccessException;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SevenWondersLauncher {

    static int nbPlayers = 3;
    static int nbGames = 5;
    static boolean boolPrint = false;
    @Autowired
    EngineServer engineServer;

    public static void main(String... args) {
        SpringApplication.run(SevenWondersLauncher.class, args);
    }

    public static List<String> fetchPlayers(int nbPlayers) {
        List<String> playerURLList = new ArrayList<>(nbPlayers);

        return playerURLList;
    }

    @Bean
    public CommandLineRunner unClient() {
        return args -> {
            /// retrieving the value
            String adresse = args.length == 1 ? "http://" + args[0] + ":8080" : "http://localhost:8080";

            System.out.println("***************** Connect ClientEngine to StatsServer ******************");
            Boolean val = engineServer.ctrl.connection(adresse);
            System.out.println("clientEngine > Connection accepted ? " + val);
            if (args.length >= 3) {
                nbPlayers = Integer.parseInt(args[0]);
                nbGames = Integer.parseInt(args[1]);
                boolPrint = Boolean.parseBoolean(args[2]);
            }

            List<String> playersURLList = fetchPlayers(nbPlayers);
            engineServer.ctrl.sendNumberOfPlayers(nbPlayers);

            for (int i = 1; i <= nbGames; i++) {
                // au lieu de playList -> urlList
                Board board = new Board(playersURLList, boolPrint);
                board.play(i);

                if (i != nbGames) {
                    System.out.printf("[7WONDERS - MALTA] Progress : %d / %d.\r", i, nbGames);
                } else {
                    System.out.printf("[7WONDERS - MALTA] Execution finished : %d games played.\n", nbGames);
                }

                engineServer.ctrl.sendStats(board.getResults());
            }
            engineServer.ctrl.showStats();

            try {
                engineServer.ctrl.disconnect();
            } catch (ResourceAccessException ignored) {
            }

            System.exit(0);
        };
    }

}
