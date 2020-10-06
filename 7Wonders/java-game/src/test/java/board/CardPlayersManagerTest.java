package board;

import gameelements.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardPlayersManagerTest {

    private ArrayList<Player> playerList;

    @BeforeEach
    public void setUp() {
        playerList = new ArrayList<>(3);
        for (int i = 0; i < 3; i++) {
            Player player = new Player(i);
            playerList.add(player);
        }
    }

    @Test
    public void leftRotationTest() {
        Board board = new Board(playerList, false);
        ArrayList<Card> cardsFirstPlayer = board.getPlayerInventoryList().get(0).getCardsInHand();
        board.getCardManager().leftRotation();
        assertEquals(cardsFirstPlayer, board.getPlayerInventoryList().get(board.getPlayerInventoryList().size() - 1).getCardsInHand());
    }

    @Test
    public void RightRotationTest() {
        Board board = new Board(playerList, false);
        ArrayList<Card> cardsFirstPlayer = board.getPlayerInventoryList().get(0).getCardsInHand();
        board.getCardManager().rightRotation();
        assertEquals(cardsFirstPlayer, board.getPlayerInventoryList().get(1).getCardsInHand());
    }
}
