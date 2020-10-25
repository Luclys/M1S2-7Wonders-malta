package effects;

import gameelements.Inventory;
import gameelements.Player;
import gameelements.effects.ChoiceAllTypeResourceEffect;
import gameelements.enums.EffectDelay;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ChoiceAllTypeResourceEffectTest {
    Player player;
    Inventory inv;
    Inventory leftNeighborInv;
    Inventory rightNeighborInv;
    ChoiceAllTypeResourceEffect choiceAllTypeResourceEffect;

    @BeforeEach
    void setUp() {
        player = new Player(0);
        inv = new Inventory(0);
        leftNeighborInv = new Inventory(1);
        rightNeighborInv = new Inventory(2);
        choiceAllTypeResourceEffect = new ChoiceAllTypeResourceEffect(true);
    }

    @Test
    void activateEffectNotEndGameTest() {
        int resChoice = inv.getAllResPremChoice();
        //Not end of the game
        choiceAllTypeResourceEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, false);
        assertEquals(resChoice + 1, inv.getAllResPremChoice());
        choiceAllTypeResourceEffect.setDelay(EffectDelay.END_OF_THE_GAME);
        choiceAllTypeResourceEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, false);
        assertTrue(inv.getEndGameEffects().contains(choiceAllTypeResourceEffect));
    }

    @Test
    void activateEffectEndGameTest() {
        int resChoice = inv.getAllResPremChoice();
        choiceAllTypeResourceEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, true);
        assertEquals(resChoice + 1, inv.getAllResPremChoice());
        inv.setAllResPremChoice(resChoice);
        choiceAllTypeResourceEffect.setDelay(EffectDelay.END_OF_THE_GAME);
        choiceAllTypeResourceEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, true);
        assertEquals(resChoice + 1, inv.getAllResPremChoice());

        int manuChoice = inv.getAllResManuChoice();
        choiceAllTypeResourceEffect = new ChoiceAllTypeResourceEffect(false);
        choiceAllTypeResourceEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, true);
        assertEquals(manuChoice + 1, inv.getAllResManuChoice());
    }
}
