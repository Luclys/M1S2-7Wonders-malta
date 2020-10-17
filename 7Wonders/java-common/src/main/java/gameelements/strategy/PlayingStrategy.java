package gameelements.strategy;

import gameelements.Inventory;
import gameelements.cards.Card;
import gameelements.enums.Action;

import java.util.ArrayList;

public interface PlayingStrategy {
    Card chooseCard(Inventory inventory);

    Action getAction();

    default ArrayList<Card> cardsAvailableToPlay(Inventory inv) {
        //We remove from playable cards the cards the player already played, you can't play the same card twice
        ArrayList<Card> cardsAvailableToPlay = new ArrayList<>(inv.getCardsInHand());
        cardsAvailableToPlay.removeIf(card -> inv.getPlayedCards().contains(card) && card.isBuilding());
        return cardsAvailableToPlay;
    }
}
