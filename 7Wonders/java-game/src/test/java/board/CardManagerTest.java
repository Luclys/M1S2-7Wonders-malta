package board;

import gameelements.Card;
import gameelements.CardsSet;
import gameelements.Inventory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardManagerTest {

    private CardManager cardManager;

    @BeforeEach
    public void setUp() {
        cardManager = new CardManager();
        for (int i = 0; i < 3; i++) {
            cardManager.playerList.add(new Player(i));
            cardManager.playerInventoryList.add(new Inventory(i));
        }
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(CardsSet.BIBLIOTHÈQUE);
        cards.add(CardsSet.ARÈNE);
        cards.add(CardsSet.THÉÂTRE);
        cardManager.playerInventoryList.get(0).setCardsInHand(cards);

        cards.add(CardsSet.CARRIÈRE);
        cards.add(CardsSet.PRÊTEUR_SUR_GAGES);
        cards.add(CardsSet.DISPENSAIRE);
        cardManager.playerInventoryList.get(1).setCardsInHand(cards);

        cards.add(CardsSet.CIRQUE);
        cards.add(CardsSet.PORT);
        cards.add(CardsSet.THÉÂTRE);
        cardManager.playerInventoryList.get(2).setCardsInHand(cards);
    }

    @Test
    public void leftRotationTest() {
        ArrayList<Card> cards =  cardManager.playerInventoryList.get(0).getCardsInHand();
        cardManager.leftRotation();
        assertEquals(cards,cardManager.playerInventoryList.get(2).getCardsInHand());
        cardManager.leftRotation();
        assertEquals(cards,cardManager.playerInventoryList.get(1).getCardsInHand());
    }


    @Test
    public void RightRotationTest() {
        ArrayList<Card> cards =  cardManager.playerInventoryList.get(0).getCardsInHand();
        cardManager.rightRotation();
        assertEquals(cards,cardManager.playerInventoryList.get(1).getCardsInHand());
        cardManager.rightRotation();
        assertEquals(cards,cardManager.playerInventoryList.get(2).getCardsInHand());
    }
}
