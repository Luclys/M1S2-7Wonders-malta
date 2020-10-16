package gameelements.effects;

import gameelements.Effect;
import gameelements.Inventory;
import gameelements.Player;
import gameelements.enums.EffectDelay;

public class ChoiceAllTypeResourceEffect extends Effect {
    Boolean primaryResource;

    public ChoiceAllTypeResourceEffect(Boolean primaryResource) {
        super(EffectDelay.INSTANTANEOUS);
        this.primaryResource = primaryResource;
    }

    @Override
    public void activateEffect(Player player, Inventory inv, Inventory leftNeighborInv, Inventory rightNeighborInv, boolean isEndGame) {
        if ((!isEndGame) && (getDelay() == EffectDelay.END_OF_THE_GAME)) {
            inv.addEndGameEffect(this);
            return;
        }
        inv.incAllResChoice(primaryResource);
    }
}
