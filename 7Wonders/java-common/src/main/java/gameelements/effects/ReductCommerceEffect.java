package gameelements.effects;

import gameelements.Effect;
import gameelements.Inventory;
import gameelements.enums.EffectDelay;
import gameelements.enums.EffectFrequency;

public class ReductCommerceEffect extends Effect {
    // 0 = Left, 1 = Right, 2 = Both.
    int whichNeighbor;
    Boolean primaryRessources;

    public ReductCommerceEffect(int whichNeighbor, Boolean primaryRessources) {
        super(EffectDelay.WHENEVER_PLAYER_WANTS, EffectFrequency.EVERY_TURN);
        this.whichNeighbor = whichNeighbor;
        this.primaryRessources = primaryRessources;
    }

    public void activateEffect(Inventory inv) {
        super.activateEffect(inv);
        // TODO : Compléter pour mettre le prix à 1 selon .
    }
}
