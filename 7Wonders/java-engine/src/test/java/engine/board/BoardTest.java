package engine.board;

import gameelements.CardActionPair;
import gameelements.Inventory;
import gameelements.cards.Card;
import gameelements.cards.CardsSet;
import gameelements.effects.ResourceEffect;
import gameelements.effects.SymbolEffect;
import gameelements.enums.Action;
import gameelements.enums.Resource;
import gameelements.enums.Symbol;
import gameelements.wonders.Step;
import gameelements.wonders.WonderBoard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BoardTest {

    Board board;

    @BeforeEach
    void setUp() {
        HashMap<Integer, String> mapPlayerID_URL = new HashMap<>(3);
        for (int i = 0; i < 3; i++) {
            mapPlayerID_URL.put(mapPlayerID_URL.size(), "url" + i);
        }

        board = new Board(mapPlayerID_URL, false, null);
    }

    @Test
    void drawCardsTest() {
        board.ageSetUp(1);

        int nbToDraw = 1;
        List<Card> listBeforeDrawing = new ArrayList<>(board.getCurrentDeckCardList());
        List<Card> card = board.drawCards(nbToDraw);
        List<Card> listAfterDrawing = new ArrayList<>(board.getCurrentDeckCardList());

        assertEquals(listBeforeDrawing.size() - nbToDraw, listAfterDrawing.size());
        assertSame(listBeforeDrawing.get(0), card.get(0));
    }

    @Test
    void claimBoard() throws Exception {
        // We claim a test Board, then test if we got the base resource.
        Inventory inv = board.getPlayerInventoryList().get(0);
        Inventory leftNeighborInv = board.getPlayerInventoryList().get(inv.getLeftNeighborId());
        Inventory rightNeighborInv = board.getPlayerInventoryList().get(inv.getRightNeighborId());

        List<Step> listSteps = new ArrayList<>();
        listSteps.add(new Step(null, new ResourceEffect(Resource.BOIS, 1)));
        listSteps.add(new Step(null, new ResourceEffect(Resource.PIERRE, 2)));
        listSteps.add(new Step(null, new SymbolEffect(Symbol.STELE, 1)));
        WonderBoard TESTBOARD = new WonderBoard("TEST", new ResourceEffect(Resource.BOIS, 1), listSteps);

        TESTBOARD.claimBoard(inv);

        assertEquals(1, inv.getResCount(Resource.BOIS));

        // We claim a test Board, then test if when buying a step, we get the resource.
        Card card = CardsSet.CHANTIER;
        TESTBOARD.buyNextStep(card, inv, leftNeighborInv, rightNeighborInv);

        assertEquals(2, inv.getResCount(Resource.BOIS));

        TESTBOARD.buyNextStep(card, inv, leftNeighborInv, rightNeighborInv);
        assertEquals(2, inv.getResCount(Resource.PIERRE));

        TESTBOARD.buyNextStep(card, inv, leftNeighborInv, rightNeighborInv);
        assertEquals(1, inv.getSymbolCount(Symbol.STELE));

        Assertions.assertThrows(Exception.class, () -> TESTBOARD.buyNextStep(card, inv, leftNeighborInv, rightNeighborInv));
    }

    @Test
    void setAgeTest() {
        assertThrows(IllegalStateException.class, () -> board.ageSetUp(10));
        board.ageSetUp(1);
        assertTrue(board.isLeftRotation());
        board.ageSetUp(2);
        assertFalse(board.isLeftRotation());
        board.ageSetUp(3);
        assertTrue(board.isLeftRotation());
    }

    @Test
    void executeActionBUILDFREETest() throws Exception {
        Inventory inv = board.getPlayerInventoryList().get(0);
        inv.setPossibleFreeBuildings(1);
        ArrayList<Card> cardArrayList = new ArrayList<>();
        cardArrayList.add(CardsSet.CHANTIER);
        inv.setCardsInHand(cardArrayList);
        CardActionPair actionPair = new CardActionPair(inv.getCardsInHand().get(0), Action.BUILDFREE);

        assertEquals(1, inv.getPossibleFreeBuildings());
        board.executePlayerAction(inv, actionPair);
        assertEquals(-1, inv.getPossibleFreeBuildings());
    }
}
