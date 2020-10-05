package gameelements.effects;

import gameelements.Effect;
import gameelements.Inventory;
import gameelements.enums.EffectDelay;
import gameelements.enums.EffectFrequency;

public class FreeDiscardedBuildingEffect extends Effect {

    public FreeDiscardedBuildingEffect() {
        super(EffectDelay.INSTANTANEOUS, EffectFrequency.ONCE);
    }

    public void activateEffect(Inventory inv) {
        super.activateEffect(inv);
        // TODO : Allow 1 free construction from discarded ⚠ Effective at the end of the turn !
    }
}
