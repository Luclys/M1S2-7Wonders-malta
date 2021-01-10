package strategy;

import board.Board;
import gameelements.Inventory;
import gameelements.cards.Card;
import gameelements.enums.Action;

import java.util.ArrayList;

/**
 * This interface describe the playing Strategies that a player can use.
 *
 * @author lamac
 */
public interface PlayingStrategy {
    Card chooseCard(Inventory inventory, Board b) throws Exception;

    Action getAction();
    Card getCard();

    PlayingStrategy copy();

    /**
     * this method remove from playable cards the cards the player already played,
     * because he can't play the same card twice
     *
     * @param inv
     * @return ArrayList<Card> card that the player associate to inv can play
     */
    default ArrayList<Card> cardsAvailableToPlay(Inventory inv) {
        //We remove from playable cards the cards the player already played, you can't play the same card twice
        ArrayList<Card> cardsAvailableToPlay = new ArrayList<>(inv.getCardsInHand());
        cardsAvailableToPlay.removeIf(card -> inv.getPlayedCards().contains(card));
        return cardsAvailableToPlay;
    }

    default ArrayList<Action> availableActions(Card c, Inventory inv){
        ArrayList<Action> list = new ArrayList<>();
        list.add(Action.SELL);
        // if can build step
        if (inv.canBuildNextStep(inv.getWonderBoard())) {
            list.add(Action.WONDER);
        }
        if (inv.canBuild(c.getRequiredResources())){
            list.add(Action.BUILDING);
        }
        if (inv.getPossibleFreeBuildings()>0){
            list.add(Action.BUILDFREE);
        }
        return list;
    }

    void updateKnowledge(ArrayList<Inventory> censoredInvList, int age, int currentTurn, int rightNeighborId, int leftNeighborId);
}
