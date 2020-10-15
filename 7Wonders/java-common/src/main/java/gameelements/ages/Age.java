package gameelements.ages;

import gameelements.cards.Card;

import java.util.ArrayList;

public interface Age {
    public abstract int getVictoryJetonValue();
    public abstract boolean isLeftRotation();
    public abstract ArrayList<Card> initiateCards(int playersCount);
}
