package board;

import gameelements.Card;
import gameelements.enums.Resource;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class TradeTest {


    @Disabled @Test
    public void findSalerTest() {
        Board board = new Board(3);
        //System.out.println(board.getPlayerList().get(1));
        Player rightNeighbor = board.getPlayerList().get(board.getPlayerList().get(0).getRightNeighborId());
        Player leftNeighbor = board.getPlayerList().get(board.getPlayerList().get(0).getLeftNeighborId());
        Player n = board.getCommerce().findSaler(Resource.ARGENT,rightNeighbor, leftNeighbor);

      //  assertEquals(n,n);
    }

    @Test
    void buyFromNeighborTest() {
        Board board = new Board(3);
        board.getCommerce().buyFromNeighbor(board.getPlayerList().get(0),board.getPlayerList().get(1));
        assertEquals(1,board.getPlayerList().get(0).getCoins());
        assertEquals(5,board.getPlayerList().get(1).getCoins());
    }
}
