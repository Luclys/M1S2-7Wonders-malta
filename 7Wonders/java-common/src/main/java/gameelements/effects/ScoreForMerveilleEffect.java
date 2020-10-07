package gameelements.effects;

import gameelements.Effect;
import gameelements.Inventory;
import gameelements.Player;
import gameelements.enums.EffectDelay;

public class ScoreForMerveilleEffect extends Effect {
    int score;

    public ScoreForMerveilleEffect(int score) {
        super(EffectDelay.END_OF_THE_GAME);
        this.score = score;
    }

    public void activateEffect(Player player, Inventory playersInv, Inventory leftNeighborInv, Inventory rightNeighborInv) {
        super.activateEffect(player, playersInv, leftNeighborInv, rightNeighborInv);
        int stepsCount = playersInv.getWonderBoard().getCurrentStepIndex();
        playersInv.addScore(score * stepsCount);
    }
}
