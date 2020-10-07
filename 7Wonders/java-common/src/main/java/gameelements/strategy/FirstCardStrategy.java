package gameelements.strategy;

import gameelements.Card;
import gameelements.Inventory;

import java.util.ArrayList;
import java.util.Random;

public class FirstCardStrategy implements PlayingStrategy {
    @Override
    public Card chooseCard(Inventory inventory, ArrayList<Card> availableCards) {
        return availableCards.get(0);
    }
}
