package gameelements.strategy;

import gameelements.Inventory;
import gameelements.cards.Card;
import gameelements.enums.Action;

import java.util.ArrayList;

public class FirstCardStrategy implements PlayingStrategy {
    Action action;

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
