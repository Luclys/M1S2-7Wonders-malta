package board;

import gameelements.Inventory;
import gameelements.Player;
import gameelements.cards.Card;
import gameelements.effects.ResourceEffect;
import gameelements.enums.Category;
import gameelements.enums.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TradeTest {
    private Card card;
    private Board board;
    private ArrayList<Player> playerList;
    @BeforeEach
    public void setUp() {
        playerList = new ArrayList<>(3);
        for (int i = 0; i < 3; i++) {
            Player player = new Player(i);
            playerList.add(player);
        }
        board = new Board(playerList, false);
        card = new Card("DUMMY", new ResourceEffect(Resource.BOIS, 1), new Resource[]{Resource.BOIS}, Category.MATIERE_PREMIERE);
    }

    @Test
    public void findSellerTest() {
        Player player = board.getPlayerList().get(0);
        Player rightNeighbor = board.getPlayerList().get(player.getRightNeighborId());
        Player leftNeighbor = board.getPlayerList().get(player.getLeftNeighborId());
        Inventory rightNeighborInv = board.getPlayerInventoryList().get(rightNeighbor.getId());
        Inventory leftNeighborInv = board.getPlayerInventoryList().get(leftNeighbor.getId());

        rightNeighborInv.getCardsInHand().add(card);
        rightNeighborInv.updateInventory(card, player, leftNeighborInv, rightNeighborInv);
        Inventory neighbourInv = board.getCommerce().findSeller(Resource.BOIS, rightNeighborInv, leftNeighborInv);
        assertEquals(rightNeighbor.getId(), neighbourInv.getPlayerId());
    }

    @Test
    void buyFromNeighborTest() {
        board.getCommerce().buyFromNeighbor(board.getPlayerInventoryList().get(0), board.getPlayerInventoryList().get(1), true);
        assertEquals(1, board.getPlayerInventoryList().get(0).getCoins());
        assertEquals(3, board.getPlayerInventoryList().get(1).getCoins());
        board.getManager().updateCoins();
        assertEquals(5, board.getPlayerInventoryList().get(1).getCoins());
    }
}
