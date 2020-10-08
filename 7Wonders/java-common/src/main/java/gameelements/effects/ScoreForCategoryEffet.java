package gameelements.effects;

import gameelements.Effect;
import gameelements.Inventory;
import gameelements.Player;
import gameelements.enums.Category;
import gameelements.enums.EffectDelay;

public class ScoreForCategoryEffet extends Effect {
    int score;
    Category category;

    public ScoreForCategoryEffet(int score, Category category) {
        super(EffectDelay.END_OF_THE_GAME);
        this.score = score;
        this.category = category;
    }

    public void activateEffect(Player player, Inventory playersInv, Inventory leftNeighborInv, Inventory rightNeighborInv) {
        super.activateEffect(player, playersInv, leftNeighborInv, rightNeighborInv);
        int cardsCount = (int) playersInv.getPlayedCards().stream().filter(card -> card.getCategory().equals(category)).count();
        playersInv.addScore(cardsCount * score);
    }
}
