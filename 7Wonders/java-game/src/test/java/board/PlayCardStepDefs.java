package board;

import gameelements.*;
import gameelements.enums.Category;
import gameelements.strategy.ResourceStrategy;
import io.cucumber.java8.Ar;
import io.cucumber.java8.En;

import java.util.ArrayList;

public class PlayCardStepDefs implements En {
    Player player;
    Inventory inventory;
    Board board;

    public PlayCardStepDefs() {
        Given("^a player has chosen a card$", () -> {
            player = new Player(0, new ResourceStrategy());
            ArrayList<Player> playerList = new ArrayList<>();
            playerList.add(player);
            board = new Board(playerList, false);
            Card card = new Card("DUMMY", (Effect) null, null, Category.MATIERE_PREMIERE);
            ArrayList<Card> cardsInHand = new ArrayList<>();
            cardsInHand.add(card);
            inventory = board.getPlayerInventoryList().get(0);
            inventory.setCardsInHand(cardsInHand);
        });
    }
}
