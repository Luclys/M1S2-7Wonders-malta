package board;

import gameelements.effects.CoinEffect;
import gameelements.Card;
import gameelements.Inventory;
import gameelements.enums.Category;
import io.cucumber.java8.En;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ChoiceOfCardStepDefs implements En {
    Player player;
    Inventory inventory;
    Board board = new Board(1, false);
    Card tavern = new Card("TAVERNE", new CoinEffect("", 5), null, Category.BATIMENT_COMMERCIEAU);
    ArrayList<Card> cards = new ArrayList<>(7);
    int initialCoinsCount = 0;

    public ChoiceOfCardStepDefs() {
        Given("a player with id {int}", (Integer playerId) -> {
            inventory = new Inventory(playerId);
            player = new Player(playerId);
        });
        And("a player has a card TAVERNE", () -> {
            cards.add(tavern);
            inventory.setCardsInHand(cards);
        });

        When("player chooses TAVERNE", () -> {
            initialCoinsCount = inventory.getCoins();
            board.playCard(inventory, inventory, player);
        });
        Then("5 coins added to player", () -> {
            assertEquals(inventory.getCoins(), (initialCoinsCount + 5));
        });
        Then("this card is unavailable", () -> {
            assertNotEquals(inventory.getCardsInHand().size(), new Inventory(2).getCardsInHand().size());
        });
    }
}
