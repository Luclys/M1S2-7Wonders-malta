package gameelements.strategy;

import gameelements.Card;
import gameelements.Inventory;

import java.util.ArrayList;

public class ResourceStrategy implements PlayingStrategy {

    @Override
    public Card chooseCard(Inventory inv, ArrayList<Card> availableCards) {
        return chooseCardToBuildStep(inv, availableCards);
    }

    private Card chooseCardToBuildStep(Inventory inv, ArrayList<Card> availableCards) {
        boolean canBuildWonderStep = inv.canBuild(inv.getWonderBoard().getCurrentStep().getRequiredResources());
        Card chosenCard = availableCards.get(0);

        if (canBuildWonderStep) {
            //Player chooses a card he cannot build
            for (Card card : availableCards) {
                //We take the first non-buildable card encountered
                if (!inv.canBuild(chosenCard.getRequiredResources())) {
                    break;
                }
                else {
                    chosenCard = card;
                }
            }
        }
        else {
            //Player chooses a card he can build
            for (Card card : availableCards) {
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
