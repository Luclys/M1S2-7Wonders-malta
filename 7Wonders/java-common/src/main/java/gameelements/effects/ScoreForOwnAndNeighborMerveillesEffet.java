package gameelements.effects;

import gameelements.Effect;
import gameelements.Inventory;
import gameelements.Player;
import gameelements.enums.EffectDelay;

public class ScoreForOwnAndNeighborMerveillesEffet extends Effect {
    int score;

    public ScoreForOwnAndNeighborMerveillesEffet(int score) {
        super(EffectDelay.END_OF_THE_GAME);
        this.score = score;
    }

    @Override
    public void activateEffect(Player player, Inventory inv, Inventory leftNeighborInv, Inventory rightNeighborInv, boolean isEndGame) {
        if ((!isEndGame) && (delay == EffectDelay.END_OF_THE_GAME)) {
            inv.addEndGameEffect(this);
            return;
        }
        int stepsCount = inv.getWonderBoard().getCurrentStepIndex();
        int leftNeighborStepsCount = leftNeighborInv.getWonderBoard().getCurrentStepIndex();
        int rightNeighborStepsCount = rightNeighborInv.getWonderBoard().getCurrentStepIndex();
        inv.addScore((stepsCount + leftNeighborStepsCount + rightNeighborStepsCount) * score);
    }
}
