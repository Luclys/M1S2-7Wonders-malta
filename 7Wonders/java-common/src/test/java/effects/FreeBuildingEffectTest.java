package effects;

import gameelements.Inventory;
import gameelements.Player;
import gameelements.effects.FreeBuildingEffect;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FreeBuildingEffectTest {
    Player player;
    Inventory inv;
    Inventory leftNeighborInv;
    Inventory rightNeighborInv;
    FreeBuildingEffect freeBuildingEffect;

    @BeforeEach
    void setUp() {
        player = new Player(0);
        inv = new Inventory(0);
        leftNeighborInv = new Inventory(1);
        rightNeighborInv = new Inventory(2);
        freeBuildingEffect = new FreeBuildingEffect();
    }

    @Test
    void activateEffectNotEndGameTest() {
        int freeBuildingsCount = inv.getPossibleFreeBuildingsCount();
        //Not end of the game
        freeBuildingEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, false);
        assertEquals(freeBuildingsCount + 1, inv.getPossibleFreeBuildingsCount());
    }

    @Test
    void activateEffectEndGameTest() {
        int freeBuildingsCount = inv.getPossibleFreeBuildingsCount();
        freeBuildingEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, true);
        assertEquals(freeBuildingsCount + 1, inv.getPossibleFreeBuildingsCount());
    }


}
