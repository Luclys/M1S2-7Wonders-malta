package board;

import gameelements.Inventory;
import gameelements.Player;
import gameelements.cards.Card;
import gameelements.effects.CoinEffect;
import gameelements.enums.Category;
import io.cucumber.java8.En;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ChoiceOfCardStepDefs implements En {
    Player player;
    Inventory inventory;
    Board board;
    Card tavern = new Card(0, "TAVERNE", new CoinEffect(5), null, Category.BATIMENT_COMMERCIAL);
    ArrayList<Card> cards = new ArrayList<>(7);
    int initialCoinsCount = 0;

    public ChoiceOfCardStepDefs() {
        ArrayList<Player> playerList = new ArrayList<>(3);
        for (int i = 0; i < 3; i++) {
            Player player = new Player(i);
            playerList.add(player);
        }
        board = new Board(playerList, false);
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
            board.executePlayerAction(inventory, player);
        });
        Then("5 coins added to player", () -> {
            assertEquals(inventory.getCoins(), (initialCoinsCount + 5));
        });
        Then("this card is unavailable", () -> {
            assertNotEquals(inventory.getCardsInHand().size(), new Inventory(2).getCardsInHand().size());
        });
    }
}
