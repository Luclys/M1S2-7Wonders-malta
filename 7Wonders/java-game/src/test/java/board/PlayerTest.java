package board;

import gameelements.Card;
import gameelements.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class PlayerTest {

    ArrayList<Card> cards = new ArrayList<>(7);
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
    public void addCoinsTest(){
        player.addCoins(5);
        assertEquals(player.getCoins(),5);
    }

    @Test
    public void removeCoinsTest(){
        player.setCoins(5);
        player.removeCoins(3);
        assertEquals(player.getCoins(),2);
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
