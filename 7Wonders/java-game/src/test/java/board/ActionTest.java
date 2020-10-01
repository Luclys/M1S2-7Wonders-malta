package board;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class ActionTest {
    @Test
    public void generetePlayers() {
        int nbPlayers = 7;

        Board board = new Board(nbPlayers);
        ArrayList<Player> playerList = board.getPlayerList();

        int playersCount = playerList.size();
        assertEquals(7, playersCount);

        Player firstPlayer = playerList.get(0);
        Player secondPlayer = playerList.get(1);
        Player lastPlayer = playerList.get(nbPlayers - 1);

        // We test the neighborhood tor to the left then to the right
        assertSame(playerList.get(firstPlayer.getLeftNeighborId()), lastPlayer);
        assertSame(playerList.get(lastPlayer.getRightNeighborId()), firstPlayer);

        // We test the left neighbor then the right
        assertSame(playerList.get(secondPlayer.getLeftNeighborId()), firstPlayer);
        assertSame(playerList.get(firstPlayer.getRightNeighborId()), secondPlayer);
    }

    @Test
    public void initiateCardsTest() {
        int nbPlayers = 3;
        Board board = new Board(nbPlayers);
        int resSize = board.getAction().initiateCards(nbPlayers).size();
        assertEquals(7 * nbPlayers, resSize);
    }
}
