package gameelements.ages;

import gameelements.cards.Card;

import java.util.List;

/**
 * This abstract class describe an Age
 *
 * @author lamac
 */
public abstract class Age {
    protected int vicoryJetonValue;
    protected boolean isLeftRotation;

    /**
     * This method return cards associate to each age
     *
     * @param playersCount
     * @return
     */
    public abstract List<Card> initiateCards(int playersCount);

    public int getVictoryJetonValue() {
        return vicoryJetonValue;
    }

    public boolean isLeftRotation() {
        return isLeftRotation;
    }
}
