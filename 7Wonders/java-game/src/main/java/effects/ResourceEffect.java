package effects;

import gameelements.Effect;
import gameelements.Inventory;
import gameelements.enums.Resource;

public class ResourceEffect extends Effect {
    Resource resource;
    int nb;

    public ResourceEffect(String name, Resource resource, int nb) {
        super(name);
        this.resource = resource;
        this.nb = nb;
    }

    public void activateEffect(Inventory inv) {
        super.activateEffect(inv);
        inv.getAvailableResources()[resource.getIndex()] += nb;

        changeStatus();
    }
}
