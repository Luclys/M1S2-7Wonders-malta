package board;

import gameelements.Inventory;
import gameelements.enums.Category;
import gameelements.enums.Resource;

import java.util.List;

public class Trade {
    public static final String LEFT = "left";
    public static final String RIGHT = "right";
    public static GameLogger log;

    public Trade(GameLogger logger) {
        this.log = logger;
    }

    protected int [] tryBuy(List<Resource> missingResources, Inventory playerInv, Inventory rightNeighborInv, Inventory leftNeighborInv, int coinsRest) {
        boolean canBuyAllResources = true;
        int rightPrice;
        int leftPrice;
        int leftTotal = 0;
        int rightTotal = 0;
        Inventory neighbor;

        int[] rightAvailableResources = rightNeighborInv.getAvailableResources().clone();
        int[] leftAvailableResources = leftNeighborInv.getAvailableResources().clone();

        for (Resource missingResource : missingResources) {
            log.coinsLeft(playerInv.getPlayerId(), playerInv.getCoins() - leftTotal - rightTotal);
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
                        log.playerCanBuyFromNeighbor(playerInv.getPlayerId(), leftNeighborInv.getPlayerId(), LEFT, missingResource.toString());
                        leftTotal += leftPrice;
                        leftAvailableResources[missingResource.getIndex()]--;
                    } else {
                        log.notEnoughCoinsToBuyResource(playerInv.getPlayerId(), LEFT, missingResource.toString());
                        canBuyAllResources = false;
                        break;
                    }
                } else if (leftPrice > rightPrice) {
                    if (playerInv.getCoins() - leftTotal - rightTotal - rightPrice >= 0) {
                        log.playerCanBuyFromNeighbor(playerInv.getPlayerId(), rightNeighborInv.getPlayerId(), RIGHT, missingResource.toString());
                        rightTotal += rightPrice;
                        rightAvailableResources[missingResource.getIndex()]--;
                    } else {
                        log.notEnoughCoinsToBuyResource(playerInv.getPlayerId(), RIGHT, missingResource.toString());
                        canBuyAllResources = false;
                        break;
                    }
                } else {
                    if (playerInv.getCoins() - leftTotal - rightTotal - rightPrice >= 0) {
                        neighbor = chooseNeighbor(rightNeighborInv, leftNeighborInv);
                        if (neighbor.equals(rightNeighborInv)) {
                            log.playerCanBuyFromNeighbor(playerInv.getPlayerId(), rightNeighborInv.getPlayerId(), RIGHT, missingResource.toString());
                            rightTotal += rightPrice;
                            rightAvailableResources[missingResource.getIndex()]--;
                        } else {
                            log.playerCanBuyFromNeighbor(playerInv.getPlayerId(), leftNeighborInv.getPlayerId(), LEFT, missingResource.toString());
                            leftTotal += rightPrice;
                            leftAvailableResources[missingResource.getIndex()]--;
                        }
                    } else {
                        log.notEnoughCoinsToBuyResource(playerInv.getPlayerId(), "any", missingResource.toString());
                        canBuyAllResources = false;
                        break;
                    }
                }
            } else if (rightHasResource) {
                if (playerInv.getCoins() - leftTotal - rightTotal - rightPrice >= 0) {
                    log.playerCanBuyFromNeighbor(playerInv.getPlayerId(), rightNeighborInv.getPlayerId(), RIGHT, missingResource.toString());
                    rightTotal += rightPrice;
                    rightAvailableResources[missingResource.getIndex()]--;
                } else {
                    log.notEnoughCoinsToBuyResource(playerInv.getPlayerId(), RIGHT, missingResource.toString());
                    canBuyAllResources = false;
                    break;
                }
            } else if (leftHasResource) {
                if (playerInv.getCoins() - leftTotal - rightTotal - leftPrice >= 0) {
                    log.playerCanBuyFromNeighbor(playerInv.getPlayerId(), leftNeighborInv.getPlayerId(), LEFT, missingResource.toString());
                    leftTotal += leftPrice;
                    leftAvailableResources[missingResource.getIndex()]--;
                } else {
                    log.notEnoughCoinsToBuyResource(playerInv.getPlayerId(), LEFT, missingResource.toString());
                    canBuyAllResources = false;
                    break;
                }
            } else {
                log.noneOfTheNeighborsHasResource(missingResource.toString());
                canBuyAllResources = false;
                break;
            }
        }
        if (canBuyAllResources) {
            return new int[]{leftTotal, rightTotal};
        } else {
            return null;
        }
    }

    protected boolean buyResources(List<Resource> missingResources, Inventory playerInv, Inventory rightNeighborInv, Inventory leftNeighborInv) {
        boolean canBuyAllResources = true;
        int rightPrice;
        int leftPrice;
        int leftTotal = 0;
        int rightTotal = 0;
        Inventory neighbor;

        int[] rightAvailableResources = rightNeighborInv.getAvailableResources().clone();
        int[] leftAvailableResources = leftNeighborInv.getAvailableResources().clone();

        for (Resource missingResource : missingResources) {
            log.coinsLeft(playerInv.getPlayerId(), playerInv.getCoins() - leftTotal - rightTotal);
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
                        log.playerCanBuyFromNeighbor(playerInv.getPlayerId(), leftNeighborInv.getPlayerId(), LEFT, missingResource.toString());
                        leftTotal += leftPrice;
                        leftAvailableResources[missingResource.getIndex()]--;
                    } else {
                        log.notEnoughCoinsToBuyResource(playerInv.getPlayerId(), LEFT, missingResource.toString());
                        canBuyAllResources = false;
                        break;
                    }
                } else if (leftPrice > rightPrice) {
                    if (playerInv.getCoins() - leftTotal - rightTotal - rightPrice >= 0) {
                        log.playerCanBuyFromNeighbor(playerInv.getPlayerId(), rightNeighborInv.getPlayerId(), RIGHT, missingResource.toString());
                        rightTotal += rightPrice;
                        rightAvailableResources[missingResource.getIndex()]--;
                    } else {
                        log.notEnoughCoinsToBuyResource(playerInv.getPlayerId(), RIGHT, missingResource.toString());
                        canBuyAllResources = false;
                        break;
                    }
                } else {
                    if (playerInv.getCoins() - leftTotal - rightTotal - rightPrice >= 0) {
                        neighbor = chooseNeighbor(rightNeighborInv, leftNeighborInv);
                        if (neighbor.equals(rightNeighborInv)) {
                            log.playerCanBuyFromNeighbor(playerInv.getPlayerId(), rightNeighborInv.getPlayerId(), RIGHT, missingResource.toString());
                            rightTotal += rightPrice;
                            rightAvailableResources[missingResource.getIndex()]--;
                        } else {
                            log.playerCanBuyFromNeighbor(playerInv.getPlayerId(), leftNeighborInv.getPlayerId(), LEFT, missingResource.toString());
                            leftTotal += rightPrice;
                            leftAvailableResources[missingResource.getIndex()]--;
                        }
                    } else {
                        log.notEnoughCoinsToBuyResource(playerInv.getPlayerId(), "any", missingResource.toString());
                        canBuyAllResources = false;
                        break;
                    }
                }
            } else if (rightHasResource) {
                if (playerInv.getCoins() - leftTotal - rightTotal - rightPrice >= 0) {
                    log.playerCanBuyFromNeighbor(playerInv.getPlayerId(), rightNeighborInv.getPlayerId(), RIGHT, missingResource.toString());
                    rightTotal += rightPrice;
                    rightAvailableResources[missingResource.getIndex()]--;
                } else {
                    log.notEnoughCoinsToBuyResource(playerInv.getPlayerId(), RIGHT, missingResource.toString());
                    canBuyAllResources = false;
                    break;
                }
            } else if (leftHasResource) {
                if (playerInv.getCoins() - leftTotal - rightTotal - leftPrice >= 0) {
                    log.playerCanBuyFromNeighbor(playerInv.getPlayerId(), leftNeighborInv.getPlayerId(), LEFT, missingResource.toString());
                    leftTotal += leftPrice;
                    leftAvailableResources[missingResource.getIndex()]--;
                } else {
                    log.notEnoughCoinsToBuyResource(playerInv.getPlayerId(), LEFT, missingResource.toString());
                    canBuyAllResources = false;
                    break;
                }
            } else {
                log.noneOfTheNeighborsHasResource(missingResource.toString());
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
        log.playerPaysCoins(playerInv.getPlayerId(), neighborInv.getPlayerId(), totalCoins);
        neighborInv.addAddedCoins(totalCoins);
        playerInv.removeCoins(totalCoins);
        playerInv.getDetailedResults().addNbCoinsSpentInTrade(totalCoins);
    }
}
