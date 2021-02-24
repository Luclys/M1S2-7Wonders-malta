package gameelements;

import gameelements.cards.Card;
import gameelements.enums.Action;

public class CardActionPair {
    Card card;
    Action action;

    public CardActionPair(Card card, Action action) {
        this.card = card;
        this.action = action;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
}
