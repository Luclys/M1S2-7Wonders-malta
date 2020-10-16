package effects;

import gameelements.Inventory;
import gameelements.Player;
import gameelements.effects.FreeDiscardedBuildingEffect;
import gameelements.enums.EffectDelay;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FreeDiscardedBuildingEffectTest {
    Player player;
    Inventory inv;
    Inventory leftNeighborInv;
    Inventory rightNeighborInv;
    FreeDiscardedBuildingEffect freeDiscardedBuildingEffect;

    @BeforeEach
    void setUp() {
        player = new Player(0);
        inv = new Inventory(0);
        leftNeighborInv = new Inventory(1);
        rightNeighborInv = new Inventory(2);
        freeDiscardedBuildingEffect = new FreeDiscardedBuildingEffect();
    }
    //addPossibleFreeDiscardedBuildingsCount

    @Test
    void activateEffectNotEndGameTest(){
        int possibleFreeDiscardedBuildingsCount = inv.getPossibleFreeDiscardedBuildingsCount();
        //Not end of the game
        freeDiscardedBuildingEffect.activateEffect(player,inv,leftNeighborInv,rightNeighborInv,false);
        assertEquals(possibleFreeDiscardedBuildingsCount+1,inv.getPossibleFreeDiscardedBuildingsCount());
        freeDiscardedBuildingEffect.setDelay(EffectDelay.END_OF_THE_GAME);
        freeDiscardedBuildingEffect.activateEffect(player,inv,leftNeighborInv,rightNeighborInv,false);
        assertTrue(inv.getEndGameEffects().contains(freeDiscardedBuildingEffect));

    }
    @Test
    void activateEffectEndGameTest(){
        int possibleFreeDiscardedBuildingsCount = inv.getPossibleFreeDiscardedBuildingsCount();
        freeDiscardedBuildingEffect.activateEffect(player,inv,leftNeighborInv,rightNeighborInv,true);
        assertEquals(possibleFreeDiscardedBuildingsCount+1,inv.getPossibleFreeDiscardedBuildingsCount());
        freeDiscardedBuildingEffect.setDelay(EffectDelay.END_OF_THE_GAME);
        freeDiscardedBuildingEffect.activateEffect(player,inv,leftNeighborInv,rightNeighborInv,true);
        assertEquals(possibleFreeDiscardedBuildingsCount+2,inv.getPossibleFreeDiscardedBuildingsCount());
    }
}
