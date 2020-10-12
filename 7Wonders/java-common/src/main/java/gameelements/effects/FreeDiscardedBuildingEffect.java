package gameelements.effects;

import gameelements.Effect;
import gameelements.Inventory;
import gameelements.Player;
import gameelements.enums.EffectDelay;

public class FreeDiscardedBuildingEffect extends Effect {

    public FreeDiscardedBuildingEffect() {
        super(EffectDelay.INSTANTANEOUS);
    }

    @Override
    public void activateEffect(Player player, Inventory inv, Inventory leftNeighborInv, Inventory rightNeighborInv, boolean isEndGame) {
        if ((!isEndGame) && (delay == EffectDelay.END_OF_THE_GAME)) {
            inv.addEndGameEffect(this);
            return;
        }
        inv.addPossibleFreeDiscardedBuildingsCount(1);
    }
}
