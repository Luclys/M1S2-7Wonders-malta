package gameelements.strategy;

import gameelements.Inventory;
import gameelements.cards.Card;
import gameelements.enums.Action;

import java.util.ArrayList;

public interface PlayingStrategy {
    Card chooseCard(Inventory inventory, ArrayList<Card> availableCards);

    Action getAction();
}
