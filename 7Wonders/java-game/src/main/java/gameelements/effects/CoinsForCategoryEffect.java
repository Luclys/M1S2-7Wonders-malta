package gameelements.effects;

import gameelements.Inventory;
import gameelements.enums.Category;
import gameelements.enums.EffectDelay;

public class CoinsForCategoryEffect extends Effect {
    int nbCoins;
    Category category;

    public CoinsForCategoryEffect(int nbCoins, Category category) {
        super(EffectDelay.INSTANTANEOUS);
        this.nbCoins = nbCoins;
        this.category = category;
    }

    @Override
    public void activateEffect(Inventory inv, Inventory leftNeighborInv, Inventory rightNeighborInv, boolean isEndGame) {
        if ((!isEndGame) && (getDelay() == EffectDelay.END_OF_THE_GAME)) {
            inv.addEndGameEffect(this);
            return;
        }
        int cardsCount = (int) inv.getPlayedCards().stream().filter(card -> card.getCategory().equals(category)).count();
        inv.addCoins(cardsCount * nbCoins);
    }

    public int getNbCoins() {
        return nbCoins;
    }

    public Category getCategory() {
        return category;
    }
}
