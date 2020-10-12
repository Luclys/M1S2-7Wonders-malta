package gameelements.effects;

import gameelements.Effect;
import gameelements.Inventory;
import gameelements.Player;
import gameelements.enums.Category;
import gameelements.enums.EffectDelay;

import java.util.ArrayList;

public class ScoreForCategoriesEffet extends Effect {
    int score;
    ArrayList<Category> categories;

    public ScoreForCategoriesEffet(int score) {
        super(EffectDelay.END_OF_THE_GAME);
        this.score = score;
        categories = new ArrayList<>();
        categories.add(Category.MATIERE_PREMIERE);
        categories.add(Category.PRODUIT_MANUFACTURE);
        categories.add(Category.GUILDE);
    }

    @Override
    public void activateEffect(Player player, Inventory inv, Inventory leftNeighborInv, Inventory rightNeighborInv, boolean isEndGame) {
        if ((!isEndGame) && (delay == EffectDelay.END_OF_THE_GAME)) {
            inv.addEndGameEffect(this);
            return;
        }
        int cardsCount = (int) inv.getPlayedCards().stream().filter(card -> categories.contains(card.getCategory())).count();
        inv.addScore(cardsCount * score);
    }
}
