package gameelements;

import gameelements.enums.EffectDelay;
import gameelements.enums.EffectFrequency;

public abstract class Effect {
    EffectDelay delay;
    EffectFrequency repeat;


    public Effect(EffectDelay delay, EffectFrequency repeat) {
        this.delay = delay;
        this.repeat = repeat;
    }

    // Needed : 2 neighbors, and Player Inventories
    public void activateEffect(Player player, Inventory inv, Inventory leftNeighborInv, Inventory rightNeighborInv) {

/*
        if (delay == 0 && repeat == 0) {
        }
*/
    }
}
