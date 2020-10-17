package gameelements.strategy;

import gameelements.Inventory;
import gameelements.cards.Card;
import gameelements.enums.Action;

import java.util.ArrayList;

public class FirstCardStrategy implements PlayingStrategy {
    Action action;

    @Override
    public Card chooseCard(Inventory inventory, ArrayList<Card> availableCards) {
        this.action = Action.BUILDING;
        return availableCards.get(0);
    }

    @Override
    public Action getAction() {
        return this.action;
    }
}
