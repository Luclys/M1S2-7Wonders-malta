package gameelements.effects;

import gameelements.Effect;
import gameelements.Inventory;
import gameelements.Player;
import gameelements.enums.EffectDelay;

public class CoinsForMerveilleEffect extends Effect {
    int nbCoins;

    public CoinsForMerveilleEffect(int nbCoins) {
        super(EffectDelay.INSTANTANEOUS);
        this.nbCoins = nbCoins;
    }

    @Override
    public void activateEffect(Player player, Inventory inv, Inventory leftNeighborInv, Inventory rightNeighborInv, boolean isEndGame) {
        if ((!isEndGame) && (delay == EffectDelay.END_OF_THE_GAME)) {
            inv.addEndGameEffect(this);
            return;
        }
        int stepsCount = inv.getWonderBoard().getCurrentStepIndex();
        inv.addCoins(nbCoins * stepsCount);
    }
}
