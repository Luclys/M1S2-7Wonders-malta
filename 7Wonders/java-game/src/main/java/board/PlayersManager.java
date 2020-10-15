package board;

import gameelements.Inventory;
import gameelements.Player;
import gameelements.SoutConsole;
import gameelements.cards.Card;
import gameelements.enums.Resource;
import gameelements.enums.Symbol;

import java.util.ArrayList;

public class PlayersManager {
    ArrayList<Player> playerList;
    ArrayList<Inventory> playerInventoryList;
    SoutConsole sout;

    public PlayersManager(SoutConsole sout) {
        this.sout = sout;
    }

    public PlayersManager() {
        this.sout = new SoutConsole(false);
        playerList = new ArrayList<>();
        playerInventoryList = new ArrayList<>();
    }

    protected void updateCoins() {
        for (Inventory inv : playerInventoryList) {
            inv.addCoins(inv.getAddedCoins());
            inv.setAddedCoins(0);
        }
    }

    protected void freeBuildFromDiscarded(ArrayList<Card> discardedDeckCardList) {
        for (Inventory inv : playerInventoryList) {
            if (discardedDeckCardList.size() == 0) {
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
        int playerBoucliersCount = invPlayer.getSymbCount(Symbol.BOUCLIER);
        int neighborBoucliersCount = invNeighbor.getSymbCount(Symbol.BOUCLIER);
        sout.conflicts(invPlayer, invNeighbor);
        sout.checkShields(playerBoucliersCount, neighborBoucliersCount);
        if (playerBoucliersCount > neighborBoucliersCount) {
            invPlayer.addVictoryJetonsScore(victoryJetonValue);
            sout.addConflictsPoint(victoryJetonValue);
        } else if (playerBoucliersCount < neighborBoucliersCount) {
            invPlayer.addDefeatJeton();
            sout.defeatChip();
        }
        sout.resolvedConflicts(invPlayer);
    }

    protected ArrayList<Player> associateNeighbor(ArrayList<Player> players) {
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

    public ArrayList<Inventory> getPlayerInventoryList() {
        return playerInventoryList;
    }

    protected ArrayList<Resource> missingResources(Inventory inv, Card c) {
        ArrayList<Resource> missing = new ArrayList<>();
        if (c.getRequiredResources() == null) {
            return null;
        }
        for (Resource r : c.getRequiredResources()) {
            if (inv.getAvailableResources()[r.getIndex()] == 0) {
                missing.add(r);
            }
        }
        return missing;
    }
}
