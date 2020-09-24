package board;

import gameelements.Card;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class PlayerTest {

    private Player player;
    ArrayList<Card> cards = new ArrayList<>(7);

    @BeforeEach
    public void setUp() {
        player = new Player(3);
        for (int i = 0; i < 7; i++) {
            cards.add(new Card());
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
}
