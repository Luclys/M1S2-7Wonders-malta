package gameelements.effects;

import gameelements.Effect;
import gameelements.Inventory;
import gameelements.Player;
import gameelements.enums.Category;
import gameelements.enums.EffectDelay;

public class ScoreForNeighborsCardsEffect extends Effect {
    int nb;
    Category category;

    public ScoreForNeighborsCardsEffect(int nb, Category category) {
        super(EffectDelay.END_OF_THE_GAME);
        this.nb = nb;
        this.category = category;
    }

    @Override
    public void activateEffect(Player player, Inventory inv, Inventory leftNeighborInv, Inventory rightNeighborInv, boolean isEndGame) {
        if ((!isEndGame) && (getDelay() == EffectDelay.END_OF_THE_GAME)) {
            inv.addEndGameEffect(this);
            return;
        }
        int leftNeighborCardsCount = (int) leftNeighborInv.getPlayedCards().stream().filter(card -> card.getCategory().equals(category)).count();
        int rightNeighborCardsCount = (int) rightNeighborInv.getPlayedCards().stream().filter(card -> card.getCategory().equals(category)).count();
        inv.addScore((leftNeighborCardsCount + rightNeighborCardsCount) * nb);
    }
}