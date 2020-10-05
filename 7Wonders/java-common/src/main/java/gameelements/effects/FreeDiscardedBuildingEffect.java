package gameelements.effects;

import gameelements.Effect;
import gameelements.Inventory;

public class FreeDiscardedBuildingEffect extends Effect {

    public FreeDiscardedBuildingEffect() {
        super(0, 0);
    }

    public void activateEffect(Inventory inv) {
        super.activateEffect(inv);
        // TODO : Allow 1 free construction from discarded ⚠ Effective at the end of the turn !
        changeStatus();
    }
}
