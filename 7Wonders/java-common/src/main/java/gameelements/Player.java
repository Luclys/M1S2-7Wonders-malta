package gameelements;

import gameelements.enums.Symbol;
import gameelements.strategy.PlayingStrategy;
import gameelements.strategy.ResourceStrategy;

import java.util.ArrayList;

public class Player {
    private final int id;
    private int rightNeighborId;
    private int leftNeighborId;
    private PlayingStrategy strategy;

    private Card chosenCard;

    public Player(int id) {
        this.id = id;
        this.strategy = new ResourceStrategy();
    }

    public String toString() {
        return "Player " + id;
    }

    public Card chooseCard(Inventory inv) {
        /*
         * Choices :
         * Can be affected by the resource choice on a card
         * - Buy a Card : cdt sufficient resources & same card not previously bought, effect : Add resources production
         * - Construct Wonder step : cdt sufficient resources & step not already constructed, effect : grant step effect
         *
         * To do whenever there is no other choice possible
         * - sell Card : unconditionally, effect : grant  3 coins. ⚠ The discarded cards must be remembered.
         * */
        ArrayList<Card> cardsAvailableToPlay = new ArrayList<>(inv.getCardsInHand());
        //We remove from playable cards the cards the player already played, you can't play the same card twice
        cardsAvailableToPlay.removeIf(card -> inv.getPlayedCards().contains(card) && card.isBatiment());
        chosenCard = strategy.chooseCard(inv, cardsAvailableToPlay);
        //Les tests ne passent plus car l'inventaire ne connaît pas encore la merveille --> mock object

        return chosenCard;
        // return the played card to the board so that the board can decide which decision to make(buy resource or discard)
    }

    public Card chooseGuildCard(ArrayList<Card> list, Inventory inv) {
        return list.get(0);
    }

    public Symbol chooseScientific(int[] currentSymbols) {
        //foreach(nb same Scientific²) + min(nb same scientific) * 7
        return Symbol.COMPAS;
    }

    //Getters and setters
    public int getId() {
        return id;
    }


    public Card getChosenCard() {
        return this.chosenCard;
    }

    public int getRightNeighborId() {
        return this.rightNeighborId;
    }


    public void setRightNeighborId(int id) {
        this.rightNeighborId = id;
    }

    public int getLeftNeighborId() {
        return this.leftNeighborId;
    }

    public void setLeftNeighborId(int id) {
        this.leftNeighborId = id;
    }

}
