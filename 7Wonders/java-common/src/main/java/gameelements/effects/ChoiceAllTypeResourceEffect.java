package gameelements.effects;

import gameelements.Effect;
import gameelements.Inventory;
import gameelements.enums.EffectDelay;
import gameelements.enums.EffectFrequency;

public class ChoiceAllTypeResourceEffect extends Effect {
    Boolean primaryResource;

    public ChoiceAllTypeResourceEffect(Boolean primaryResource) {
        super(EffectDelay.INSTANTANEOUS, EffectFrequency.EVERY_TURN);
        this.primaryResource = primaryResource;
    }

    public void activateEffect(Inventory inv) {
        super.activateEffect(inv);
        inv.incAllResChoice(primaryResource);
    }
}
