package board;

import client.Client;
import com.fasterxml.jackson.core.JsonProcessingException;
import gameelements.Player;
import strategy.Monte;
import strategy.WonderStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SevenWondersLauncher {
    private final static Logger log = Logger.getLogger(SevenWondersLauncher.class.getName());
    static Client client;
    static int nbPlayers = 3;
    static int nbGames = 1;
    static boolean boolPrint = false;
    static Board board;

    public static void main(String... args) throws InterruptedException, JsonProcessingException {
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
            board.getPlayerList().get(nbPlayers-1).setStrategy(new Monte());
            int winner = board.play(i);
            winsCount.set(winner, winsCount.get(winner)+1);
            if (i != nbGames) {
                System.out.printf("[7WONDERS - LAMAC] Progress : %d / %d.\r", i, nbGames);
            } else {
                System.out.printf("[7WONDERS - LAMAC] Execution finished : %d games played.\n", nbGames);
            }
        }
        for (int i = 0; i < winsCount.size(); i++) {
            System.out.printf("[7WONDERS - LAMAC] Player %d wins %d times", i, winsCount.get(i));
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

    public static void startClient() {
        client = new Client("http://127.0.0.1:10101");
        client.start();
    }

}
