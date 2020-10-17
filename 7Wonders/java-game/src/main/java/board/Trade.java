package board;

import gameelements.Inventory;
import gameelements.SoutConsole;
import gameelements.enums.Category;
import gameelements.enums.Resource;

import java.util.List;

public class Trade {
    SoutConsole sout;

    public Trade(SoutConsole sout) {
        this.sout = sout;
    }

    protected boolean buyResources(List<Resource> missingResources, Inventory playerInv, Inventory rightNeighborInv, Inventory leftNeighborInv) {
        boolean canBuyAllResources = true;
        int rightPrice;
        int leftPrice;
        int leftTotal = 0;
        int rightTotal = 0;
        Inventory neighbor;

        int [] rightAvailableResources = rightNeighborInv.getAvailableResources().clone();
        int [] leftAvailableResources = leftNeighborInv.getAvailableResources().clone();
        String left ="left";
        String right ="right";

        sout.pricesOfResources(playerInv);
        for (Resource missingResource : missingResources) {
            sout.display("Player " + playerInv.getPlayerId() + " coins left: " + (playerInv.getCoins() - leftTotal - rightTotal));
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
                        sout.playerCanBuyFromNeighbor(playerInv.getPlayerId(), leftNeighborInv.getPlayerId(), "left", missingResource.toString());
                        leftTotal += leftPrice;
                        leftAvailableResources[missingResource.getIndex()]--;
                    } else {
                        sout.notEnoughCoinsToBuyResource(playerInv.getPlayerId(), "left", missingResource.toString());
                        canBuyAllResources = false;
                        break;
                    }
                } else if (leftPrice > rightPrice) {
                    if (playerInv.getCoins() - leftTotal - rightTotal - rightPrice >= 0) {
                        sout.playerCanBuyFromNeighbor(playerInv.getPlayerId(), rightNeighborInv.getPlayerId(), "right", missingResource.toString());
                        rightTotal += rightPrice;
                        rightAvailableResources[missingResource.getIndex()]--;
                    } else {
                        sout.notEnoughCoinsToBuyResource(playerInv.getPlayerId(), "right", missingResource.toString());
                        canBuyAllResources = false;
                        break;
                    }
                } else {
                    if (playerInv.getCoins() - leftTotal - rightTotal  - rightPrice >= 0) {
                        neighbor = chooseNeighbor(rightNeighborInv, leftNeighborInv);
                        if (neighbor.equals(rightNeighborInv)) {
                            sout.playerCanBuyFromNeighbor(playerInv.getPlayerId(), rightNeighborInv.getPlayerId(), "right", missingResource.toString());
                            rightTotal += rightPrice;
                            rightAvailableResources[missingResource.getIndex()]--;
                        } else {
                            sout.playerCanBuyFromNeighbor(playerInv.getPlayerId(), leftNeighborInv.getPlayerId(), "left", missingResource.toString());
                            leftTotal += rightPrice;
                            leftAvailableResources[missingResource.getIndex()]--;
                        }
                    } else {
                        sout.notEnoughCoinsToBuyResource(playerInv.getPlayerId(), "any", missingResource.toString());
                        canBuyAllResources = false;
                        break;
                    }
                }
            } else if (rightHasResource) {
                if (playerInv.getCoins() - leftTotal - rightTotal - rightPrice >= 0) {
                    sout.playerCanBuyFromNeighbor(playerInv.getPlayerId(), rightNeighborInv.getPlayerId(), "right", missingResource.toString());
                    rightTotal += rightPrice;
                    rightAvailableResources[missingResource.getIndex()]--;
                } else {
                    sout.notEnoughCoinsToBuyResource(playerInv.getPlayerId(), "right", missingResource.toString());
                    canBuyAllResources = false;
                    break;
                }
            } else if (leftHasResource) {
                if (playerInv.getCoins() - leftTotal - rightTotal - leftPrice >= 0) {
                    sout.playerCanBuyFromNeighbor(playerInv.getPlayerId(), leftNeighborInv.getPlayerId(), "left", missingResource.toString());
                    leftTotal += leftPrice;
                    leftAvailableResources[missingResource.getIndex()]--;
                } else {
                    sout.notEnoughCoinsToBuyResource(playerInv.getPlayerId(), "left", missingResource.toString());
                    canBuyAllResources = false;
                    break;
                }
            } else {
                sout.noneOfTheNeighborsHasResource(missingResource.toString());
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
        if (rightNeighborInv.getCoins() < leftNeighborInv.getCoins()) { // Maybe AI
           return rightNeighborInv;
        } else {
            return leftNeighborInv;
        }
    }

    protected void payToNeighbor(Inventory playerInv, Inventory neighborInv, int totalCoins) {// add that the neighbor can't use the adding coins till next turn
        sout.playerPaysCoins(playerInv.getPlayerId(), neighborInv.getPlayerId(), totalCoins);
        neighborInv.setAddedCoins(totalCoins);
        playerInv.removeCoins(totalCoins);
    }
}
