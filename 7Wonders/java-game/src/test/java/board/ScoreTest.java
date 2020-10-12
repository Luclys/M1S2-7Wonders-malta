package board;

import gameelements.Inventory;
import gameelements.Player;
import gameelements.cards.Card;
import gameelements.cards.CardsSet;
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

public class ScoreTest {
    ArrayList<Player> playerList;

    @BeforeEach
    public void setUp() {
        playerList = new ArrayList<>(3);
        for (int i = 0; i < 3; i++) {
            Player player = new Player(i);
            playerList.add(player);
        }
    }

    @Test
    void claimBoard() {
        // We claim a test Board, then test if we got the base resource.
        Board board = new Board(playerList, false);
        Player player = playerList.get(0);
        Inventory inv = board.getPlayerInventoryList().get(0);
        Inventory leftNeighborInv = board.getPlayerInventoryList().get(player.getLeftNeighborId());
        Inventory rightNeighborInv = board.getPlayerInventoryList().get(player.getRightNeighborId());

        ArrayList<Step> listSteps = new ArrayList<>();
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
        assertEquals(1, inv.getSymbCount(Symbol.STELE));

        //assertThrows(Error, TESTBOARD.buyNextStep(card));
    }
}


