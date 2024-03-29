package engine.board;

import gameelements.Inventory;
import gameelements.cards.Card;
import gameelements.effects.ChoiceScientificEffect;
import gameelements.effects.Effect;
import gameelements.effects.SymbolEffect;
import gameelements.enums.Symbol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ScoreTest {
    private HashMap<Integer, String> mapPlayerID_URL;

    @BeforeEach
    void setUp() {
        mapPlayerID_URL = new HashMap<>(3);
        for (int i = 0; i < 3; i++) {
            mapPlayerID_URL.put(mapPlayerID_URL.size(), "url" + i);
        }
    }

    /*
     * We test that when the player is given the guild of scientific card,
     * the symbol is added only at the end of the game -> Inventory@scores()
     * the symbol chosen by the player is accounted when calculating scores.
     * */
    @Test
    void guildChooseScientificScoreTest() {
        Board board = new Board(mapPlayerID_URL, false, null);
        Inventory inv = board.getPlayerInventoryList().get(0);

        Card GUILDE_DES_SCIENTIFIQUES = new Card(1, "GUILDE DES SCIENTIFIQUES TEST", new Effect[]{new ChoiceScientificEffect()}, null, null, 0, null, null);
        Card card2ScientificSymbol = new Card(2, "", new Effect[]{new SymbolEffect(Symbol.STELE, 1), new SymbolEffect(Symbol.ROUAGE, 1)}, null, null, 0, null, null);
        inv.setCoins(0);

        inv.updateInventory(card2ScientificSymbol, null, null);
        inv.updateInventory(GUILDE_DES_SCIENTIFIQUES, null, null);
        assertEquals(0, inv.getSymbolCount(Symbol.COMPAS));

        board.scores();
        assertEquals(10, inv.getScore());
    }

    /*
     * Testing that multiple end effects are rightfully accounted into score.
     * */
    @Disabled
    @Test
    void multipleEndEffectScoreTest() {
        Board board = new Board(mapPlayerID_URL, false, null);
        Inventory inv = board.getPlayerInventoryList().get(0);

        Card card2ScientificSymbol = new Card(1, "", new Effect[]{new SymbolEffect(Symbol.COMPAS, 1), new SymbolEffect(Symbol.ROUAGE, 1)}, null, null, 0, null, null);
        Card GUILDE_DES_SCIENTIFIQUES = new Card(2, "GUILDE DES SCIENTIFIQUES TEST", new Effect[]{new ChoiceScientificEffect()}, null, null, 0, null, null);

        inv.setCoins(0);

        inv.updateInventory(card2ScientificSymbol, null, null);
        inv.updateInventory(GUILDE_DES_SCIENTIFIQUES, null, null);
        inv.updateInventory(GUILDE_DES_SCIENTIFIQUES, null, null);

        board.scores();
        assertEquals(13, inv.getScore());
    }

    @Test
    void conflictScoreTest() {
        Board board = new Board(mapPlayerID_URL, false, null);
        Inventory inv = board.getPlayerInventoryList().get(0);

        inv.setCoins(0);
        inv.setVictoryChipsScore(5);
        inv.setDefeatChipsCount(1);

        board.scores();
        assertEquals(4, inv.getScore());
        inv.setScore(0);

        inv.setVictoryChipsScore(2);
        inv.setDefeatChipsCount(5);

        board.scores();
        assertEquals(-3, inv.getScore());
    }

    /**
     * Testing that every 3 coins grants 1 score point.
     */
    @Test
    void coinScoreTest() {
        Board board = new Board(mapPlayerID_URL, false, null);
        Inventory inv = board.getPlayerInventoryList().get(0);

        inv.setCoins(0);
        board.scores();
        assertEquals(0, inv.getScore());
        inv.setScore(0);

        inv.setCoins(3);
        board.scores();
        assertEquals(1, inv.getScore());
        inv.setScore(0);

        inv.setCoins(4);
        board.scores();
        assertEquals(1, inv.getScore());
        inv.setScore(0);

        inv.setCoins(9);
        board.scores();
        assertEquals(3, inv.getScore());
        inv.setScore(0);
    }

    /* Testing the scores according to the nb of symbols
     * 1 singe symbol : 1 victory point.
     * 2 symbols identical : 4 victory point.
     * 3 symbols identical : 9 victory point.
     * 4 symbols identical : 16 victory point.
     * */
    @Test
    void sameScientificScoreTest() {
        Board board = new Board(mapPlayerID_URL, false, null);
        Inventory inv = board.getPlayerInventoryList().get(0);

        Card card1Compas = new Card(1, "", new Effect[]{new SymbolEffect(Symbol.COMPAS, 1)}, null, null, 0, null, null);
        Card card2Compas = new Card(2, "", new Effect[]{new SymbolEffect(Symbol.COMPAS, 2)}, null, null, 0, null, null);
        inv.setCoins(0);

        inv.updateInventory(card1Compas, null, null);
        board.scores();
        assertEquals(1, inv.getScore());
        inv.setScore(0);

        inv.updateInventory(card1Compas, null, null);
        board.scores();
        assertEquals(4, inv.getScore());
        inv.setScore(0);

        inv.updateInventory(card2Compas, null, null);
        board.scores();
        assertEquals(16, inv.getScore());
    }

    /*
     * Testing that each group of 3 symbol grants 7 victory points.
     * */
    @Test
    void tripleScientificScoreTest() {
        Board board = new Board(mapPlayerID_URL, false, null);
        Inventory inv = board.getPlayerInventoryList().get(0);

        Card card3ScientificSymbol = new Card(1, "", new Effect[]{new SymbolEffect(Symbol.COMPAS, 1), new SymbolEffect(Symbol.STELE, 1), new SymbolEffect(Symbol.ROUAGE, 1)}, null, null, 0, null, null);
        inv.setCoins(0);

        inv.updateInventory(card3ScientificSymbol, null, null);
        board.scores();
        assertEquals(10, inv.getScore());
        inv.setScore(0);

        inv.updateInventory(card3ScientificSymbol, null, null);
        board.scores();
        assertEquals(26, inv.getScore());
        inv.setScore(0);

        Card card1Compas = new Card(2, "", new Effect[]{new SymbolEffect(Symbol.COMPAS, 1)}, null, null, 0, null, null);
        inv.updateInventory(card1Compas, null, null);
        board.scores();
        assertEquals(31, inv.getScore());
        inv.setScore(0);
    }


}


