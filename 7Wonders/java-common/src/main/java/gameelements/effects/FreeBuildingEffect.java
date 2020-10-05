package gameelements.effects;

import gameelements.Effect;
import gameelements.Inventory;

public class FreeBuildingEffect extends Effect {

    public FreeBuildingEffect() {
        super(2, 0);
    }

    public void activateEffect(Inventory inv) {
        super.activateEffect(inv);
        // TODO : Allow 1 free construction whenever Player wants
    }
}
