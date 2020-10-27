package board;

import gameelements.GameLogger;
import gameelements.Inventory;
import gameelements.enums.Category;
import gameelements.enums.Resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ResourceManager {
    private ArrayList<int[]> indexCombinationsFromPairResourcesChoices;
    private final Trade commerce;
    private List<Resource> requiredMatieresPremieresResources;
    private List<Resource> requiredProduitsManufactures;
    int rightTotal;
    int leftTotal;
    int tempLeftTotal;
    int tempRightTotal;

    private void unset() {
        requiredMatieresPremieresResources = new ArrayList<>();
        requiredProduitsManufactures = new ArrayList<>();
        rightTotal = 0;
        leftTotal = 0;
        tempLeftTotal = 0;
        tempRightTotal = 0;
    }

    public ResourceManager(GameLogger log) {
        commerce = new Trade(log);
        rightTotal = 0;
        leftTotal = 0;
        tempLeftTotal = 0;
        tempRightTotal = 0;
    }

    public ArrayList<int[]> getIndexCombinationsFromPairResourcesChoices() {
        return indexCombinationsFromPairResourcesChoices;
    }

    /**
     * This recursive function fills in indexCombinationsFromPairResourcesChoices with all the index combinations for
     * choosing from cards with pair of resources for choice
     * Index is an int in [0,1] and means which resource we choose from each pair
     * @param i current index (from which we continue to construct the combination)
     * @param combination current combination
     */
    public void fillInIndexCombinationsFromPairResourcesChoices(int i, int[] combination) {
        //if combination is ready (length == number of pairs) then add to indexCombinationsFromPairResourcesChoices
        if (combination.length == i) {
            int[] readyCombination = combination.clone();
            indexCombinationsFromPairResourcesChoices.add(readyCombination);
            return;
        }
        //let current index be 0 and then continue construct the combination
        combination[i] = 0;
        fillInIndexCombinationsFromPairResourcesChoices(i + 1, combination);
        //let current index be 1 and then continue construct the combination
        combination[i] = 1;
        fillInIndexCombinationsFromPairResourcesChoices(i + 1, combination);
    }

    /**
     * Empties indexCombinationsFromPairResourcesChoices and calls a recursive function fillInIndexCombinationsFromPairResourcesChoices
     * to fill in indexCombinationsFromPairResourcesChoices
     * @param playerInventory player's inventory
     */
    public void fillInIndexCombinationsFromPairResourcesChoices(Inventory playerInventory) {
        indexCombinationsFromPairResourcesChoices = new ArrayList<>();
        if (playerInventory.getPairResChoice().size() > 0) {
            fillInIndexCombinationsFromPairResourcesChoices(0, new int[playerInventory.getPairResChoice().size()]);
        }
    }

    /**
     * Sorts list of lists by size
     * @param list List of lists
     * @return List<List<T>> returns sorted list
     */
    public <T> List<List<T>> sortByArraySize(List<List<T>> list) {
        list.sort(Comparator.comparingInt(List::size));
        return list;
    }

    /**
     * Removes required resources which can be chosen from cards with pair of resources for choice
     * and returns all the combinations of the remaining required resources sorted by array size
     * @param playerInventory Player's inventory
     * @param requiredResources List of requiredResources
     * @return List<List<Resource>> Returns the list with all the combinations of the remaining required resources sorted by array size
     */
    public List<List<Resource>> sortedRemainingRequiredResourcesCombinationsAfterPairsChoice(Inventory playerInventory, List<Resource> requiredResources) {
        fillInIndexCombinationsFromPairResourcesChoices(playerInventory);
        List<List<Resource>> remainingRequiredResourcesCombination = new ArrayList<>();
        //check each possible combination
        for (int[] combination : indexCombinationsFromPairResourcesChoices) {
            List<Resource> remainingRequiredResources = new ArrayList<>(requiredResources);;
            for (int i = 0; i < playerInventory.getPairResChoice().size(); i++) {
                //choose resource in each pair according to combination
                Resource chosenResource = playerInventory.getPairResChoice().get(i)[combination[i]];

                int foundRequiredResourceIndex = remainingRequiredResources.indexOf(chosenResource);
                //if chosen resource is one the required resources remove in from remainingRequiredResources
                if (foundRequiredResourceIndex != -1) {
                    remainingRequiredResources.remove(foundRequiredResourceIndex);
                }
                //if we found all the resources return
                if (remainingRequiredResources.size() == 0) {
                    return new ArrayList<>();
                }
            }
            remainingRequiredResourcesCombination.add(remainingRequiredResources);
        }
        return sortByArraySize(remainingRequiredResourcesCombination);
    }

    /**
     * Removes required resources which can be chosen from cards with one produced resource
     * and returns the remaining required resources
     * @param inventory player's inventory
     * @param requiredResources List of requiredResources
     * @return List<Resource> List of remaining resources
     */
    public List<Resource> remainingRequiredResourcesAfterChoiceMaximumFromAvailable(Inventory inventory, List<Resource> requiredResources) {
        List<Resource> remainingRequiredResources = new ArrayList<>(requiredResources);
        List<Resource> availableResources = convertResourcesByIndexesToResources(inventory.getAvailableResources());
        remainingRequiredResources.removeAll(availableResources);
        return remainingRequiredResources;
    }

    /**
     * Converts indexes of resources (enum) to list of resources
     * @param resourcesByIndex player's inventory
     * @return List<Resource> List of resources
     */
    public List<Resource> convertResourcesByIndexesToResources(int[] resourcesByIndex) {
        List<Resource> resources = new ArrayList<>();
        for (int i = 0; i < resourcesByIndex.length; i++) {
            for (int j = 0; j < resourcesByIndex[i]; j++) {
                resources.add(Resource.values()[i]);
            }
        }
        return resources;
    }

    public int findCombinationIndexWithMinTotal(
            Inventory playerInventory,
            Inventory leftNeighborInventory,
            Inventory rightNeighborInventory,
            List<List<Resource>> requiredResourcesCombinations
    ) {
        int minTotal = Integer.MAX_VALUE;
        int indexOfMin = -1;
        for (int i = 0; i < requiredResourcesCombinations.size(); i++) {
            int [] neighborTotals = commerce.tryBuy(requiredResourcesCombinations.get(i), playerInventory, rightNeighborInventory, leftNeighborInventory, playerInventory.getCoins() - rightTotal - leftTotal);
            if (neighborTotals != null) {
                if (minTotal > neighborTotals[0] + neighborTotals[1]) {
                    minTotal = neighborTotals[0] + neighborTotals[1];
                    indexOfMin = i;
                    tempLeftTotal = neighborTotals[0];
                    tempRightTotal = neighborTotals[1];
                }
            }
        }
        return indexOfMin;
    }

    private void splitByCategory(List<Resource> requiredResources) {
        for (Resource resource : requiredResources) {
            if (resource.getCategory().equals(Category.PRODUIT_MANUFACTURE)) {
                requiredProduitsManufactures.add(resource);
            } else {
                requiredMatieresPremieresResources.add(resource);
            }
        }
    }

    private boolean updateRemainingRequiredResourcesAfterChoiceAnyProduitsManufactures(Inventory inventory, Inventory leftNeighbor, Inventory rightNeighbor){
        if (requiredProduitsManufactures.size() <= inventory.getAnyProduitManufactureAvailableCount()) {
            requiredProduitsManufactures = new ArrayList<>();
        } else {
            requiredProduitsManufactures = requiredProduitsManufactures.subList(inventory.getAnyProduitManufactureAvailableCount(), requiredProduitsManufactures.size());
            for (Resource resource : requiredProduitsManufactures) {
                int [] neighborTotals =  commerce.tryBuy(requiredProduitsManufactures, inventory, leftNeighbor, rightNeighbor, inventory.getCoins() - rightTotal - leftTotal);
                if (neighborTotals == null) {
                    return false;
                } else {
                    leftTotal+=neighborTotals[0];
                    rightTotal+=neighborTotals[1];
                }
            }
        }
        return true;
    }

    public boolean updateRemainingRequiredResourcesAfterChoiceFromMatieresPremieres(
            Inventory playerInventory,
            Inventory leftNeighborInventory,
            Inventory rightNeighborInventory,
            List<List<Resource>> requiredResourcesCombinations
    ) {
        int combinationLengthToFindBestPrice = requiredResourcesCombinations.get(0).size();
        List<List<Resource>> resourceCombinationsOfSameLength = new ArrayList<>();

        for (int i = 0; i < requiredResourcesCombinations.size(); i++) {
            List<Resource> requiredResourcesCombination = new ArrayList<>(requiredResourcesCombinations.get(i));
            int currentCombinationLength = requiredResourcesCombination.size();
            int anyMatierePremiereAvailableCount = playerInventory.getAnyMatierePremiereAvailableCount();
            //if player can get all the rest resources from cards with choice from category
            if (currentCombinationLength <= anyMatierePremiereAvailableCount) {
                return true;
            } else {
                //remove first n=anyMatierePremiereAvailableCount elements
                requiredResourcesCombination = requiredResourcesCombination.subList(anyMatierePremiereAvailableCount, currentCombinationLength);
                currentCombinationLength = requiredResourcesCombination.size();
                // if this is the last combination
                if (i == requiredResourcesCombinations.size() - 1) {
                    resourceCombinationsOfSameLength.add(requiredResourcesCombination);
                }
                //if length of combination has changed we can check if we can be minimum resources for minimum price
                if (currentCombinationLength > combinationLengthToFindBestPrice || i == requiredResourcesCombinations.size() - 1) {
                    int indexOfBestCombination = findCombinationIndexWithMinTotal(playerInventory, leftNeighborInventory, rightNeighborInventory, resourceCombinationsOfSameLength);
                    //if found best combination
                    if (indexOfBestCombination != -1) {
                        leftTotal+=tempLeftTotal;
                        rightTotal+=tempRightTotal;
                        return true;
                    } else {
                        combinationLengthToFindBestPrice = currentCombinationLength;
                        resourceCombinationsOfSameLength = new ArrayList<>();
                        resourceCombinationsOfSameLength.add(requiredResourcesCombination);
                    }
                } else {
                    resourceCombinationsOfSameLength.add(requiredResourcesCombination);
                }
            }
        }
        return false;
    }

    public boolean canBuild(Inventory inventory, Inventory leftNeighbor, Inventory rightNeighbor, List<Resource> requiredResources) {
        unset();
        requiredResources = remainingRequiredResourcesAfterChoiceMaximumFromAvailable(inventory, requiredResources);
        splitByCategory(requiredResources);
        if (!updateRemainingRequiredResourcesAfterChoiceAnyProduitsManufactures(inventory, leftNeighbor, rightNeighbor)) {
            return false;
        }
        List<List<Resource>> combinations = sortedRemainingRequiredResourcesCombinationsAfterPairsChoice(inventory, requiredMatieresPremieresResources);
        if (combinations.size() == 0) {
            return true;
        }
        if (updateRemainingRequiredResourcesAfterChoiceFromMatieresPremieres(inventory, leftNeighbor, rightNeighbor, combinations)){
            commerce.payToNeighbor(inventory, leftNeighbor, leftTotal);
            commerce.payToNeighbor(inventory, rightNeighbor, rightTotal);
            return true;
        } else {
            return false;
        }
    }
}
