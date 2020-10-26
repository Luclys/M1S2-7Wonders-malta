package board;

import client.Client;
import gameelements.Player;
import gameelements.strategy.WonderStrategy;
import server.Server;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SevenWondersLauncher {
    private final static Logger log = Logger.getLogger(SevenWondersLauncher.class.getName());
    static Client client;
    static int nbPlayers = 3;
    static int nbGames = 1000;
    static boolean boolPrint = false;

    public static void main(String[] args) {
        //Starting the server
        //startServer();

        //Starting the client
        client = new Client("http://127.0.0.1:10101");
        client.start();

        //Maven's arguments
        if (args.length >= 3) {
            nbPlayers = Integer.parseInt(args[0]);
            nbGames = Integer.parseInt(args[1]);
            boolPrint = Boolean.parseBoolean(args[2]);
        }

        List<Player> playerList = fetchPlayers(nbPlayers);

        for (int i = 1; i <= nbGames; i++) {
            Board board = new Board(playerList, boolPrint);
            board.play(i);
            if (i != nbGames) {
                log.info(String.format("[7WONDERS - LAMAC] Progress : %d / %d.\r", i, nbGames));
            } else {
                log.info(String.format("[7WONDERS - LAMAC] Execution finished : %d games played.\n", nbGames));
            }
        }
    }

    public static List<Player> fetchPlayers(int nbPlayers) {
        List<Player> playerList = new ArrayList<>(nbPlayers);
        for (int i = 0; i < nbPlayers; i++) {
            Player player = new Player(i, new WonderStrategy());
            playerList.add(player);
        }
        return playerList;
    }

    private static void startServer() {
        log.info("Starting the server...\n");
        Thread server = new Thread(new Runnable() {
            @Override
            public void run() {
                Server.main(null);
            }
        });

        server.start();
    }

    public static void startClient() {
        client = new Client("http://127.0.0.1:10101");
        client.start();
    }

}
