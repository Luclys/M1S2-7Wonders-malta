package board;

import statistic.DetailedResults;
import gameelements.Inventory;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RanksTest {
    ArrayList<Inventory> playerInventoryList = new ArrayList<>();


    /*
     * Simple denseRanking score test.
     * */
    @Test
    void condenseRankingTest() {
        Inventory inv0 = new Inventory(0);
        inv0.setDetailedResults(new DetailedResults());
        Inventory inv1 = new Inventory(1);
        inv1.setDetailedResults(new DetailedResults());
        Inventory inv2 = new Inventory(2);
        inv2.setDetailedResults(new DetailedResults());
        playerInventoryList.add(inv0);
        playerInventoryList.add(inv1);
        playerInventoryList.add(inv2);

        inv0.setScore(15);
        inv1.setScore(10);
        inv2.setScore(5);

        Board.denseRanking(playerInventoryList);

        assertEquals(1, inv0.getRank());
        assertEquals(2, inv1.getRank());
        assertEquals(3, inv2.getRank());
    }

    /*
     * We test that players wich have the same score have the same rank
     * */
    @Test
    void condenseRankingTestScoreEqual1() {
        Inventory inv0 = new Inventory(0);
        inv0.setDetailedResults(new DetailedResults());
        Inventory inv1 = new Inventory(1);
        inv1.setDetailedResults(new DetailedResults());
        Inventory inv2 = new Inventory(2);
        inv2.setDetailedResults(new DetailedResults());
        playerInventoryList.add(inv0);
        playerInventoryList.add(inv1);
        playerInventoryList.add(inv2);

        inv0.setScore(15);
        inv1.setScore(5);
        inv2.setScore(5);

        Board.denseRanking(playerInventoryList);

        assertEquals(2, inv1.getRank());
        assertEquals(2, inv2.getRank());
    }

    /*
     * We test that players wich have the same score have the same rank even when presented in disorder
     * */
    @Test
    void condenseRankingTestScoreEqualDisordered() {
        Inventory inv0 = new Inventory(0);
        inv0.setDetailedResults(new DetailedResults());
        Inventory inv1 = new Inventory(1);
        inv1.setDetailedResults(new DetailedResults());
        Inventory inv2 = new Inventory(2);
        inv2.setDetailedResults(new DetailedResults());
        Inventory inv3 = new Inventory(3);
        inv3.setDetailedResults(new DetailedResults());
        playerInventoryList.add(inv0);
        playerInventoryList.add(inv1);
        playerInventoryList.add(inv2);
        playerInventoryList.add(inv3);

        inv0.setScore(15);
        inv1.setScore(5);
        inv2.setScore(5);
        inv3.setScore(10);

        Board.denseRanking(playerInventoryList);

        assertEquals(1, inv0.getRank());
        assertEquals(3, inv1.getRank());
        assertEquals(3, inv2.getRank());
        assertEquals(2, inv3.getRank());
    }

    /*
     * We test that players wich have the same score and the same number of coins have the same rank
     * */
    @Test
    void condenseRankingTestCoinsEqual() {
        Inventory inv0 = new Inventory(0);
        inv0.setDetailedResults(new DetailedResults());
        Inventory inv1 = new Inventory(1);
        inv1.setDetailedResults(new DetailedResults());
        Inventory inv2 = new Inventory(2);
        inv2.setDetailedResults(new DetailedResults());
        Inventory inv3 = new Inventory(3);
        inv3.setDetailedResults(new DetailedResults());
        playerInventoryList.add(inv0);
        playerInventoryList.add(inv1);
        playerInventoryList.add(inv2);
        playerInventoryList.add(inv3);

        inv0.setScore(15);

        inv1.setScore(10);
        inv1.setCoins(10);
        inv2.setScore(10);
        inv2.setCoins(10);

        inv3.setScore(11);
        inv3.setCoins(20);

        Board.denseRanking(playerInventoryList);

        assertEquals(1, inv0.getRank());
        assertEquals(3, inv1.getRank());
        assertEquals(3, inv2.getRank());
        assertEquals(2, inv3.getRank());

    }

    /*
     * We test that players wich have the same score and a different number of coins don't have the same rank
     * */
    @Test
    void condenseRankingTestCoinsDifferent() {
        Inventory inv0 = new Inventory(0);
        inv0.setDetailedResults(new DetailedResults());
        Inventory inv1 = new Inventory(1);
        inv1.setDetailedResults(new DetailedResults());
        Inventory inv2 = new Inventory(2);
        inv2.setDetailedResults(new DetailedResults());
        playerInventoryList.add(inv0);
        playerInventoryList.add(inv1);
        playerInventoryList.add(inv2);

        inv0.setScore(15);

        inv1.setScore(10);
        inv1.setCoins(10);

        inv2.setScore(10);
        inv2.setCoins(15);

        Board.denseRanking(playerInventoryList);

        assertEquals(1, inv0.getRank());
        assertEquals(3, inv1.getRank());
        assertEquals(2, inv2.getRank());
    }

}


