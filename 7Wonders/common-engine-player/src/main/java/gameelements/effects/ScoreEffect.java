package gameelements.effects;

import gameelements.Inventory;
import gameelements.enums.EffectDelay;

public class ScoreEffect extends Effect {
    int nb;

    public ScoreEffect(int nb) {
        super(EffectDelay.INSTANTANEOUS);
        this.nb = nb;
    }

    @Override
    public void activateEffect(Inventory inv, Inventory leftNeighborInv, Inventory rightNeighborInv, boolean isEndGame) {
        if ((!isEndGame) && (getDelay() == EffectDelay.END_OF_THE_GAME)) {
            inv.addEndGameEffect(this);
            return;
        }
        inv.addScore(nb);
    }

    @Override
    public int getConstantlyAddedItem() {
        return nb;
    }

}
