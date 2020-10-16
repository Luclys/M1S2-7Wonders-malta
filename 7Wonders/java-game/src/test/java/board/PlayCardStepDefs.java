package board;

import gameelements.*;
import gameelements.cards.Card;
import gameelements.enums.Category;
import gameelements.strategy.WonderStrategy;
import io.cucumber.java8.En;

import java.util.ArrayList;

public class PlayCardStepDefs implements En {
    Player player;
    Inventory inventory;
    Board board;

    public PlayCardStepDefs() {
        Given("^a player has chosen a card$", () -> {
            player = new Player(0, new WonderStrategy());
            ArrayList<Player> playerList = new ArrayList<>();
            playerList.add(player);
            board = new Board(playerList, false);
            Card card = new Card(0, "DUMMY", (Effect) null, null, Category.MATIERE_PREMIERE);
            ArrayList<Card> cardsInHand = new ArrayList<>();
            cardsInHand.add(card);
            inventory = board.getPlayerInventoryList().get(0);
            inventory.setCardsInHand(cardsInHand);
        });
    }
}
