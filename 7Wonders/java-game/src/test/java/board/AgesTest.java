package board;

import gameelements.Player;
import gameelements.ages.Age;
import gameelements.ages.AgeI;
import gameelements.ages.AgeII;
import gameelements.ages.AgeIII;
import gameelements.cards.Card;
import gameelements.cards.CardsSet;
import gameelements.enums.Category;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AgesTest {

    private int playersCount;
    private Board board;
    private List<Player> playerList;

    Age age;
    @Test
     void initiateCardsTest() {
        age = new AgeI();
        Age finalAge = age;
        assertThrows(IllegalStateException.class, () -> age.initiateCards(2));
        List<Card> cards = age.initiateCards(3);
        assertTrue(cards.contains(CardsSet.PALISSADE));
        cards = age.initiateCards(4);
        assertTrue(cards.contains(CardsSet.EXCAVATION));
        assertFalse(cards.contains(CardsSet.MINE));
         age = new AgeII();

        assertThrows(IllegalStateException.class, () -> age.initiateCards(8));
        cards = age.initiateCards(3);
        assertTrue(cards.contains(CardsSet.FONDERIE));
        assertFalse(cards.contains(CardsSet.BAZAR));
        cards = age.initiateCards(4);
        assertTrue(cards.contains(CardsSet.BAZAR));
        age = new AgeIII();
        assertThrows(IllegalStateException.class, () -> age.initiateCards(8));
        cards = age.initiateCards(4);
        assertTrue(cards.contains(CardsSet.PORT));
        assertTrue(cards.contains(CardsSet.OBSERVATOIRE));

    }

    @Disabled


    @Test
     void AgeISetUpTest() {
        playersCount = playerList.size();
        board = new Board(playerList, false);

        board.ageSetUp(1);
        assertEquals(board.getTurn(), 0);
        assertEquals(board.getCurrentDeckCardList().size(), playersCount * Board.CARDS_NUMBER);
        assertTrue(board.getCurrentDeckCardList().contains(CardsSet.EXCAVATION));
        assertTrue(board.isLeftRotation());
        assertEquals(board.getJetonVictoryValue(), 1);

        playerList.add(new Player(4));
        playersCount = playerList.size();
        board = new Board(playerList, false);
        board.ageSetUp(1);
        assertEquals(board.getTurn(), 0);
        assertEquals(board.getCurrentDeckCardList().size(), playersCount * Board.CARDS_NUMBER);
        assertTrue(board.getCurrentDeckCardList().contains(CardsSet.GISEMENT));
        assertTrue(board.isLeftRotation());
        assertEquals(board.getJetonVictoryValue(), 1);

        playerList.add(new Player(5));
        board = new Board(playerList, false);
        playersCount = playerList.size();
        board.ageSetUp(1);
        assertEquals(board.getTurn(), 0);
        assertEquals(board.getCurrentDeckCardList().size(), playersCount * Board.CARDS_NUMBER);
        assertEquals(board.getCurrentDeckCardList().stream().filter(card -> card.equals(CardsSet.CASERNE)).count(), 2);
        assertTrue(board.isLeftRotation());
        assertEquals(board.getJetonVictoryValue(), 1);
    }

    @Disabled
    @Test
     void AgeIISetUpTest() {
        playersCount = playerList.size();
        board = new Board(playerList, false);
        board.ageSetUp(2);
        assertEquals(board.getTurn(), 0);
        assertEquals(board.getCurrentDeckCardList().size(), playersCount * Board.CARDS_NUMBER);
        assertTrue(board.getCurrentDeckCardList().contains(CardsSet.BAZAR));
        assertFalse(board.isLeftRotation());
        assertEquals(board.getJetonVictoryValue(), 3);

        playerList.add(new Player(4));
        playersCount = playerList.size();
        board = new Board(playerList, false);
        board.ageSetUp(2);
        assertEquals(board.getTurn(), 0);
        assertEquals(board.getCurrentDeckCardList().size(), playersCount * Board.CARDS_NUMBER);
        assertEquals(board.getCurrentDeckCardList().stream().filter(card -> card.equals(CardsSet.TRIBUNAL)).count(), 2);
        assertFalse(board.isLeftRotation());
        assertEquals(board.getJetonVictoryValue(), 3);

        playerList.add(new Player(5));
        playerList.add(new Player(6));
        playersCount = playerList.size();
        board = new Board(playerList, false);
        board.ageSetUp(2);
        assertEquals(board.getTurn(), 0);
        assertEquals(board.getCurrentDeckCardList().size(), playersCount * Board.CARDS_NUMBER);
        assertEquals(board.getCurrentDeckCardList().stream().filter(card -> card.equals(CardsSet.FORUM)).count(), 3);
        assertFalse(board.isLeftRotation());
        assertEquals(board.getJetonVictoryValue(), 3);
    }

    @Disabled
    @Test
     void AgeIIISetUpTest() {
        playersCount = playerList.size();
        board = new Board(playerList, false);
        board.ageSetUp(3);
        assertEquals(board.getTurn(), 0);
        assertEquals(board.getCurrentDeckCardList().size(), playersCount * Board.CARDS_NUMBER);
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
        assertEquals(board.getCurrentDeckCardList().size(), playersCount * Board.CARDS_NUMBER);
        assertEquals(board.getCurrentDeckCardList().stream().filter(card -> card.equals(CardsSet.OBSERVATOIRE)).count(), 2);
        assertEquals(board.getCurrentDeckCardList().stream().filter(card -> card.equals(CardsSet.ARENE)).count(), 3);
        assertEquals(board.getCurrentDeckCardList().stream().filter(card -> card.getCategory().equals(Category.GUILDE)).count(), playersCount + 2);
        assertTrue(board.isLeftRotation());
        assertEquals(board.getJetonVictoryValue(), 5);
    }
}
