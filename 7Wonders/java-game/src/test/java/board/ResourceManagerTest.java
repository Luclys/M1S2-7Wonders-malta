package board;

import gameelements.GameLogger;
import gameelements.Inventory;
import gameelements.enums.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ResourceManagerTest {
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
}
