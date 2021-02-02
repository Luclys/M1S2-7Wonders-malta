package gameelements.effects;

import gameelements.Inventory;
import gameelements.Player;
import gameelements.enums.EffectDelay;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ScoreEffectTest {
    Player player;
    Inventory inv;
    Inventory leftNeighborInv;
    Inventory rightNeighborInv;
    ScoreEffect scoreEffect;


    @BeforeEach
    void setUp() {
        player = new Player(0);
        inv = new Inventory(0);
        leftNeighborInv = new Inventory(1);
        rightNeighborInv = new Inventory(2);
        scoreEffect = new ScoreEffect(1);
    }

    @Test
    void activateEffectNotEndGameTest() {
        int score = inv.getScore();
        //Not end of the game
        scoreEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, false);
        assertEquals(score + 1, inv.getScore());
        scoreEffect.setDelay(EffectDelay.END_OF_THE_GAME);
        scoreEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, false);
        assertEquals(score + 1, inv.getScore());
        assertTrue(inv.getEndGameEffects().contains(scoreEffect));
    }

    @Test
    void activateEffectEndGameTest() {
        int score = inv.getScore();
        //Not end of the game
        scoreEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, true);
        assertEquals(score + 1, inv.getScore());
        scoreEffect.setDelay(EffectDelay.END_OF_THE_GAME);
        scoreEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, true);
        assertEquals(score + 2, inv.getScore());
    }
}
