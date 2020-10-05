package gameelements.effects;

import gameelements.Effect;
import gameelements.Inventory;
import gameelements.enums.EffectDelay;
import gameelements.enums.EffectFrequency;

public class ScoreAndCoinsForMerveilleEffect extends Effect {
    int nbCoins;
    int score;

    public ScoreAndCoinsForMerveilleEffect(int nbCoins, int score) {
        super(EffectDelay.END_OF_THE_GAME, EffectFrequency.ONCE);
        this.nbCoins = nbCoins;
        this.score = score;
    }

    public void activateEffect(Inventory playersInv) {
        super.activateEffect(playersInv);
        int stepsCount = playersInv.getWonderBoard().getCurrentStepIndex();
        playersInv.addCoins(nbCoins * stepsCount);
        playersInv.addScore(score * stepsCount);
    }
}
