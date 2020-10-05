package gameelements.effects;

import gameelements.Effect;
import gameelements.Inventory;
import gameelements.enums.EffectDelay;
import gameelements.enums.EffectFrequency;

public class PlayLastCardEffect extends Effect {

    public PlayLastCardEffect() {
        super(EffectDelay.INSTANTANEOUS, EffectFrequency.EVERY_AGE);
    }

    public void activateEffect(Inventory inv) {
        super.activateEffect(inv);
        // TODO : Allow Player to play the last card instead of discarding it.
    }
}
