package gameelements.strategy;

import gameelements.Card;
import gameelements.Inventory;
import gameelements.enums.Action;

import java.util.ArrayList;

public interface PlayingStrategy {
    Card chooseCard(Inventory inventory, ArrayList<Card> availableCards);

    Action getAction();
}
