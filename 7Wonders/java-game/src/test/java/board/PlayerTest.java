package board;

import io.cucumber.java8.Pl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class PlayerTest {

    private Player player;

    @BeforeEach
    public void setUp() {
        player = new Player(3);
    }

    @Test
    public void playCardTest() {
        assertEquals(player.getScore(), 0);
        player.playCard();
        assertEquals(player.getScore(), 1);
        assertNotEquals(player.getCardArrayList().size(), new Player(2).getCardArrayList().size());
    }

    @Test
    public void setCardTest() {

    }
}
