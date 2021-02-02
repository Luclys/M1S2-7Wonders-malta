package gameelements.effects;

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

    @Override
    public void activateEffect(Player player, Inventory inv, Inventory leftNeighborInv, Inventory rightNeighborInv, boolean isEndGame) {
        if ((!isEndGame) && (getDelay() == EffectDelay.END_OF_THE_GAME)) {
            inv.addEndGameEffect(this);
            return;
        }

        if (primaryRessources.equals(Boolean.TRUE)) {
            switch (whichNeighbor) {
                case LEFT:
                    inv.setMatieresPremieresPriceLeft(1);
                    break;
                case RIGHT:
                    inv.setMatieresPremieresPriceRight(1);
                    break;
                default:
                    inv.setMatieresPremieresPriceRight(1);
                    inv.setMatieresPremieresPriceLeft(1);
                    break;
            }
        } else {
            inv.setProduitsManifacturesPrice(1);
        }
    }

    public Neighbor getWhichNeighbor() {
        return whichNeighbor;
    }
}
