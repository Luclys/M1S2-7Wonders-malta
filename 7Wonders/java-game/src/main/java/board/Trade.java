package board;

import gameelements.Inventory;
import gameelements.enums.Category;
import gameelements.enums.Resource;

import java.util.ArrayList;

public class Trade {
    SoutConsole sout;

    public Trade(SoutConsole sout) {
        this.sout = sout;
    }

    protected boolean saleResources(ArrayList<Resource> missingResources, Inventory playerInv, Inventory rightNeighborInv, Inventory leftNeighborInv) {
        boolean canBuyAllResources = true;
        int rightPrice;
        int leftPrice;
        int leftTotal = 0;
        int rightTotal = 0;
        Inventory neighbor;

        int [] rightAvailableResources = rightNeighborInv.getAvailableResources();
        int [] leftAvailableResources = leftNeighborInv.getAvailableResources();

        for (Resource missingResource : missingResources) {
            if (missingResource.getCategory().equals(Category.MATIERE_PREMIERE)) {
                rightPrice = playerInv.getMatieresPremieresPriceRight();
                leftPrice = playerInv.getMatieresPremieresPriceLeft();
            } else {
                rightPrice = playerInv.getProduitsManifacturesPrice();
                leftPrice = playerInv.getProduitsManifacturesPrice();
            }
            boolean rightHasResource = rightAvailableResources[missingResource.getIndex()] > 0;
            boolean leftHasResource = leftAvailableResources[missingResource.getIndex()] > 0;

            if (rightHasResource && leftHasResource) {
                if (leftPrice < rightPrice) {
                    if (playerInv.getCoins() - leftTotal - rightTotal - leftPrice >= 0) {
                        sout.display("Player can buy " + missingResource.toString() + " from left neighbor");
                        leftTotal += leftPrice;
                        leftAvailableResources[missingResource.getIndex()]--;
                    } else {
                        sout.display("Not enough coins to buy " + missingResource.toString() + " from left neighbor");
                        canBuyAllResources = false;
                        break;
                    }
                } else if (leftPrice > rightPrice) {
                    if (playerInv.getCoins() - leftTotal - rightTotal - rightPrice >= 0) {
                        sout.display("Player can buy " + missingResource.toString() + " from right neighbor");
                        rightTotal += rightPrice;
                        rightAvailableResources[missingResource.getIndex()]--;
                    } else {
                        sout.display("Not enough coins to buy " + missingResource.toString() + " from right neighbor");
                        canBuyAllResources = false;
                        break;
                    }
                } else {
                    if (playerInv.getCoins() - leftTotal - rightTotal  - rightPrice >= 0) {
                        neighbor = chooseNeighbor(rightNeighborInv, leftNeighborInv);
                        if (neighbor.equals(rightNeighborInv)) {
                            sout.display("Player can buy " + missingResource.toString() + " from right neighbor");
                            rightTotal += rightPrice;
                            rightAvailableResources[missingResource.getIndex()]--;
                        } else {
                            sout.display("Player can buy " + missingResource.toString() + " from left neighbor");
                            leftTotal += rightPrice;
                            leftAvailableResources[missingResource.getIndex()]--;
                        }
                    } else {
                        sout.display("Not enough coins to buy " + missingResource.toString() + " from any neighbor");
                        canBuyAllResources = false;
                        break;
                    }
                }
            } else if (rightHasResource) {
                if (playerInv.getCoins() - leftTotal - rightTotal - rightPrice >= 0) {
                    sout.display("Player can buy " + missingResource.toString() + " from right neighbor");
                    rightTotal += rightPrice;
                    rightAvailableResources[missingResource.getIndex()]--;
                } else {
                    sout.display("Not enough coins to buy " + missingResource.toString() + " from right neighbor");
                    canBuyAllResources = false;
                    break;
                }
            } else if (leftHasResource) {
                if (playerInv.getCoins() - leftTotal - rightTotal - leftPrice >= 0) {
                    sout.display("Player can buy " + missingResource.toString() + " from left neighbor");
                    leftTotal += leftPrice;
                    leftAvailableResources[missingResource.getIndex()]--;
                } else {
                    sout.display("Not enough coins to buy " + missingResource.toString() + " from left neighbor");
                    canBuyAllResources = false;
                    break;
                }
            } else {
                sout.display("None of the neighbors has " + missingResource.toString());
                canBuyAllResources = false;
                break;
            }
        }
        if (canBuyAllResources) {
            if (rightTotal > 0) {
                payToNeighbor(playerInv, rightNeighborInv, rightTotal);
            }
            if (leftTotal > 0) {
                payToNeighbor(playerInv, leftNeighborInv, leftTotal);
            }
        }
        return canBuyAllResources;
    }

    protected Inventory chooseNeighbor(Inventory rightNeighborInv, Inventory leftNeighborInv) {
        if (rightNeighborInv.getCoins() < leftNeighborInv.getCoins()) { //TODO: Maybe AI
           return rightNeighborInv;
        } else {
            return leftNeighborInv;
        }
    }

    protected void payToNeighbor(Inventory playerInv, Inventory neighborInv, int totalCoins) {// add that the neighbor can't use the adding coins till next turn
        sout.display("Player " + playerInv.getPlayerId() + "pays " + totalCoins + " coins to player " + neighborInv.getPlayerId());
        neighborInv.setAddedCoins(totalCoins);
        playerInv.removeCoins(totalCoins);
    }
}
