package board;

import gameelements.Inventory;
import gameelements.Player;
import gameelements.cards.CardsSet;
import gameelements.enums.Resource;
import gameelements.enums.Symbol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayersManagerTest {

    private PlayersManager playersManager;

    @BeforeEach
     void setUp() {
        playersManager = new PlayersManager();
        for (int i = 0; i < 3; i++) {
            playersManager.playerList.add(new Player(i));
            playersManager.playerInventoryList.add(new Inventory(i));
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

    @Test
     void associateNeighborTest() {
        List<Player> playerList = playersManager.associateNeighbor(playersManager.playerList);
        int playersCount = playerList.size();
        assertEquals(3, playersCount);

        Player firstPlayer = playerList.get(0);
        Player secondPlayer = playerList.get(1);
        Player lastPlayer = playerList.get(playersCount - 1);

        // We test the neighborhood tor to the left then to the right
        assertSame(playerList.get(firstPlayer.getLeftNeighborId()), lastPlayer);
        assertSame(playerList.get(lastPlayer.getRightNeighborId()), firstPlayer);

        // We test the left neighbor then the right
        assertSame(playerList.get(secondPlayer.getLeftNeighborId()), firstPlayer);
        assertSame(playerList.get(firstPlayer.getRightNeighborId()), secondPlayer);
    }
}
