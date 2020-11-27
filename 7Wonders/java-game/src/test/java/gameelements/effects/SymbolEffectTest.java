package gameelements.effects;

import gameelements.Inventory;
import gameelements.Player;
import gameelements.enums.EffectDelay;
import gameelements.enums.Symbol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SymbolEffectTest {
    Player player;
    Inventory inv;
    Inventory leftNeighborInv;
    Inventory rightNeighborInv;
    SymbolEffect symbolEffect;


    @BeforeEach
    void setUp() {
        player = new Player(0);
        inv = new Inventory(0);
        leftNeighborInv = new Inventory(1);
        rightNeighborInv = new Inventory(2);
        symbolEffect = new SymbolEffect(Symbol.BOUCLIER, 1);
    }

    @Test
    void activateEffectNotEndGameTest() {
        int nbSymbol = inv.getAvailableSymbols()[symbolEffect.getSymbol().getIndex()];
        //Not end of the game
        symbolEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, false);
        assertEquals(nbSymbol + 1, inv.getAvailableSymbols()[symbolEffect.getSymbol().getIndex()]);
        symbolEffect.setDelay(EffectDelay.END_OF_THE_GAME);
        symbolEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, false);
        assertEquals(nbSymbol + 1, inv.getAvailableSymbols()[symbolEffect.getSymbol().getIndex()]);
        assertTrue(inv.getEndGameEffects().contains(symbolEffect));
    }

    @Test
    void activateEffectEndGameTest() {
        int nbSymbol = inv.getAvailableSymbols()[symbolEffect.getSymbol().getIndex()];
        //Not end of the game
        symbolEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, true);
        assertEquals(nbSymbol + 1, inv.getAvailableSymbols()[symbolEffect.getSymbol().getIndex()]);
        symbolEffect.setDelay(EffectDelay.END_OF_THE_GAME);
        symbolEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, true);
        assertEquals(nbSymbol + 2, inv.getAvailableSymbols()[symbolEffect.getSymbol().getIndex()]);
    }
}
