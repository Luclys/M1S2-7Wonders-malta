package board;

import effects.ResourceEffect;
import effects.SymbolEffect;
import gameelements.Card;
import gameelements.Inventory;
import gameelements.enums.Category;
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
        Card card = new Card("DUMMY", new ResourceEffect("", Resource.BOIS, 1), null, Category.MATIERE_PREMIERE);
        TESTBOARD.buyNextStep(card);

        assertEquals(2, inv.getResCount(Resource.BOIS));

        TESTBOARD.buyNextStep(card);
        assertEquals(2, inv.getResCount(Resource.PIERRE));

        TESTBOARD.buyNextStep(card);
        assertEquals(1, inv.getSymbCount(Symbol.STELE));

        //assertThrows(Error, TESTBOARD.buyNextStep(card));
    }

    /*@Test
    public void fightWithNeighborTest() {
        Card bouclierCard = new Card("BOUCLIER", new SymbolEffect("", Symbol.BOUCLIER, 1), null);
        Card boisCard = new Card("BOIS", new ResourceEffect("", Resource.BOIS, 1), null);

        Player neighbor = new Player(2);
        Inventory neighborInv = new Inventory(2);

        cards.set(0, bouclierCard);
        neighborInv.setCards(cards);
        neighborInv.updateInventory(neighbor.playCard(inv)); //Neighbor has 1 bouclier

        //Player has less boucliers than his neighbor
        player = new Player(3);
        addCardAndPlayIt(player, boisCard); //Player has no boucliers

        player.fightWithNeighbor(neighbor, 3);
        assertEquals(inv.getConflictPoints(), -1);

        //Player has same amount of boucliers than his neighbor
        player = new Player(4);
        addCardAndPlayIt(player, bouclierCard); //Player has 1 bouclier

        player.fightWithNeighbor(neighbor, 3);
        assertEquals(inv.getConflictPoints(), 0);

        //Player has more boucliers than his neighbor
        player = new Player(5);
        addCardAndPlayIt(player, bouclierCard); //Player has 1 bouclier
        addCardAndPlayIt(player, bouclierCard); //Player has 2 boucliers

        player.fightWithNeighbor(neighbor, 3);
        assertEquals(inv.getConflictPoints(), 3);
    }*/

}


