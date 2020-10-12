package gameelements;

import gameelements.enums.EffectDelay;

public abstract class Effect {
    EffectDelay delay;


    public Effect(EffectDelay delay) {
        this.delay = delay;
    }

    // Needed : 2 neighbors, and Player Inventories
    public void activateEffect(Player player, Inventory inv, Inventory leftNeighborInv, Inventory rightNeighborInv) {
        activateEffect(player, inv, leftNeighborInv, rightNeighborInv, false);
    }

    public void activateEffect(Player player, Inventory inv, Inventory leftNeighborInv, Inventory rightNeighborInv, boolean isEndGame) {
        if ((!isEndGame) && (delay == EffectDelay.END_OF_THE_GAME)) {
            inv.addEndGameEffect(this);
            return;
        }
    }
}
