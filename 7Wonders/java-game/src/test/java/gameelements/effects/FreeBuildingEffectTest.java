package gameelements.effects;

import gameelements.Inventory;
import gameelements.Player;
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
        int freeBuildingsCount = inv.getPossibleFreeBuildings();
        //Not end of the game
        assertEquals(0, inv.getPossibleFreeBuildings());
        freeBuildingEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, false);
        assertEquals(1, inv.getPossibleFreeBuildings());
    }

    @Test
    void activateEffectEndGameTest() {
        int freeBuildingsCount = inv.getPossibleFreeBuildings();
        freeBuildingEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, true);
        assertEquals(1, inv.getPossibleFreeBuildings());
    }


}
