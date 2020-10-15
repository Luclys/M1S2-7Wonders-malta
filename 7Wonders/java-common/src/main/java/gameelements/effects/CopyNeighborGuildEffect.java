package gameelements.effects;

import gameelements.Effect;
import gameelements.Inventory;
import gameelements.Player;
import gameelements.cards.Card;
import gameelements.enums.Category;
import gameelements.enums.EffectDelay;

import java.util.ArrayList;

public class CopyNeighborGuildEffect extends Effect {

    public CopyNeighborGuildEffect() {
        super(EffectDelay.END_OF_THE_GAME);
    }

    @Override
    public void activateEffect(Player player, Inventory inv, Inventory leftNeighborInv, Inventory rightNeighborInv, boolean isEndGame) {
        if ((!isEndGame) && (delay == EffectDelay.END_OF_THE_GAME)) {
            inv.addEndGameEffect(this);
            return;
        }
        ArrayList<Card> list = new ArrayList<>();
        list.addAll(rightNeighborInv.getPlayedCards());
        list.addAll(leftNeighborInv.getPlayedCards());
        list.removeIf(card -> card.getCategory() != Category.GUILDE);

        if (list.isEmpty()) {
            Card card = player.chooseGuildCard(list);
            inv.updateInventory(card, player, leftNeighborInv, rightNeighborInv);
        }

    }
}
