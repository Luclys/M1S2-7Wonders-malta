package gameelements.strategy;

import gameelements.Card;
import gameelements.Inventory;
import gameelements.enums.Action;

import java.util.ArrayList;
import java.util.Random;

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
