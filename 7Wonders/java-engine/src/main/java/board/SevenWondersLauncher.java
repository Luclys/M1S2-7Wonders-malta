package board;

import client.ClientEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.ResourceAccessException;
import strategy.FirstCardStrategy;
import strategy.RuleBasedAI;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SevenWondersLauncher {

    static int nbPlayers = 3;
    static int nbGames = 5;
    static boolean boolPrint = false;
    @Autowired
    ClientEngine clientEngine;

    public static void main(String... args) {
        SpringApplication.run(SevenWondersLauncher.class, args);
    }

    public static List<String> fetchPlayers(int nbPlayers) {
        List<String> playerURLList = new ArrayList<>(nbPlayers);

        Player player1 = new Player(playerURLList.size(), new RuleBasedAI());
        playerURLList.add(player1);
        Player player2 = new Player(playerURLList.size(), new RuleBasedAI());
        playerURLList.add(player2);
        Player player3 = new Player(playerURLList.size(), new FirstCardStrategy());
        playerURLList.add(player3);


        for (int i = playerURLList.size(); i < nbPlayers; i++) {
            Player player = new Player(i, new FirstCardStrategy());
            playerURLList.add(player);
        }
        return playerURLList;
    }

    @Bean
    public CommandLineRunner unClient() {
        return args -> {
            /// retrieving the value
            String adresse = args.length == 1 ? "http://" + args[0] + ":8080" : "http://localhost:8080";

            System.out.println("***************** Connect ClientEngine to Server ******************");
            Boolean val = clientEngine.crl.connection(adresse);
            System.out.println("clientEngine > Connection accepted ? " + val);
            if (args.length >= 3) {
                nbPlayers = Integer.parseInt(args[0]);
                nbGames = Integer.parseInt(args[1]);
                boolPrint = Boolean.parseBoolean(args[2]);
            }

            List<Player> playerList = fetchPlayers(nbPlayers);
            clientEngine.crl.sendNumberOfPlayers(nbPlayers);

            for (int i = 1; i <= nbGames; i++) {
                // au lieu de playList -> urlList
                Board board = new Board(playerList, boolPrint);
                board.play(i);

                if (i != nbGames) {
                    System.out.printf("[7WONDERS - LAMAC] Progress : %d / %d.\r", i, nbGames);
                } else {
                    System.out.printf("[7WONDERS - LAMAC] Execution finished : %d games played.\n", nbGames);
                }

                clientEngine.crl.sendStats(board.getResults());
            }
            clientEngine.crl.showStats();

            try {
                clientEngine.crl.disconnect();
            } catch (ResourceAccessException ignored) {
            }

            System.exit(0);
        };
    }

}
