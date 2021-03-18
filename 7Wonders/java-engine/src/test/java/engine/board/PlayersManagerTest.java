package engine.board;

import gameelements.Inventory;
import gameelements.cards.Card;
import gameelements.cards.CardsSet;
import gameelements.enums.Symbol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import statistic.DetailedResults;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayersManagerTest {

    private PlayersManager playersManager;

    @BeforeEach
    void setUp() {
        playersManager = new PlayersManager();
        for (int i = 0; i < 3; i++) {
            Inventory inv = new Inventory(i);
            inv.setDetailedResults(new DetailedResults());
            playersManager.playerInventoryList.add(inv);
        }
    }

    @Test
    void updateCoinsTest() {
        assertEquals(0, playersManager.playerInventoryList.get(0).getAddedCoins());
        assertEquals(3, playersManager.playerInventoryList.get(0).getCoins());
        playersManager.playerInventoryList.get(0).setAddedCoins(2);
        assertEquals(2, playersManager.playerInventoryList.get(0).getAddedCoins());
        assertEquals(3, playersManager.playerInventoryList.get(0).getCoins());
        playersManager.updateCoins();
        assertEquals(0, playersManager.playerInventoryList.get(0).getAddedCoins());
        assertEquals(5, playersManager.playerInventoryList.get(0).getCoins());
    }

    @Test
    void fightWithNeighborTest() {
        Inventory inv = playersManager.playerInventoryList.get(0);
        Inventory invNeighbor = playersManager.playerInventoryList.get(2);
        inv.getAvailableSymbols()[Symbol.BOUCLIER.getIndex()]++;
        int victoryPoint = inv.getVictoryChipsScore();
        playersManager.fightWithNeighbor(inv, invNeighbor, 1);
        assertEquals(victoryPoint + 1, inv.getVictoryChipsScore());
        invNeighbor.getAvailableSymbols()[Symbol.BOUCLIER.getIndex()]++;
        invNeighbor.getAvailableSymbols()[Symbol.BOUCLIER.getIndex()]++;
        int defeatJetonsCount = inv.getDefeatChipsCount();
        playersManager.fightWithNeighbor(inv, invNeighbor, 5);
        assertEquals(defeatJetonsCount + 1, inv.getDefeatChipsCount());
    }

    //TODO
/*
    @Test
    void associateNeighborTest() {
        List<Inventory> inventoryListList = playersManager.associateNeighbor(playersManager.playerList);
        int playersCount = inventoryListList.size();
        assertEquals(3, playersCount);

        Inventory firstPlayer = inventoryListList.get(0);
        Inventory secondPlayer = inventoryListList.get(1);
        Inventory lastPlayer = inventoryListList.get(playersCount - 1);

        // We test the neighborhood tor to the left then to the right
        assertSame(inventoryListList.get(firstPlayer.getLeftNeighborId()), lastPlayer);
        assertSame(inventoryListList.get(lastPlayer.getRightNeighborId()), firstPlayer);

        // We test the left neighbor then the right
        assertSame(inventoryListList.get(secondPlayer.getLeftNeighborId()), firstPlayer);
        assertSame(inventoryListList.get(firstPlayer.getRightNeighborId()), secondPlayer);
    }
*/
    @Test
    void freeBuildFromDiscardedTest() {
        int possibleFree = playersManager.playerInventoryList.get(1).getPossibleFreeDiscardedBuildingsCount();
        playersManager.getPlayerInventoryList().get(1).setPossibleFreeDiscardedBuildingsCount(1);
        List<Card> cards = new ArrayList<>();
        cards.add(CardsSet.PRETEUR_SUR_GAGES);
        playersManager.freeBuildFromDiscarded(cards);
        assertEquals(possibleFree, playersManager.playerInventoryList.get(1).getPossibleFreeDiscardedBuildingsCount());
    }
}
