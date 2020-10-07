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

    public void activateEffect(Player player, Inventory playersInv, Inventory leftNeighborInv, Inventory rightNeighborInv) {
        super.activateEffect(player, playersInv, leftNeighborInv, rightNeighborInv);
        int cardsCount = (int) playersInv.getPlayedCards().stream().filter(card -> categories.contains(card.getCategory())).count();
        playersInv.addScore(cardsCount * score);
    }
}
