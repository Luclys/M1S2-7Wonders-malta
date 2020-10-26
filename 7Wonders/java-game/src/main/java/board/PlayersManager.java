package board;

import gameelements.GameLogger;
import gameelements.Inventory;
import gameelements.Player;
import gameelements.cards.Card;
import gameelements.enums.Symbol;

import java.util.ArrayList;
import java.util.List;

public class PlayersManager {
    private final GameLogger log;
    List<Player> playerList;
    List<Inventory> playerInventoryList;

    public PlayersManager(GameLogger logger) {
        this.log = logger;
    }

    public PlayersManager() {
        this.log = new GameLogger(false);
        playerList = new ArrayList<>();
        playerInventoryList = new ArrayList<>();
    }

    protected void updateCoins() {
        for (Inventory inv : playerInventoryList) {
            inv.addCoins(inv.getAddedCoins());
            inv.setAddedCoins(0);
        }
    }

    protected void freeBuildFromDiscarded(List<Card> discardedDeckCardList) {
        for (Inventory inv : playerInventoryList) {
            if (discardedDeckCardList.isEmpty()) {
                return;
            }
            if (inv.getPossibleFreeDiscardedBuildingsCount() != 0) {
                Player player = playerList.get(inv.getPlayerId());
                Card card = player.chooseDiscardedCardToBuild(new Inventory(inv), discardedDeckCardList);
                inv.updateInventory(card, player, playerInventoryList.get(player.getLeftNeighborId()), playerInventoryList.get(player.getRightNeighborId()));
                inv.addPossibleFreeDiscardedBuildingsCount(-1);
            }
        }
    }

    protected void fightWithNeighbor(Inventory invPlayer, Inventory invNeighbor, int victoryJetonValue) { // victoryJetonValue depends on Age
        int playerBoucliersCount = invPlayer.getSymbolCount(Symbol.BOUCLIER);
        int neighborBoucliersCount = invNeighbor.getSymbolCount(Symbol.BOUCLIER);
        log.conflicts(invPlayer, invNeighbor);
        log.checkShields(playerBoucliersCount, neighborBoucliersCount);
        if (playerBoucliersCount > neighborBoucliersCount) {
            invPlayer.addVictoryJetonsScore(victoryJetonValue);
            log.addConflictsPoint(victoryJetonValue, invPlayer.getPlayerId());
        } else if (playerBoucliersCount < neighborBoucliersCount) {
            invPlayer.addDefeatJeton();
            log.defeatChip(invPlayer.getPlayerId());
        }
        log.resolvedConflicts(invPlayer);
    }

    protected List<Player> associateNeighbor(List<Player> players) {
        playerInventoryList = new ArrayList<>();
        for (int i = 0; i < players.size(); i++) {
            Inventory inv = new Inventory(i);
            // To make a sure we bind the first's left to last id
            if (i == 0) {
                players.get(i).setLeftNeighborId(players.size() - 1);
            } else {
                players.get(i).setLeftNeighborId(i - 1);
            }
            // To make a sure we bind the last's right to first id
            if (i == players.size() - 1) {
                players.get(i).setRightNeighborId(0);
            } else {
                players.get(i).setRightNeighborId(i + 1);
            }
            playerInventoryList.add(inv);
        }
        playerList = players;
        return players;
    }

    public List<Inventory> getPlayerInventoryList() {
        return playerInventoryList;
    }
}
