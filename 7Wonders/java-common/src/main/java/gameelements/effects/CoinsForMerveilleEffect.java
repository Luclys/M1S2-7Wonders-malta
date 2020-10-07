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

    public void activateEffect(Player player, Inventory playersInv, Inventory leftNeighborInv, Inventory rightNeighborInv) {
        super.activateEffect(player, playersInv, leftNeighborInv, rightNeighborInv);
        int stepsCount = playersInv.getWonderBoard().getCurrentStepIndex();
        playersInv.addCoins(nbCoins * stepsCount);
    }
}
