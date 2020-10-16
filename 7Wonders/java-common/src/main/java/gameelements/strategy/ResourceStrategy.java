package gameelements.strategy;

import gameelements.Inventory;
import gameelements.cards.Card;
import gameelements.enums.Action;
import java.util.ArrayList;

public class ResourceStrategy implements PlayingStrategy {
    Action action;

    @Override
    public Card chooseCard(Inventory inv, ArrayList<Card> availableCards) {
        return chooseCardToBuildStep(inv, availableCards);
    }

    @Override
    public Action getAction() {
        return this.action;
    }

    private void setAction(Action action) {
        this.action = action;
    }

    private Card chooseCardToBuildStep(Inventory inv, ArrayList<Card> availableCards) {
        boolean canBuildStep = inv.canBuild(inv.getWonderBoard().getCurrentStep().getRequiredResources());
        Card chosenCard = availableCards.get(0);

        if (canBuildStep) {
            setAction(Action.WONDER);
            //Player picks a card he cannot build
            for (Card card : availableCards) {
                //We pick the first non-buildable card
                if (!inv.canBuild(chosenCard.getRequiredResources())) {
                    break;
                }
                else {
                    chosenCard = card;
                }
            }
        }
        else {
            setAction(Action.BUILDING);
            //Player picks a card he can build
            for (Card card : availableCards) {
                //Player picks the first buildable card
                if (inv.canBuild(chosenCard.getRequiredResources())) {
                   break;
                }
                else {
                    chosenCard = card;
                }
            }
        }

        return chosenCard;
    }
}
