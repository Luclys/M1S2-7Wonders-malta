package gameelements.effects;

import gameelements.Effect;
import gameelements.Inventory;
import gameelements.enums.Resource;

public class ChoiceResourceEffect extends Effect {
    Resource[] resources;
    int nb;

    public ChoiceResourceEffect(String name, Resource[] resources, int nb) {
        super(name, 0, 1);
        this.resources = resources;
        this.nb = nb;
    }

    public void activateEffect(Inventory inv) {
        super.activateEffect(inv);
        // TODO : ask Player which resource he chooses
        changeStatus();
    }
}
