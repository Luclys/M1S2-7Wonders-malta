package gameelements;

import gameelements.cards.Card;
import gameelements.enums.EffectDelay;

public abstract class Effect {
    public EffectDelay delay;


    public Effect(EffectDelay delay) {
        this.delay = delay;
    }

    // Needed : 2 neighbors, and Player Inventories
    public void activateEffect(Player player, Inventory inv, Inventory leftNeighborInv, Inventory rightNeighborInv) {
        activateEffect(player, inv, leftNeighborInv, rightNeighborInv, false);
    }

    public abstract void activateEffect(Player player, Inventory inv, Inventory leftNeighborInv, Inventory rightNeighborInv, boolean isEndGame);
}
