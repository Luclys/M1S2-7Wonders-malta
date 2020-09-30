package board;

import gameelements.Card;
import gameelements.Resource;
import io.cucumber.java8.En;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardDiscardingStepDefs implements En {
  Player player;
  Card cavite = new Card("CAVITÉ", new Resource[]{Resource.PIERRE}, new Resource[0]);
  ArrayList<Card> cards = new ArrayList<>(7);
  int initialCoinsCount = 0;
  int initialCardsCount = 0;

  public CardDiscardingStepDefs() {
    Given("Player has a card", () -> {
      player = new Player(1);
      cards.add(cavite);
      player.setCards(cards);
    });

    When("player discards a card", () -> {
      initialCoinsCount = player.getCoins();
      initialCardsCount = player.getCards().size();
      player.saleCard();
    });
    Then("3 coins added to player", () -> {
      assertEquals(player.getCoins(), (initialCoinsCount + 3));
    });
    Then("the card is no more available", () -> {
      assertEquals(player.getCards().size(), initialCardsCount - 1);
    });
  }
}