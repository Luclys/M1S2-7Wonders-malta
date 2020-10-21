package board;

import gameelements.Inventory;
import gameelements.Player;
import gameelements.cards.Card;
import gameelements.cards.CardsSet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class PlayerTest {

    final List<Card> cards = new ArrayList<>(7);
    private Player player;
    private Inventory inv, playerRightNeighbor, playerLeftNeighbor;
    private Board board;

    @Test
    void chooseCardTest() {
        player = new Player(0);
        inv = new Inventory(0);
        playerRightNeighbor = new Inventory(1);
        playerLeftNeighbor = new Inventory(2);
        cards.add(CardsSet.BIBLIOTHEQUE);
        cards.add(CardsSet.BIBLIOTHEQUE);
        cards.add(CardsSet.THEATRE);
        inv.setCardsInHand(cards);
        assertEquals(CardsSet.BIBLIOTHEQUE, player.chooseCard(inv));
        inv.updateInventory(CardsSet.BIBLIOTHEQUE, player, playerRightNeighbor, playerLeftNeighbor);
        assertEquals(CardsSet.THEATRE, player.chooseCard(inv));
    }

    /*
    @BeforeEach
     void setUp() {
        ArrayList<Player> playerList = new ArrayList<>();
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
     void playCardTest() {
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
     void updateAvailableResourcesTest() {
        assertEquals(0, inv.getAvailableResources()[Resource.BOIS.getIndex()]);
        inv.setCardsInHand(cards);
        inv.updateInventory(player.chooseCard(inv));
        assertEquals(1, inv.getAvailableResources()[Resource.BOIS.getIndex()]);
    }
*/
}