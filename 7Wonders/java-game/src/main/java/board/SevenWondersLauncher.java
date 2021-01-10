package board;

import client.Client;
import gameelements.GameLogger;
import gameelements.Player;
import strategy.MonteCarloStrategy;
import strategy.RandomStrategy;

import java.util.ArrayList;
import java.util.List;

public class SevenWondersLauncher {
    private  static final GameLogger log = new GameLogger(true);
    public static  Client client;
    static int nbPlayers = 3;
    static int nbGames = 1;
    static boolean boolPrint = false;
    static Board board;

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
            board = new Board(playerList, boolPrint);
            board.getPlayerList().get(nbPlayers-1).setStrategy(new MonteCarloStrategy());
            board.getPlayerList().get(nbPlayers-2).setStrategy(new RandomStrategy());
            int winner = board.play(i);
            winsCount.set(winner, winsCount.get(winner)+1);
            if (i != nbGames) {
                log.display("[7WONDERS - LAMAC] Progress :" + i+"/"+ nbGames+"\r");
            } else {
                log.display("[7WONDERS - LAMAC] Execution finished : "+nbGames+" games played.\n");
            }
        }
        for (int i = 0; i < winsCount.size(); i++) {
            log.display("[7WONDERS - LAMAC] Player "+ i+" wins " +  winsCount.get(i)+" times\n");
        }
        client.showStats();
    }

    public static List<Player> fetchPlayers(int nbPlayers) {
        List<Player> playerList = new ArrayList<>(nbPlayers);
        for (int i = 0; i < nbPlayers ; i++) {
            Player player = new Player(i);
            playerList.add(player);
        }
        return playerList;
    }

    public static void startClient() throws Exception {
        client = new Client("http://127.0.0.1:10101");
        client.start();
    }

}
