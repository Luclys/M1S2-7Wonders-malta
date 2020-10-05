package gameelements.effects;

import gameelements.Effect;
import gameelements.Inventory;

public class ScoreEffect extends Effect {
    int nb;

    public ScoreEffect(String name, int nb) {
        super(name);
        this.nb = nb;
    }

    public void activateEffect(Inventory inv) {
        super.activateEffect(inv);
        inv.setScore(inv.getScore() + nb);
    }
}
