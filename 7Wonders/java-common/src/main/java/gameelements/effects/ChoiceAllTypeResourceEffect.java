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

    public void activateEffect(Player player, Inventory inv, Inventory leftNeighborInv, Inventory rightNeighborInv) {
        super.activateEffect(player, inv, leftNeighborInv, rightNeighborInv);
        inv.incAllResChoice(primaryResource);
    }
}
