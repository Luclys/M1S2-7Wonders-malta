package board;

import gameelements.Card;
import gameelements.enums.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class PlayerTest {

    final ArrayList<Card> cards = new ArrayList<>(7);
    private Player player;

    @BeforeEach
    public void setUp() {
        player = new Player(3);
        for (int i = 0; i < 7; i++) {
            cards.add(new Card("CHANTIER", new Resource[]{Resource.BOIS}, 1, new Resource[0]));
        }
        player.setCards(cards);
    }

    @Test
    public void playCardTest() {
        assertEquals(0, player.getScore());
        player.updatePlayer(player.playCard());
        assertEquals(1, player.getScore());
        assertNotEquals(player.getCards().size(), new Player(2).getCards().size());
    }

    @Test
    public void updateAvailableResourcesTest() {
        assertEquals(0, player.getAvailableResources()[Resource.BOIS.getIndex()]);
        player.updatePlayer(player.playCard());
        assertEquals(1, player.getAvailableResources()[Resource.BOIS.getIndex()]);
        assertEquals(0, player.getAvailableResources()[Resource.MINERAI.getIndex()]);
    }

    @Test
    public void discardLastCardTest() {
        assertThrows(Error.class, () -> player.discardLastCard());
        while (player.getCards().size() > 1) {
            player.updatePlayer(player.playCard());
        }
        Card lastCard = player.getCards().get(0);
        assertSame(lastCard, player.discardLastCard());
        assertTrue(player.getCards().isEmpty());
    }

    @Test
    public void addCoinsTest() {
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
        Card bouclierCard = new Card("BOUCLIER", new Resource[]{Resource.BOUCLIER}, 1, new Resource[0]);
        Card boisCard = new Card("BOIS", new Resource[]{Resource.BOIS}, 1, new Resource[0]);

        Player neighbor = new Player(2);
        cards.set(0, bouclierCard);
        neighbor.setCards(cards);
        neighbor.updatePlayer(neighbor.playCard()); //Neighbor has 1 bouclier

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
        player.setCards(cards);
        player.updatePlayer(player.playCard());
    }

    @Test
    void missingResourcesTest() {
        Card c = new Card("CHANTIER", new Resource[]{Resource.BOIS}, new Resource[]{Resource.BOIS});
        Resource[] m = player.missingResources(c);
        assertEquals(Resource.BOIS, m[0]);
    }

    @Test
    void UpdatePlayer() {
        assertEquals(0, player.getAvailableResources()[Resource.BOIS.getIndex()]);
        player.updatePlayer(player.playCard());
        assertEquals(1, player.getAvailableResources()[Resource.BOIS.getIndex()]);
    }
}
