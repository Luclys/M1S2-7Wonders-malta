package board;

import gameelements.Inventory;
import gameelements.cards.Card;
import gameelements.cards.CardsSet;
import io.cucumber.java8.En;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CardDiscardingSteps implements En {
    Card card = CardsSet.CAVITE;
    Inventory inv = new Inventory(0);
    int initialCoinsCount;
    int initialCardsCount;
     public CardDiscardingSteps(){
        Given("Inventory has a card", () -> {
            List<Card> cards = new ArrayList<>();
            cards.add(card);
            inv.setCardsInHand(cards);
        });

        When("inventory discards a card", () -> {
            initialCardsCount = inv.getCardsInHand().size();
            initialCoinsCount = inv.getCoins();
            inv.sellCard(card);
        });
        Then("coins added to inventory", () -> {
            assertEquals(initialCoinsCount+3,inv.getCoins());

        });
        And("the card is no more available", () -> {
            assertEquals(initialCardsCount-1,inv.getCardsInHand().size());
        });
        Given("inventory has not the card", () -> {
                    initialCardsCount = inv.getCardsInHand().size();
                    initialCoinsCount = inv.getCoins();
        });
        When("inventory discards the card", () -> {
            assertThrows(Error.class, () -> inv.sellCard(CardsSet.CHANTIER));

        });
        Then("no modification", () -> {
            assertEquals(initialCoinsCount,inv.getCoins());
            assertEquals(initialCardsCount,inv.getCardsInHand().size());
        });
    }

}
