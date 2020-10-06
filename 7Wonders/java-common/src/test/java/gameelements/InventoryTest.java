package gameelements;

import gameelements.enums.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InventoryTest {
    Inventory inventory;
    @BeforeEach
    public void setUp(){
         inventory = new Inventory(0);
    }

    @Test
    public void discardLastCardTest(){
        // the method throw an erreur if the number of cards in hand of the
        // inventory is more then 1
        assertThrows(Error.class, () -> inventory.discardLastCard());
        // test if the number of cards is 1
        setCards();
        assertEquals(inventory.getCardsInHand().get(0), inventory.discardLastCard());
    }

    @Test
    public void sellCardTest(){
        // if the inventory try to sell a card that he doesn"t have an erreur is lanched
        assertThrows(Error.class, () -> inventory.sellCard(CardsSet.CHANTIER));
        int coins = inventory.getCoins();
        setCards();
        // if the inventory try to sell a card that he has then he gets 3 coins
        inventory.sellCard(CardsSet.CHANTIER);
        assertEquals(coins+3, inventory.getCoins());
    }

    @Test
    public void updateInventoryTest(){
        setCards();
        assertEquals(CardsSet.CHANTIER, inventory.getCardsInHand().get(0));
        inventory.updateInventory(CardsSet.CHANTIER);
        assertEquals(0, inventory.getCardsInHand().size());
        assertEquals(CardsSet.CHANTIER, inventory.getPlayedCards().get(0));
    }

    void setCards(){
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(CardsSet.CHANTIER);
        inventory.setCardsInHand(cards);
    }
}
