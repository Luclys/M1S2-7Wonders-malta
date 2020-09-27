package board;

import gameelements.Card;
import gameelements.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class PlayerTest {

    ArrayList<Card> cards = new ArrayList<>(7);
    private Player player;

    @BeforeEach
    public void setUp() {
        player = new Player(3);
        for (int i = 0; i < 7; i++) {
            cards.add(new Card("CHANTIER", new Resource[]{Resource.BOIS}));
        }
        player.setCards(cards);
    }

    @Test
    public void playCardTest() {
        assertEquals(player.getScore(), 0);
        player.playCard();
        assertEquals(player.getScore(), 1);
        assertNotEquals(player.getCards().size(), new Player(2).getCards().size());
    }

    @Test
    public void updateAvailableResourcesTest() {
        assertEquals(0, player.getAvailableResources()[Resource.BOIS.getIndex()]);
        player.playCard();
        assertEquals(1, player.getAvailableResources()[Resource.BOIS.getIndex()]);
        assertEquals(0, player.getAvailableResources()[Resource.MINERAI.getIndex()]);
    }

    @Test
    public void discardLastCardTest() {
        assertThrows(Error.class, () -> player.discardLastCard());
        while (player.getCards().size() > 1) {
            player.playCard();
        }
        Card lastCard = player.getCards().get(0);
        assertSame(lastCard, player.discardLastCard());
        assertTrue(player.getCards().isEmpty());
    }
}
