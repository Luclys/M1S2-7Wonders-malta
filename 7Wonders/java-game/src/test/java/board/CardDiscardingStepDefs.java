package board;

import gameelements.Card;
import gameelements.Inventory;
import gameelements.effects.ResourceEffect;
import gameelements.enums.Category;
import gameelements.enums.Resource;
import io.cucumber.java8.En;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardDiscardingStepDefs implements En {
    Player player;
    Inventory inv;
    Card card = new Card("CAVITÉ", new ResourceEffect(Resource.PIERRE, 1), null, Category.MATIERE_PREMIERE);
    ArrayList<Card> cards = new ArrayList<>(7);
    int initialCoinsCount = 0;
    int initialCardsCount = 0;

    public CardDiscardingStepDefs() {
        Given("Player has a card", () -> {
            player = new Player(1);
            cards.add(card);
            inv.setCardsInHand(cards);
        });

        When("player discards a card", () -> {
            initialCoinsCount = inv.getCoins();
            initialCardsCount = inv.getCardsInHand().size();
            inv.sellCard(card);
        });
        Then("3 coins added to player", () -> {
            assertEquals(inv.getCoins(), (initialCoinsCount + 3));
        });
        Then("the card is no more available", () -> {
            assertEquals(inv.getCardsInHand().size(), initialCardsCount - 1);
        });
    }
}