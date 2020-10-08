package board;

import gameelements.Inventory;
import gameelements.Player;
import gameelements.cards.Card;
import gameelements.cards.CardsSet;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class PlayerTest {

    final ArrayList<Card> cards = new ArrayList<>(7);
    private Player player;
    private Inventory inv, playerRightNeighbor, playerLeftNeighbor;


    @Test
    public void chooseCardTest() {
        player = new Player(0);
        inv = new Inventory(0);
        playerRightNeighbor = new Inventory(1);
        playerLeftNeighbor = new Inventory(2);
        cards.add(CardsSet.BIBLIOTHÈQUE);
        cards.add(CardsSet.BIBLIOTHÈQUE);
        cards.add(CardsSet.THÉÂTRE);
        inv.setCardsInHand(cards);
        assertEquals(CardsSet.BIBLIOTHÈQUE, player.chooseCard(inv));
        inv.updateInventory(CardsSet.BIBLIOTHÈQUE, player, playerRightNeighbor, playerLeftNeighbor);
        assertEquals(CardsSet.THÉÂTRE, player.chooseCard(inv));
    }

    /*
    @BeforeEach
    public void setUp() {
        playerList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Player player = new Player(i);
            playerList.add(player);
        }
        board = new Board(playerList, false);
        player = board.getPlayerList().get(0);
        inv = board.getPlayerInventoryList().get(player.getId());
        for (int i = 0; i < 7; i++) {
            cards.add(new Card("DUMMY", new Effect[]{new ScoreEffect(1), new ResourceEffect(Resource.BOIS, 1)}, null, Category.BATIMENT_CIVIL));
        }
        inv.setCardsInHand(cards);
    }

    @Test
    public void playCardTest() {
        assertEquals(0, inv.getScore());
        Card playedCard = player.chooseCard(inv);
        inv.updateInventory(playedCard, player, null, null);
        assertEquals(1, inv.getScore());
        assertNotEquals(inv.getCardsInHand().size(), new Inventory(2).getCardsInHand().size());


        //test if player can't build two identical buildings
        Card taverne = CardsSet.TAVERNE;
        inv.getCardsInHand().set(0, taverne);
        inv.updateInventory(player.chooseCard(inv), player, null, null);
        inv.getCardsInHand().set(0, taverne);
        assertNotEquals(taverne,player.chooseCard(inv));
    }

    @Test
    public void updateAvailableResourcesTest() {
        assertEquals(0, inv.getAvailableResources()[Resource.BOIS.getIndex()]);
        inv.setCardsInHand(cards);
        inv.updateInventory(player.chooseCard(inv));
        assertEquals(1, inv.getAvailableResources()[Resource.BOIS.getIndex()]);
        assertEquals(0, inv.getAvailableResources()[Resource.MINERAI.getIndex()]);
    }
*/
}