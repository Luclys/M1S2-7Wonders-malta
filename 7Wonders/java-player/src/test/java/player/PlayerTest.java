package player;

import gameelements.Inventory;
import gameelements.cards.Card;
import gameelements.cards.CardsSet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import strategy.FirstCardStrategy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

class PlayerTest {

    final List<Card> cards = new ArrayList<>(7);
    private Player player;
    private Inventory inv, playerRightNeighbor, playerLeftNeighbor;

    @Test
    void chooseCardTest() throws Exception {
        player = new Player(0, new FirstCardStrategy());
        inv = new Inventory(0);
        playerRightNeighbor = new Inventory(1);
        playerLeftNeighbor = new Inventory(2);
        cards.add(CardsSet.BIBLIOTHEQUE);
        cards.add(CardsSet.BIBLIOTHEQUE);
        cards.add(CardsSet.THEATRE);
        inv.setCardsInHand(cards);
        assertEquals(CardsSet.BIBLIOTHEQUE, player.chooseCard(inv));
        inv.updateInventory(CardsSet.BIBLIOTHEQUE, playerRightNeighbor, playerLeftNeighbor);
        assertEquals(CardsSet.THEATRE, player.chooseCard(inv));
    }

}