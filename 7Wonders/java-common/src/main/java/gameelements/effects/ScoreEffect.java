package gameelements.effects;

import gameelements.Effect;
import gameelements.Inventory;

public class ScoreEffect extends Effect {
    int nb;

    public ScoreEffect(int nb) {
        this.nb = nb;
    }

    public void activateEffect(Inventory inv) {
        super.activateEffect(inv);
        inv.addScore(nb);
        changeStatus();
    }
}
