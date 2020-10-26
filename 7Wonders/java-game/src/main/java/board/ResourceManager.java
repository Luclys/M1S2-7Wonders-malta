package board;

import gameelements.GameLogger;
import gameelements.Inventory;
import gameelements.enums.Resource;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ResourceManager {
    private ArrayList<int[]> indexCombinationsFromPairResourcesChoices;
    private List<Resource> requiredResources;
    private final Trade commerce;
    private final GameLogger log;

    public ResourceManager(GameLogger log) {
        this.log = log;
        commerce = new Trade(log);
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
    private static <T> List<List<T>> sortByArraySize(List<List<T>> list) {
        list.sort(Comparator.comparingInt(List::size));
        return list;
    }

    /**
     * Removes required resources which can be chosen from cards with pair of resources for choice
     * and returns the remaining required resources
     * @param requiredResources List of requiredResources
     * @return List<Resource> Returns list of remaining resources
     */
    public List<List<Resource>> getSortedRemainingRequiredResourcesCombinationsAfterPairs(Inventory playerInventory, List<Resource> requiredResources) {
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

    public ArrayList<int[]> getIndexCombinationsFromPairResourcesChoices() {
        return indexCombinationsFromPairResourcesChoices;
    }
}
