package gameelements;

import gameelements.enums.Resource;
import gameelements.enums.Symbol;
import gameelements.wonders.WonderBoard;

import java.util.ArrayList;

public class Inventory {
    private final int playerId;
    private final int[] availableResources;
    private final int[] availableSymbols;
    private ArrayList<Card> cardsInHand;
    private ArrayList<Card> playedCards;
    private WonderBoard wonderBoard;

    private int score;
    private int victoryJetonsScore;
    private int defeatJetonsCount;
    private int coins;
    private int priceLeft;
    private int priceRight;

    public Inventory(int playerId) {
        this.playerId = playerId;
        this.cardsInHand = new ArrayList<>(7);
        this.playedCards = new ArrayList<>(7 * 3);
        this.availableResources = new int[Resource.values().length];
        this.availableSymbols = new int[Symbol.values().length];
        this.coins = 3;
        this.priceRight = 2;
        this.priceLeft = 2;
        this.victoryJetonsScore = 0;
        this.defeatJetonsCount = 0;
    }

    public Inventory(Inventory inventory) {
        // Consider implementing Cloneable instead...
        this.playerId = inventory.playerId;
        this.availableResources = inventory.availableResources;
        this.availableSymbols = inventory.availableSymbols;
        this.cardsInHand = inventory.cardsInHand;
        this.playedCards = inventory.playedCards;
        this.wonderBoard = inventory.wonderBoard;
        this.score = inventory.score;
        this.victoryJetonsScore = inventory.victoryJetonsScore;
        this.defeatJetonsCount = inventory.defeatJetonsCount;
        this.coins = inventory.coins;
        this.priceLeft = inventory.priceLeft;
        this.priceRight = inventory.priceRight;
    }

    public Card discardLastCard() {
        if (cardsInHand.size() == 1) {
            //System.out.println("Player " + id + " discard the " + cards.get(0).getName() + " card.");
            return cardsInHand.remove(0);
        } else {
            throw new Error("There is more than 1 card left.");
        }
    }

    public void sellCard(Card card) {
        if (cardsInHand.contains(card)) {
            //System.out.println("Player " + id + " discard the " + cards.get(0).getName() + " card.");
            addCoins(3);
            cardsInHand.remove(0);
        } else {
            throw new Error("Can't sell a card you don't have.");
        }
    }

    public void updateInventory(Card playedCard) {
        for (Effect effect : playedCard.getEffects()) {
            effect.activateEffect(this);
        }
        playedCards.add(playedCard);
        cardsInHand.remove(0);
    }

    public int getVictoryJetonsScore() {
        return victoryJetonsScore;
    }

    public int getDefeatJetonsCount() {
        return defeatJetonsCount;
    }

    public void addVictoryJetonsScore(int victoryJetonsScore) {
        this.victoryJetonsScore += victoryJetonsScore;
    }

    public void addDefeatJeton() {
        this.defeatJetonsCount++;
    }

    public void removeCoins(int coins) {
        setCoins(this.coins - coins);
    }

    public void addCoins(int coins) {
        this.coins += coins;
    }


    // TWEAKED GETTERS
    public int getSymbCount(Symbol symbol) {
        return this.availableSymbols[symbol.getIndex()];
    }

    public int getResCount(Resource resource) {
        return this.availableResources[resource.getIndex()];
    }

    // GETTERS & SETTERS
    public int getPlayerId() {
        return playerId;
    }

    public int[] getAvailableResources() {
        return availableResources;
    }

    public int[] getAvailableSymbols() {
        return availableSymbols;
    }

    public ArrayList<Card> getCardsInHand() {
        return cardsInHand;
    }

    public void setCardsInHand(ArrayList<Card> cardsInHand) {
        this.cardsInHand = cardsInHand;
    }

    public ArrayList<Card> getPlayedCards() {
        return playedCards;
    }

    public void setPlayedCards(ArrayList<Card> playedCards) {
        this.playedCards = playedCards;
    }

    public WonderBoard getWonderBoard() {
        return wonderBoard;
    }

    public void setWonderBoard(WonderBoard wonderBoard) {
        this.wonderBoard = wonderBoard;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
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
}
