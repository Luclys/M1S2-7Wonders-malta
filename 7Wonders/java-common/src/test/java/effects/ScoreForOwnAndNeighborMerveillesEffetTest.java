package effects;

import gameelements.Inventory;
import gameelements.Player;
import gameelements.effects.ScoreForOwnAndNeighborMerveillesEffet;
import org.junit.jupiter.api.BeforeEach;

public class ScoreForOwnAndNeighborMerveillesEffetTest {

    Player player;
    Inventory inv;
    Inventory leftNeighborInv;
    Inventory rightNeighborInv;
    ScoreForOwnAndNeighborMerveillesEffet scoreForOwnAndNeighborMerveillesEffetTest;

    @BeforeEach
    void setUp() {
        player = new Player(0);
        inv = new Inventory(0);
        leftNeighborInv = new Inventory(1);
        rightNeighborInv = new Inventory(2);
        scoreForOwnAndNeighborMerveillesEffetTest = new ScoreForOwnAndNeighborMerveillesEffet(5);
    }
/*
    @Test
    void activateEffectNotEndGameTest(){
        int coins = inv.getCoins();
        //Not end of the game
        scoreForOwnAndNeighborMerveillesEffetTest.activateEffect(player,inv,leftNeighborInv,rightNeighborInv,false);
        assertEquals(coins+1,inv.getCoins());
        inv.setCoins(coins);
        scoreForOwnAndNeighborMerveillesEffetTest.setDelay(EffectDelay.END_OF_THE_GAME);
        scoreForOwnAndNeighborMerveillesEffetTest.activateEffect(player,inv,leftNeighborInv,rightNeighborInv,false);
        assertTrue(inv.getEndGameEffects().contains(scoreForOwnAndNeighborMerveillesEffetTest));
    }
*/

/*
*         if ((!isEndGame) && (getDelay() == EffectDelay.END_OF_THE_GAME)) {
            inv.addEndGameEffect(this);
            return;
        }
        int stepsCount = inv.getWonderBoard().getCurrentStepIndex();
        int leftNeighborStepsCount = leftNeighborInv.getWonderBoard().getCurrentStepIndex();
        int rightNeighborStepsCount = rightNeighborInv.getWonderBoard().getCurrentStepIndex();
        inv.addScore((stepsCount + leftNeighborStepsCount + rightNeighborStepsCount) * score);*/


}
