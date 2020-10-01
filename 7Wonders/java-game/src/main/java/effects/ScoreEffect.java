package effects;

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
        // Request the add of nb score points to the player
        //inv.addscore(nb);
        changeStatus();
    }
}
