package board;

import statistic.DetailedResults;
import gameelements.GameLogger;
import gameelements.Inventory;
import gameelements.enums.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TradeTest {
    private Trade trade;
    private List<Resource> list;
    private Inventory player, rightNeighbor, leftNeighbor;


    @BeforeEach
    void setUp() {
        trade = new Trade(new GameLogger(false));
        player = new Inventory(0);
        player.setDetailedResults(new DetailedResults());
        rightNeighbor = new Inventory(1);
        leftNeighbor = new Inventory(2);
        list = new ArrayList<>();
    }

    /**
     * Player tries to buy resources, but none of the neighbors has it
     *
     * @result buyResources must return false and the coins of all players must remain unchanged
     */
    @Test
    void buyResourcesWhenNoResourcesTest() {
        list.add(Resource.ARGILE);

        int playerInitialCoins = player.getCoins();
        int leftNeighborInitialAddedCoins = leftNeighbor.getAddedCoins();
        int rightNeighborInitialAddedCoins = rightNeighbor.getAddedCoins();

        assertFalse(trade.buyResources(list, player, rightNeighbor, leftNeighbor));
        assertEquals(playerInitialCoins, player.getCoins());
        assertEquals(leftNeighborInitialAddedCoins, leftNeighbor.getAddedCoins());
        assertEquals(rightNeighborInitialAddedCoins, rightNeighbor.getAddedCoins());
    }

    /**
     * Player tries to buy resources, only right neighbor has it and player has enough coins
     *
     * @result buyResources must return true, player must have less coins, rightNeighbor must have more coins and
     * leftNeighbor's coins are unchanged
     */
    @Test
    void buyResourcesFromRightNeighborTest() {
        list.add(Resource.ARGILE);
        rightNeighbor.getAvailableResources()[Resource.ARGILE.getIndex()]++;
        player.setMatieresPremieresPriceRight(3);

        int playerInitialCoins = player.getCoins();
        int leftNeighborInitialAddedCoins = leftNeighbor.getAddedCoins();
        int rightNeighborInitialAddedCoins = rightNeighbor.getAddedCoins();

        assertTrue(trade.buyResources(list, player, rightNeighbor, leftNeighbor));
        assertEquals(playerInitialCoins - player.getMatieresPremieresPriceRight(), player.getCoins());
        assertEquals(leftNeighborInitialAddedCoins, leftNeighbor.getAddedCoins());
        assertEquals(rightNeighborInitialAddedCoins + player.getMatieresPremieresPriceRight(), rightNeighbor.getAddedCoins());
    }

    /**
     * Player tries to buy resources,
     * both neighbors have it and player has enough coins,
     * leftPlayer has less coins than rightPlayer,
     * Matieres Premieres prices are equal
     *
     * @result buyResources must return true, player must have less coins, leftPlayer must have more coins and
     * rightPlayer's coins are unchanged
     */
    @Test
    void buyResourcesFromNeighborWithLessCoinsTest() {
        list.add(Resource.ARGILE);
        rightNeighbor.getAvailableResources()[Resource.ARGILE.getIndex()]++;
        leftNeighbor.getAvailableResources()[Resource.ARGILE.getIndex()]++;
        rightNeighbor.setCoins(5);
        leftNeighbor.setCoins(2);

        player.setMatieresPremieresPriceRight(3);
        player.setMatieresPremieresPriceLeft(3);

        int playerInitialCoins = player.getCoins();
        int leftNeighborInitialAddedCoins = leftNeighbor.getAddedCoins();
        int rightNeighborInitialAddedCoins = rightNeighbor.getAddedCoins();

        assertTrue(trade.buyResources(list, player, rightNeighbor, leftNeighbor));
        assertEquals(playerInitialCoins - player.getMatieresPremieresPriceLeft(), player.getCoins());
        assertEquals(leftNeighborInitialAddedCoins + player.getMatieresPremieresPriceLeft(), leftNeighbor.getAddedCoins());
        assertEquals(rightNeighborInitialAddedCoins, rightNeighbor.getAddedCoins());
    }

    /**
     * Player tries to buy resources,
     * both neighbors have it,
     * player has enough coins,
     * leftPlayer and rightPlayer has the same amount of coins,
     * Matieres Premieres prices are equal
     *
     * @result buyResources must return true, player must have less coins, leftPlayer must have more coins and
     * rightPlayer's coins are unchanged
     */
    @Test
    void buyResourcesFromDefaultNeighborTest() {
        list.add(Resource.ARGILE);
        rightNeighbor.getAvailableResources()[Resource.ARGILE.getIndex()]++;
        leftNeighbor.getAvailableResources()[Resource.ARGILE.getIndex()]++;
        rightNeighbor.setCoins(5);
        leftNeighbor.setCoins(5);

        player.setMatieresPremieresPriceRight(3);
        player.setMatieresPremieresPriceLeft(3);

        int playerInitialCoins = player.getCoins();
        int leftNeighborInitialAddedCoins = leftNeighbor.getAddedCoins();
        int rightNeighborInitialAddedCoins = rightNeighbor.getAddedCoins();

        assertTrue(trade.buyResources(list, player, rightNeighbor, leftNeighbor));
        assertEquals(playerInitialCoins - player.getMatieresPremieresPriceLeft(), player.getCoins());
        assertEquals(leftNeighborInitialAddedCoins + player.getMatieresPremieresPriceLeft(), leftNeighbor.getAddedCoins());
        assertEquals(rightNeighborInitialAddedCoins, rightNeighbor.getAddedCoins());
    }

    /**
     * Player tries to buy resources,
     * both neighbors have it,
     * player has enough coins,
     * rightPlayer's Matieres Premieres price is less than leftPlayer's
     *
     * @result buyResources must return true, player must have less coins, rightPlayer must have more coins and
     * leftPlayer's coins are unchanged
     */
    @Test
    void buyResourcesFromNeighborWithLessPriceTest() {
        list.add(Resource.ARGILE);
        rightNeighbor.getAvailableResources()[Resource.ARGILE.getIndex()]++;
        leftNeighbor.getAvailableResources()[Resource.ARGILE.getIndex()]++;
        rightNeighbor.setCoins(5);
        leftNeighbor.setCoins(5);

        player.setMatieresPremieresPriceRight(1);
        player.setMatieresPremieresPriceLeft(3);

        int playerInitialCoins = player.getCoins();
        int leftNeighborInitialAddedCoins = leftNeighbor.getAddedCoins();
        int rightNeighborInitialAddedCoins = rightNeighbor.getAddedCoins();

        assertTrue(trade.buyResources(list, player, rightNeighbor, leftNeighbor));
        assertEquals(playerInitialCoins - player.getMatieresPremieresPriceRight(), player.getCoins());
        assertEquals(leftNeighborInitialAddedCoins, leftNeighbor.getAddedCoins());
        assertEquals(rightNeighborInitialAddedCoins + player.getMatieresPremieresPriceRight(), rightNeighbor.getAddedCoins());
    }

    /**
     * Player tries to buy 2 identical resources,
     * each neighbor has one,
     * player has enough coins,
     * rightPlayer's Matieres Premieres price is less than leftPlayer's
     *
     * @result buyResources must return true, player must have less coins, rightPlayer must have more coins and
     * leftPlayer's must have more coins
     */
    @Test
    void buyResourcesOneFromEachNeighborPriceTest() {
        list.add(Resource.ARGILE);
        list.add(Resource.ARGILE);
        rightNeighbor.getAvailableResources()[Resource.ARGILE.getIndex()]++;
        leftNeighbor.getAvailableResources()[Resource.ARGILE.getIndex()]++;
        rightNeighbor.setCoins(5);
        leftNeighbor.setCoins(5);

        player.setMatieresPremieresPriceRight(1);
        player.setMatieresPremieresPriceLeft(2);
        player.setCoins(3);

        int playerInitialCoins = player.getCoins();
        int leftNeighborInitialAddedCoins = leftNeighbor.getAddedCoins();
        int rightNeighborInitialAddedCoins = rightNeighbor.getAddedCoins();

        assertTrue(trade.buyResources(list, player, rightNeighbor, leftNeighbor));
        assertEquals(playerInitialCoins - player.getMatieresPremieresPriceRight() - player.getMatieresPremieresPriceLeft(), player.getCoins());
        assertEquals(leftNeighborInitialAddedCoins + player.getMatieresPremieresPriceLeft(), leftNeighbor.getAddedCoins());
        assertEquals(rightNeighborInitialAddedCoins + player.getMatieresPremieresPriceRight(), rightNeighbor.getAddedCoins());
    }

    /**
     * Player tries to buy 2 identical resources,
     * each neighbor has one,
     * player has enough coins only for one,
     * rightPlayer's Matieres Premieres price is less than leftPlayer's
     *
     * @result buyResources must return false, the coins of all players must remain unchanged
     */
    @Test
    void buyResourcesNotEnoughCoinsForSecondResourceTest() {
        list.add(Resource.ARGILE);
        list.add(Resource.ARGILE);
        rightNeighbor.getAvailableResources()[Resource.ARGILE.getIndex()]++;
        leftNeighbor.getAvailableResources()[Resource.ARGILE.getIndex()]++;
        rightNeighbor.setCoins(5);
        leftNeighbor.setCoins(5);

        player.setMatieresPremieresPriceRight(1);
        player.setMatieresPremieresPriceLeft(3);
        player.setCoins(1);

        int playerInitialCoins = player.getCoins();
        int leftNeighborInitialAddedCoins = leftNeighbor.getAddedCoins();
        int rightNeighborInitialAddedCoins = rightNeighbor.getAddedCoins();

        assertFalse(trade.buyResources(list, player, rightNeighbor, leftNeighbor));
        assertEquals(playerInitialCoins, player.getCoins());
        assertEquals(leftNeighborInitialAddedCoins, leftNeighbor.getAddedCoins());
        assertEquals(rightNeighborInitialAddedCoins, rightNeighbor.getAddedCoins());
    }

    /**
     * Player tries to buy 3 resources,
     * leftNeighbor has first and second, rightNeighbor has second and third,
     * player has enough coins,
     * rightPlayer's Matieres Premieres price is less than leftPlayer's
     *
     * @result buyResources must return true, play must have less coins, rightNeighbor has more coins (2 resources sold),
     * leftNeighbor has more coins (1 resource sold)
     */
    @Test
    void buyResourcesSeveralResourcesWithOneNeighborChoiceByPriceTest() {
        list.add(Resource.ARGILE);
        list.add(Resource.BOIS);
        list.add(Resource.MINERAI);
        leftNeighbor.getAvailableResources()[Resource.ARGILE.getIndex()]++;
        rightNeighbor.getAvailableResources()[Resource.BOIS.getIndex()]++;
        leftNeighbor.getAvailableResources()[Resource.BOIS.getIndex()]++;
        rightNeighbor.getAvailableResources()[Resource.MINERAI.getIndex()]++;
        rightNeighbor.setCoins(5);
        leftNeighbor.setCoins(5);

        player.setMatieresPremieresPriceRight(1);
        player.setMatieresPremieresPriceLeft(3);
        player.setCoins(5);

        int playerInitialCoins = player.getCoins();
        int leftNeighborInitialAddedCoins = leftNeighbor.getAddedCoins();
        int rightNeighborInitialAddedCoins = rightNeighbor.getAddedCoins();

        assertTrue(trade.buyResources(list, player, rightNeighbor, leftNeighbor));
        assertEquals(playerInitialCoins - 2 * player.getMatieresPremieresPriceRight() - player.getMatieresPremieresPriceLeft(), player.getCoins());
        assertEquals(leftNeighborInitialAddedCoins + player.getMatieresPremieresPriceLeft(), leftNeighbor.getAddedCoins());
        assertEquals(rightNeighborInitialAddedCoins + 2 * player.getMatieresPremieresPriceRight(), rightNeighbor.getAddedCoins());
    }

    /**
     * Player tries to buy 2 Matieres Premieres and 1 Produit Manifacture,
     * both neighbors have all resources,
     * player has enough coins,
     * rightPlayer's Matieres Premieres price is less than leftPlayer's
     * leftPlayer has less coins than rightPlayer
     *
     * @result buyResources must return false, play must have less coins, rightNeighbor has more coins (2 Matieres Premieres sold),
     * leftNeighbor has more coins (1 Produit Manifacture sold)
     */
    @Test
    void buyResourcesSeveralResourcesWithChoiceByPriceAndChoiceByCoinsTest() {
        list.add(Resource.ARGILE);
        list.add(Resource.BOIS);
        list.add(Resource.TISSU);
        leftNeighbor.getAvailableResources()[Resource.ARGILE.getIndex()]++;
        rightNeighbor.getAvailableResources()[Resource.ARGILE.getIndex()]++;
        leftNeighbor.getAvailableResources()[Resource.BOIS.getIndex()]++;
        rightNeighbor.getAvailableResources()[Resource.BOIS.getIndex()]++;
        leftNeighbor.getAvailableResources()[Resource.TISSU.getIndex()]++;
        rightNeighbor.getAvailableResources()[Resource.TISSU.getIndex()]++;
        rightNeighbor.setCoins(5);
        leftNeighbor.setCoins(3);

        player.setMatieresPremieresPriceRight(1);
        player.setMatieresPremieresPriceLeft(3);
        player.setProduitsManifacturesPrice(4);
        player.setCoins(6);

        int playerInitialCoins = player.getCoins();
        int leftNeighborInitialAddedCoins = leftNeighbor.getAddedCoins();
        int rightNeighborInitialAddedCoins = rightNeighbor.getAddedCoins();

        assertTrue(trade.buyResources(list, player, rightNeighbor, leftNeighbor));
        assertEquals(playerInitialCoins - 2 * player.getMatieresPremieresPriceRight() - player.getProduitsManifacturesPrice(), player.getCoins());
        assertEquals(leftNeighborInitialAddedCoins + player.getProduitsManifacturesPrice(), leftNeighbor.getAddedCoins());
        assertEquals(rightNeighborInitialAddedCoins + 2 * player.getMatieresPremieresPriceRight(), rightNeighbor.getAddedCoins());
    }

    /**
     * Player tries to buy 2 Matieres Premieres and 1 Produit Manifacture,
     * both neighbors have all resources,
     * player has enough coins only for Matieres Premieres,
     * rightPlayer's Matieres Premieres price is less than leftPlayer's
     * leftPlayer has less coins than rightPlayer
     *
     * @result buyResources must return false, the coins of all players must remain unchanged
     */
    @Test
    void buyResourcesSeveralResourcesNotEnoughMoneyForProduitManifactureTest() {
        list.add(Resource.ARGILE);
        list.add(Resource.BOIS);
        list.add(Resource.TISSU);
        leftNeighbor.getAvailableResources()[Resource.ARGILE.getIndex()]++;
        rightNeighbor.getAvailableResources()[Resource.ARGILE.getIndex()]++;
        leftNeighbor.getAvailableResources()[Resource.BOIS.getIndex()]++;
        rightNeighbor.getAvailableResources()[Resource.BOIS.getIndex()]++;
        leftNeighbor.getAvailableResources()[Resource.TISSU.getIndex()]++;
        rightNeighbor.getAvailableResources()[Resource.TISSU.getIndex()]++;
        rightNeighbor.setCoins(5);
        leftNeighbor.setCoins(3);

        player.setMatieresPremieresPriceRight(1);
        player.setMatieresPremieresPriceLeft(3);
        player.setProduitsManifacturesPrice(4);
        player.setCoins(4);

        int playerInitialCoins = player.getCoins();
        int leftNeighborInitialAddedCoins = leftNeighbor.getAddedCoins();
        int rightNeighborInitialAddedCoins = rightNeighbor.getAddedCoins();

        assertFalse(trade.buyResources(list, player, rightNeighbor, leftNeighbor));
        assertEquals(playerInitialCoins, player.getCoins());
        assertEquals(leftNeighborInitialAddedCoins, leftNeighbor.getAddedCoins());
        assertEquals(rightNeighborInitialAddedCoins, rightNeighbor.getAddedCoins());
    }

    /**
     * Choose seller by amount of coins, right neighbor has less coins than left
     *
     * @result chooseNeighbor must return rightNeighbor
     */
    @Test
    void chooseNeighborRightHasLessCoinsTest() {
        rightNeighbor.setCoins(5);
        leftNeighbor.setCoins(7);
        assertEquals(trade.chooseNeighbor(rightNeighbor, leftNeighbor), rightNeighbor);
    }

    /**
     * Choose seller by amount of coins, right neighbor has more coins than left
     *
     * @result chooseNeighbor must return leftNeighbor
     */
    @Test
    void chooseNeighborLeftHasLessCoinsTest() {
        rightNeighbor.setCoins(8);
        leftNeighbor.setCoins(7);
        assertEquals(trade.chooseNeighbor(rightNeighbor, leftNeighbor), leftNeighbor);
    }

    /**
     * Choose seller by amount of coins, right neighbor has same amount of coins as left
     *
     * @result chooseNeighbor must return leftNeighbor
     */
    @Test
    void chooseNeighborNeighborsHaveSameCoinsTest() {
        rightNeighbor.setCoins(6);
        leftNeighbor.setCoins(6);
        assertEquals(trade.chooseNeighbor(rightNeighbor, leftNeighbor), leftNeighbor);
    }

    /**
     * Pay N coins to neighbor
     *
     * @result player must have N less coins, neighbor must have N more coins
     */
    @Test
    void payToNeighborTest() {
        int playerInitialCoins = 5;
        int leftNeighborInitialCoins = 5;
        player.setCoins(playerInitialCoins);
        leftNeighbor.setCoins(leftNeighborInitialCoins);

        trade.payToNeighbor(player, leftNeighbor, 2);
        assertEquals(player.getCoins(), playerInitialCoins - 2);
        assertEquals(leftNeighbor.getAddedCoins(), 2);
        assertEquals(leftNeighbor.getCoins(), leftNeighborInitialCoins);

        playerInitialCoins = player.getCoins();
        trade.payToNeighbor(player, leftNeighbor, 3);
        assertEquals(player.getCoins(), playerInitialCoins - 3);
        assertEquals(leftNeighbor.getAddedCoins(), 5);// 3 + 2
        assertEquals(leftNeighbor.getCoins(), leftNeighborInitialCoins);
    }

    @Test
    void buyResourceTest() {
        list.add(Resource.ARGILE);
        rightNeighbor.getAvailableResources()[Resource.ARGILE.getIndex()]++;
        leftNeighbor.getAvailableResources()[Resource.ARGILE.getIndex()]++;
        player.setMatieresPremieresPriceRight(3);
        player.setMatieresPremieresPriceLeft(1);
        trade.buyResources(list, player, rightNeighbor, leftNeighbor);
        assertEquals(1, leftNeighbor.getAddedCoins());
        assertEquals(0, rightNeighbor.getAddedCoins());
        list.add(Resource.ARGILE);

        player.setMatieresPremieresPriceRight(1);
        player.setMatieresPremieresPriceLeft(2);

        player.setCoins(0);
        assertFalse(trade.buyResources(list, player, rightNeighbor, leftNeighbor));
    }


    @Test
    void cantBuyResourceTest() {
        list.add(Resource.ARGILE);
        rightNeighbor.getAvailableResources()[Resource.ARGILE.getIndex()]++;
        leftNeighbor.getAvailableResources()[Resource.ARGILE.getIndex()]++;
        player.setMatieresPremieresPriceRight(6);
        player.setMatieresPremieresPriceLeft(4);
        assertFalse(trade.buyResources(list, player, rightNeighbor, leftNeighbor));
    }

    @Test
    void cantBuyResourceFromRightNeighborTest() {
        list.add(Resource.ARGILE);
        rightNeighbor.getAvailableResources()[Resource.ARGILE.getIndex()]++;
        player.setMatieresPremieresPriceRight(4);
        assertFalse(trade.buyResources(list, player, rightNeighbor, leftNeighbor));
    }

}
