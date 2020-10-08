package gameelements.effects;

import gameelements.Effect;
import gameelements.Inventory;
import gameelements.Player;
import gameelements.enums.EffectDelay;

public class FreeDiscardedBuildingEffect extends Effect {

    public FreeDiscardedBuildingEffect() {
        super(EffectDelay.INSTANTANEOUS);
    }

    public void activateEffect(Player player, Inventory inv, Inventory leftNeighborInv, Inventory rightNeighborInv) {
        super.activateEffect(player, inv, leftNeighborInv, rightNeighborInv);
        inv.addPossibleFreeDiscardedBuildingsCount(1);
    }
}
