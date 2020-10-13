package board;

import gameelements.Inventory;
import gameelements.enums.Resource;

import java.util.ArrayList;

public class Trade {
    SoutConsole sout;

    public Trade(SoutConsole sout) {
        this.sout = sout;
    }

    protected boolean buyResources(ArrayList<Resource> missingResources, Inventory playerInv, Inventory rightNeighborInv, Inventory leftNeighborInv) {
        boolean result = false;
        ArrayList<Inventory> playersWithResources = new ArrayList<Inventory>();
        Inventory neighbor;
        int k = 0;
        sout.display("Check if player "+playerInv.getPlayerId()+ " has enough coins .");
        sout.display("Search for neighbors whom have the missing resources ");

        for (Resource r : missingResources) {// check if the player has enough coins to buy resource
            if (r != null) {

                if (playerInv.getCoins() - (2 * k) > 1) {
                    neighbor = findSeller(r, rightNeighborInv, leftNeighborInv);
                    if (neighbor == null) {// check if one of the neigbor has the resource
                        break;
                    } else {
                        sout.display("Neighbor "+neighbor.getPlayerId()+" can sell "+ r +" to player "+playerInv.getPlayerId());
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
            sout.display("The player can buy all missing resources");
            for(Inventory inv :playersWithResources ){
                right = inv.equals(rightNeighborInv);
                buyFromNeighbor(playerInv, inv, right);
            }
            result = true;
        }else{
            sout.display("Player "+playerInv.getPlayerId()+ "can't buy all resources");
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
        sout.display("Player "+playerInv.getPlayerId()+"buy from his");
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
