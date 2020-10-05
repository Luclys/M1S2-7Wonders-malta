package board;

import gameelements.Inventory;
import gameelements.enums.Resource;

import java.util.ArrayList;

public class Trade {

    protected boolean saleResources(ArrayList<Resource> missingResources, Inventory playerInv, Inventory rightNeighborInv, Inventory leftNeighborInv) {
        boolean result = false;
        ArrayList<Inventory> playersWithResources = new ArrayList<Inventory>();
        Inventory neighbor;
        int k = 0;
        for (Resource r : missingResources) {// check if the player has enough coins to buy resource
            if (r != null) {
                if (playerInv.getCoins() - (2 * k) > 1) {
                    neighbor = findSeller(r, rightNeighborInv, leftNeighborInv);
                    if (neighbor == null) {// check if one of the neigbor has the resource
                        break;
                    } else {
                        playersWithResources.add(neighbor);
                        k++;
                    }
                }
            } else {
                break;
            }
        }

        if (k == missingResources.size()) {// neighbors have all the missing resources
            boolean right;
            for(Inventory inv :playersWithResources ){
                if(inv.equals(rightNeighborInv)){
                    right = true;
                }else{
                    right = false;
                }
                buyFromNeighbor(playerInv, inv, right);
            }
            result = true;
        }
        return result;
    }

    protected void buyFromNeighbor(Inventory playerInv, Inventory neighborInv, Boolean rightNeighbor) {// add that the neighbor can't use the adding coins till next turn
        int price = 2;
        //check which neighbor to determinate price
      /*  if(rightNeighbor){
            price = rightPrice
        }else{
            price = leftPrice
        }*/
        neighborInv.setAddedCoins(price);
        playerInv.removeCoins(price);
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
