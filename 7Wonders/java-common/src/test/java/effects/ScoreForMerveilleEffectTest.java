package effects;

import gameelements.Inventory;
import gameelements.Player;
import gameelements.effects.ScoreForMerveilleEffect;
import gameelements.enums.EffectDelay;
import gameelements.wonders.WonderBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ScoreForMerveilleEffectTest {
    Player player;
    Inventory inv;
    Inventory leftNeighborInv;
    Inventory rightNeighborInv;
    ScoreForMerveilleEffect scoreEffect;


    @BeforeEach
    void setUp() {
        player = new Player(0);
        inv = new Inventory(0);
        leftNeighborInv = new Inventory(1);
        rightNeighborInv = new Inventory(2);
        scoreEffect = new ScoreForMerveilleEffect(5);
        inv.setWonderBoard(WonderBoard.initiateColossus());
    }

    @Test
    void activateEffectNotEndGameTest() {
        scoreEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, false);
        assertTrue(inv.getEndGameEffects().contains(scoreEffect));
        scoreEffect.setDelay(EffectDelay.INSTANTANEOUS);
        int stepsCount = inv.getWonderBoard().getCurrentStepIndex();
        int score = scoreEffect.getScore() * stepsCount;
        scoreEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, false);
        assertEquals(score, inv.getScore());
    }

    @Test
    void activateEffectEndGameTest() {
        int stepsCount = inv.getWonderBoard().getCurrentStepIndex();
        int score = scoreEffect.getScore() * stepsCount;
        scoreEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, true);
        assertEquals(score, inv.getScore());
        scoreEffect.setDelay(EffectDelay.INSTANTANEOUS);
        stepsCount = inv.getWonderBoard().getCurrentStepIndex();
        score = scoreEffect.getScore() * stepsCount;
        scoreEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, true);
        assertEquals(score, inv.getScore());
    }
}
