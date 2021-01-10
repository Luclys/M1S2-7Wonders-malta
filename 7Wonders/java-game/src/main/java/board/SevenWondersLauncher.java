package board;


import client.Client;
import gameelements.GameLogger;
import gameelements.Player;
import strategy.FirstCardStrategy;
import strategy.MonteCarloStrategy;
import strategy.WonderStrategy;

import java.util.ArrayList;
import java.util.List;

public class SevenWondersLauncher {
    private  static final GameLogger log = new GameLogger(true);
    public static  Client client;
    static int nbPlayers = 3;
    static int nbGames = 1000;
    static boolean boolPrint = false;


    public static void main(String... args) throws Exception {
        //Starting the client
        startClient();

        //Maven's arguments
        if (args.length >= 3) {
            nbPlayers = Integer.parseInt(args[0]);
            nbGames = Integer.parseInt(args[1]);
            boolPrint = Boolean.parseBoolean(args[2]);
        }

        List<Player> playerList = fetchPlayers(nbPlayers);
        client.sendNumberOfPlayers(nbPlayers);
        ArrayList<Integer> winsCount = new ArrayList<>();
        for (int i = 0; i < nbPlayers; i++) {
            winsCount.add(0);
        }
        for (int i = 1; i <= nbGames; i++) {
            Board board = new Board(playerList, boolPrint);
            int winner = board.play(i);
            winsCount.set(winner, winsCount.get(winner)+1);
            if (i != nbGames) {
                System.out.printf("[7WONDERS - LAMAC] Progress : %d / %d.\r", i, nbGames);
            } else {
                System.out.printf("[7WONDERS - LAMAC] Execution finished : %d games played.\n", nbGames);
            }
        }
        for (int i = 0; i < winsCount.size(); i++) {
            System.out.printf("[7WONDERS - LAMAC] Player "+ i+" wins " +  winsCount.get(i)+" times\n");
        }
        client.showStats();
    }

    public static List<Player> fetchPlayers(int nbPlayers) {
        List<Player> playerList = new ArrayList<>(nbPlayers);

        Player player1 = new Player(0, new MonteCarloStrategy());
        Player player2 = new Player(1, new WonderStrategy());
        playerList.add(player1);
        playerList.add(player2);

        for (int i = playerList.size(); i < nbPlayers; i++) {
            Player player = new Player(i, new FirstCardStrategy());
            playerList.add(player);
        }
        return playerList;
    }

    public static void startClient() throws Exception {
        client = new Client("http://127.0.0.1:10101");
        client.start();
    }

}
