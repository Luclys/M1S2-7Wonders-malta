package board;

import gameelements.Card;
import gameelements.Category;
import gameelements.Resource;
import io.cucumber.java8.Ca;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class PlayerTest {

    final ArrayList<Card> cards = new ArrayList<>(7);
    private Player player;

    @BeforeEach
    public void setUp() {
        player = new Player(3);
        for (int i = 0; i < 7; i++) {
            cards.add(new Card("CHANTIER", new Resource[]{Resource.BOIS}, 1, new Resource[0], Category.MATIERE_PREMIERE));
        }
        player.setCardsInHand(cards);
    }

    @Test
    public void playCardTest() {
        assertEquals(0, player.getScore());
        player.updatePlayerWithPlayedCard(player.playCard());
        assertEquals(1, player.getScore());
        assertNotEquals(player.getCardsInHand().size(), new Player(2).getCardsInHand().size());

        //test if player can't build two identical buildings
        Card taverne = new Card("TAVERNE", new Resource[0], new Resource[0], Category.BATIMENT_COMMERCIEAU);
        player.updatePlayerWithPlayedCard(taverne);
        player.getCardsInHand().set(0, taverne);
        player.playCard();
        assertEquals(player.getCardsInHand().get(0), taverne);
    }

    @Test
    public void updateAvailableResourcesTest() {
        assertEquals(0, player.getAvailableResources()[Resource.BOIS.getIndex()]);
        player.updatePlayerWithPlayedCard(player.playCard());
        assertEquals(1, player.getAvailableResources()[Resource.BOIS.getIndex()]);
        assertEquals(0, player.getAvailableResources()[Resource.MINERAI.getIndex()]);
    }

   @Test
    public void discardLastCardTest() {
        assertThrows(Error.class, () -> player.discardLastCard());
        while (player.getCardsInHand().size() > 1) {
            player.updatePlayerWithPlayedCard(player.playCard());
        }
        Card lastCard = player.getCardsInHand().get(0);
        assertSame(lastCard, player.discardLastCard());
        assertTrue(player.getCardsInHand().isEmpty());
    }

    @Test
    public void addCoinsTest(){
        player.setCoins(0);
        player.addCoins(5);
        assertEquals(5, player.getCoins());
    }

    @Test
    public void removeCoinsTest() {
        player.setCoins(5);
        player.removeCoins(3);
        assertEquals(player.getCoins(), 2);
    }

    @Test
    public void fightWithNeighborTest() {
        Card bouclierCard = new Card("BOUCLIER", new Resource[]{Resource.BOUCLIER}, 1, new Resource[0], Category.MATIERE_PREMIERE);
        Card boisCard = new Card("BOIS", new Resource[]{Resource.BOIS}, 1, new Resource[0], Category.MATIERE_PREMIERE);

        Player neighbor = new Player(2);
        cards.set(0, bouclierCard);
        neighbor.setCardsInHand(cards);
        neighbor.updatePlayerWithPlayedCard(neighbor.playCard()); //Neighbor has 1 bouclier

        //Player has less boucliers than his neighbor
        player = new Player(3);
        addCardAndPlayIt(player, boisCard); //Player has no boucliers

        player.fightWithNeighbor(neighbor, 3);
        assertEquals(player.getConflictPoints(), -1);

        //Player has same amount of boucliers than his neighbor
        player = new Player(4);
        addCardAndPlayIt(player, bouclierCard); //Player has 1 bouclier

        player.fightWithNeighbor(neighbor, 3);
        assertEquals(player.getConflictPoints(), 0);

        //Player has more boucliers than his neighbor
        player = new Player(5);
        addCardAndPlayIt(player, bouclierCard); //Player has 1 bouclier
        addCardAndPlayIt(player, bouclierCard); //Player has 2 boucliers

        player.fightWithNeighbor(neighbor, 3);
        assertEquals(player.getConflictPoints(), 3);
    }

    private void addCardAndPlayIt(Player player, Card card) {
        cards.set(0, card);
        player.setCardsInHand(cards);
        player.updatePlayerWithPlayedCard(player.playCard());
    }

    @Test
    void missingResourcesTest() {
        Card c = new Card("CHANTIER", new Resource[]{Resource.BOIS}, new Resource[]{Resource.BOIS}, Category.MATIERE_PREMIERE);
        Resource[] m = player.missingResources(c);
        assertEquals(Resource.BOIS, m[0]);
    }

    @Test
    void UpdatePlayer() {
        assertEquals(0, player.getAvailableResources()[Resource.BOIS.getIndex()]);
        player.updatePlayerWithPlayedCard(player.playCard());
        assertEquals(1, player.getAvailableResources()[Resource.BOIS.getIndex()]);
    }
}
