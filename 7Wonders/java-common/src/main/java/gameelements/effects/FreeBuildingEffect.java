package gameelements.effects;

import gameelements.Effect;
import gameelements.Inventory;
import gameelements.Player;
import gameelements.enums.EffectDelay;
import gameelements.enums.EffectFrequency;

public class FreeBuildingEffect extends Effect {

    public FreeBuildingEffect() {
        super(EffectDelay.WHENEVER_PLAYER_WANTS, EffectFrequency.ONCE);
    }

    public void activateEffect(Player player, Inventory inv, Inventory leftNeighborInv, Inventory rightNeighborInv) {
        super.activateEffect(player, inv, leftNeighborInv, rightNeighborInv);
        inv.addPossibleFreeBuildingsCount(1);
    }
}
