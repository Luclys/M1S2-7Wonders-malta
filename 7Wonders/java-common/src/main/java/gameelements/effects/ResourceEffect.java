package gameelements.effects;

import gameelements.Effect;
import gameelements.Inventory;
import gameelements.enums.EffectDelay;
import gameelements.enums.EffectFrequency;
import gameelements.enums.Resource;

public class ResourceEffect extends Effect {
    Resource resource;
    int nb;

    public ResourceEffect(Resource resource, int nb) {
        super(EffectDelay.WHENEVER_PLAYER_WANTS, EffectFrequency.EVERY_TURN);
        this.resource = resource;
        this.nb = nb;
    }

    public void activateEffect(Inventory inv) {
        super.activateEffect(inv);
        inv.getAvailableResources()[resource.getIndex()] += nb;

        }
}
