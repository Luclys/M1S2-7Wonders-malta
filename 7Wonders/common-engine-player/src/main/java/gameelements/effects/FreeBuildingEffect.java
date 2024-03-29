package gameelements.effects;

import gameelements.Inventory;
import gameelements.enums.EffectDelay;

public class FreeBuildingEffect extends Effect {

    public FreeBuildingEffect() {
        super(EffectDelay.INSTANTANEOUS);
    }

    @Override
    public void activateEffect(Inventory inv, Inventory leftNeighborInv, Inventory rightNeighborInv, boolean isEndGame) {
        inv.setPossibleFreeBuildings(1);
    }
}
