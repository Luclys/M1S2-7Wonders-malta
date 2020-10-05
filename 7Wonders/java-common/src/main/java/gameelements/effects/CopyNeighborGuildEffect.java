package gameelements.effects;

import gameelements.Effect;
import gameelements.Inventory;

public class CopyNeighborGuildEffect extends Effect {

    public CopyNeighborGuildEffect(String name) {
        super(name, 1, 0);
    }

    public void activateEffect(Inventory inv) {
        super.activateEffect(inv);
        // TODO : At the end game, ask Player to choose a guild card from one of his neighbor (if there is one).
    }
}
