package gameelements.effects;

import gameelements.Effect;
import gameelements.Inventory;
import gameelements.enums.EffectDelay;
import gameelements.enums.EffectFrequency;

public class ScoreForOwnAndNeighborMerveillesEffet extends Effect {
    int score;

  public ScoreForOwnAndNeighborMerveillesEffet(int score) {
      super(EffectDelay.END_OF_THE_GAME, EffectFrequency.ONCE);
      this.score = score;
  }

  public void activateEffect(Inventory playersInv, Inventory leftNeighborInv, Inventory rightNeighborInv) {
      super.activateEffect(playersInv);
      int stepsCount = playersInv.getWonderBoard().getCurrentStep();
      int leftNeighborStepsCount = leftNeighborInv.getWonderBoard().getCurrentStep();
      int rightNeighborStepsCount = rightNeighborInv.getWonderBoard().getCurrentStep();
      playersInv.addScore((stepsCount + leftNeighborStepsCount + rightNeighborStepsCount) * score);
  }
}
