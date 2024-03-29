package gameelements.effects;

import gameelements.Inventory;
import gameelements.enums.EffectDelay;
import gameelements.enums.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RessourceEffectTest {
    Inventory inv;
    Inventory leftNeighborInv;
    Inventory rightNeighborInv;
    ResourceEffect resourceEffect;


    @BeforeEach
    void setUp() {
        inv = new Inventory(0);
        leftNeighborInv = new Inventory(1);
        rightNeighborInv = new Inventory(2);
        resourceEffect = new ResourceEffect(Resource.MINERAI, 1);
    }

    @Test
    void activateEffectNotEndGameTest() {
        int nbResource = inv.getAvailableResources()[resourceEffect.getResource().getIndex()];
        //Not end of the game
        resourceEffect.activateEffect(inv, leftNeighborInv, rightNeighborInv, false);
        assertEquals(nbResource + 1, inv.getAvailableResources()[resourceEffect.getResource().getIndex()]);
        resourceEffect.setDelay(EffectDelay.END_OF_THE_GAME);
        resourceEffect.activateEffect(inv, leftNeighborInv, rightNeighborInv, false);
        assertEquals(nbResource + 1, inv.getAvailableResources()[resourceEffect.getResource().getIndex()]);
        assertTrue(inv.getEndGameEffects().contains(resourceEffect));
    }

    @Test
    void activateEffectEndGameTest() {
        int nbResource = inv.getAvailableResources()[resourceEffect.getResource().getIndex()];
        //Not end of the game
        resourceEffect.activateEffect(inv, leftNeighborInv, rightNeighborInv, true);
        assertEquals(nbResource + 1, inv.getAvailableResources()[resourceEffect.getResource().getIndex()]);
        resourceEffect.setDelay(EffectDelay.END_OF_THE_GAME);
        resourceEffect.activateEffect(inv, leftNeighborInv, rightNeighborInv, true);
        assertEquals(nbResource + 2, inv.getAvailableResources()[resourceEffect.getResource().getIndex()]);
    }
}
