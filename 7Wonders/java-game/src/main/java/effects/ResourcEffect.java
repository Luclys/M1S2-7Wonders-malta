package effects;

import gameelements.Effect;
import gameelements.enums.Resource;

public class ResourcEffect extends Effect {
    Resource resource;
    int nb;
    public ResourcEffect(String name, Resource resource, int nb) {
        super(name);
        this.resource = resource;
        this.nb = nb;
    }

    public void activateEffect() {
        super.activateEffect();
        // Request the add of nb resource to the player
        for (int i = 0; i < nb; i++) {
             //availableResources[resource.getIndex()]++;
        }
        changeStatus();
    }
}
