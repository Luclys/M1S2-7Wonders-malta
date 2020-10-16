package board;

import gameelements.Player;

import java.util.ArrayList;

public class SevenWondersLauncher {
    static int nbPlayers = 3;
    static int nbGames = 1;
    static boolean boolPrint = true;


    public static void main(String[] args) {
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
        for (int i = 0; i < nbPlayers; i++) {
            Player player = new Player(i);
            playerList.add(player);
        }
        return playerList;
    }

}
