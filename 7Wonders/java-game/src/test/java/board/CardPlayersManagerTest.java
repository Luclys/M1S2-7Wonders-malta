package board;

import gameelements.Card;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardPlayersManagerTest {

    @Test
    public void leftRotationTest(){
        Board board = new Board(3);
        ArrayList<Card> cardsFirstPlayer = board.getPlayerInventoryList().get(0).getCardsInHand();
        board.getCardManager().leftRotation();
        assertEquals(cardsFirstPlayer,board.getPlayerInventoryList().get(board.getPlayerInventoryList().size()-1).getCardsInHand());
    }

    @Test
    public void RightRotationTest(){
        Board board = new Board(3);
        ArrayList<Card> cardsFirstPlayer = board.getPlayerInventoryList().get(0).getCardsInHand();
        board.getCardManager().rightRotation();
        assertEquals(cardsFirstPlayer,board.getPlayerInventoryList().get(1).getCardsInHand());
    }

    @Test
    public void initiateCardsTest() {
        int nbPlayers = 3;
        Board board = new Board(nbPlayers);
        int resSize = board.getCardManager().initiateCards(nbPlayers).size();
        assertEquals(7 * nbPlayers, resSize);
    }
}
