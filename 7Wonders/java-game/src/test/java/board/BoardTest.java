package board;

import gameelements.Card;
import gameelements.CardsSet;
import gameelements.Inventory;
import gameelements.effects.ResourceEffect;
import gameelements.effects.SymbolEffect;
import gameelements.enums.Resource;
import gameelements.enums.Symbol;
import gameelements.wonders.Step;
import gameelements.wonders.WonderBoard;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class BoardTest {
    @Test
    public void playTest() {
        Board board = new Board(3, false);
        board.play();
        assertEquals(6, board.getTurn());
    }

    @Test
    public void testInitializedDeckCardList() {
        int nbPlayers = 7;
        Board board = new Board(nbPlayers, false);
        int deckCardsCount = board.getCurrentDeckCardList().size();
        assertEquals(nbPlayers * 7, deckCardsCount);
    }

    @Test
    public void drawCardsTest() {
        int nbPlayers = 3;
        Board board = new Board(nbPlayers, false);

        int nbToDraw = 1;
        ArrayList<Card> listBeforeDrawing = (ArrayList<Card>) board.getCurrentDeckCardList().clone();
        ArrayList<Card> card = board.drawCards(nbToDraw);
        ArrayList<Card> listAfterDrawing = (ArrayList<Card>) board.getCurrentDeckCardList().clone();

        assertEquals(listBeforeDrawing.size() - nbToDraw, listAfterDrawing.size());
        assertSame(listBeforeDrawing.get(0), card.get(0));
    }

    @Test
    void claimBoard() {
        // We claim a test Board, then test if we got the base resource.
        Board board = new Board(3, false);
        Inventory inv = board.getPlayerInventoryList().get(0);

        ArrayList<Step> listSteps = new ArrayList<>();
        listSteps.add(new Step(null, new ResourceEffect("", Resource.BOIS, 1)));
        listSteps.add(new Step(null, new ResourceEffect("", Resource.PIERRE, 2)));
        listSteps.add(new Step(null, new SymbolEffect("", Symbol.STELE, 1)));
        WonderBoard TESTBOARD = new WonderBoard("TEST", new ResourceEffect("", Resource.BOIS, 1), listSteps);

        TESTBOARD.claimBoard(inv);

        assertEquals(1, inv.getResCount(Resource.BOIS));

        // We claim a test Board, then test if when buying a step, we get the resource.
        Card card = CardsSet.CHANTIER;
        TESTBOARD.buyNextStep(card);

        assertEquals(2, inv.getResCount(Resource.BOIS));

        TESTBOARD.buyNextStep(card);
        assertEquals(2, inv.getResCount(Resource.PIERRE));

        TESTBOARD.buyNextStep(card);
        assertEquals(1, inv.getSymbCount(Symbol.STELE));

        //assertThrows(Error, TESTBOARD.buyNextStep(card));
    }
}


