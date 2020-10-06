package gameelements.effects;

import gameelements.Effect;
import gameelements.Inventory;
import gameelements.enums.EffectDelay;
import gameelements.enums.EffectFrequency;

public class CoinsForMerveilleEffect extends Effect {
    int nbCoins;

    public CoinsForMerveilleEffect(int nbCoins) {
        super(EffectDelay.INSTANTANEOUS, EffectFrequency.ONCE);
        this.nbCoins = nbCoins;
    }

    public void activateEffect(Inventory playersInv) {
        super.activateEffect(playersInv);
        int stepsCount = playersInv.getWonderBoard().getCurrentStepIndex();
        playersInv.addCoins(nbCoins * stepsCount);
    }
}
