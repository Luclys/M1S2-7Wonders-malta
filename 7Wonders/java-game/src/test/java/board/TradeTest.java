package board;

import effects.ResourceEffect;
import gameelements.Card;
import gameelements.Inventory;
import gameelements.enums.Category;
import gameelements.enums.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class TradeTest {
    private Card card;
    private Board board;

    @BeforeEach
    public void setUp() {
        board = new Board(3);
        card = new Card("DUMMY", new ResourceEffect("", Resource.BOIS, 1), new Resource[]{Resource.BOIS}, Category.MATIERE_PREMIERE);
    }

    @Test
    public void findSellerTest() {
        Player rightNeighbor = board.getPlayerList().get(board.getPlayerList().get(0).getRightNeighborId());
        Player leftNeighbor = board.getPlayerList().get(board.getPlayerList().get(0).getLeftNeighborId());
        board.getPlayerInventoryList().get(rightNeighbor.getId()).getCardsInHand().add(card);
        board.getPlayerInventoryList().get(rightNeighbor.getId()).updateInventory(card);
        Inventory neighbourInv = board.getCommerce().findSeller(
                Resource.BOIS,
                board.getPlayerInventoryList().get(rightNeighbor.getId()),
                board.getPlayerInventoryList().get(leftNeighbor.getId()));
        // assertEquals(board.getPlayerInventoryList().get(rightNeighbor.getId()), neighbourInv);
    }

    @Test
    void buyFromNeighborTest() {
        board.getCommerce().buyFromNeighbor(board.getPlayerInventoryList().get(0), board.getPlayerInventoryList().get(1));
        assertEquals(1, board.getPlayerInventoryList().get(0).getCoins());
        assertEquals(5, board.getPlayerInventoryList().get(1).getCoins());
    }
}
