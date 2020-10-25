package effects;

import gameelements.Inventory;
import gameelements.Player;
import gameelements.effects.CoinsForMerveilleEffect;
import gameelements.enums.EffectDelay;
import gameelements.wonders.WonderBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CoinsForMerveilleEffectTest {
    Player player;
    Inventory inv;
    Inventory leftNeighborInv;
    Inventory rightNeighborInv;
    CoinsForMerveilleEffect coinsEffect;


    @BeforeEach
    void setUp() {
        player = new Player(0);
        inv = new Inventory(0);
        leftNeighborInv = new Inventory(1);
        rightNeighborInv = new Inventory(2);
        coinsEffect = new CoinsForMerveilleEffect(5);
        inv.setWonderBoard(WonderBoard.initiateColossusA());
    }

    @Test
    void activateEffectNotEndGameTest() {
        int stepsCount = inv.getWonderBoard().getCurrentStepIndex();
        int coins = inv.getCoins();
        coins += coinsEffect.getNbCoins() * stepsCount;
        coinsEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, false);
        assertEquals(coins, inv.getCoins());
        coinsEffect.setDelay(EffectDelay.END_OF_THE_GAME);
        coinsEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, false);
        assertTrue(inv.getEndGameEffects().contains(coinsEffect));
    }

    @Test
    void activateEffectEndGameTest() {
        int stepsCount = inv.getWonderBoard().getCurrentStepIndex();
        int coins = inv.getCoins();
        coins += coinsEffect.getNbCoins() * stepsCount;
        coinsEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, true);
        assertEquals(coins, inv.getCoins());
        coinsEffect.setDelay(EffectDelay.END_OF_THE_GAME);
        stepsCount = inv.getWonderBoard().getCurrentStepIndex();
        coins += coinsEffect.getNbCoins() * stepsCount;
        coinsEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, true);
        assertEquals(coins, inv.getCoins());
    }
}
