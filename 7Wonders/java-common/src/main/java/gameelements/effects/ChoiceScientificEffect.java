package gameelements.effects;

import gameelements.Effect;
import gameelements.Inventory;
import gameelements.enums.EffectDelay;
import gameelements.enums.EffectFrequency;

public class ChoiceScientificEffect extends Effect {

    public ChoiceScientificEffect() {
        super(EffectDelay.END_OF_THE_GAME, EffectFrequency.ONCE);
    }

    public void activateEffect(Inventory inv) {
        super.activateEffect(inv);
        // TODO : At the end game, ask Player which scientific he chooses.
    }
}
