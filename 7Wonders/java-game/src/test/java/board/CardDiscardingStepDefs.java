package board;

import effects.ResourceEffect;
import gameelements.Card;
import gameelements.Inventory;
import gameelements.enums.Resource;
import io.cucumber.java8.En;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardDiscardingStepDefs implements En {
    Inventory inventory;
    Card cavite = new Card("CAVITÉ", new ResourceEffect("", Resource.PIERRE, 1), null);
    ArrayList<Card> cards = new ArrayList<>(7);
    int initialCoinsCount = 0;
    int initialCardsCount = 0;

    public CardDiscardingStepDefs() {
        Given("Inventory has a card", () -> {
            inventory = new Inventory(1);
            cards.add(cavite);
            inventory.setCards(cards);
        });

        When("player discards a card", () -> {
            initialCoinsCount = inventory.getCoins();
            initialCardsCount = inventory.getCards().size();
            inventory.sellCard(cavite);
        });
        Then("3 coins added to player", () -> {
            assertEquals(inventory.getCoins(), (initialCoinsCount + 3));
        });
        Then("the card is no more available", () -> {
            assertEquals(inventory.getCards().size(), initialCardsCount - 1);
        });
    }
}