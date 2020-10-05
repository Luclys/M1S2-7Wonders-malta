package gameelements.effects;

import gameelements.Effect;
import gameelements.Inventory;

public class CoinEffect extends Effect {
    int nb;

    public CoinEffect(int nb) {
        this.nb = nb;
    }

    public void activateEffect(Inventory inv) {
        super.activateEffect(inv);
        inv.addCoins(nb);
    }
}
