package board;

import gameelements.Card;
import gameelements.Effect;
import gameelements.Inventory;
import gameelements.enums.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class TradeTest {
    private Card card;

    @BeforeEach
    public void setUp() {
        card = new Card("DUMMY", new Effect())
    }
    @Test
    public void findSellerTest() {
        Board board = new Board(3);
        Player rightNeighbor = board.getPlayerList().get(board.getPlayerList().get(0).getRightNeighborId());
        Player leftNeighbor = board.getPlayerList().get(board.getPlayerList().get(0).getLeftNeighborId());
        board.getPlayerInventoryList().get(rightNeighbor.getId()).updateInventory(new Card("DUMMY", ));
        Inventory neighbourInv = board.getCommerce().findSeller(Resource.BOIS, board.getPlayerInventoryList().get(rightNeighbor.getId()), board.getPlayerInventoryList().get(leftNeighbor.getId()));
    }

    @Test
    void buyFromNeighborTest() {
        Board board = new Board(3);
        board.getCommerce().buyFromNeighbor(board.getPlayerList().get(0), board.getPlayerList().get(1));
        assertEquals(1, board.getPlayerList().get(0).getCoins());
        assertEquals(5, board.getPlayerList().get(1).getCoins());
    }
}
