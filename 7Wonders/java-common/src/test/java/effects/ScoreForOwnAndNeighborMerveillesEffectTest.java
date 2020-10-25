package effects;

import gameelements.Inventory;
import gameelements.Player;
import gameelements.effects.ScoreForOwnAndNeighborMerveillesEffect;
import gameelements.enums.EffectDelay;
import gameelements.wonders.WonderBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ScoreForOwnAndNeighborMerveillesEffectTest {
    Player player;
    Inventory inv;
    Inventory leftNeighborInv;
    Inventory rightNeighborInv;
    ScoreForOwnAndNeighborMerveillesEffect scoreEffect;

    @BeforeEach
    void setUp() {
        player = new Player(0);
        inv = new Inventory(0);
        leftNeighborInv = new Inventory(1);
        rightNeighborInv = new Inventory(2);
        scoreEffect = new ScoreForOwnAndNeighborMerveillesEffect(2);
        inv.setWonderBoard(WonderBoard.initiateColossusA());
        leftNeighborInv.setWonderBoard(WonderBoard.initiateColossusA());
        rightNeighborInv.setWonderBoard(WonderBoard.initiateColossusA());
    }

    @Test
    void activateEffectNotEndGameTest() {
        scoreEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, false);
        assertTrue(inv.getEndGameEffects().contains(scoreEffect));
        scoreEffect.setDelay(EffectDelay.INSTANTANEOUS);

        int stepsCount = inv.getWonderBoard().getCurrentStepIndex();
        int leftNeighborStepsCount = leftNeighborInv.getWonderBoard().getCurrentStepIndex();
        int rightNeighborStepsCount = rightNeighborInv.getWonderBoard().getCurrentStepIndex();
        int score = (stepsCount + leftNeighborStepsCount + rightNeighborStepsCount) * scoreEffect.getScore()+inv.getScore();
        scoreEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, false);
        assertEquals(score, inv.getScore());
    }

    @Test
    void activateEffectEndGameTest() {
        int stepsCount = inv.getWonderBoard().getCurrentStepIndex();
        int leftNeighborStepsCount = leftNeighborInv.getWonderBoard().getCurrentStepIndex();
        int rightNeighborStepsCount = rightNeighborInv.getWonderBoard().getCurrentStepIndex();
        int score = (stepsCount + leftNeighborStepsCount + rightNeighborStepsCount) * scoreEffect.getScore()+inv.getScore();
        scoreEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, true);
        assertEquals(score, inv.getScore());
        scoreEffect.setDelay(EffectDelay.INSTANTANEOUS);
        stepsCount = inv.getWonderBoard().getCurrentStepIndex();
        leftNeighborStepsCount = leftNeighborInv.getWonderBoard().getCurrentStepIndex();
        rightNeighborStepsCount = rightNeighborInv.getWonderBoard().getCurrentStepIndex();
        score = (stepsCount + leftNeighborStepsCount + rightNeighborStepsCount) * scoreEffect.getScore()+inv.getScore();
        scoreEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, true);
        assertEquals(score, inv.getScore());
    }
}
