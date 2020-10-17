package board;

import gameelements.Player;
import gameelements.ages.Age;
import gameelements.ages.AgeI;
import gameelements.ages.AgeII;
import gameelements.ages.AgeIII;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AgesTest {

    Age age;
    private int playersCount;
    private Board board;
    private List<Player> playerList;

    @Test
    void initiateCardsTest() {
        age = new AgeI();
        assertThrows(IllegalStateException.class, () -> age.initiateCards(8));
        assertThrows(IllegalStateException.class, () -> age.initiateCards(1));
        assertEquals(21, age.initiateCards(3).size());
        assertEquals(28, age.initiateCards(4).size());
        assertEquals(35, age.initiateCards(5).size());
        assertEquals(42, age.initiateCards(6).size());
        assertEquals(49, age.initiateCards(7).size());
        age = new AgeII();

        assertThrows(IllegalStateException.class, () -> age.initiateCards(8));
        assertThrows(IllegalStateException.class, () -> age.initiateCards(1));
        assertEquals(21, age.initiateCards(3).size());
        assertEquals(28, age.initiateCards(4).size());
        assertEquals(35, age.initiateCards(5).size());
        assertEquals(42, age.initiateCards(6).size());
        assertEquals(49, age.initiateCards(7).size());
        age = new AgeIII();
        assertThrows(IllegalStateException.class, () -> age.initiateCards(8));
        assertThrows(IllegalStateException.class, () -> age.initiateCards(1));
        assertEquals(21, age.initiateCards(3).size());
        assertEquals(28, age.initiateCards(4).size());
        assertEquals(35, age.initiateCards(5).size());
        assertEquals(42, age.initiateCards(6).size());
        assertEquals(49, age.initiateCards(7).size());
    }
}
