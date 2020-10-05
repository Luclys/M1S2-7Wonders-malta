package gameelements.effects;

import gameelements.Effect;
import gameelements.Inventory;

public class ScoreAndCoinsForMerveilleEffect extends Effect {
    int nbCoins;
    int score;

    public ScoreAndCoinsForMerveilleEffect(int nbCoins, int score) {
        super();
        this.nbCoins = nbCoins;
        this.score = score;
    }

    public void activateEffect(Inventory playersInv) {
        super.activateEffect(playersInv);
        int stepsCount = playersInv.getWonderBoard().getCurrentStep();
        playersInv.addCoins(nbCoins * stepsCount);
        playersInv.addScore(score * stepsCount);
    }
}
