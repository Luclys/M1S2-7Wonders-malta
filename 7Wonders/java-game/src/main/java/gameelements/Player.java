package gameelements;

import board.Board;
import gameelements.cards.Card;
import gameelements.enums.Action;
import gameelements.enums.Symbol;
import strategy.FirstCardStrategy;
import strategy.PlayingStrategy;

import java.util.List;

/**
 * This class describe a player
 *
 * @author lamac
 */
public class Player {
    private final int id;
    private  PlayingStrategy strategy;
    private int rightNeighborId;
    private int leftNeighborId;
    private Card chosenCard;

    public Player(int id) {
        this.id = id;
        this.strategy = new FirstCardStrategy();
    }

    public Player(Player p) {
        this.id = p.id;
        this.strategy = p.strategy.copy();
        rightNeighborId = p.rightNeighborId;
        leftNeighborId = p.leftNeighborId;
        chosenCard = p.chosenCard;
    }

    public Player(int id, PlayingStrategy strategy) {
        this.id = id;
        this.strategy = strategy;
    }

    public String toString() {
        return "Player " + id;
    }

    /**
     * This method returns a card according to the strategy used
     *
     * @param inv
     * @return chosen card
     */
    public Card chooseCard(Inventory inv, Board b) throws Exception {
        chosenCard = strategy.chooseCard(inv,b);
        return strategy.getCard();
    }

    public Card chooseGuildCard(List<Card> list, Inventory inv, Inventory leftNeighborInv, Inventory rightNeighborInv) {
        return list.get(0);
    }

    public Symbol chooseScientific(int[] currentSymbols) {
        // scientific score rule :
        // foreach(nb same Scientific²) + min(nb same scientific) * 7
        return Symbol.COMPAS;
    }

    public Card chooseDiscardedCardToBuild(Inventory inventory, List<Card> discardedDeckCardList) {
        return discardedDeckCardList.get(0);
    }

    public String getStrategyName() {
        return strategy.getClass().getName();
    }

    public PlayingStrategy getStrategy() {
        return strategy;
    }

    //Getters and setters
    public int getId() {
        return id;
    }

    public Card getChosenCard() {
        return strategy.getCard();
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

    public Action getAction() {
        return strategy.getAction();
    }
    public void setStrategy(PlayingStrategy s){
        this.strategy = s;
    }
}
