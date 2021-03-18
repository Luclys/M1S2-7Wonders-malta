package gameelements.effects;

import gameelements.Inventory;
import gameelements.enums.EffectDelay;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ChoiceAnyResourceFromCategoryEffectTest {
    Inventory inv;
    Inventory leftNeighborInv;
    Inventory rightNeighborInv;
    ChoiceAnyResourceFromCategoryEffect choiceAnyResourceFromCategoryEffect;

    @BeforeEach
    void setUp() {
        inv = new Inventory(0);
        leftNeighborInv = new Inventory(1);
        rightNeighborInv = new Inventory(2);
        choiceAnyResourceFromCategoryEffect = new ChoiceAnyResourceFromCategoryEffect(true);
    }

    @Test
    void activateEffectNotEndGameTest() {
        int resChoice = inv.getAnyMatierePremiereAvailableCount();
        //Not end of the game
        choiceAnyResourceFromCategoryEffect.activateEffect(inv, leftNeighborInv, rightNeighborInv, false);
        assertEquals(resChoice + 1, inv.getAnyMatierePremiereAvailableCount());
        choiceAnyResourceFromCategoryEffect.setDelay(EffectDelay.END_OF_THE_GAME);
        choiceAnyResourceFromCategoryEffect.activateEffect(inv, leftNeighborInv, rightNeighborInv, false);
        assertTrue(inv.getEndGameEffects().contains(choiceAnyResourceFromCategoryEffect));
    }

    @Test
    void activateEffectEndGameTest() {
        int resChoice = inv.getAnyMatierePremiereAvailableCount();
        choiceAnyResourceFromCategoryEffect.activateEffect(inv, leftNeighborInv, rightNeighborInv, true);
        assertEquals(resChoice + 1, inv.getAnyMatierePremiereAvailableCount());
        inv.setAnyMatierePremiereAvailableCount(resChoice);
        choiceAnyResourceFromCategoryEffect.setDelay(EffectDelay.END_OF_THE_GAME);
        choiceAnyResourceFromCategoryEffect.activateEffect(inv, leftNeighborInv, rightNeighborInv, true);
        assertEquals(resChoice + 1, inv.getAnyMatierePremiereAvailableCount());

        choiceAnyResourceFromCategoryEffect = new ChoiceAnyResourceFromCategoryEffect(false);
        resChoice = inv.getAnyProduitManufactureAvailableCount();
        choiceAnyResourceFromCategoryEffect.activateEffect(inv, leftNeighborInv, rightNeighborInv, true);
        assertEquals(resChoice + 1, inv.getAnyMatierePremiereAvailableCount());
    }
}
