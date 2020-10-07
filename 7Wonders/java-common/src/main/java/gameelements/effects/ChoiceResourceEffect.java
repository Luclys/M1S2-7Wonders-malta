package gameelements.effects;

import gameelements.Effect;
import gameelements.Inventory;
import gameelements.Player;
import gameelements.enums.EffectDelay;
import gameelements.enums.Resource;

public class ChoiceResourceEffect extends Effect {
    Resource[] resources;
    int nb;

    public ChoiceResourceEffect(Resource[] resources, int nb) {
        super(EffectDelay.INSTANTANEOUS);
        this.resources = resources;
        this.nb = nb;
    }

    public void activateEffect(Player player, Inventory inv, Inventory leftNeighborInv, Inventory rightNeighborInv) {
        super.activateEffect(player, inv, leftNeighborInv, rightNeighborInv);
        if (resources.length == 2) {
            inv.addPairResChoice(resources);
        }
    }
}
