package strategy;

import gameelements.Inventory;
import gameelements.cards.Card;
import gameelements.enums.Action;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

public class RandomStrategy implements PlayingStrategy {
    Action chosenAction;
    Card chosenCard;

    @Override
    public Card chooseCard(Inventory inventory) throws Exception {
        Random r1 = new SecureRandom();
        ArrayList<Card> listCard = cardsAvailableToPlay(inventory);
        if(!listCard.isEmpty()){
            int randomCard = r1.nextInt(listCard.size());
            chosenCard = listCard.get(randomCard);
            ArrayList<Action> listActions = availableActions(chosenCard, inventory);
            int randomAction = r1.nextInt(listActions.size());
            chosenAction = listActions.get(randomAction);
        }else{
            int randomCard = r1.nextInt(inventory.getCardsInHand().size());
            chosenCard = inventory.getCardsInHand().get(randomCard);
            chosenAction = Action.SELL;
        }
        return chosenCard;
    }

    @Override
    public Action getAction() {
        return chosenAction;
    }

    @Override
    public Card getCard() {
        return chosenCard;
    }

    @Override
    public void setCard(Card card) {
        this.chosenCard = card;
    }

    @Override
    public void setAction(Action action) {
        this.chosenAction = action;
    }

    @Override
    public PlayingStrategy copy() {
        RandomStrategy s = new RandomStrategy();
        s.chosenAction = this.chosenAction;
        s.chosenCard = this.chosenCard;
        return s;
    }

    @Override
    public void updateKnowledge(ArrayList<Inventory> censoredInvList, int age, int currentTurn, int rightNeighborId, int leftNeighborId) {

    }
}
