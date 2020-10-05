package gameelements.effects;

import gameelements.Effect;
import gameelements.Inventory;
import gameelements.enums.EffectDelay;
import gameelements.enums.EffectFrequency;

public class ScoreForNeighborsDefeatJetonsEffet extends Effect {
    int score;

    public ScoreForNeighborsDefeatJetonsEffet(int score) {
        super(EffectDelay.END_OF_THE_GAME, EffectFrequency.ONCE);
        this.score = score;
    }

    public void activateEffect(Inventory playersInv, Inventory leftNeighborInv, Inventory rightNeighborInv) {
        super.activateEffect(playersInv);
        playersInv.addScore((leftNeighborInv.getDefeatJetonsCount() + rightNeighborInv.getDefeatJetonsCount()) * score);
    }
}
