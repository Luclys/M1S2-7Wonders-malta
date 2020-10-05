package gameelements.effects;

import gameelements.enums.Category;
import gameelements.Effect;
import gameelements.Inventory;

public class ScoreForNeighborsCardsEffect extends Effect {
    int nb;
    Category category;

    public ScoreForNeighborsCardsEffect(int nb, Category category) {
        super();
        this.nb = nb;
        this.category = category;
    }

    public void activateEffect(Inventory playersInv, Inventory leftNeighborInv, Inventory rightNeighborInv) {
        super.activateEffect(playersInv);
        int leftNeighborCardsCount = (int) leftNeighborInv.getPlayedCards().stream().filter(card -> card.getCategory().equals(category)).count();
        int rightNeighborCardsCount = (int) rightNeighborInv.getPlayedCards().stream().filter(card -> card.getCategory().equals(category)).count();
        playersInv.addScore((leftNeighborCardsCount + rightNeighborCardsCount) * nb);
    }
}