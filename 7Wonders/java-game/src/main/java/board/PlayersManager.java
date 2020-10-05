package board;

import gameelements.Card;
import gameelements.Effect;
import gameelements.Inventory;
import gameelements.effects.*;
import gameelements.enums.Resource;
import gameelements.enums.Symbol;
import gameelements.wonders.Step;
import gameelements.wonders.WonderBoard;

import java.util.ArrayList;

public class PlayersManager {
    ArrayList<Player> playerList;
    ArrayList<Inventory> playerInventoryList;

    protected void fightWithNeighbor(Inventory invPlayer, Inventory invNeighbor, int conflictPoints) { // conflictPoints depends on Age
        int playerBoucliersCount = invPlayer.getSymbCount(Symbol.BOUCLIER);
        int neighborBoucliersCount = invNeighbor.getSymbCount(Symbol.BOUCLIER);
        //System.out.println("[RESOLVING WAR CONFLICTS] Player has " + playerBoucliersCount + " boucliers while neighbor has " + neighborBoucliersCount);
        if (playerBoucliersCount > neighborBoucliersCount) {
            invPlayer.updateConflictPoints(conflictPoints);
            //System.out.println("[RESOLVING WAR CONFLICTS] " + conflictPoints + " conflict points added");
        } else if (playerBoucliersCount < neighborBoucliersCount) {
            invPlayer.updateConflictPoints(-1);
            //System.out.println("[RESOLVING WAR CONFLICTS] Defeat jeton (-1 conflict point) added");
        }
        //System.out.println("[RESOLVING WAR CONFLICTS] Total player conflict points: " + this.getConflictPoints());
    }

    protected ArrayList<Player> generatePlayers(int nbPlayers) {
        playerList = new ArrayList<>(nbPlayers);
        playerInventoryList = new ArrayList<>(nbPlayers);
        for (int i = 0; i < nbPlayers; i++) {
            Player player = new Player(i);
            Inventory inv = new Inventory(i);
            // To make a sure we bind the first's left to last id
            if (i == 0) {
                player.setLeftNeighborId(nbPlayers - 1);
            } else {
                player.setLeftNeighborId(i - 1);
            }
            // To make a sure we bind the last's right to first id
            if (i == nbPlayers - 1) {
                player.setRightNeighborId(0);
            } else {
                player.setRightNeighborId(i + 1);
            }
            playerList.add(player);
            playerInventoryList.add(inv);
        }
        return playerList;
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
