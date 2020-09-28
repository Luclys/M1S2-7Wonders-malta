package board;

import gameelements.Card;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    @Test
    public void playTest() {
        Board board = new Board(1);
        board.play();
        assertEquals(6, board.getTurn());
    }

    @Test
    public void testInitializedPlayerList() {
        int nbPlayers = 10;

        Board board = new Board(nbPlayers);
        ArrayList<Player> playerList = board.getPlayerList();

        int playersCount = playerList.size();
        assertEquals(10, playersCount);

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
    public void testInitializedDeckCardList() {
        int nbPlayers = 10;
        Board board = new Board(nbPlayers);
        int deckCardsCount = board.getCurrentDeckCardList().size();
        assertEquals(nbPlayers * 7, deckCardsCount);
    }

    @Test
    public void initiateCardsTest() {
        int nbPlayers = 1;
        Board board = new Board(nbPlayers);
        int resSize = board.initiateCards(nbPlayers).size();
        assertEquals(7, resSize);
    }

    @Test
    public void drawCardsTest() {
        int nbPlayers = 1;
        Board board = new Board(nbPlayers);

        int nbToDraw = 1;
        ArrayList<Card> listBeforeDrawing = (ArrayList<Card>) board.getCurrentDeckCardList().clone();
        ArrayList<Card> card = board.drawCards(nbToDraw);
        ArrayList<Card> listAfterDrawing = (ArrayList<Card>) board.getCurrentDeckCardList().clone();

        assertEquals(listBeforeDrawing.size() - nbToDraw, listAfterDrawing.size());
        assertSame(listBeforeDrawing.get(0), card.get(0));
    }
}


