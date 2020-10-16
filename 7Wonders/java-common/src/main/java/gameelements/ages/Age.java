package gameelements.ages;

import gameelements.cards.Card;

import java.util.List;

public abstract class Age {
    protected int vicoryJetonValue;
    protected boolean isLeftRotation;

    public abstract List<Card> initiateCards(int playersCount);

    public int getVictoryJetonValue() {
        return vicoryJetonValue;
    }

    public boolean isLeftRotation() {
        return isLeftRotation;
    }
}
