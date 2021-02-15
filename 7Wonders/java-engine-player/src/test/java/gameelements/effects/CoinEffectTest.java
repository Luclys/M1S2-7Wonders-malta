package gameelements.effects;

import gameelements.Inventory;
import gameelements.enums.EffectDelay;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CoinEffectTest {
    Inventory inv;
    Inventory leftNeighborInv;
    Inventory rightNeighborInv;
    CoinEffect coinEffect;

    @BeforeEach
    void setUp() {
        inv = new Inventory(0);
        leftNeighborInv = new Inventory(1);
        rightNeighborInv = new Inventory(2);
        coinEffect = new CoinEffect(1);
    }

    @Test
    void activateEffectNotEndGameTest() {
        int coins = inv.getCoins();
        //Not end of the game
        coinEffect.activateEffect(inv, leftNeighborInv, rightNeighborInv, false);
        assertEquals(coins + 1, inv.getCoins());
        inv.setCoins(coins);
        coinEffect.setDelay(EffectDelay.END_OF_THE_GAME);
        coinEffect.activateEffect(inv, leftNeighborInv, rightNeighborInv, false);
        assertTrue(inv.getEndGameEffects().contains(coinEffect));

    }

    @Test
    void activateEffectEndGameTest() {
        int coins = inv.getCoins();
        coinEffect.activateEffect(inv, leftNeighborInv, rightNeighborInv, true);
        assertEquals(coins + 1, inv.getCoins());
        inv.setCoins(coins);
        coinEffect.setDelay(EffectDelay.END_OF_THE_GAME);
        coinEffect.activateEffect(inv, leftNeighborInv, rightNeighborInv, true);
        assertEquals(coins + 1, inv.getCoins());
    }
}
