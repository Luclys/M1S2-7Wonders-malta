package gameelements.effects;

import gameelements.Effect;
import gameelements.Inventory;
import gameelements.Player;
import gameelements.cards.Card;
import gameelements.enums.Category;
import gameelements.enums.EffectDelay;
import gameelements.enums.EffectFrequency;

import java.util.ArrayList;

public class CopyNeighborGuildEffect extends Effect {

    public CopyNeighborGuildEffect() {
        super(EffectDelay.END_OF_THE_GAME, EffectFrequency.ONCE);
    }

    public void activateEffect(Inventory playerInv, Inventory leftNeighborInv, Inventory rightNeighborInv, Player player) {
        super.activateEffect(player, playerInv, leftNeighborInv, rightNeighborInv);

        ArrayList<Card> list = new ArrayList<>();
        list.addAll(rightNeighborInv.getPlayedCards());
        list.addAll(leftNeighborInv.getPlayedCards());
        list.removeIf(card -> card.getCategory() != Category.GUILDE);

        if (list.size() != 0) {
            Card card = player.chooseGuildCard(list, new Inventory(playerInv));
            playerInv.updateInventory(card, player, leftNeighborInv, rightNeighborInv);
        }

    }
}
