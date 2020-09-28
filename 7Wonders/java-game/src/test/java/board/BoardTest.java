package board;

import gameelements.Card;
import gameelements.Resource;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    @Test
    public void playTest() {
        Board board = new Board(3);
        board.play();
        assertEquals(6, board.getTurn());
    }

    @Test
    public void testInitializedPlayerList() {
        int nbPlayers = 7;

        Board board = new Board(nbPlayers);
        ArrayList<Player> playerList = board.getPlayerList();

        int playersCount = playerList.size();
        assertEquals(7, playersCount);

        Player firstPlayer = playerList.get(0);
        Player secondPlayer = playerList.get(1);
        Player lastPlayer = playerList.get(nbPlayers - 1);

        // We test the neighborhood tor to the left then to the right
        assertSame(playerList.get(firstPlayer.getLeftNeighborId()), lastPlayer);
        assertSame(playerList.get(lastPlayer.getRightNeighborId()), firstPlayer);

        // We test the left neighbor then the right
        assertSame(playerList.get(secondPlayer.getLeftNeighborId()), firstPlayer);
        assertSame(playerList.get(firstPlayer.getRightNeighborId()), secondPlayer);
    }

    @Test
    public void testInitializedDeckCardList() {
        int nbPlayers = 7;
        Board board = new Board(nbPlayers);
        int deckCardsCount = board.getCurrentDeckCardList().size();
        assertEquals(nbPlayers * 7, deckCardsCount);
    }

    @Test
    public void initiateCardsTest() {
        int nbPlayers = 3;
        Board board = new Board(nbPlayers);
        int resSize = board.initiateCards(nbPlayers).size();
        assertEquals(7*nbPlayers, resSize);
    }


   @Test
    public void drawCardsTest() {
        int nbPlayers = 3;
        Board board = new Board(nbPlayers);

        int nbToDraw = 1;
        ArrayList<Card> listBeforeDrawing = (ArrayList<Card>) board.getCurrentDeckCardList().clone();
        ArrayList<Card> card = board.drawCards(nbToDraw);
        ArrayList<Card> listAfterDrawing = (ArrayList<Card>) board.getCurrentDeckCardList().clone();
<<<<<<< HEAD

        assertEquals(listBeforeDrawing.size() - nbToDraw, listAfterDrawing.size());
        assertSame(listBeforeDrawing.get(0), card.get(0));
=======

        assertEquals(listBeforeDrawing.size() - nbToDraw, listAfterDrawing.size());
        assertSame(listBeforeDrawing.get(0), card.get(0));
    }

   @Disabled @Test
    public void chooseNeighborTest() {
        Board board = new Board(3);
        System.out.println(board.getPlayerList().get(1));
        Player n = board.chooseNeighbor(Resource.ARGENT, board.getPlayerList().get(0));

        assertEquals(null,n);
    }

    @Test
    void buyFromNeighborTest() {
        Board board = new Board(3);
        board.buyFromNeighbor(board.getPlayerList().get(0),board.getPlayerList().get(1));
        assertEquals(1,board.getPlayerList().get(0).getCoins());
        assertEquals(5,board.getPlayerList().get(1).getCoins());
>>>>>>> 1697b1af85d0b9615bb512ee0a2a09104391b6f8
    }
}


