package effects;

import gameelements.Effect;
import gameelements.enums.Resource;

public class ChoiceResourceEffect extends Effect {
    Resource[] resources;
    int nb;

    public ChoiceResourceEffect(String name, Resource[] resources, int nb) {
        super(name, 0, 1);
        this.resources = resources;
        this.nb = nb;
    }

    public void activateEffect() {
        super.activateEffect();
        // ask Player which resource he chooses
        changeStatus();
    }
}
