package gameelements.effects;

import gameelements.Inventory;
import gameelements.Player;
import gameelements.enums.Category;
import gameelements.enums.EffectDelay;

public class ScoreForCategoryEffect extends Effect {
    int score;
    Category category;

    public ScoreForCategoryEffect(int score, Category category) {
        super(EffectDelay.END_OF_THE_GAME);
        this.score = score;
        this.category = category;
    }

    @Override
    public void activateEffect(Player player, Inventory inv, Inventory leftNeighborInv, Inventory rightNeighborInv, boolean isEndGame) {
        if ((!isEndGame) && (getDelay() == EffectDelay.END_OF_THE_GAME)) {
            inv.addEndGameEffect(this);
            return;
        }
        int cardsCount = (int) inv.getPlayedCards().stream().filter(card -> card.getCategory().equals(category)).count();
        inv.addScore(cardsCount * score);
    }

    public int getScore() {
        return score;
    }

    public Category getCategory() {
        return category;
    }
}
