package gameelements.effects;

import gameelements.Inventory;
import gameelements.enums.EffectDelay;

public class ScoreForNeighborsDefeatJetonsEffect extends Effect {
    int score;

    public ScoreForNeighborsDefeatJetonsEffect(int score) {
        super(EffectDelay.END_OF_THE_GAME);
        this.score = score;
    }

    @Override
    public void activateEffect(Inventory inv, Inventory leftNeighborInv, Inventory rightNeighborInv, boolean isEndGame) {
        if ((!isEndGame) && (getDelay() == EffectDelay.END_OF_THE_GAME)) {
            inv.addEndGameEffect(this);
            return;
        }
        inv.addScore((leftNeighborInv.getDefeatChipsCount() + rightNeighborInv.getDefeatChipsCount()) * score);
    }

    public int getScore() {
        return score;
    }
}
