package gameelements.strategy;

import gameelements.Card;
import gameelements.Inventory;

import java.util.ArrayList;

public interface PlayingStrategy {
    Card chooseCard(Inventory inventory, ArrayList<Card> availableCards);
}
