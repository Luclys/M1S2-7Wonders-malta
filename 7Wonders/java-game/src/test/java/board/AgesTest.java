package board;

import gameelements.CardsSet;
import gameelements.enums.Category;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AgesTest {

    private int playersCount;
    private Board board;

    @Test
    public void AgeISetUpTest() {
        playersCount = 4;
        board = new Board(4, false);
        board.ageSetUp(1);
        assertEquals(board.getCurrentDeckCardList().size(), playersCount * Board.NOMBRE_CARTES);
        assertTrue(board.getCurrentDeckCardList().contains(CardsSet.EXCAVATION));
        assertTrue(board.isLeftRotation());
        assertEquals(board.getConflictPoints(), 1);

        playersCount = 5;
        board = new Board(playersCount, false);
        board.ageSetUp(1);
        assertEquals(board.getCurrentDeckCardList().size(), playersCount * Board.NOMBRE_CARTES);
        assertTrue(board.getCurrentDeckCardList().contains(CardsSet.GISEMENT));
        assertTrue(board.isLeftRotation());
        assertEquals(board.getConflictPoints(), 1);

        playersCount = 6;
        board = new Board(playersCount, false);
        board.ageSetUp(1);
        assertEquals(board.getCurrentDeckCardList().size(), playersCount * Board.NOMBRE_CARTES);
        assertEquals(board.getCurrentDeckCardList().stream().filter(card -> card.equals(CardsSet.CASERNE)).count(), 2);
        assertTrue(board.isLeftRotation());
        assertEquals(board.getConflictPoints(), 1);
    }

    @Test
    public void AgeIISetUpTest() {
        playersCount = 4;
        board = new Board(playersCount, false);
        board.ageSetUp(2);
        assertEquals(board.getCurrentDeckCardList().size(), playersCount * Board.NOMBRE_CARTES);
        assertTrue(board.getCurrentDeckCardList().contains(CardsSet.BAZAR));
        assertFalse(board.isLeftRotation());
        assertEquals(board.getConflictPoints(), 3);

        playersCount = 5;
        board = new Board(playersCount, false);
        board.ageSetUp(2);
        assertEquals(board.getCurrentDeckCardList().size(), playersCount * Board.NOMBRE_CARTES);
        assertEquals(board.getCurrentDeckCardList().stream().filter(card -> card.equals(CardsSet.TRIBUNAL)).count(), 2);
        assertFalse(board.isLeftRotation());
        assertEquals(board.getConflictPoints(), 3);

        playersCount = 7;
        board = new Board(playersCount, false);
        board.ageSetUp(2);
        assertEquals(board.getCurrentDeckCardList().size(), playersCount * Board.NOMBRE_CARTES);
        assertEquals(board.getCurrentDeckCardList().stream().filter(card -> card.equals(CardsSet.FORUM)).count(), 3);
        assertFalse(board.isLeftRotation());
        assertEquals(board.getConflictPoints(), 3);
    }

    @Test
    public void AgeIIISetUpTest() {
        playersCount = 4;
        board = new Board(playersCount, false);
        board.ageSetUp(3);
        assertEquals(board.getCurrentDeckCardList().size(), playersCount * Board.NOMBRE_CARTES);
        assertEquals(board.getCurrentDeckCardList().stream().filter(card -> card.equals(CardsSet.CHAMBRE_DE_COMMERCE)).count(), 1);
        assertEquals(board.getCurrentDeckCardList().stream().filter(card -> card.equals(CardsSet.JARDINS)).count(), 2);
        assertEquals(board.getCurrentDeckCardList().stream().filter(card -> card.getCategory().equals(Category.GUILDE)).count(), playersCount + 2);
        assertTrue(board.isLeftRotation());
        assertEquals(board.getConflictPoints(), 5);

        playersCount = 7;
        board = new Board(playersCount, false);
        board.ageSetUp(3);
        assertEquals(board.getCurrentDeckCardList().size(), playersCount * Board.NOMBRE_CARTES);
        assertEquals(board.getCurrentDeckCardList().stream().filter(card -> card.equals(CardsSet.OBSERVATOIRE)).count(), 2);
        assertEquals(board.getCurrentDeckCardList().stream().filter(card -> card.equals(CardsSet.ARÈNE)).count(), 3);
        assertEquals(board.getCurrentDeckCardList().stream().filter(card -> card.getCategory().equals(Category.GUILDE)).count(), playersCount + 2);
        assertTrue(board.isLeftRotation());
        assertEquals(board.getConflictPoints(), 5);
    }
}
