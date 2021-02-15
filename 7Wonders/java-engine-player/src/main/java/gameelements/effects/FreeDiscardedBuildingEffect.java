package gameelements.effects;

import gameelements.Inventory;
import gameelements.enums.EffectDelay;

public class FreeDiscardedBuildingEffect extends Effect {

    public FreeDiscardedBuildingEffect() {
        super(EffectDelay.INSTANTANEOUS);
    }

    @Override
    public void activateEffect(Inventory inv, Inventory leftNeighborInv, Inventory rightNeighborInv, boolean isEndGame) {
        if ((!isEndGame) && (getDelay() == EffectDelay.END_OF_THE_GAME)) {
            inv.addEndGameEffect(this);
            return;
        }
        inv.addPossibleFreeDiscardedBuildingsCount(1);
    }
}
