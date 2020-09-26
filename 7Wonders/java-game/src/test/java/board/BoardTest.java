package board;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    @Test
    public void playTest() {
        Board board = new Board(1);
        board.play();
        assertEquals(6, board.getTour());
    }

    @Test
    public void testInitializedPlayerList() {
        int nbPlayers = 10;

        Board board = new Board(nbPlayers);
        ArrayList<Player>  playerList = board.getPlayerList();

        int playersCount = playerList.size();
        assertEquals(10, playersCount);

        Player firstPlayer = playerList.get(0);
        Player secondPlayer = playerList.get(1);
        Player lastPlayer = playerList.get(nbPlayers-1);

        // We test the neighborhood tor to the left then to the right
        assertSame(playerList.get(firstPlayer.getLeftNeighbor()), lastPlayer);
        assertSame(playerList.get(lastPlayer.getRightNeighbor()), firstPlayer);

        // We test the left neighbor then the right
        assertSame(playerList.get(secondPlayer.getLeftNeighbor()), firstPlayer);
        assertSame(playerList.get(firstPlayer.getRightNeighbor()), secondPlayer);
    }

    @Test
    public void testInitializedDeckCardList() {
        Board board = new Board(10);
        int deckCardsCount = board.getCurrentDeckCardList().size();
        assertEquals(7, deckCardsCount);
    }

    @Test
    public void initiateCardsTest() {
       Board board = new Board(1);
       int resSize = board.initiateCards(5).size();
       assertEquals(7, resSize);
    }
}


