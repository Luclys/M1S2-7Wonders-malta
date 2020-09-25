package board;
import org.junit.jupiter.api.Test;
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
        Board board = new Board(10);
        int playersCount = board.getPlayerList().size();
        assertEquals(10, playersCount);
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


