package gameelements.effects;

import gameelements.Effect;
import gameelements.Inventory;
import gameelements.enums.EffectDelay;
import gameelements.enums.EffectFrequency;

public class ScoreForMerveilleEffect extends Effect {
    int score;

    public ScoreForMerveilleEffect(int score) {
        super(EffectDelay.END_OF_THE_GAME, EffectFrequency.ONCE);
        this.score = score;
    }

    public void activateEffect(Inventory playersInv) {
        super.activateEffect(playersInv);
        int stepsCount = playersInv.getWonderBoard().getCurrentStepIndex();;
        playersInv.addScore(score * stepsCount);
    }
}
