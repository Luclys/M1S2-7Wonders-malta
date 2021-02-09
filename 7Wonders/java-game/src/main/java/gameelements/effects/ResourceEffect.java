package gameelements.effects;

import gameelements.Inventory;
import gameelements.enums.EffectDelay;
import gameelements.enums.Resource;

public class ResourceEffect extends Effect {
    Resource resource;
    int nb;

    public ResourceEffect(Resource resource, int nb) {
        super(EffectDelay.INSTANTANEOUS);
        this.resource = resource;
        this.nb = nb;
    }

    @Override
    public void activateEffect(Inventory inv, Inventory leftNeighborInv, Inventory rightNeighborInv, boolean isEndGame) {
        if ((!isEndGame) && (getDelay() == EffectDelay.END_OF_THE_GAME)) {
            inv.addEndGameEffect(this);
            return;
        }
        inv.getAvailableResources()[resource.getIndex()] += nb;
    }

    @Override
    public int getConstantlyAddedItem() {
        return nb;
    }


    public Resource getResource() {
        return resource;
    }
}
