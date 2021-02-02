package strategy;

import board.Board;
import gameelements.Inventory;
import gameelements.cards.Card;
import gameelements.enums.Action;

import java.util.ArrayList;

/**
 * This class describe the first card strategy
 *
 * @author lamac
 */
public class FirstCardStrategy implements PlayingStrategy {
    Action action;
    Card chosen;

    @Override
    public PlayingStrategy copy() {
        FirstCardStrategy s = new FirstCardStrategy();
        s.chosen = this.chosen;
        s.action = this.action;
        return s;
    }

    @Override
    public void setCard(Card card) {
        this.chosen = card;
    }

    @Override
    public void setAction(Action action) {
        this.action = action;
    }

    /**
     * This method allows to choose a card
     * to build it if it's possible
     * or to sell it
     *
     * @param inv
     * @return Card chosen card according to which action the player can use
     */
    @Override
    public Card chooseCard(Inventory inv, Board b) {
        this.action = Action.BUILDING;

        ArrayList<Card> available = cardsAvailableToPlay(inv);
        if (available.isEmpty()) {
            this.action = Action.SELL;
            chosen = inv.getCardsInHand().get(0);

        }else{
            chosen = available.get(0);
        }

        return chosen;
    }

    @Override
    public Action getAction() {
        return this.action;
    }

    @Override
    public void updateKnowledge(ArrayList<Inventory> censoredInvList, int age, int currentTurn, int rightNeighborId, int leftNeighborId) {
    }

    public Card getCard(){
        return chosen;
    }


}
