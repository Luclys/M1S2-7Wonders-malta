package gameelements.effects;

import gameelements.Effect;
import gameelements.Inventory;
import gameelements.Player;
import gameelements.enums.Category;
import gameelements.enums.EffectDelay;

public class CoinsForCategoryEffet extends Effect {
    int nbCoins;
    Category category;

    public CoinsForCategoryEffet(int nbCoins, Category category) {
        super(EffectDelay.INSTANTANEOUS);
        this.nbCoins = nbCoins;
        this.category = category;
    }

    @Override
    public void activateEffect(Player player, Inventory inv, Inventory leftNeighborInv, Inventory rightNeighborInv, boolean isEndGame) {
        if ((!isEndGame) && (delay == EffectDelay.END_OF_THE_GAME)) {
            inv.addEndGameEffect(this);
            return;
        }
        int cardsCount = (int) inv.getPlayedCards().stream().filter(card -> card.getCategory().equals(category)).count();
        inv.addCoins(cardsCount * nbCoins);
    }
}
