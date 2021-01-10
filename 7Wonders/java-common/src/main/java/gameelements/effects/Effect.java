package gameelements.effects;

import gameelements.Inventory;
import gameelements.Player;
import gameelements.enums.EffectDelay;

/**
 * This abstract class describe All effects
 *
 * @author lamac
 */
public abstract class Effect {
    private EffectDelay delay;

    public Effect(EffectDelay delay) {
        this.delay = delay;
    }

    // Needed : 2 neighbors, and Player Inventories
    public void activateEffect(Player player, Inventory inv, Inventory leftNeighborInv, Inventory rightNeighborInv) {
        activateEffect(player, inv, leftNeighborInv, rightNeighborInv, false);
    }

    public int getConstantlyAddedItem() {
        return 0;
    }

    public abstract void activateEffect(Player player, Inventory inv, Inventory leftNeighborInv, Inventory rightNeighborInv, boolean isEndGame);

    public EffectDelay getDelay() {
        return delay;
    }

    public void setDelay(EffectDelay delay) {
        this.delay = delay;
    }
}
