package gameelements.effects;

import gameelements.Effect;
import gameelements.Inventory;
import gameelements.enums.EffectDelay;
import gameelements.enums.EffectFrequency;
import gameelements.enums.Neighbor;

public class ReductCommerceEffect extends Effect {
    Neighbor whichNeighbor;
    Boolean primaryRessources;

    public ReductCommerceEffect(Neighbor whichNeighbor, Boolean primaryRessources) {
        super(EffectDelay.WHENEVER_PLAYER_WANTS, EffectFrequency.EVERY_TURN);
        this.whichNeighbor = whichNeighbor;
        this.primaryRessources = primaryRessources;
    }

    public void activateEffect(Inventory inv) {
        super.activateEffect(inv);
        if (whichNeighbor.equals(Neighbor.LEFT)) {
            inv.setPriceLeft(1);
        } else if (whichNeighbor.equals(Neighbor.RIGHT)) {
            inv.setPriceRight(1);
        } else {
            inv.setPriceLeft(1);
            inv.setPriceRight(1);
        }
    }
}
