package strategy;

import board.Board;
import gameelements.Inventory;
import gameelements.cards.Card;
import gameelements.cards.CardsSet;
import gameelements.enums.Action;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class FirstCardStrategyTest {

    @Mock
    Board board;

    @Test
    void chooseCardTest() {
        Inventory inv = new Inventory(0);
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(CardsSet.HOTEL_DE_VILLE);
        cards.add(CardsSet.CHANTIER);
        inv.setCardsInHand(cards);
        FirstCardStrategy strategy = new FirstCardStrategy();
        assertEquals(CardsSet.HOTEL_DE_VILLE, strategy.chooseCard(inv));
        assertEquals(Action.BUILDING, strategy.getAction());
        inv.setPlayedCards(cards);
        inv.getCardsInHand().remove(1);
        assertEquals(CardsSet.HOTEL_DE_VILLE, strategy.chooseCard(inv));
        assertEquals(Action.SELL, strategy.getAction());
    }
}
