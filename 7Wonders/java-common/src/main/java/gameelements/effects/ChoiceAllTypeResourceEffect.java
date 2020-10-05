package gameelements.effects;

import gameelements.Effect;
import gameelements.Inventory;
import gameelements.enums.EffectDelay;
import gameelements.enums.EffectFrequency;

public class ChoiceAllTypeResourceEffect extends Effect {
    Boolean PrimaryResource;

    public ChoiceAllTypeResourceEffect(Boolean PrimaryResource) {
        super(EffectDelay.INSTANTANEOUS, EffectFrequency.EVERY_TURN);
        this.PrimaryResource = PrimaryResource;
    }

    public void activateEffect(Inventory inv) {
        super.activateEffect(inv);
        if (PrimaryResource) {
            // Choice among all Primary Resource : BOIS/PIERRE/MINERAI/ARGILE

        } else {
            // Choice among all Manufactured Resource : VERRE/PAPYRUS/TISSU

        }

        // TODO : ask Player which resource he chooses
    }
}
