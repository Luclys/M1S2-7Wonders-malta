import board.Board;
import gameelements.Inventory;
import gameelements.cards.Card;
import gameelements.cards.CardsSet;
import gameelements.enums.Action;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import player.Player;
import strategy.PlayingStrategy;
import strategy.RuleBasedAI;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BoardTestITCase {
    Board board;
    Player player;
    PlayingStrategy strategy = new RuleBasedAI();

    @BeforeEach
    void setUp() {
        player = new Player(1, strategy);
        List<Player> playerList = new ArrayList<>(1);
        playerList.add(player);
        board = new Board(playerList, false);
    }

    @Test
    public void executePlayerActionTest() throws Exception {
        Inventory inventory = board.getPlayerInventoryList().get(0);
        List<Card> cards = new ArrayList<Card>();
        cards.add(CardsSet.BIBLIOTHEQUE);
        inventory.setCardsInHand(cards);
        int coins = inventory.getCoins();
        player.getStrategy().setAction(Action.SELL);
        player.getStrategy().setCard(inventory.getCardsInHand().get(0));
        board.executePlayerAction(inventory, player);
        assertEquals(coins+3, inventory.getCoins());
    }
}
