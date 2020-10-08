package gameelements;

import gameelements.enums.EffectDelay;

public abstract class Effect {
    EffectDelay delay;


    public Effect(EffectDelay delay) {
        this.delay = delay;
    }

    // Needed : 2 neighbors, and Player Inventories
    public void activateEffect(Player player, Inventory inv, Inventory leftNeighborInv, Inventory rightNeighborInv) {

/*
        if (delay == 0 && repeat == 0) {
        }
*/
    }
}
