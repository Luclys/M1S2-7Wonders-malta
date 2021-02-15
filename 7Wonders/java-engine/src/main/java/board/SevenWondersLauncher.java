package board;

import client.Client;
import gameelements.GameLogger;
import player.Player;
import strategy.FirstCardStrategy;
import strategy.RuleBasedAI;

import java.util.ArrayList;
import java.util.List;

public class SevenWondersLauncher {
    private static final GameLogger log = new GameLogger(true);
    public static Client client;
    static int nbPlayers = 3;
    static int nbGames = 1000;
    static boolean boolPrint = false;


    public static void main(String... args) throws Exception {
        //Starting the client
        String serverIp;
        if (args.length == 1) {
            serverIp = args[0];
            System.out.println("With argument : " + serverIp);
        }
        else {
            serverIp = "172.28.0.253";
            System.out.println("Without argument : " + serverIp);
        }
        //String serverIp = args.length == 1 ? args[0] : "127.0.0.253";
        startClient(serverIp);

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
            if (i != nbGames) {
                System.out.printf("[7WONDERS - LAMAC] Progress : %d / %d.\r", i, nbGames);
            } else {
                System.out.printf("[7WONDERS - LAMAC] Execution finished : %d games played.\n", nbGames);
            }

            winsCount.set(winner, winsCount.get(winner) + 1);
        }

        for (int i = 0; i < winsCount.size(); i++) {
            System.out.print("[7WONDERS - LAMAC] player.Player playing stratégie " + playerList.get(i).getStrategyName() + " wins " + winsCount.get(i) + " times\n");
        }
        client.stop();
        System.exit(0);
    }

    public static List<Player> fetchPlayers(int nbPlayers) {
        List<Player> playerList = new ArrayList<>(nbPlayers);

        Player player1 = new Player(playerList.size(), new RuleBasedAI());
        playerList.add(player1);
        Player player2 = new Player(playerList.size(), new RuleBasedAI());
        playerList.add(player2);
        Player player3 = new Player(playerList.size(), new RuleBasedAI());
        playerList.add(player3);



        for (int i = playerList.size(); i < nbPlayers; i++) {
            Player player = new Player(i, new FirstCardStrategy());
            playerList.add(player);
        }
        return playerList;
    }

    public static void startClient(String ip) throws Exception {
        client = new Client("http://" + ip + ":10101");
        client.start();
    }

}
