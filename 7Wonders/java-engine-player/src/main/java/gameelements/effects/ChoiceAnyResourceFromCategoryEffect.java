package gameelements.effects;

import gameelements.Inventory;
import gameelements.enums.EffectDelay;

/**
 * This  class describe  Choice of Any Resource From Category Effect
 *
 * @author lamac
 */

public class ChoiceAnyResourceFromCategoryEffect extends Effect {
    Boolean primaryResource;

    public ChoiceAnyResourceFromCategoryEffect(Boolean primaryResource) {
        super(EffectDelay.INSTANTANEOUS);
        this.primaryResource = primaryResource;
    }

    /**
     * This method activate the effect by incrementing the count of primary Resource
     * or the count of manufacture resource when it is end of the game
     *  @param inv
     * @param leftNeighborInv
     * @param rightNeighborInv
     * @param isEndGame
     */

    @Override
    public void activateEffect(Inventory inv, Inventory leftNeighborInv, Inventory rightNeighborInv, boolean isEndGame) {
        if ((!isEndGame) && (getDelay() == EffectDelay.END_OF_THE_GAME)) {
            inv.addEndGameEffect(this);
            return;
        }
        inv.incAllResChoice(primaryResource);
    }
}
