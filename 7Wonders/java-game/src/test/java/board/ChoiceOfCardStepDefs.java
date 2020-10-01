package board;

import gameelements.Card;
import gameelements.enums.Resource;
import io.cucumber.java8.En;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ChoiceOfCardStepDefs implements En {
    Player player;
    Card tavern = new Card("TAVERNE", new Resource[0], new Resource[0]);
    ArrayList<Card> cards = new ArrayList<>(7);
    int initialCoinsCount = 0;

    public ChoiceOfCardStepDefs() {
        Given("a player with id {int}", (Integer playerId) -> {
            player = new Player(playerId);
        });
        And("a player has a card TAVERNE", () -> {
            cards.add(tavern);
            player.setCards(cards);
        });

        When("player chooses TAVERNE", () -> {
            initialCoinsCount = player.getCoins();
            player.playCard();
        });
        Then("5 coins added to player", () -> {
            assertEquals(player.getCoins(), (initialCoinsCount + 5));
        });
        Then("this card is unavailable", () -> {
            assertNotEquals(player.getCards().size(), new Player(2).getCards().size());
        });
    }
}
