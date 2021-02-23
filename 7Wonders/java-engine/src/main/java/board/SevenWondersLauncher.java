package board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import player.Player;
import strategy.FirstCardStrategy;
import strategy.RuleBasedAI;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SevenWondersLauncher {
    private static final GameLogger log = new GameLogger(true);

    static int nbPlayers = 3;
    static int nbGames = 1000;
    static boolean boolPrint = false;


    public static void main(String... args) throws Exception {
        SpringApplication.run(SevenWondersLauncher.class, args);
    }
    @Autowired
    Client client;
    @Bean
    public CommandLineRunner unClient() {
        return args -> {
            /// retrieving the value
            System.out.println("*****************Connect Client to server******************");
            Boolean val =  client.crl.connection();
            System.out.println("client > Connextion accepeted ? "+val);
            if (args.length >= 3) {
                nbPlayers = Integer.parseInt(args[0]);
                nbGames = Integer.parseInt(args[1]);
                boolPrint = Boolean.parseBoolean(args[2]);
            }

            List<Player> playerList = fetchPlayers(nbPlayers);
            client.crl.sendNumberOfPlayers(nbPlayers);

            for (int i = 1; i <= nbGames; i++) {
                // au lieu de playList -> urlList
                Board board = new Board(playerList, boolPrint);
                board.play(i);
                if (i != nbGames) {
                    System.out.printf("[7WONDERS - LAMAC] Progress : %d / %d.\r", i, nbGames);
                } else {
                    System.out.printf("[7WONDERS - LAMAC] Execution finished : %d games played.\n", nbGames);
                }
                client.crl.sendStats(board.getResults());
            }
            client.crl.showStats();
            System.exit(0);
        };
    }

    public static List<Player> fetchPlayers(int nbPlayers) {
        List<Player> playerList = new ArrayList<>(nbPlayers);

        Player player1 = new Player(playerList.size(), new RuleBasedAI());
        playerList.add(player1);
        Player player2 = new Player(playerList.size(), new RuleBasedAI());
        playerList.add(player2);
        Player player3 = new Player(playerList.size(), new FirstCardStrategy());
        playerList.add(player3);



        for (int i = playerList.size(); i < nbPlayers; i++) {
            Player player = new Player(i, new FirstCardStrategy());
            playerList.add(player);
        }
        return playerList;
    }

}
