package gameelements.effects;

import gameelements.Effect;
import gameelements.Inventory;

public class ChoiceScientificEffect extends Effect {

    public ChoiceScientificEffect() {
        super(1, 0);
    }

    public void activateEffect(Inventory inv) {
        super.activateEffect(inv);
        // TODO : At the end game, ask Player which scientific he chooses.
        changeStatus();
    }
}
