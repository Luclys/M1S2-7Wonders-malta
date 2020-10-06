package board;

import gameelements.CardsSet;
import gameelements.enums.Category;
import io.cucumber.java8.Ar;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AgesTest {

    private int playersCount;
    private Board board;
    private ArrayList<Player> playerList;

    @BeforeEach
    public void setup(){
        playerList = new ArrayList<>(3);
        for (int i = 0; i < 4; i++) {
            Player player = new Player(i);
            playerList.add(player);
        }
    }

    @Test
    public void AgeISetUpTest() {
        playersCount = playerList.size();
        board = new Board(playerList, false);
        board.ageSetUp(1);
        assertEquals(board.getTurn(), 0);
        assertEquals(board.getCurrentDeckCardList().size(), playersCount * Board.NOMBRE_CARTES);
        assertTrue(board.getCurrentDeckCardList().contains(CardsSet.EXCAVATION));
        assertTrue(board.isLeftRotation());
        assertEquals(board.getJetonVictoryValue(), 1);

        playerList.add(new Player(4));
        playersCount = playerList.size();
        board = new Board(playerList, false);
        board.ageSetUp(1);
        assertEquals(board.getTurn(), 0);
        assertEquals(board.getCurrentDeckCardList().size(), playersCount * Board.NOMBRE_CARTES);
        assertTrue(board.getCurrentDeckCardList().contains(CardsSet.GISEMENT));
        assertTrue(board.isLeftRotation());
        assertEquals(board.getJetonVictoryValue(), 1);

        playerList.add(new Player(5));
        board = new Board(playerList, false);
        playersCount = playerList.size();
        board.ageSetUp(1);
        assertEquals(board.getTurn(), 0);
        assertEquals(board.getCurrentDeckCardList().size(), playersCount * Board.NOMBRE_CARTES);
        assertEquals(board.getCurrentDeckCardList().stream().filter(card -> card.equals(CardsSet.CASERNE)).count(), 2);
        assertTrue(board.isLeftRotation());
        assertEquals(board.getJetonVictoryValue(), 1);
    }

    @Test
    public void AgeIISetUpTest() {
        playersCount = playerList.size();
        board = new Board(playerList, false);
        board.ageSetUp(2);
        assertEquals(board.getTurn(), 0);
        assertEquals(board.getCurrentDeckCardList().size(), playersCount * Board.NOMBRE_CARTES);
        assertTrue(board.getCurrentDeckCardList().contains(CardsSet.BAZAR));
        assertFalse(board.isLeftRotation());
        assertEquals(board.getJetonVictoryValue(), 3);

        playerList.add(new Player(4));
        playersCount = playerList.size();
        board = new Board(playerList, false);
        board.ageSetUp(2);
        assertEquals(board.getTurn(), 0);
        assertEquals(board.getCurrentDeckCardList().size(), playersCount * Board.NOMBRE_CARTES);
        assertEquals(board.getCurrentDeckCardList().stream().filter(card -> card.equals(CardsSet.TRIBUNAL)).count(), 2);
        assertFalse(board.isLeftRotation());
        assertEquals(board.getJetonVictoryValue(), 3);

        playerList.add(new Player(5));
        playerList.add(new Player(6));
        playersCount = playerList.size();
        board = new Board(playerList, false);
        board.ageSetUp(2);
        assertEquals(board.getTurn(), 0);
        assertEquals(board.getCurrentDeckCardList().size(), playersCount * Board.NOMBRE_CARTES);
        assertEquals(board.getCurrentDeckCardList().stream().filter(card -> card.equals(CardsSet.FORUM)).count(), 3);
        assertFalse(board.isLeftRotation());
        assertEquals(board.getJetonVictoryValue(), 3);
    }

    @Test
    public void AgeIIISetUpTest() {
        playersCount = playerList.size();
        board = new Board(playerList, false);
        board.ageSetUp(3);
        assertEquals(board.getTurn(), 0);
        assertEquals(board.getCurrentDeckCardList().size(), playersCount * Board.NOMBRE_CARTES);
        assertEquals(board.getCurrentDeckCardList().stream().filter(card -> card.equals(CardsSet.CHAMBRE_DE_COMMERCE)).count(), 1);
        assertEquals(board.getCurrentDeckCardList().stream().filter(card -> card.equals(CardsSet.JARDINS)).count(), 2);
        assertEquals(board.getCurrentDeckCardList().stream().filter(card -> card.getCategory().equals(Category.GUILDE)).count(), playersCount + 2);
        assertTrue(board.isLeftRotation());
        assertEquals(board.getJetonVictoryValue(), 5);
        playersCount = playerList.size();
        playerList.add(new Player(4));
        playerList.add(new Player(5));
        playerList.add(new Player(6));

        board = new Board(playerList, false);
        board.ageSetUp(3);
        playersCount = playerList.size();
        assertEquals(board.getTurn(), 0);
        assertEquals(board.getCurrentDeckCardList().size(), playersCount * Board.NOMBRE_CARTES);
        assertEquals(board.getCurrentDeckCardList().stream().filter(card -> card.equals(CardsSet.OBSERVATOIRE)).count(), 2);
        assertEquals(board.getCurrentDeckCardList().stream().filter(card -> card.equals(CardsSet.ARÈNE)).count(), 3);
        assertEquals(board.getCurrentDeckCardList().stream().filter(card -> card.getCategory().equals(Category.GUILDE)).count(), playersCount + 2);
        assertTrue(board.isLeftRotation());
        assertEquals(board.getJetonVictoryValue(), 5);
    }
}
