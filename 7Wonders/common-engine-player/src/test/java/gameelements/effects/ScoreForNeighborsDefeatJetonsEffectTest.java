package gameelements.effects;

import gameelements.Inventory;
import gameelements.enums.EffectDelay;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import statistic.DetailedResults;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ScoreForNeighborsDefeatJetonsEffectTest {
    Inventory inv;
    Inventory leftNeighborInv;
    Inventory rightNeighborInv;
    ScoreForNeighborsDefeatJetonsEffect scoreEffect;


    @BeforeEach
    void setUp() {
        inv = new Inventory(0);
        leftNeighborInv = new Inventory(1);
        leftNeighborInv.setDetailedResults(new DetailedResults());
        rightNeighborInv = new Inventory(2);
        rightNeighborInv.setDetailedResults(new DetailedResults());
        scoreEffect = new ScoreForNeighborsDefeatJetonsEffect(5);
    }

    @Test
    void activateEffectNotEndGameTest() {
        scoreEffect.activateEffect(inv, leftNeighborInv, rightNeighborInv, false);
        assertTrue(inv.getEndGameEffects().contains(scoreEffect));

        scoreEffect.setDelay(EffectDelay.INSTANTANEOUS);
        int score = inv.getScore();
        rightNeighborInv.addDefeatJeton();
        leftNeighborInv.addDefeatJeton();
        //Not end of the game
        score += (leftNeighborInv.getDefeatChipsCount() + rightNeighborInv.getDefeatChipsCount()) * scoreEffect.getScore();
        scoreEffect.activateEffect(inv, leftNeighborInv, rightNeighborInv, false);
        assertEquals(score, inv.getScore());
    }

    @Test
    void activateEffectEndGameTest() {
        int score = inv.getScore();
        rightNeighborInv.addDefeatJeton();
        leftNeighborInv.addDefeatJeton();
        // end of the game
        score += (leftNeighborInv.getDefeatChipsCount() + rightNeighborInv.getDefeatChipsCount()) * scoreEffect.getScore();
        scoreEffect.activateEffect(inv, leftNeighborInv, rightNeighborInv, true);
        assertEquals(score, inv.getScore());
        scoreEffect.setDelay(EffectDelay.INSTANTANEOUS);
        score += (leftNeighborInv.getDefeatChipsCount() + rightNeighborInv.getDefeatChipsCount()) * scoreEffect.getScore();
        scoreEffect.activateEffect(inv, leftNeighborInv, rightNeighborInv, true);
        assertEquals(score, inv.getScore());
    }
}
