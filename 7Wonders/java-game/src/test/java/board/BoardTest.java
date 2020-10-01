package board;

import gameelements.Card;
import gameelements.enums.Resource;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class BoardTest {
    @Test
    public void playTest() {
        Board board = new Board(3);
        board.play();
        assertEquals(6, board.getTurn());
    }


    @Test
    public void testInitializedDeckCardList() {
        int nbPlayers = 7;
        Board board = new Board(nbPlayers);
        int deckCardsCount = board.getCurrentDeckCardList().size();
        assertEquals(nbPlayers * 7, deckCardsCount);
    }


    @Test
    public void drawCardsTest() {
        int nbPlayers = 3;
        Board board = new Board(nbPlayers);

        int nbToDraw = 1;
        ArrayList<Card> listBeforeDrawing = (ArrayList<Card>) board.getCurrentDeckCardList().clone();
        ArrayList<Card> card = board.drawCards(nbToDraw);
        ArrayList<Card> listAfterDrawing = (ArrayList<Card>) board.getCurrentDeckCardList().clone();

        assertEquals(listBeforeDrawing.size() - nbToDraw, listAfterDrawing.size());
        assertSame(listBeforeDrawing.get(0), card.get(0));
    }
}


