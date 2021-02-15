package board;

import gameelements.Inventory;
import gameelements.enums.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ResourceManagerTest {
    ResourceManager resourceManager;
    Inventory inventory;

    @BeforeEach
    void setUp() {
        resourceManager = new ResourceManager(new GameLogger(false));
        inventory = new Inventory(0);
    }

    boolean compareArraysOfArrays(ArrayList<int[]> arr1, ArrayList<int[]> arr2) {
        if (arr1.size() != arr2.size()) {
            return false;
        } else {
            for (int i = 0; i < arr1.size(); i++) {
                if (!Arrays.equals(arr1.get(i), arr2.get(i))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Test fillInIndexCombinationsFromPairResourcesChoices when there is 1 resource pair for choice
     *
     * @result Combinations are [0], [1]
     */
    @Test
    void fillInIndexCombinationsFromPairResourcesChoices1Pair() {
        Resource[] resources = new Resource[]{Resource.PIERRE, Resource.TISSU};
        inventory.addPairResChoice(resources);

        ArrayList<int[]> result = new ArrayList<>();
        result.add(new int[]{0});
        result.add(new int[]{1});

        resourceManager.fillInIndexCombinationsFromPairResourcesChoices(inventory);
        assertTrue(compareArraysOfArrays(result, resourceManager.getIndexCombinationsFromPairResourcesChoices()));
    }

    /**
     * Test fillInIndexCombinationsFromPairResourcesChoices when there are 2 resource pairs for choice
     *
     * @result Combinations are [0,0], [0,1], [1,0], [1,1]
     */
    @Test
    void fillInIndexCombinationsFromPairResourcesChoices2Pairs() {
        Resource[] resources = new Resource[]{Resource.PIERRE, Resource.TISSU};
        inventory.addPairResChoice(resources);
        resources = new Resource[]{Resource.BOIS, Resource.MINERAI};
        inventory.addPairResChoice(resources);

        ArrayList<int[]> result = new ArrayList<>();
        result.add(new int[]{0,0});
        result.add(new int[]{0,1});
        result.add(new int[]{1,0});
        result.add(new int[]{1,1});

        resourceManager.fillInIndexCombinationsFromPairResourcesChoices(inventory);
        assertTrue(compareArraysOfArrays(result, resourceManager.getIndexCombinationsFromPairResourcesChoices()));
    }

    /**
     * Test fillInIndexCombinationsFromPairResourcesChoices when there are 4 resource pairs for choice
     *
     * @result Combinations are all binary combinations of length 4
     */
    @Test
    void fillInIndexCombinationsFromPairResourcesChoices4Pairs() {
        Resource[] resources = new Resource[]{Resource.BOIS, Resource.ARGILE};
        inventory.addPairResChoice(resources);
        resources = new Resource[]{Resource.MINERAI, Resource.ARGILE};
        inventory.addPairResChoice(resources);
        resources = new Resource[]{Resource.ARGILE, Resource.MINERAI};
        inventory.addPairResChoice(resources);
        resources = new Resource[]{Resource.PIERRE, Resource.BOIS};
        inventory.addPairResChoice(resources);

        ArrayList<int[]> result = new ArrayList<>();
        result.add(new int[]{0,0,0,0});
        result.add(new int[]{0,0,0,1});
        result.add(new int[]{0,0,1,0});
        result.add(new int[]{0,0,1,1});
        result.add(new int[]{0,1,0,0});
        result.add(new int[]{0,1,0,1});
        result.add(new int[]{0,1,1,0});
        result.add(new int[]{0,1,1,1});
        result.add(new int[]{1,0,0,0});
        result.add(new int[]{1,0,0,1});
        result.add(new int[]{1,0,1,0});
        result.add(new int[]{1,0,1,1});
        result.add(new int[]{1,1,0,0});
        result.add(new int[]{1,1,0,1});
        result.add(new int[]{1,1,1,0});
        result.add(new int[]{1,1,1,1});
        resourceManager.fillInIndexCombinationsFromPairResourcesChoices(inventory);
        assertTrue(compareArraysOfArrays(result, resourceManager.getIndexCombinationsFromPairResourcesChoices()));
    }

    /**
     * No available resources ([0,0,0,0,0,0,0])
     * @result converted array is []
     */
    @Test
    void resourcesByIndexesToResourcesEmpty() {
        ArrayList<Resource> resultResources = new ArrayList<>();
        ArrayList<Resource> convertedResources = new ArrayList<>(resourceManager.convertResourcesByIndexesToResources(inventory.getAvailableResources()));
        assertEquals(resultResources, convertedResources);
    }

    /**
     * AvailableResources are 1 Pierre and 1 Argile ([1,1,0,0,0,0,0])
     * @result converted array is [Pierre, Argile]
     */
    @Test
    void resourcesByIndexesToResources() {
        inventory.getAvailableResources()[Resource.PIERRE.getIndex()]++;
        inventory.getAvailableResources()[Resource.ARGILE.getIndex()]++;

        ArrayList<Resource> resultResources = new ArrayList<>();
        resultResources.add(Resource.ARGILE);
        resultResources.add(Resource.PIERRE);

        ArrayList<Resource> convertedResources = new ArrayList<>(resourceManager.convertResourcesByIndexesToResources(inventory.getAvailableResources()));

        assertEquals(resultResources, convertedResources);
    }

    /**
     * AvailableResources are 1 Pierre and 1 Argile ([3,1,0,0,0,0,0])
     * @result converted array is [Pierre, Argile, Argile, Argile]
     */
    @Test
    void resourcesByIndexesToResourcesWithIdenticals() {
        inventory.getAvailableResources()[Resource.PIERRE.getIndex()]++;
        inventory.getAvailableResources()[Resource.ARGILE.getIndex()]+=3;

        ArrayList<Resource> resultResources = new ArrayList<>();
        resultResources.add(Resource.ARGILE);
        resultResources.add(Resource.ARGILE);
        resultResources.add(Resource.ARGILE);
        resultResources.add(Resource.PIERRE);

        ArrayList<Resource> convertedResources = new ArrayList<>(resourceManager.convertResourcesByIndexesToResources(inventory.getAvailableResources()));

        assertEquals(resultResources, convertedResources);
    }

    /**
     * AvailableResources are 1 Pierre and 1 Argile, required resources = [Pierre, Argile]
     * @result there are no remaining resources
     */
    @Test
    void remainingRequiredResourcesAfterChoiceMaximumFromAvailableAll() {
        inventory.getAvailableResources()[Resource.PIERRE.getIndex()]++;
        inventory.getAvailableResources()[Resource.ARGILE.getIndex()]++;

        ArrayList<Resource> requiredResources = new ArrayList<>();
        requiredResources.add(Resource.PIERRE);
        requiredResources.add(Resource.ARGILE);

        ArrayList<Resource> resultResources = new ArrayList<>();

        assertEquals(resultResources, resourceManager.remainingRequiredResourcesAfterChoiceMaximumFromAvailable(inventory, requiredResources));
    }

    /**
     * AvailableResources are 2 Pierres and 1 Argile, required resources = [Pierre, Pierre, Argile]
     * @result there are no remaining resources
     */
    @Test
    void remainingRequiredResourcesAfterChoiceMaximumFromAvailableAllWithIdenticals() {
        inventory.getAvailableResources()[Resource.PIERRE.getIndex()]++;
        inventory.getAvailableResources()[Resource.PIERRE.getIndex()]++;
        inventory.getAvailableResources()[Resource.ARGILE.getIndex()]++;

        ArrayList<Resource> requiredResources = new ArrayList<>();
        requiredResources.add(Resource.PIERRE);
        requiredResources.add(Resource.PIERRE);
        requiredResources.add(Resource.ARGILE);

        ArrayList<Resource> resultResources = new ArrayList<>();

        assertEquals(resultResources, resourceManager.remainingRequiredResourcesAfterChoiceMaximumFromAvailable(inventory, requiredResources));
    }

    /**
     * AvailableResources are 1 Pierre and 1 Argile, required resources = [Pierre, Argile, Minerai]
     * @result [Minerai] is remaining
     */
    @Test
    void remainingRequiredResourcesAfterChoiceMaximumFromAvailable() {
        inventory.getAvailableResources()[Resource.PIERRE.getIndex()]++;
        inventory.getAvailableResources()[Resource.ARGILE.getIndex()]++;

        ArrayList<Resource> requiredResources = new ArrayList<>();
        requiredResources.add(Resource.PIERRE);
        requiredResources.add(Resource.ARGILE);
        requiredResources.add(Resource.MINERAI);

        ArrayList<Resource> resultResources = new ArrayList<>();
        resultResources.add(Resource.MINERAI);

        assertEquals(resultResources, resourceManager.remainingRequiredResourcesAfterChoiceMaximumFromAvailable(inventory, requiredResources));
    }

    /**
     * AvailableResources are 1 Pierre and 1 Argile, required resources = [Minerai, Minerai]
     * @result [Minerai, Minerai] is remaining
     */
    @Test
    void remainingRequiredResourcesAfterChoiceMaximumFromAvailableNoResources() {
        inventory.getAvailableResources()[Resource.PIERRE.getIndex()]++;
        inventory.getAvailableResources()[Resource.ARGILE.getIndex()]++;

        ArrayList<Resource> requiredResources = new ArrayList<>();
        requiredResources.add(Resource.MINERAI);
        requiredResources.add(Resource.MINERAI);

        ArrayList<Resource> resultResources = new ArrayList<>(requiredResources);

        assertEquals(resultResources, resourceManager.remainingRequiredResourcesAfterChoiceMaximumFromAvailable(inventory, requiredResources));
    }

    /**
     * Available pairs for choice are ARGILE/MINERAI ET BOIS/PIERRE, required resources = [MINERAI, PIERRE]
     * @result No remaining resources
     */
    @Test
    void sortedRemainingRequiredResourcesCombinationsAfterPairsChoiceAll() {
        Resource[] resources = new Resource[]{Resource.ARGILE, Resource.MINERAI};
        inventory.addPairResChoice(resources);
        resources = new Resource[]{Resource.BOIS, Resource.PIERRE};
        inventory.addPairResChoice(resources);

        ArrayList<Resource> requiredResources = new ArrayList<>();
        requiredResources.add(Resource.MINERAI);
        requiredResources.add(Resource.PIERRE);

        assertEquals(0, resourceManager.sortedRemainingRequiredResourcesCombinationsAfterPairsChoice(inventory, requiredResources).size());
    }

    /**
     * Available pairs for choice are ARGILE/MINERAI, required resources = [MINERAI, ARGILE]
     * @result Possible combinations of remaining resources are [MINERAI] and [ARGILE]
     */
    @Test
    void sortedRemainingRequiredResourcesCombinationsAfterPairsChoiceOnePair() {
        Resource[] resources = new Resource[]{Resource.ARGILE, Resource.MINERAI};
        inventory.addPairResChoice(resources);

        ArrayList<Resource> requiredResources = new ArrayList<>();
        requiredResources.add(Resource.ARGILE);
        requiredResources.add(Resource.MINERAI);

        ArrayList<ArrayList<Resource>> combinations = new ArrayList<>();

        ArrayList<Resource> comb = new ArrayList<>();
        comb.add(Resource.MINERAI);
        combinations.add(comb);

        comb = new ArrayList<>();
        comb.add(Resource.ARGILE);
        combinations.add(comb);

        assertEquals(combinations, resourceManager.sortedRemainingRequiredResourcesCombinationsAfterPairsChoice(inventory, requiredResources));
    }

    /**
     * Available pairs for choice are ARGILE/MINERAI, BOIS/PIERRE, MINERAI/BOIS required resources = [MINERAI, MINERAI, BOIS, PIERRE]
     * @result Possible combinations of remaining resources are:
     * [PIERRE] if choose MINERAI from first pair, BOIS from second pair and MINERAI from third pair
     * [BOIS] if choose MINERAI from first pair, PIERRE from second pair and MINERAI from third pair
     * [MINERAI] if choose MINERAI from first pair, PIERRE from second pair and BOIS from third pair
     * [MINERAI, PIERRE] if choose MINERAI from first pair, BOIS from second pair and BOIS from third pair
     * [MINERAI, PIERRE] if choose ARGILE from first pair, BOIS from second pair and MINERAI from third pair
     * [MINERAI, BOIS] if choose ARGILE from first pair, PIERRE from second pair and MINERAI from third pair
     * [MINERAI, MINERAI] if choose ARGILE from first pair, PIERRE from second pair and BOIS from third pair
     * [MINERAI, MINERAI, PIERRE] if choose ARGILE from first pair, BOIS from second pair and BOIS from third pair
     */
    @Test
    void sortedRemainingRequiredResourcesCombinationsAfterPairsChoiceThreePairs() {
        Resource[] resources = new Resource[]{Resource.ARGILE, Resource.MINERAI};
        inventory.addPairResChoice(resources);
        resources = new Resource[]{Resource.BOIS, Resource.PIERRE};
        inventory.addPairResChoice(resources);
        resources = new Resource[]{Resource.MINERAI, Resource.BOIS};
        inventory.addPairResChoice(resources);

        ArrayList<Resource> requiredResources = new ArrayList<>();
        requiredResources.add(Resource.MINERAI);
        requiredResources.add(Resource.MINERAI);
        requiredResources.add(Resource.BOIS);
        requiredResources.add(Resource.PIERRE);
        List<List<Resource>> combinations = new ArrayList<>();

        ArrayList<Resource> comb = new ArrayList<>();
        comb.add(Resource.PIERRE);
        combinations.add(comb);

        comb = new ArrayList<>();
        comb.add(Resource.BOIS);
        combinations.add(comb);

        comb = new ArrayList<>();
        comb.add(Resource.MINERAI);
        combinations.add(comb);

        comb = new ArrayList<>();
        comb.add(Resource.MINERAI);
        comb.add(Resource.PIERRE);
        combinations.add(comb);
        combinations.add(comb);

        comb = new ArrayList<>();
        comb.add(Resource.MINERAI);
        comb.add(Resource.BOIS);
        combinations.add(comb);

        comb = new ArrayList<>();
        comb.add(Resource.MINERAI);
        comb.add(Resource.MINERAI);
        combinations.add(comb);

        comb = new ArrayList<>();
        comb.add(Resource.MINERAI);
        comb.add(Resource.MINERAI);
        comb.add(Resource.PIERRE);
        combinations.add(comb);

        combinations = resourceManager.sortByArraySize(combinations);
        List<List<Resource>> resultCombinations = resourceManager.sortedRemainingRequiredResourcesCombinationsAfterPairsChoice(inventory, requiredResources);
        assertEquals(8, resultCombinations.size());
        for (List<Resource> combination : combinations) {
            assertTrue(resultCombinations.contains(combination));
        }
    }

    /**
     * Combinations of required resources are [PIERRE], [ARGILE], [BOIS]
     * right is cheaper than left, right has BOIS and left has ARGILE
     * @result The cheapest is BOIS
     */
    @Test
    void findCombinationIndexWithMinTotal() {
        Inventory leftNeighbor = new Inventory(1);
        Inventory rightNeighbor = new Inventory(2);
        leftNeighbor.getAvailableResources()[Resource.ARGILE.getIndex()]++;
        rightNeighbor.getAvailableResources()[Resource.BOIS.getIndex()]++;

        inventory.setMatieresPremieresPriceRight(1);
        inventory.setMatieresPremieresPriceLeft(2);

        List<List<Resource>> combinations = new ArrayList<>();

        ArrayList<Resource> comb = new ArrayList<>();
        comb.add(Resource.PIERRE);
        combinations.add(comb);

        comb = new ArrayList<>();
        comb.add(Resource.ARGILE);
        combinations.add(comb);

        comb = new ArrayList<>();
        comb.add(Resource.BOIS);
        combinations.add(comb);

        assertEquals(2, resourceManager.findCombinationIndexWithMinTotal(inventory, leftNeighbor, rightNeighbor, combinations));
    }

    /**
     * Combinations of required resources are [PIERRE, BOIS], [BOIS, ARGILE], [ARGILE, MINERAI]
     * right is cheaper than left, right has BOIS and left has ARGILE and MINERAI
     * @result The cheapest is ARGILE + BOIS (sum = 3)
     */
    @Test
    void findCombinationIndexWithMinTotalTwoResources() {
        Inventory leftNeighbor = new Inventory(1);
        Inventory rightNeighbor = new Inventory(2);
        leftNeighbor.getAvailableResources()[Resource.ARGILE.getIndex()]++;
        leftNeighbor.getAvailableResources()[Resource.MINERAI.getIndex()]++;
        rightNeighbor.getAvailableResources()[Resource.BOIS.getIndex()]++;

        inventory.setCoins(5);
        inventory.setAnyMatierePremiereAvailableCount(1);
        inventory.setMatieresPremieresPriceRight(1);
        inventory.setMatieresPremieresPriceLeft(2);

        List<List<Resource>> combinations = new ArrayList<>();

        ArrayList<Resource> comb = new ArrayList<>();
        comb.add(Resource.PIERRE);
        comb.add(Resource.BOIS);
        combinations.add(comb);

        comb = new ArrayList<>();
        comb.add(Resource.ARGILE);
        comb.add(Resource.BOIS);
        combinations.add(comb);

        comb = new ArrayList<>();
        comb.add(Resource.ARGILE);
        comb.add(Resource.MINERAI);
        combinations.add(comb);

        assertEquals(1, resourceManager.findCombinationIndexWithMinTotal(inventory, leftNeighbor, rightNeighbor, combinations));
    }

    /**
     * Combinations of required resources are [PIERRE, BOIS], [BOIS, ARGILE], [ARGILE, MINERAI]
     * neighbors don't have any
     * @result Can't use any of these combinations
     */
    @Test
    void findCombinationIndexWithMinTotalNoResources() {
        Inventory leftNeighbor = new Inventory(1);
        Inventory rightNeighbor = new Inventory(2);

        List<List<Resource>> combinations = new ArrayList<>();

        ArrayList<Resource> comb = new ArrayList<>();
        comb.add(Resource.PIERRE);
        comb.add(Resource.BOIS);
        combinations.add(comb);

        comb = new ArrayList<>();
        comb.add(Resource.ARGILE);
        comb.add(Resource.BOIS);
        combinations.add(comb);

        comb = new ArrayList<>();
        comb.add(Resource.ARGILE);
        comb.add(Resource.MINERAI);
        combinations.add(comb);

        assertEquals(-1, resourceManager.findCombinationIndexWithMinTotal(inventory, leftNeighbor, rightNeighbor, combinations));
    }

    /**
     * User has 2 ANY MATIERE PREMIERE cards
     * Required count is 2
     * @result can build all the resources with cards
     */
    @Test
    void remainingRequiredResourcesAfterChoiceFromMatieresPremieresAll() {
        Inventory leftNeighbor = new Inventory(1);
        Inventory rightNeighbor = new Inventory(2);

        inventory.setAnyMatierePremiereAvailableCount(2);

        List<List<Resource>> combinations = new ArrayList<>();

        ArrayList<Resource> comb = new ArrayList<>();
        comb.add(Resource.PIERRE);
        comb.add(Resource.PIERRE);
        combinations.add(comb);

        comb = new ArrayList<>();
        comb.add(Resource.MINERAI);
        comb.add(Resource.PIERRE);
        combinations.add(comb);


        assertTrue(resourceManager.updateRemainingRequiredResourcesAfterChoiceFromMatieresPremieres(inventory, leftNeighbor, rightNeighbor, combinations));
    }


    @Test
    void remainingRequiredResourcesAfterChoiceFromMatieresPremieres() {
        Inventory leftNeighbor = new Inventory(1);
        Inventory rightNeighbor = new Inventory(2);
        leftNeighbor.getAvailableResources()[Resource.BOIS.getIndex()]++;
        leftNeighbor.getAvailableResources()[Resource.MINERAI.getIndex()]++;
        rightNeighbor.getAvailableResources()[Resource.ARGILE.getIndex()]++;

        inventory.setCoins(5);
        inventory.setAnyMatierePremiereAvailableCount(1);
        inventory.setMatieresPremieresPriceRight(1);
        inventory.setMatieresPremieresPriceLeft(2);

        List<List<Resource>> combinations = new ArrayList<>();

        ArrayList<Resource> comb = new ArrayList<>();
        comb.add(Resource.PIERRE);
        comb.add(Resource.PIERRE);
        combinations.add(comb);

        comb = new ArrayList<>();
        comb.add(Resource.MINERAI);
        comb.add(Resource.ARGILE);
        combinations.add(comb);

        comb = new ArrayList<>();
        comb.add(Resource.BOIS);
        comb.add(Resource.BOIS);
        combinations.add(comb);

        assertTrue(resourceManager.updateRemainingRequiredResourcesAfterChoiceFromMatieresPremieres(inventory, leftNeighbor, rightNeighbor, combinations));
    }

    @Test
    void remainingRequiredResourcesAfterChoiceFromMatieresPremieresCant() {
        Inventory leftNeighbor = new Inventory(1);
        Inventory rightNeighbor = new Inventory(2);
        leftNeighbor.getAvailableResources()[Resource.BOIS.getIndex()]++;
        leftNeighbor.getAvailableResources()[Resource.MINERAI.getIndex()]++;

        inventory.setCoins(5);
        inventory.setAnyMatierePremiereAvailableCount(0);
        inventory.setMatieresPremieresPriceRight(1);
        inventory.setMatieresPremieresPriceLeft(2);

        List<List<Resource>> combinations = new ArrayList<>();

        ArrayList<Resource> comb = new ArrayList<>();
        comb.add(Resource.PIERRE);
        comb.add(Resource.ARGILE);
        combinations.add(comb);

        comb = new ArrayList<>();
        comb.add(Resource.MINERAI);
        comb.add(Resource.ARGILE);
        combinations.add(comb);

        comb = new ArrayList<>();
        comb.add(Resource.BOIS);
        comb.add(Resource.ARGILE);
        combinations.add(comb);

        assertFalse(resourceManager.updateRemainingRequiredResourcesAfterChoiceFromMatieresPremieres(inventory, leftNeighbor, rightNeighbor, combinations));
    }

    @Test
    void canBuild() {
        Inventory leftNeighbor = new Inventory(1);
        Inventory rightNeighbor = new Inventory(2);
        leftNeighbor.getAvailableResources()[Resource.PIERRE.getIndex()]++;
        rightNeighbor.getAvailableResources()[Resource.ARGILE.getIndex()]++;

        inventory.setCoins(5);
        inventory.setAnyMatierePremiereAvailableCount(2);
        inventory.getAvailableResources()[Resource.TISSU.getIndex()]++;
        inventory.getAvailableResources()[Resource.VERRE.getIndex()]++;
        Resource [] resources = new Resource[]{Resource.MINERAI, Resource.BOIS};
        inventory.addPairResChoice(resources);

        inventory.setMatieresPremieresPriceRight(1);
        inventory.setMatieresPremieresPriceLeft(2);

        ArrayList<Resource> requiredResources = new ArrayList<>();
        requiredResources.add(Resource.PIERRE);
        requiredResources.add(Resource.ARGILE);
        requiredResources.add(Resource.PIERRE);
        requiredResources.add(Resource.ARGILE);
        requiredResources.add(Resource.TISSU);
        requiredResources.add(Resource.VERRE);
        requiredResources.add(Resource.BOIS);

        assertTrue(resourceManager.canBuild(inventory, leftNeighbor, rightNeighbor, requiredResources));
    }

    @Test
    void cantBuildNoTissu() {
        Inventory leftNeighbor = new Inventory(1);
        Inventory rightNeighbor = new Inventory(2);
        leftNeighbor.getAvailableResources()[Resource.PIERRE.getIndex()]++;
        rightNeighbor.getAvailableResources()[Resource.ARGILE.getIndex()]++;

        inventory.setCoins(5);
        inventory.setAnyMatierePremiereAvailableCount(2);
        inventory.getAvailableResources()[Resource.VERRE.getIndex()]++;
        Resource [] resources = new Resource[]{Resource.MINERAI, Resource.BOIS};
        inventory.addPairResChoice(resources);

        inventory.setMatieresPremieresPriceRight(1);
        inventory.setMatieresPremieresPriceLeft(2);

        ArrayList<Resource> requiredResources = new ArrayList<>();
        requiredResources.add(Resource.PIERRE);
        requiredResources.add(Resource.ARGILE);
        requiredResources.add(Resource.PIERRE);
        requiredResources.add(Resource.ARGILE);
        requiredResources.add(Resource.TISSU);
        requiredResources.add(Resource.VERRE);
        requiredResources.add(Resource.BOIS);

        assertFalse(resourceManager.canBuild(inventory, leftNeighbor, rightNeighbor, requiredResources));
    }


    @Test
    void cantBuildNoMoney() {
        Inventory leftNeighbor = new Inventory(1);
        Inventory rightNeighbor = new Inventory(2);
        leftNeighbor.getAvailableResources()[Resource.PIERRE.getIndex()]++;
        rightNeighbor.getAvailableResources()[Resource.ARGILE.getIndex()]++;

        inventory.setCoins(1);
        inventory.setAnyMatierePremiereAvailableCount(2);
        inventory.getAvailableResources()[Resource.VERRE.getIndex()]++;
        Resource [] resources = new Resource[]{Resource.MINERAI, Resource.BOIS};
        inventory.addPairResChoice(resources);

        inventory.setMatieresPremieresPriceRight(1);
        inventory.setMatieresPremieresPriceLeft(2);

        ArrayList<Resource> requiredResources = new ArrayList<>();
        requiredResources.add(Resource.PIERRE);
        requiredResources.add(Resource.ARGILE);
        requiredResources.add(Resource.PIERRE);
        requiredResources.add(Resource.ARGILE);
        requiredResources.add(Resource.VERRE);
        requiredResources.add(Resource.BOIS);

        assertFalse(resourceManager.canBuild(inventory, leftNeighbor, rightNeighbor, requiredResources));
    }

    @Test
    void canBuildTwoPairs() {
        Inventory leftNeighbor = new Inventory(1);
        Inventory rightNeighbor = new Inventory(2);
        leftNeighbor.getAvailableResources()[Resource.PIERRE.getIndex()]+=2;
        rightNeighbor.getAvailableResources()[Resource.ARGILE.getIndex()]+=2;

        inventory.setCoins(10);
        inventory.setAnyMatierePremiereAvailableCount(2);
        inventory.getAvailableResources()[Resource.TISSU.getIndex()]++;
        inventory.getAvailableResources()[Resource.VERRE.getIndex()]++;

        Resource [] resources = new Resource[]{Resource.MINERAI, Resource.BOIS};
        inventory.addPairResChoice(resources);
        resources = new Resource[]{Resource.MINERAI, Resource.ARGILE};
        inventory.addPairResChoice(resources);

        inventory.setMatieresPremieresPriceRight(1);
        inventory.setMatieresPremieresPriceLeft(2);

        ArrayList<Resource> requiredResources = new ArrayList<>();
        requiredResources.add(Resource.PIERRE);
        requiredResources.add(Resource.ARGILE);
        requiredResources.add(Resource.PIERRE);
        requiredResources.add(Resource.ARGILE);
        requiredResources.add(Resource.ARGILE);
        requiredResources.add(Resource.TISSU);
        requiredResources.add(Resource.VERRE);
        requiredResources.add(Resource.BOIS);

        assertTrue(resourceManager.canBuild(inventory, leftNeighbor, rightNeighbor, requiredResources));
    }

    @Test
    void canBuildTwoPairsChooseAny() {
        Inventory leftNeighbor = new Inventory(1);
        Inventory rightNeighbor = new Inventory(2);
        leftNeighbor.getAvailableResources()[Resource.PIERRE.getIndex()]++;
        rightNeighbor.getAvailableResources()[Resource.ARGILE.getIndex()]++;

        inventory.setCoins(10);
        inventory.setAnyMatierePremiereAvailableCount(2);
        inventory.getAvailableResources()[Resource.TISSU.getIndex()]++;
        inventory.getAvailableResources()[Resource.VERRE.getIndex()]++;

        Resource [] resources = new Resource[]{Resource.MINERAI, Resource.BOIS};
        inventory.addPairResChoice(resources);
        resources = new Resource[]{Resource.MINERAI, Resource.ARGILE};
        inventory.addPairResChoice(resources);
        resources = new Resource[]{Resource.MINERAI, Resource.BOIS};
        inventory.addPairResChoice(resources);
        resources = new Resource[]{Resource.MINERAI, Resource.ARGILE};
        inventory.addPairResChoice(resources);

        inventory.setMatieresPremieresPriceRight(1);
        inventory.setMatieresPremieresPriceLeft(2);

        ArrayList<Resource> requiredResources = new ArrayList<>();
        requiredResources.add(Resource.PIERRE);
        requiredResources.add(Resource.ARGILE);
        requiredResources.add(Resource.PIERRE);
        requiredResources.add(Resource.ARGILE);
        requiredResources.add(Resource.ARGILE);
        requiredResources.add(Resource.TISSU);
        requiredResources.add(Resource.VERRE);
        requiredResources.add(Resource.BOIS);

        assertTrue(resourceManager.canBuild(inventory, leftNeighbor, rightNeighbor, requiredResources));
    }
}
