package gameelements.effects;

import gameelements.Effect;
import gameelements.Inventory;
import gameelements.enums.Category;

import java.util.ArrayList;

public class ScoreForCategoriesEffet extends Effect {
    int score;
    ArrayList<Category> categories;

    public ScoreForCategoriesEffet(int score) {
        super();
        this.score = score;
        categories = new ArrayList<>();
        categories.add(Category.MATIERE_PREMIERE);
        categories.add(Category.PRODUIT_MANUFACTURE);
        categories.add(Category.GUILDE);
    }

    public void activateEffect(Inventory playersInv) {
        super.activateEffect(playersInv);
        int cardsCount = (int) playersInv.getPlayedCards().stream().filter(card -> categories.contains(card.getCategory())).count();
        playersInv.addScore(cardsCount*score);
        changeStatus();
    }
}
