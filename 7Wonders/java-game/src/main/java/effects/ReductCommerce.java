package effects;

import gameelements.Effect;

public class ReductCommerce extends Effect {
    // 0 = Left, 1 = Right, 2 = Both.
    int whichNeighbor;
    Boolean primaryRessources;

    public ReductCommerce(String name, int whichNeighbor, Boolean primaryRessources) {
        super(name);
        this.whichNeighbor = whichNeighbor;
        this.primaryRessources = primaryRessources;
    }

    public void activateEffect() {
        super.activateEffect();
        // TODO : Compléter pour mettre le prix à 1.
        changeStatus();
    }
}
