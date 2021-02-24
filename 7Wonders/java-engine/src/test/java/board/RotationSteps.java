package board;

import gameelements.Inventory;
import gameelements.cards.Card;
import gameelements.cards.CardsSet;
import io.cucumber.java8.En;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RotationSteps implements En {
    CardManager cardManager;
    List<Card> initalCardPlayer0;
    List<Card> initalCardPlayer1;
    List<Card> initalCardPlayer2;

    public RotationSteps() {
        Given("Inventories have cards", () -> {
            cardManager = new CardManager();
            for (int i = 0; i < 3; i++) {
                cardManager.getPlayerInventoryList().add(new Inventory(i));
            }
            List<Card> cards = new ArrayList<>();
            cards.add(CardsSet.BIBLIOTHEQUE);
            cards.add(CardsSet.ARENE);
            cards.add(CardsSet.ACADEMIE);
            cardManager.getPlayerInventoryList().get(0).setCardsInHand(cards);
            cards.clear();
            cards.add(CardsSet.ARENE);
            cards.add(CardsSet.CARRIERE);
            cards.add(CardsSet.UNIVERSITE);
            cardManager.getPlayerInventoryList().get(1).setCardsInHand(cards);
            cards.clear();
            cards.add(CardsSet.THEATRE);
            cards.add(CardsSet.CASERNE);
            cards.add(CardsSet.THEATRE);
            cardManager.getPlayerInventoryList().get(2).setCardsInHand(cards);
        });

        When("left rotation", () -> {
            initalCardPlayer0 = cardManager.getPlayerInventoryList().get(0).getCardsInHand();
            initalCardPlayer1 = cardManager.getPlayerInventoryList().get(1).getCardsInHand();
            initalCardPlayer2 = cardManager.getPlayerInventoryList().get(2).getCardsInHand();
            cardManager.leftRotation();
        });
        Then("inventories change cards between them in left direction", () -> {
            assertEquals(initalCardPlayer0, cardManager.getPlayerInventoryList().get(2).getCardsInHand());
            assertEquals(initalCardPlayer1, cardManager.getPlayerInventoryList().get(0).getCardsInHand());
            assertEquals(initalCardPlayer2, cardManager.getPlayerInventoryList().get(1).getCardsInHand());
        });

        When("right rotation", () -> {
            cardManager.rightRotation();
        });
        Then("inventories change cards between them in right direction", () -> {
            assertEquals(initalCardPlayer0, cardManager.getPlayerInventoryList().get(0).getCardsInHand());
            assertEquals(initalCardPlayer1, cardManager.getPlayerInventoryList().get(1).getCardsInHand());
            assertEquals(initalCardPlayer2, cardManager.getPlayerInventoryList().get(2).getCardsInHand());
        });
    }
}

