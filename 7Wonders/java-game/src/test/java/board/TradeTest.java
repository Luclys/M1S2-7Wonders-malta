package board;

import gameelements.Inventory;
import gameelements.SoutConsole;
import gameelements.enums.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TradeTest {
    private Trade trade;
    private ArrayList<Resource> list = new ArrayList<>();
    private Inventory player, rightNeighbor, leftNeighbor;


    @BeforeEach
    public void setUp() {
        trade = new Trade(new SoutConsole(false));
        player = new Inventory(0);
        rightNeighbor = new Inventory(1);
        leftNeighbor = new Inventory(2);
    }

    @Test
    public void saleResourcesTest() {

        trade.saleResources(list, player, rightNeighbor, leftNeighbor);

        assertEquals(3, player.getCoins());
        assertEquals(3, rightNeighbor.getCoins());
        assertEquals(3, leftNeighbor.getCoins());

        list.add(Resource.ARGILE);
        list.add(Resource.VERRE);

        trade.saleResources(list, player, rightNeighbor, leftNeighbor);

        assertEquals(3, player.getCoins());
        assertEquals(3, rightNeighbor.getCoins());
        assertEquals(3, leftNeighbor.getCoins());

        leftNeighbor.getAvailableResources()[Resource.ARGILE.getIndex()]++;

        trade.saleResources(list, player, rightNeighbor, leftNeighbor);

        assertEquals(3, player.getCoins());
        assertEquals(3, rightNeighbor.getCoins());
        assertEquals(3, leftNeighbor.getCoins());

        rightNeighbor.getAvailableResources()[Resource.VERRE.getIndex()]++;

        player.addCoins(1);

        trade.saleResources(list, player, rightNeighbor, leftNeighbor);

        assertNotEquals(4, player.getCoins());
        assertEquals(3, rightNeighbor.getCoins());
        assertEquals(3, leftNeighbor.getCoins());
        assertNotEquals(0, leftNeighbor.getAddedCoins());
        assertNotEquals(0, rightNeighbor.getAddedCoins());
    }


    @Test
    public void findSellerTest() {
        Inventory neighbor = trade.findSeller(Resource.ARGILE, rightNeighbor, leftNeighbor);

        assertNull(neighbor);

        rightNeighbor.getAvailableResources()[Resource.ARGILE.getIndex()]++;

        neighbor = trade.findSeller(Resource.ARGILE, rightNeighbor, leftNeighbor);

        assertEquals(rightNeighbor, neighbor);

        rightNeighbor.getAvailableResources()[Resource.ARGILE.getIndex()]--;
        leftNeighbor.getAvailableResources()[Resource.ARGILE.getIndex()]++;
        neighbor = trade.findSeller(Resource.ARGILE, rightNeighbor, leftNeighbor);

        assertEquals(leftNeighbor, neighbor);
        rightNeighbor.getAvailableResources()[Resource.ARGILE.getIndex()]++;
        neighbor = trade.findSeller(Resource.ARGILE, rightNeighbor, leftNeighbor);

        assertEquals(rightNeighbor, neighbor);
    }

    @Test
    void buyFromNeighborTest() {
        assertEquals(3, player.getCoins());
        assertEquals(3, rightNeighbor.getCoins());

        assertEquals(0, player.getAddedCoins());
        assertEquals(0, rightNeighbor.getAddedCoins());

        trade.buyFromNeighbor(player, rightNeighbor, true);

        assertEquals(1, player.getCoins());
        assertEquals(3, rightNeighbor.getCoins());

        assertEquals(0, player.getAddedCoins());
        assertEquals(2, rightNeighbor.getAddedCoins());
    }
}
