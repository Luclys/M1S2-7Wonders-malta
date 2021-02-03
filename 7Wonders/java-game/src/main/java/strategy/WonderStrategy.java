package strategy;

import gameelements.Inventory;
import gameelements.cards.Card;
import gameelements.enums.Action;

import java.util.ArrayList;

/**
 * This class describe the wonder Strategy
 *
 * @author lamac
 */

public class WonderStrategy implements PlayingStrategy {
    Action action;
    Card chosen;

    @Override
    public PlayingStrategy copy() {
        WonderStrategy s = new WonderStrategy();
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

    @Override
    public Card chooseCard(Inventory inv) {
        return chooseCardToBuildStep(inv);
    }

    @Override
    public Action getAction() {
        return this.action;
    }

    @Override
    public void updateKnowledge(ArrayList<Inventory> censoredInvList, int age, int currentTurn, int rightNeighborId, int leftNeighborId) {
    }


    /**
     * this method choose a card according to this actions
     * to build a step o the wonder associate to the inv if it s possible
     * or build sell a card if the player can't play any of the cards in his hand
     * or build a free building if he can (he has the effect)
     * or build a card if he has the required resource or can buy then
     *
     * @param inv
     * @return Card the chosen card according to the action that the player can do
     */

    public Card chooseCardToBuildStep(Inventory inv) {
        boolean canBuildStep = inv.canBuildNextStep(inv.getWonderBoard());

        Card chosenCard = inv.getCardsInHand().get(0);

        if (canBuildStep) {
            setAction(Action.WONDER);
            //Player picks a card he cannot build
            for (Card card : inv.getCardsInHand()) {
                //We pick the first non-buildable card
                if (!inv.canBuild(card.getRequiredResources())) {
                    chosenCard = card;
                    break;
                }
            }
        } else {
            ArrayList<Card> availableCards = cardsAvailableToPlay(inv);

            //Player picks a card he can build
            if (availableCards.isEmpty()) {
                this.action = Action.SELL;
                chosen = inv.getCardsInHand().get(0);
                return chosen;
            }
            if (inv.getPossibleFreeBuildings() > 0) {
                setAction(Action.BUILDFREE);
                for (Card card : inv.getCardsInHand()) {
                    //We pick the first non-buildable card
                    if (!inv.canBuild(card.getRequiredResources())) {
                        break;
                    } else {
                        chosen = card;
                        return chosen;
                    }
                }
            }

            setAction(Action.BUILDING);
            for (Card card : availableCards) {
                //Player picks the first buildable card
                if (inv.canBuild(card.getRequiredResources())) {
                    chosen = card;
                    return chosen;
                }
            }
        }
        chosen = chosenCard;
        return chosenCard;
    }

    public Card getCard(){
        return chosen;
    }
}
