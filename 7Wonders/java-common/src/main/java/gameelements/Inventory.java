package gameelements;


import gameelements.enums.Resource;
import gameelements.enums.Symbol;

import java.util.ArrayList;

public class Inventory {
    private final int playerId;
    private final int[] availableResources;
    private final int[] availableSymbols;
    private ArrayList<Card> cards;
    private int score;

    private int conflictPoints;
    private int coins;
    private int priceLeft;
    private int priceRight;

    public Inventory(int playerId, int[] availableResources, int[] availableSymbols, ArrayList<Card> cards, int score, int conflictPoints, int coins, int priceLeft, int priceRight) {
        this.playerId = playerId;

        this.availableResources = availableResources;
        this.availableSymbols = availableSymbols;
        this.conflictPoints = conflictPoints;
        this.cards = cards;
        this.score = score;
        this.coins = coins;
        this.priceLeft = priceLeft;
        this.priceRight = priceRight;
    }

    public Inventory(int playerId) {
        this.playerId = playerId;

        this.cards = new ArrayList<>(7);
        this.availableResources = new int[Resource.values().length];
        this.availableSymbols = new int[Symbol.values().length];
        this.coins = 3;
        this.priceRight = 2;
        this.priceLeft = 2;
        this.conflictPoints = 0;

    }

    public Inventory(Inventory inventory) {
        // Consider implementing Cloneable instead...
        this.playerId =  inventory.playerId;
        this.availableResources =  inventory.availableResources;
        this.availableSymbols = inventory.availableSymbols;
        this.cards =  inventory.cards;
        this.score =  inventory.score;
        this.conflictPoints =  inventory.conflictPoints;
        this.coins =  inventory.coins;
        this.priceLeft =  inventory.priceLeft;
        this.priceRight =  inventory.priceRight;
    }

    public Card discardLastCard() {
        if (cards.size() == 1) {
            //System.out.println("Player " + id + " discard the " + cards.get(0).getName() + " card.");
            return cards.remove(0);
        } else {
            throw new Error("There is more than 1 card left.");
        }
    }

    public void sellCard(Card card) {
        if (cards.contains(card)){
            //System.out.println("Player " + id + " discard the " + cards.get(0).getName() + " card.");
            addCoins(3);
            cards.remove(0);

        } else{
            throw new Error("Can't sell a card you don't have.");
        }
    }

    public void updateInventory(Card playedCard) {
        for (Effect effect : playedCard.getEffects()) {
            effect.activateEffect(this);
        }
        cards.remove(0);
    }


    public void updateConflictPoints(int conflictPoints) {
        this.conflictPoints += conflictPoints;
    }

    public void removeCoins(int coins) {
        setCoins(this.coins - coins);
    }

    public void addCoins(int coins) {
        this.coins += coins;
    }


    // GETTERS & SETTERS
    public int getSymbCount(Symbol symbol) {
        return this.availableSymbols[symbol.getIndex()];
    }

    public int getResCount(Resource resource) {
        return this.availableResources[resource.getIndex()];
    }

    public int getPlayerId() {
        return playerId;
    }

    public int[] getAvailableResources() {
        return availableResources;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getConflictPoints() {
        return conflictPoints;
    }

    public void setConflictPoints(int conflictPoints) {
        this.conflictPoints = conflictPoints;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getPriceLeft() {
        return priceLeft;
    }

    public void setPriceLeft(int priceLeft) {
        this.priceLeft = priceLeft;
    }

    public int getPriceRight() {
        return priceRight;
    }

    public void setPriceRight(int priceRight) {
        this.priceRight = priceRight;
    }

    public int[] getAvailableSymbols() {
        return this.availableSymbols;
    }
}
