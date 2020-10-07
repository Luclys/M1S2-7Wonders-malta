package gameelements.ages;

import board.Board;
import gameelements.Player;
import gameelements.cards.Card;
import gameelements.cards.CardsSet;
import gameelements.enums.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AgesTest {

    private int playersCount;
    private Board board;
    private ArrayList<Player> playerList;


    @Test
    public void initiateCardsTest() {
        assertThrows(Error.class, () -> AgeI.initiateCards(2));
        ArrayList<Card> cards = AgeI.initiateCards(3);
        assertTrue(cards.contains(CardsSet.PALISSADE));
        cards = AgeI.initiateCards(4);
        assertTrue(cards.contains(CardsSet.EXCAVATION));
        assertFalse(cards.contains(CardsSet.MINE));

        assertThrows(Error.class, () -> AgeII.initiateCards(8));
        cards = AgeII.initiateCards(3);
        assertTrue(cards.contains(CardsSet.FONDERIE));
        assertFalse(cards.contains(CardsSet.BAZAR));
        cards = AgeII.initiateCards(4);
        assertTrue(cards.contains(CardsSet.BAZAR));

        assertThrows(Error.class, () -> AgeIII.initiateCards(8));
        cards = AgeIII.initiateCards(4);
        assertTrue(cards.contains(CardsSet.PORT));
        assertTrue(cards.contains(CardsSet.OBSERVATOIRE));

    }

    @Disabled


    @Test
    public void AgeISetUpTest() {
        playersCount = playerList.size();
        board = new Board(playerList, false);

        board.ageSetUp(1);
        assertEquals(board.getTurn(), 0);
        assertEquals(board.getCurrentDeckCardList().size(), playersCount * Board.NOMBRE_CARTES);
        Assertions.assertTrue(board.getCurrentDeckCardList().contains(CardsSet.EXCAVATION));
        Assertions.assertTrue(board.isLeftRotation());
        assertEquals(board.getJetonVictoryValue(), 1);

        playerList.add(new Player(4));
        playersCount = playerList.size();
        board = new Board(playerList, false);
        board.ageSetUp(1);
        assertEquals(board.getTurn(), 0);
        assertEquals(board.getCurrentDeckCardList().size(), playersCount * Board.NOMBRE_CARTES);
        Assertions.assertTrue(board.getCurrentDeckCardList().contains(CardsSet.GISEMENT));
        Assertions.assertTrue(board.isLeftRotation());
        assertEquals(board.getJetonVictoryValue(), 1);

        playerList.add(new Player(5));
        board = new Board(playerList, false);
        playersCount = playerList.size();
        board.ageSetUp(1);
        assertEquals(board.getTurn(), 0);
        assertEquals(board.getCurrentDeckCardList().size(), playersCount * Board.NOMBRE_CARTES);
        assertEquals(board.getCurrentDeckCardList().stream().filter(card -> card.equals(CardsSet.CASERNE)).count(), 2);
        Assertions.assertTrue(board.isLeftRotation());
        assertEquals(board.getJetonVictoryValue(), 1);
    }

    @Disabled
    @Test
    public void AgeIISetUpTest() {
        playersCount = playerList.size();
        board = new Board(playerList, false);
        board.ageSetUp(2);
        assertEquals(board.getTurn(), 0);
        assertEquals(board.getCurrentDeckCardList().size(), playersCount * Board.NOMBRE_CARTES);
        Assertions.assertTrue(board.getCurrentDeckCardList().contains(CardsSet.BAZAR));
        Assertions.assertFalse(board.isLeftRotation());
        assertEquals(board.getJetonVictoryValue(), 3);

        playerList.add(new Player(4));
        playersCount = playerList.size();
        board = new Board(playerList, false);
        board.ageSetUp(2);
        assertEquals(board.getTurn(), 0);
        assertEquals(board.getCurrentDeckCardList().size(), playersCount * Board.NOMBRE_CARTES);
        assertEquals(board.getCurrentDeckCardList().stream().filter(card -> card.equals(CardsSet.TRIBUNAL)).count(), 2);
        Assertions.assertFalse(board.isLeftRotation());
        assertEquals(board.getJetonVictoryValue(), 3);

        playerList.add(new Player(5));
        playerList.add(new Player(6));
        playersCount = playerList.size();
        board = new Board(playerList, false);
        board.ageSetUp(2);
        assertEquals(board.getTurn(), 0);
        assertEquals(board.getCurrentDeckCardList().size(), playersCount * Board.NOMBRE_CARTES);
        assertEquals(board.getCurrentDeckCardList().stream().filter(card -> card.equals(CardsSet.FORUM)).count(), 3);
        Assertions.assertFalse(board.isLeftRotation());
        assertEquals(board.getJetonVictoryValue(), 3);
    }

    @Disabled
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
        Assertions.assertTrue(board.isLeftRotation());
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
        Assertions.assertTrue(board.isLeftRotation());
        assertEquals(board.getJetonVictoryValue(), 5);
    }
}
