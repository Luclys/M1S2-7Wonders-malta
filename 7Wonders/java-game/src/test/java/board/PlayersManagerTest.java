package board;

import gameelements.Inventory;
import gameelements.Player;
import gameelements.cards.Card;
import gameelements.cards.CardsSet;
import gameelements.enums.Symbol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

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

    @Test
    void freeBuildFromDiscardedTest() {
        int possibleFree = playersManager.playerInventoryList.get(1).getPossibleFreeDiscardedBuildingsCount();
        playersManager.getPlayerInventoryList().get(1).setPossibleFreeDiscardedBuildingsCount(1);
        List<Card> cards = new ArrayList<>();
        cards.add(CardsSet.PRETEUR_SUR_GAGES);
        playersManager.freeBuildFromDiscarded(cards);
        assertEquals(possibleFree, playersManager.playerInventoryList.get(1).getPossibleFreeDiscardedBuildingsCount());
        /*        for (Inventory inv : playerInventoryList) {
            if (discardedDeckCardList.isEmpty()) {
                return;
            }
            if (inv.getPossibleFreeDiscardedBuildingsCount() != 0) {
                Player player = playerList.get(inv.getPlayerId());
                Card card = player.chooseDiscardedCardToBuild(new Inventory(inv),discardedDeckCardList);
                inv.updateInventory(card, player, playerInventoryList.get(player.getLeftNeighborId()), playerInventoryList.get(player.getRightNeighborId()));
                inv.addPossibleFreeDiscardedBuildingsCount(-1);
            }
        }*/


    }
}
