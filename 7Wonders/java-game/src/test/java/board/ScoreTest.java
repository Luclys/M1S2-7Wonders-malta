package board;

import gameelements.Effect;
import gameelements.Inventory;
import gameelements.Player;
import gameelements.cards.Card;
import gameelements.effects.ChoiceScientificEffect;
import gameelements.effects.SymbolEffect;
import gameelements.enums.Symbol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ScoreTest {
    ArrayList<Player> playerList;

    @Mock
    Player playerMocked;

    @BeforeEach
    public void setUp() {
        playerList = new ArrayList<>(3);
        for (int i = 0; i < 3; i++) {
            Player player = new Player(i);
            playerList.add(player);
        }
    }

    /*
     * We test that when the player is given the guild of scientific card,
     * the symbol is added only at the end of the game -> Inventory@scores()
     * the symbol chosen by the player is accounted when calculating scores.
     * */
    @Disabled
    @Test
    void guildChooseScientificScoreTest() {
        Board board = new Board(playerList, false);
        Inventory inv = board.getPlayerInventoryList().get(0);

        //  MOCKITO IS NOT RESPONDING THE WAY I WANT ?!
        //  TODO : REPAIR MOCK !
        //when(playerMocked.chooseScientific(inv.getAvailableSymbols())).thenReturn(Symbol.COMPAS, Symbol.COMPAS);
        doReturn(Symbol.COMPAS).when(playerMocked).chooseScientific(null);

        Card GUILDE_DES_SCIENTIFIQUES = new Card("GUILDE DES SCIENTIFIQUES TEST", new ChoiceScientificEffect(), null, null);
        Card card2ScientificSymbol = new Card("", new Effect[]{new SymbolEffect(Symbol.STELE, 1), new SymbolEffect(Symbol.ROUAGE, 1)}, null, null);
        inv.setCoins(0);

        inv.updateInventory(card2ScientificSymbol, null, null, null);
        inv.updateInventory(GUILDE_DES_SCIENTIFIQUES, playerMocked, null, null);
        assertEquals(0, inv.getSymbCount(Symbol.COMPAS));

        board.scores();
        assertEquals(10, inv.getScore());
    }

    /*
     * Testing that multiple end effects are rightfully accounted into score.
     * */
    @Disabled
    @Test
    void multipleEndEffectScoreTest() {
        Board board = new Board(playerList, false);
        Inventory inv = board.getPlayerInventoryList().get(0);

        when(playerMocked.chooseScientific(null)).thenReturn(Symbol.STELE, Symbol.STELE);
        //  MOCKITO IS NOT RESPONDING THE WAY I WANT ?!
        //  TODO : REPAIR MOCK !

        Card GUILDE_DES_SCIENTIFIQUES = new Card("GUILDE DES SCIENTIFIQUES TEST", new ChoiceScientificEffect(), null, null);
        Card card2ScientificSymbol = new Card("", new Effect[]{new SymbolEffect(Symbol.COMPAS, 1), new SymbolEffect(Symbol.ROUAGE, 1)}, null, null);
        inv.setCoins(0);

        inv.updateInventory(card2ScientificSymbol, null, null, null);
        inv.updateInventory(GUILDE_DES_SCIENTIFIQUES, playerMocked, null, null);
        inv.updateInventory(GUILDE_DES_SCIENTIFIQUES, playerMocked, null, null);

        board.scores();
        assertEquals(13, inv.getScore());
    }

    @Test
    void conflictScoreTest() {
        // We claim a test Board, then test if we got the base resource.
        Board board = new Board(playerList, false);
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

    @Test
    void coinScoreTest() {
        // We claim a test Board, then test if we got the base resource.
        Board board = new Board(playerList, false);
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

    @Test
    void sameScientificScoreTest() {
        // We claim a test Board, then test if we got the base resource.
        Board board = new Board(playerList, false);
        Inventory inv = board.getPlayerInventoryList().get(0);

        /*
         * 1 seul symbole : 1 point de victoire
         * 2 symboles identiques : 4 points de victoire
         * 3 symboles identiques : 9 points de victoire
         * 4 symboles identiques : 16 points de victoire
         * */

        Card card1Compas = new Card("", new SymbolEffect(Symbol.COMPAS, 1), null, null);
        Card card2Compas = new Card("", new SymbolEffect(Symbol.COMPAS, 2), null, null);
        inv.setCoins(0);

        inv.updateInventory(card1Compas, null, null, null);
        board.scores();
        assertEquals(1, inv.getScore());
        inv.setScore(0);

        inv.updateInventory(card1Compas, null, null, null);
        board.scores();
        assertEquals(4, inv.getScore());
        inv.setScore(0);

        inv.updateInventory(card2Compas, null, null, null);
        board.scores();
        assertEquals(16, inv.getScore());

    }

    @Test
    void tripleScientificScoreTest() {
        // We claim a test Board, then test if we got the base resource.
        Board board = new Board(playerList, false);
        Inventory inv = board.getPlayerInventoryList().get(0);

        Card card3ScientificSymbol = new Card("", new Effect[]{new SymbolEffect(Symbol.COMPAS, 1), new SymbolEffect(Symbol.STELE, 1), new SymbolEffect(Symbol.ROUAGE, 1)}, null, null);
        inv.setCoins(0);

        inv.updateInventory(card3ScientificSymbol, null, null, null);
        board.scores();
        assertEquals(10, inv.getScore());
        inv.setScore(0);

        inv.updateInventory(card3ScientificSymbol, null, null, null);
        board.scores();
        assertEquals(26, inv.getScore());
        inv.setScore(0);

        Card card1Compas = new Card("", new SymbolEffect(Symbol.COMPAS, 1), null, null);
        inv.updateInventory(card1Compas, null, null, null);
        board.scores();
        assertEquals(31, inv.getScore());
        inv.setScore(0);
    }


}


