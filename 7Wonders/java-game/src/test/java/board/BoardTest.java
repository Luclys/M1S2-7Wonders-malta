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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class BoardTest {
    ArrayList<Player> playerList;
    Board board;
    @BeforeEach
    public void setUp() {
        playerList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Player player = new Player(i);
            playerList.add(player);
        }
        board = new Board(playerList, false);
    }

    @Test
    public void playTest() {
        board.play();
        assertEquals(6, board.getTurn());
    }

    @Test
    public void drawCardsTest() {
        board.ageSetUp(1);

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
        Inventory inv = board.getPlayerInventoryList().get(0);

        ArrayList<Step> listSteps = new ArrayList<>();
        listSteps.add(new Step(null, new ResourceEffect(Resource.BOIS, 1)));
        listSteps.add(new Step(null, new ResourceEffect(Resource.PIERRE, 2)));
        listSteps.add(new Step(null, new SymbolEffect(Symbol.STELE, 1)));
        WonderBoard TESTBOARD = new WonderBoard("TEST", new ResourceEffect(Resource.BOIS, 1), listSteps);

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


