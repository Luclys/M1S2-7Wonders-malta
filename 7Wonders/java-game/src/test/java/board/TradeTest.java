package board;

import gameelements.Inventory;
import gameelements.enums.Resource;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TradeTest {


    @Disabled
    @Test
    public void findSalerTest() {
        Board board = new Board(3);
        //System.out.println(board.getPlayerList().get(1));
        Inventory rightNeighbor = board.getPlayerInventoryList().get(board.getPlayerList().get(0).getRightNeighborId());
        Inventory leftNeighbor = board.getPlayerInventoryList().get(board.getPlayerList().get(0).getLeftNeighborId());
        Inventory n = board.getCommerce().findSaler(Resource.ARGENT, rightNeighbor, leftNeighbor);

        //  assertEquals(n,n);
    }

    @Test
    void buyFromNeighborTest() {
        Board board = new Board(3);
        board.getCommerce().buyFromNeighbor(board.getPlayerInventoryList().get(0), board.getPlayerInventoryList().get(1));
        assertEquals(1, board.getPlayerInventoryList().get(0).getCoins());
        assertEquals(5, board.getPlayerInventoryList().get(1).getCoins());
    }
}
