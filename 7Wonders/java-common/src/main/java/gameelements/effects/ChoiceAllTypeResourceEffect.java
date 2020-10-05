package gameelements.effects;

import gameelements.Effect;
import gameelements.Inventory;

public class ChoiceAllTypeResourceEffect extends Effect {
    Boolean PrimaryResource;

    public ChoiceAllTypeResourceEffect(Boolean PrimaryResource) {
        super(0, 1);
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
        changeStatus();
    }
}
