package gameelements.effects;

import gameelements.Effect;
import gameelements.Inventory;

public class ReductCommerceEffect extends Effect {
    // 0 = Left, 1 = Right, 2 = Both.
    int whichNeighbor;
    Boolean primaryRessources;

    public ReductCommerceEffect(int whichNeighbor, Boolean primaryRessources) {
        this.whichNeighbor = whichNeighbor;
        this.primaryRessources = primaryRessources;
    }

    public void activateEffect(Inventory inv) {
        super.activateEffect(inv);
        // TODO : Compléter pour mettre le prix à 1.
        changeStatus();
    }
}
