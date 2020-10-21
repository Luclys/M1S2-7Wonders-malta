package gameelements;

import gameelements.cards.Card;
import gameelements.enums.Action;
import gameelements.enums.Symbol;
import gameelements.strategy.FirstCardStrategy;
import gameelements.strategy.PlayingStrategy;

import java.util.List;

public class Player {
    private final int id;
    private final PlayingStrategy strategy;
    private int rightNeighborId;
    private int leftNeighborId;
    private Card chosenCard;

    public Player(int id) {
        this.id = id;
        this.strategy = new FirstCardStrategy();
    }

    public Player(int id, PlayingStrategy strategy) {
        this.id = id;
        this.strategy = strategy;
    }

    public String toString() {
        return "Player " + id;
    }

    public Card chooseCard(Inventory inv) {
        chosenCard = strategy.chooseCard(inv);
        return chosenCard;
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

    public Action getAction() {
        return strategy.getAction();
    }
}
