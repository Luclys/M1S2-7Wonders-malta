package gameelements.effects;

import gameelements.Inventory;
import gameelements.Player;
import gameelements.enums.EffectDelay;

public class ScoreForOwnAndNeighborMerveillesEffect extends Effect {
    int score;

    public ScoreForOwnAndNeighborMerveillesEffect(int score) {
        super(EffectDelay.END_OF_THE_GAME);
        this.score = score;
    }

    @Override
    public void activateEffect(Player player, Inventory inv, Inventory leftNeighborInv, Inventory rightNeighborInv, boolean isEndGame) {
        if ((!isEndGame) && (getDelay() == EffectDelay.END_OF_THE_GAME)) {
            inv.addEndGameEffect(this);
            return;
        }
        int stepsCount = inv.getWonderBoard().getCurrentStepIndex();
        int leftNeighborStepsCount = leftNeighborInv.getWonderBoard().getCurrentStepIndex();
        int rightNeighborStepsCount = rightNeighborInv.getWonderBoard().getCurrentStepIndex();
        inv.addScore((stepsCount + leftNeighborStepsCount + rightNeighborStepsCount) * score);
    }

    public int getScore() {
        return score;
    }
}
