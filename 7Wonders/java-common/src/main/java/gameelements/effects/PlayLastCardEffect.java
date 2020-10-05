package gameelements.effects;

import gameelements.Effect;
import gameelements.Inventory;

public class PlayLastCardEffect extends Effect {

    public PlayLastCardEffect(String name) {
        super(name, 0, 2);
    }

    public void activateEffect(Inventory inv) {
        super.activateEffect(inv);
        // TODO : Allow Player to play the last card instead of discarding it.
        changeStatus();
    }
}
