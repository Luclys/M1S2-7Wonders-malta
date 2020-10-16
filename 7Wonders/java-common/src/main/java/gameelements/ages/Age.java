package gameelements.ages;

import gameelements.cards.Card;

import java.util.ArrayList;

public abstract class Age {
    protected int vicoryJetonValue;
    protected boolean isLeftRotation;
    public abstract ArrayList<Card> initiateCards(int playersCount);
    public  int getVictoryJetonValue() {
        return vicoryJetonValue;
    }
    public  boolean isLeftRotation() {
        return isLeftRotation;
    }
}
