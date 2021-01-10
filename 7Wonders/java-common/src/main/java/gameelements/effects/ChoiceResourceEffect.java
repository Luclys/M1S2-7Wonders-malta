package gameelements.effects;

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

    @Override
    public void activateEffect(Player player, Inventory inv, Inventory leftNeighborInv, Inventory rightNeighborInv, boolean isEndGame) {
        if ((!isEndGame) && (getDelay() == EffectDelay.END_OF_THE_GAME)) {
            inv.addEndGameEffect(this);
            return;
        }
        if (resources.length == 2) {
            inv.addPairResChoice(resources);
        }
    }

    public Resource[] getResources() {
        return resources;
    }
}
