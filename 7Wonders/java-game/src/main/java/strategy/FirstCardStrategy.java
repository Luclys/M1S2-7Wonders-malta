package strategy;

import gameelements.Inventory;
import gameelements.cards.Card;
import gameelements.enums.Action;

import java.util.ArrayList;

/**
 * This class describe the first card strategy
 *
 * @author lamac
 */
public class FirstCardStrategy implements PlayingStrategy {
    Action action;

    /**
     * This method allows to choose a card
     * to build it if it's possible
     * or to sell it
     *
     * @param inv
     * @return Card chosen card according to which action the player can use
     */
    @Override
    public Card chooseCard(Inventory inv) {
        this.action = Action.BUILDING;

        ArrayList<Card> available = cardsAvailableToPlay(inv);
        if (available.isEmpty()) {
            this.action = Action.SELL;
            return inv.getCardsInHand().get(0);
        }
        return available.get(0);
    }

    @Override
    public Action getAction() {
        return this.action;
    }
}
