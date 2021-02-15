package gameelements.effects;

import gameelements.Inventory;
import gameelements.enums.EffectDelay;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FreeDiscardedBuildingEffectTest {
    Inventory inv;
    Inventory leftNeighborInv;
    Inventory rightNeighborInv;
    FreeDiscardedBuildingEffect freeDiscardedBuildingEffect;

    @BeforeEach
    void setUp() {
        inv = new Inventory(0);
        leftNeighborInv = new Inventory(1);
        rightNeighborInv = new Inventory(2);
        freeDiscardedBuildingEffect = new FreeDiscardedBuildingEffect();
    }

    @Test
    void activateEffectNotEndGameTest() {
        int possibleFreeDiscardedBuildingsCount = inv.getPossibleFreeDiscardedBuildingsCount();
        //Not end of the game
        freeDiscardedBuildingEffect.activateEffect(inv, leftNeighborInv, rightNeighborInv, false);
        assertEquals(possibleFreeDiscardedBuildingsCount + 1, inv.getPossibleFreeDiscardedBuildingsCount());
        freeDiscardedBuildingEffect.setDelay(EffectDelay.END_OF_THE_GAME);
        freeDiscardedBuildingEffect.activateEffect(inv, leftNeighborInv, rightNeighborInv, false);
        assertTrue(inv.getEndGameEffects().contains(freeDiscardedBuildingEffect));

    }

    @Test
    void activateEffectEndGameTest() {
        int possibleFreeDiscardedBuildingsCount = inv.getPossibleFreeDiscardedBuildingsCount();
        freeDiscardedBuildingEffect.activateEffect(inv, leftNeighborInv, rightNeighborInv, true);
        assertEquals(possibleFreeDiscardedBuildingsCount + 1, inv.getPossibleFreeDiscardedBuildingsCount());
        freeDiscardedBuildingEffect.setDelay(EffectDelay.END_OF_THE_GAME);
        freeDiscardedBuildingEffect.activateEffect(inv, leftNeighborInv, rightNeighborInv, true);
        assertEquals(possibleFreeDiscardedBuildingsCount + 2, inv.getPossibleFreeDiscardedBuildingsCount());
    }
}
