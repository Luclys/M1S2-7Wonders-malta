package gameelements.effects;

import gameelements.Effect;
import gameelements.Inventory;
import gameelements.Player;
import gameelements.enums.EffectDelay;
import gameelements.enums.Neighbor;

public class ReductCommerceEffect extends Effect {
    Neighbor whichNeighbor;
    Boolean primaryRessources;

    public ReductCommerceEffect(Neighbor whichNeighbor, Boolean primaryRessources) {
        super(EffectDelay.INSTANTANEOUS);
        this.whichNeighbor = whichNeighbor;
        this.primaryRessources = primaryRessources;
    }

    public void activateEffect(Player player, Inventory inv, Inventory leftNeighborInv, Inventory rightNeighborInv) {
        super.activateEffect(player, inv, leftNeighborInv, rightNeighborInv);
        if (primaryRessources) {
            if (whichNeighbor.equals(Neighbor.LEFT)) {
                inv.setMatieresPremieresPriceLeft(1);
            } else if (whichNeighbor.equals(Neighbor.RIGHT)) {
                inv.setMatieresPremieresPriceRight(1);
            }
        } else {
            inv.setProduitsManifacturesPrice(1);
        }
    }
}
