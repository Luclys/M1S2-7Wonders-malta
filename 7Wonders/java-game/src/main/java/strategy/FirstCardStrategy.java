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
        b.log.display("[Available Cards to play] id "+ inv.getPlayerId() +" cards " + available);
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

    public Card getCard(){
        return chosen;
    }
}
