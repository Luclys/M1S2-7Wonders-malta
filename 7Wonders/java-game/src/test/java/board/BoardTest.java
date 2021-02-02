package board;

import gameelements.Inventory;
import gameelements.Player;
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
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class BoardTest {

    List<Player> playerList;
    Board board;
    @Mock
    Player player;
    Board boardMOCK;
    ArrayList<Inventory> listInv;
    Inventory inv;

    @BeforeEach
    void setUp() {
        playerList = new ArrayList<>(3);
        for (int i = 0; i < 3; i++) {
            //Player player = new Player(i, new WonderStrategy());
            playerList.add(player);
        }
        board = new Board(playerList, false);
    }

    @Test
    void drawCardsTest() {
        int nbPlayers = 3;
        Board board = new Board(playerList, false);
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
        Board board = new Board(playerList, false);
        Player player = playerList.get(0);
        Inventory inv = board.getPlayerInventoryList().get(0);
        Inventory leftNeighborInv = board.getPlayerInventoryList().get(player.getLeftNeighborId());
        Inventory rightNeighborInv = board.getPlayerInventoryList().get(player.getRightNeighborId());

        List<Step> listSteps = new ArrayList<>();
        listSteps.add(new Step(null, new ResourceEffect(Resource.BOIS, 1)));
        listSteps.add(new Step(null, new ResourceEffect(Resource.PIERRE, 2)));
        listSteps.add(new Step(null, new SymbolEffect(Symbol.STELE, 1)));
        WonderBoard TESTBOARD = new WonderBoard("TEST", new ResourceEffect(Resource.BOIS, 1), listSteps);

        TESTBOARD.claimBoard(player, inv);

        assertEquals(1, inv.getResCount(Resource.BOIS));

        // We claim a test Board, then test if when buying a step, we get the resource.
        Card card = CardsSet.CHANTIER;
        TESTBOARD.buyNextStep(player, card, leftNeighborInv, rightNeighborInv);

        assertEquals(2, inv.getResCount(Resource.BOIS));

        TESTBOARD.buyNextStep(player, card, leftNeighborInv, rightNeighborInv);
        assertEquals(2, inv.getResCount(Resource.PIERRE));

        TESTBOARD.buyNextStep(player, card, leftNeighborInv, rightNeighborInv);
        assertEquals(1, inv.getSymbolCount(Symbol.STELE));

        Assertions.assertThrows(Exception.class, () -> TESTBOARD.buyNextStep(player, card, leftNeighborInv, rightNeighborInv));
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
        assertEquals(1, board.getPlayerInventoryList().get(0).getPossibleFreeBuildings());
        doReturn(Action.BUILDFREE).when(player).getAction();
        board.executePlayerAction(inv, player);
        assertEquals(-1, board.getPlayerInventoryList().get(0).getPossibleFreeBuildings());
    }
}


