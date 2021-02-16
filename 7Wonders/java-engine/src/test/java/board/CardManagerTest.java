package board;


import gameelements.Inventory;
import gameelements.cards.Card;
import gameelements.cards.CardsSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import player.Player;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CardManagerTest {

    private CardManager cardManager;

    @BeforeEach
    void setUp() {
        cardManager = new CardManager();
        for (int i = 0; i < 3; i++) {
            cardManager.getPlayerInventoryList().add(new Inventory(i));
        }
        List<Card> cards = new ArrayList<>();
        cards.add(CardsSet.BIBLIOTHEQUE);
        cards.add(CardsSet.ARENE);
        cards.add(CardsSet.THEATRE);
        cardManager.getPlayerInventoryList().get(0).setCardsInHand(cards);

        cards.add(CardsSet.CARRIERE);
        cards.add(CardsSet.PRETEUR_SUR_GAGES);
        cards.add(CardsSet.DISPENSAIRE);
        cardManager.getPlayerInventoryList().get(1).setCardsInHand(cards);

        cards.add(CardsSet.CIRQUE);
        cards.add(CardsSet.PORT);
        cards.add(CardsSet.THEATRE);
        cardManager.getPlayerInventoryList().get(2).setCardsInHand(cards);
    }

    @Test
    void leftRotationTest() {
        List<Card> cards = cardManager.getPlayerInventoryList().get(0).getCardsInHand();
        cardManager.leftRotation();
        assertEquals(cards, cardManager.getPlayerInventoryList().get(2).getCardsInHand());
        cardManager.leftRotation();
        assertEquals(cards, cardManager.getPlayerInventoryList().get(1).getCardsInHand());
    }


    @Test
    void RightRotationTest() {
        List<Card> cards = cardManager.getPlayerInventoryList().get(0).getCardsInHand();
        cardManager.rightRotation();
        assertEquals(cards, cardManager.getPlayerInventoryList().get(1).getCardsInHand());
        cardManager.rightRotation();
        assertEquals(cards, cardManager.getPlayerInventoryList().get(2).getCardsInHand());
    }

    @Test
    void playersCardsRotationTest() {
        List<Card> cards = cardManager.getPlayerInventoryList().get(0).getCardsInHand();
        cardManager.playersCardsRotation(false);
        assertEquals(cards, cardManager.getPlayerInventoryList().get(1).getCardsInHand());
        cardManager.playersCardsRotation(true);
        assertEquals(cards, cardManager.getPlayerInventoryList().get(0).getCardsInHand());


    }

}
