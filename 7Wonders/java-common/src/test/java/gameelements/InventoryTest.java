package gameelements;

import gameelements.cards.Card;
import gameelements.cards.CardsSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {
    Inventory inventory;

    @BeforeEach
     void setUp() {
        inventory = new Inventory(0);
    }

    @Test
     void discardLastCardTest() {
        // the method throw an erreur if the number of cards in hand of the
        // inventory is more then 1
        assertThrows(Error.class, () -> inventory.discardLastCard());
        // test if the number of cards is 1
        setCards();
        assertSame(inventory.getCardsInHand().get(0), inventory.discardLastCard());
        assertTrue(inventory.getCardsInHand().isEmpty());
    }

    @Test
     void sellCardTest() {
        // if the inventory try to sell a card that he doesn"t have an erreur is lanched
        assertThrows(Error.class, () -> inventory.sellCard(CardsSet.CHANTIER));
        int coins = inventory.getCoins();
        setCards();
        // if the inventory try to sell a card that he has then he gets 3 coins
        inventory.sellCard(CardsSet.CHANTIER);
        assertEquals(coins + 3, inventory.getCoins());
    }

    @Test
     void updateInventoryTest() {
        setCards();
        assertEquals(CardsSet.CHANTIER, inventory.getCardsInHand().get(0));
        inventory.updateInventory(CardsSet.CHANTIER,null,null,null);
        assertEquals(0, inventory.getCardsInHand().size());
        assertEquals(CardsSet.CHANTIER, inventory.getPlayedCards().get(0));
    }

    @Test
     void addCoinsTest() {
        inventory.setCoins(0);
        inventory.addCoins(5);
        assertEquals(5, inventory.getCoins());
    }

    @Test
     void removeCoinsTest() {
        inventory.setCoins(5);
        inventory.removeCoins(3);
        assertEquals(2, inventory.getCoins());
    }

    void setCards() {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(CardsSet.CHANTIER);
        inventory.setCardsInHand(cards);
    }

}
