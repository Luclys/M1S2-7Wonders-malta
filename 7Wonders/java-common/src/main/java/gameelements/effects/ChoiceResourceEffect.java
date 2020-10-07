package gameelements.effects;

import gameelements.Effect;
import gameelements.Inventory;
import gameelements.enums.EffectDelay;
import gameelements.enums.EffectFrequency;
import gameelements.enums.Resource;

public class ChoiceResourceEffect extends Effect {
    Resource[] resources;
    int nb;

    public ChoiceResourceEffect(Resource[] resources, int nb) {
        super(EffectDelay.INSTANTANEOUS, EffectFrequency.EVERY_TURN);
        this.resources = resources;
        this.nb = nb;
    }

    public void activateEffect(Inventory inv) {
        super.activateEffect(inv);
        if (resources.length == 2) {
            inv.addPairResChoice(resources);
        }
    }
}
