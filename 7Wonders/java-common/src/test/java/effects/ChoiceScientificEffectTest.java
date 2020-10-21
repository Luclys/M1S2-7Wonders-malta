package effects;

import gameelements.Inventory;
import gameelements.Player;
import gameelements.effects.ChoiceScientificEffect;
import gameelements.enums.EffectDelay;
import gameelements.enums.Symbol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ChoiceScientificEffectTest {
    Player player;
    Inventory inv;
    Inventory leftNeighborInv;
    Inventory rightNeighborInv;
    ChoiceScientificEffect choiceScientificEffect;

    @BeforeEach
    void setUp() {
        player = new Player(0);
        inv = new Inventory(0);
        leftNeighborInv = new Inventory(1);
        rightNeighborInv = new Inventory(2);
        choiceScientificEffect = new ChoiceScientificEffect();
    }

    @Test
    void activateEffectNotEndGameTest() {
        choiceScientificEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, false);
        assertTrue(inv.getEndGameEffects().contains(choiceScientificEffect));
        choiceScientificEffect.setDelay(EffectDelay.INSTANTANEOUS);
        Symbol symbol = player.chooseScientific(inv.getAvailableSymbols().clone());
        int countSumbol =inv.getAvailableSymbols()[symbol.getIndex()];
        choiceScientificEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, false);
        assertEquals(countSumbol+1, inv.getAvailableSymbols()[symbol.getIndex()]);
    }

    @Test
    void activateEffectEndGameTest() {
        Symbol symbol = player.chooseScientific(inv.getAvailableSymbols().clone());
        int countSumbol =inv.getAvailableSymbols()[symbol.getIndex()];
        choiceScientificEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, true);
        assertEquals(countSumbol+1, inv.getAvailableSymbols()[symbol.getIndex()]);
        choiceScientificEffect.setDelay(EffectDelay.INSTANTANEOUS);
        symbol = player.chooseScientific(inv.getAvailableSymbols().clone());
        countSumbol =inv.getAvailableSymbols()[symbol.getIndex()];
        choiceScientificEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, true);
        assertEquals(countSumbol+1, inv.getAvailableSymbols()[symbol.getIndex()]);
    }
}
