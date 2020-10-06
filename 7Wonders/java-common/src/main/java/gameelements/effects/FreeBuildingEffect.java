package gameelements.effects;

import gameelements.Effect;
import gameelements.Inventory;
import gameelements.enums.EffectDelay;
import gameelements.enums.EffectFrequency;

public class FreeBuildingEffect extends Effect {

    public FreeBuildingEffect() {
        super(EffectDelay.WHENEVER_PLAYER_WANTS, EffectFrequency.ONCE);
    }

    public void activateEffect(Inventory inv) {
        super.activateEffect(inv);
        inv.addPossibleFreeBuildingsCount(1);
    }
}
