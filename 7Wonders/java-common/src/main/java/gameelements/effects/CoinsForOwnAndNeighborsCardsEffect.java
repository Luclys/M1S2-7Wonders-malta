package gameelements.effects;

import gameelements.Effect;
import gameelements.Inventory;
import gameelements.Player;
import gameelements.enums.Category;
import gameelements.enums.EffectDelay;

public class CoinsForOwnAndNeighborsCardsEffect extends Effect {
    int nb;
    Category category;

    public CoinsForOwnAndNeighborsCardsEffect(int nb, Category category) {
        super(EffectDelay.END_OF_THE_GAME);
        this.nb = nb;
        this.category = category;
    }

    public void activateEffect(Player player, Inventory playersInv, Inventory leftNeighborInv, Inventory rightNeighborInv) {
        super.activateEffect(player, playersInv, leftNeighborInv, rightNeighborInv);
        int ownCardsCount = (int) playersInv.getPlayedCards().stream().filter(card -> card.getCategory().equals(category)).count();
        int leftNeighborCardsCount = (int) leftNeighborInv.getPlayedCards().stream().filter(card -> card.getCategory().equals(category)).count();
        int rightNeighborCardsCount = (int) rightNeighborInv.getPlayedCards().stream().filter(card -> card.getCategory().equals(category)).count();
        playersInv.addCoins((ownCardsCount + leftNeighborCardsCount + rightNeighborCardsCount) * nb);
    }
}
