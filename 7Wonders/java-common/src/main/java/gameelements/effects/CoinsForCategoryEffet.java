package gameelements.effects;

import gameelements.Effect;
import gameelements.Inventory;
import gameelements.Player;
import gameelements.enums.Category;
import gameelements.enums.EffectDelay;
import gameelements.enums.EffectFrequency;

public class CoinsForCategoryEffet extends Effect {
    int nbCoins;
    Category category;

    public CoinsForCategoryEffet(int nbCoins, Category category) {
        super(EffectDelay.INSTANTANEOUS, EffectFrequency.ONCE);
        this.nbCoins = nbCoins;
        this.category = category;
    }

    public void activateEffect(Player player, Inventory playersInv, Inventory leftNeighborInv, Inventory rightNeighborInv) {
        super.activateEffect(player, playersInv, leftNeighborInv, rightNeighborInv);
        int cardsCount = (int) playersInv.getPlayedCards().stream().filter(card -> card.getCategory().equals(category)).count();
        playersInv.addCoins(cardsCount * nbCoins);
    }
}
