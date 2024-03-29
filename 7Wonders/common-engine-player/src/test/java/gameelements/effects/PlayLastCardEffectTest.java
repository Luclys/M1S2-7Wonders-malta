package gameelements.effects;

import gameelements.Inventory;
import gameelements.enums.EffectDelay;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PlayLastCardEffectTest {
    Inventory inv;
    Inventory leftNeighborInv;
    Inventory rightNeighborInv;
    PlayLastCardEffect playLastCardEffect;

    @BeforeEach
    void setUp() {
        inv = new Inventory(0);
        leftNeighborInv = new Inventory(1);
        rightNeighborInv = new Inventory(2);
        playLastCardEffect = new PlayLastCardEffect();
    }

    @Test
    void activateEffectNotEndGameTest() {
        inv.setCanPlayLastCard(false);
        playLastCardEffect.activateEffect(inv, leftNeighborInv, rightNeighborInv, false);
        assertTrue(inv.isCanPlayLastCard());
        inv.setCanPlayLastCard(false);
        playLastCardEffect.setDelay(EffectDelay.END_OF_THE_GAME);
        playLastCardEffect.activateEffect(inv, leftNeighborInv, rightNeighborInv, false);
        assertTrue(inv.getEndGameEffects().contains(playLastCardEffect));
    }

    @Test
    void activateEffectEndGameTest() {
        inv.setCanPlayLastCard(false);
        playLastCardEffect.activateEffect(inv, leftNeighborInv, rightNeighborInv, true);
        assertTrue(inv.isCanPlayLastCard());
        inv.setCanPlayLastCard(false);
        playLastCardEffect.setDelay(EffectDelay.END_OF_THE_GAME);
        playLastCardEffect.activateEffect(inv, leftNeighborInv, rightNeighborInv, true);
        assertTrue(inv.isCanPlayLastCard());
    }
}
