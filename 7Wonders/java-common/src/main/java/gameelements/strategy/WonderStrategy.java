package gameelements.strategy;

import gameelements.Inventory;
import gameelements.cards.Card;
import gameelements.enums.Action;

import java.util.ArrayList;

public class WonderStrategy implements PlayingStrategy {
    Action action;

    @Override
    public Card chooseCard(Inventory inv) {
        return chooseCardToBuildStep(inv);
    }

    @Override
    public Action getAction() {
        return this.action;
    }

    private void setAction(Action action) {
        this.action = action;
    }

    public Card chooseCardToBuildStep(Inventory inv) {
        boolean canBuildStep = inv.canBuild(inv.getWonderRequiredResources());

        Card chosenCard = inv.getCardsInHand().get(0);

        if (canBuildStep) {
            setAction(Action.WONDER);
            //Player picks a card he cannot build
            for (Card card : inv.getCardsInHand()) {
                //We pick the first non-buildable card
                if (!inv.canBuild(chosenCard.getRequiredResources())) {
                    break;
                } else {
                    chosenCard = card;
                }
            }
        } else {
            ArrayList<Card> availableCards = cardsAvailableToPlay(inv);

            //Player picks a card he can build AAAAAAAAAAAAAAAAAAAAAa
            if (availableCards.isEmpty()) {
                this.action = Action.SELL;
                return inv.getCardsInHand().get(0);
            }
            if (inv.getPossibleFreeBuildings() > 0) {
                setAction(Action.BUILDFREE);
                for (Card card : inv.getCardsInHand()) {
                    //We pick the first non-buildable card
                    if (!inv.canBuild(card.getRequiredResources())) {
                        break;
                    } else {
                        return card;
                    }
                }
            }

            setAction(Action.BUILDING);
            for (Card card : availableCards) {
                //Player picks the first buildable card
                if (inv.canBuild(chosenCard.getRequiredResources())) {
                    break;
                } else {
                    chosenCard = card;
                }
            }
        }

        return chosenCard;
    }
}
