package gameelements.effects;

import gameelements.Effect;
import gameelements.Inventory;
import gameelements.Player;
import gameelements.enums.EffectDelay;

public class ScoreForNeighborsDefeatJetonsEffet extends Effect {
    int score;

    public ScoreForNeighborsDefeatJetonsEffet(int score) {
        super(EffectDelay.END_OF_THE_GAME);
        this.score = score;
    }

    @Override
    public void activateEffect(Player player, Inventory inv, Inventory leftNeighborInv, Inventory rightNeighborInv, boolean isEndGame) {
        if ((!isEndGame) && (getDelay() == EffectDelay.END_OF_THE_GAME)) {
            inv.addEndGameEffect(this);
            return;
        }
        inv.addScore((leftNeighborInv.getDefeatChipsCount() + rightNeighborInv.getDefeatChipsCount()) * score);
    }
}
