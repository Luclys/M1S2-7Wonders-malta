package board;

import gameelements.Inventory;
import gameelements.enums.Resource;

import java.util.ArrayList;

public class Trade {

    protected boolean saleResources(ArrayList<Resource> missingResources, Inventory playerInv, Inventory rightNeighborInv, Inventory leftNeighborInv) {
        boolean result = false;
        Inventory[] playersWithResources = new Inventory[10];
        Inventory neighbor;
        int k = 0;
        for (Resource r : missingResources) {// check if the player has enough coins to buy resource
            if (r != null) {
                if (playerInv.getCoins() - (2 * k) > 1) {
                    neighbor = findSeller(r, rightNeighborInv, leftNeighborInv);
                    if (neighbor == null) {// check if one of the neigbor has the resource
                        break;
                    } else {
                        playersWithResources[k] = neighbor;
                        k++;
                    }
                }
            } else {
                break;
            }
        }
        if (k == 10) {// neighbors have all the missing resources
            for (int i = 0; i < 10; i++) {// buy  resources from neighbors
                buyFromNeighbor(playerInv, playersWithResources[i]);
            }
            result = true;
        }
        return result;
    }

    protected void buyFromNeighbor(Inventory playerInv, Inventory neighborInv) {// add that the neighbor can't use the adding coins till next turn
        neighborInv.addCoins(2);
        playerInv.removeCoins(2);
    }

    protected Inventory findSeller(Resource missingResource, Inventory rightNeighborInv, Inventory leftNeighborInv) {// check price left and price right if the player can buy from both neighbor
        Inventory neighborInv = null;
        if (rightNeighborInv.getAvailableResources()[missingResource.getIndex()] > 0) {
            neighborInv = rightNeighborInv;
        } else {
            if (leftNeighborInv.getAvailableResources()[missingResource.getIndex()] > 0) {
                neighborInv = leftNeighborInv;
            }
        }
        return neighborInv;
    }
}
