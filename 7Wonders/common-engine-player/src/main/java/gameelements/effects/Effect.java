package gameelements.effects;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import gameelements.Inventory;
import gameelements.enums.EffectDelay;

/**
 * This abstract class describe All effects
 *
 * @author lamac
 */
@JsonDeserialize(as=FreeDiscardedBuildingEffect.class)
public abstract class Effect {
    private EffectDelay delay;
    public Effect(EffectDelay delay) {
        this.delay = delay;
    }

    // Needed : 2 neighbors, and Player Inventories
    public void activateEffect(Inventory inv, Inventory leftNeighborInv, Inventory rightNeighborInv) {
        activateEffect(inv, leftNeighborInv, rightNeighborInv, false);
    }

    public int getConstantlyAddedItem() {
        return 0;
    }

    public abstract void activateEffect(Inventory inv, Inventory leftNeighborInv, Inventory rightNeighborInv, boolean isEndGame);

    public EffectDelay getDelay() {
        return delay;
    }

    public void setDelay(EffectDelay delay) {
        this.delay = delay;
    }
}
