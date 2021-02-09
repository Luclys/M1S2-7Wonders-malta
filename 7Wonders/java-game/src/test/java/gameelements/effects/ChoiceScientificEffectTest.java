package gameelements.effects;

import gameelements.Inventory;
import gameelements.enums.EffectDelay;
import gameelements.enums.Symbol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ChoiceScientificEffectTest {
    Inventory inv;
    Inventory leftNeighborInv;
    Inventory rightNeighborInv;
    ChoiceScientificEffect choiceScientificEffect;

    @BeforeEach
    void setUp() {
        inv = new Inventory(0);
        leftNeighborInv = new Inventory(1);
        rightNeighborInv = new Inventory(2);
        choiceScientificEffect = new ChoiceScientificEffect();
    }

    @Test
    void activateEffectNotEndGameTest() {
        choiceScientificEffect.activateEffect(inv, leftNeighborInv, rightNeighborInv, false);
        assertTrue(inv.getEndGameEffects().contains(choiceScientificEffect));
        choiceScientificEffect.setDelay(EffectDelay.INSTANTANEOUS);
        int countSumbol = inv.getAvailableSymbols()[Symbol.COMPAS.getIndex()];
        choiceScientificEffect.activateEffect(inv, leftNeighborInv, rightNeighborInv, false);
        assertEquals(countSumbol + 1, inv.getAvailableSymbols()[Symbol.COMPAS.getIndex()]);
    }

    @Test
    void activateEffectEndGameTest() {
        int countSumbol = inv.getAvailableSymbols()[Symbol.COMPAS.getIndex()];
        choiceScientificEffect.activateEffect(inv, leftNeighborInv, rightNeighborInv, true);
        assertEquals(countSumbol + 1, inv.getAvailableSymbols()[Symbol.COMPAS.getIndex()]);
        choiceScientificEffect.setDelay(EffectDelay.INSTANTANEOUS);
        countSumbol = inv.getAvailableSymbols()[Symbol.COMPAS.getIndex()];
        choiceScientificEffect.activateEffect(inv, leftNeighborInv, rightNeighborInv, true);
        assertEquals(countSumbol + 1, inv.getAvailableSymbols()[Symbol.COMPAS.getIndex()]);
    }
}
