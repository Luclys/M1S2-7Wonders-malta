package gameelements.effects;

import gameelements.Effect;
import gameelements.Inventory;

public class ScoreForOwnAndNeighborMerveillesEffet extends Effect {
    int score;

  public ScoreForOwnAndNeighborMerveillesEffet(int score) {
      super();
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
