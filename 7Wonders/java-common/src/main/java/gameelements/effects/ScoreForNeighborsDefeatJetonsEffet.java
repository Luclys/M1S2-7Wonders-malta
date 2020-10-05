package gameelements.effects;

import gameelements.Effect;
import gameelements.Inventory;

public class ScoreForNeighborsDefeatJetonsEffet extends Effect {
    int score;

    public ScoreForNeighborsDefeatJetonsEffet(int score) {
        super();
        this.score = score;
    }

    public void activateEffect(Inventory playersInv, Inventory leftNeighborInv, Inventory rightNeighborInv) {
        super.activateEffect(playersInv);
        playersInv.addScore((leftNeighborInv.getDefeatJetonsCount() + rightNeighborInv.getDefeatJetonsCount()) * score);
        changeStatus();
    }
}
