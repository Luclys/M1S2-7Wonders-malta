package gameelements.effects;

import gameelements.Effect;
import gameelements.Inventory;
import gameelements.enums.EffectDelay;
import gameelements.enums.EffectFrequency;

public class ScoreEffect extends Effect {
    int nb;

    public ScoreEffect(int nb) {
        super(EffectDelay.INSTANTANEOUS, EffectFrequency.ONCE);
        this.nb = nb;
    }

    public void activateEffect(Inventory inv) {
        super.activateEffect(inv);
        inv.addScore(nb);
    }
}
