package board;

import gameelements.Player;
import gameelements.strategy.FirstCardStrategy;
import gameelements.strategy.ResourceStrategy;

import java.util.ArrayList;

public class SevenWondersLauncher {
    static int NB_PLAYERS = 3;
    static int NB_GAMES = 1;
    static boolean BOOL_PRINT = true;


    public static void main(String[] args) {
        //Default settings
        int nbPlayers = NB_PLAYERS; //Default = 3
        int nbGames = NB_GAMES; //Default = 1
        boolean boolPrint = BOOL_PRINT; //Default = true

        //Maven's arguments
        if (args.length >= 3) {
            nbPlayers = Integer.parseInt(args[0]);
            nbGames = Integer.parseInt(args[1]);
            boolPrint = Boolean.parseBoolean(args[2]);
        }

        ArrayList<Player> playerList = fetchPlayers(nbPlayers);

        Board board = new Board(playerList, boolPrint);
        for (int i = 1; i <= nbGames; i++) {
            board.play();
        }
    }

    private static ArrayList<Player> fetchPlayers(int nbPlayers) {
        ArrayList<Player> playerList = new ArrayList<>(nbPlayers);
        for (int i = 0; i < nbPlayers-1; i++) {
            Player player = new Player(i, new FirstCardStrategy());
            playerList.add(player);
        }
    Player player = new Player(nbPlayers-1, new ResourceStrategy());
        playerList.add(player);
        return playerList;
    }

}
