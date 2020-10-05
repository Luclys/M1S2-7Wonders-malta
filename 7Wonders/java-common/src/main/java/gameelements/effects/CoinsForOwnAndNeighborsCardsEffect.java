package gameelements.effects;

import gameelements.Effect;
import gameelements.Inventory;
import gameelements.enums.Category;
import gameelements.enums.EffectDelay;
import gameelements.enums.EffectFrequency;

public class CoinsForOwnAndNeighborsCardsEffect extends Effect {
    int nb;
    Category category;

    public CoinsForOwnAndNeighborsCardsEffect(int nb, Category category) {
        super(EffectDelay.END_OF_THE_GAME, EffectFrequency.ONCE);
        this.nb = nb;
        this.category = category;
    }

    public void activateEffect(Inventory playersInv, Inventory leftNeighborInv, Inventory rightNeighborInv) {
        super.activateEffect(playersInv);
        int ownCardsCount = (int) playersInv.getPlayedCards().stream().filter(card -> card.getCategory().equals(category)).count();
        int leftNeighborCardsCount = (int) leftNeighborInv.getPlayedCards().stream().filter(card -> card.getCategory().equals(category)).count();
        int rightNeighborCardsCount = (int) rightNeighborInv.getPlayedCards().stream().filter(card -> card.getCategory().equals(category)).count();
        playersInv.addCoins((ownCardsCount + leftNeighborCardsCount + rightNeighborCardsCount) * nb);
    }
}
