package board;

import gameelements.Resource;

public class Trade {
    String outputText;
    public Trade(){
        outputText = "";
    }
    protected boolean saleResources(Resource[] missingResources,Player p,Player rightNeighbor, Player leftNeighbor ) {
        boolean result = false;
        Player[] playersWithResources = new Player[4];
        Player neighbor = null;
        int k = 0;
        for (Resource r : missingResources) {// check if the player has enough coins to buy resource
            if (r != null) {
                if (p.getCoins() - (2 * k) > 1) {
                    neighbor = findSaler(r, rightNeighbor,leftNeighbor);
                    if (neighbor == null) {// check if one of the neigbor has the resource
                        break;
                    } else {
                        System.out.println("*the player  can buy the resource " + r);
                        playersWithResources[k] = neighbor;
                        k++;
                    }
                }
            } else {
                break;
            }
        }
        if (k == 4) {// neighbors have all the missing resources
            for (int i = 0; i < 4; i++) {// buy  resources from neighbors
                buyFromNeighbor(p, playersWithResources[i]);
            }
            result = true;
        }
        return result;
    }
    protected void buyFromNeighbor(Player p, Player neighbor) {// add that the neighbor can't use the adding coins till next turn
        neighbor.addCoins(2);
        p.removeCoins(2);
    }

    protected Player findSaler(Resource missingResource, Player rightNeighbor, Player leftNeighbor  ) {// check price left and price right if the player can buy from both neighbor
        Player neighbor = null;
        outputText ="";
        if (rightNeighbor.getAvailableResources()[missingResource.getIndex()] > 0) {
            outputText = " from the right neighbor the resource " + missingResource;
            neighbor = leftNeighbor;
        } else {
            if (leftNeighbor.getAvailableResources()[missingResource.getIndex()] > 0) {
                outputText += " from the left neighbor the resource " + missingResource;
                neighbor = rightNeighbor;
            }
        }
        System.out.println(outputText);
        return neighbor;
    }
}
