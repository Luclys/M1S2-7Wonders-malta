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
    private int victoryChipsScore;
    private int defeatChipsCount;
    private int coins;
    private int addedCoins;

    private int priceLeft;
    private int priceRight;
    int possibleFreeBuildingsCount;
    int possibleFreeDiscardedBuildingsCount;
    boolean canPlayLastCard;

    public Inventory(int playerId) {
        this.playerId = playerId;
        this.availableResources = new int[Resource.values().length];
        this.availableSymbols = new int[Symbol.values().length];
        this.cardsInHand = new ArrayList<>(7);
        this.playedCards = new ArrayList<>(7 * 3);

        this.score = 0;
        this.victoryChipsScore = 0;
        this.defeatChipsCount = 0;
        this.coins = 3;
        this.addedCoins = 0;

        this.priceLeft = 2;
        this.priceRight = 2;
        this.possibleFreeBuildingsCount = 0;
        this.possibleFreeDiscardedBuildingsCount = 0;
        this.canPlayLastCard = false;
    }

    public Inventory(Inventory inventory) {
        // Consider implementing Cloneable instead...
        this.playerId = inventory.playerId;
        this.availableResources = inventory.availableResources;;
        this.availableSymbols = inventory.availableSymbols;
        this.cardsInHand = inventory.cardsInHand;
        this.playedCards = inventory.playedCards;
        this.wonderBoard = inventory.wonderBoard;

        this.score = inventory.score;
        this.victoryChipsScore = inventory.victoryChipsScore;
        this.defeatChipsCount = inventory.defeatChipsCount;
        this.coins = inventory.coins;
        this.addedCoins = inventory.addedCoins;

        this.priceLeft = inventory.priceLeft;
        this.priceRight = inventory.priceRight;
        this.possibleFreeBuildingsCount = inventory.possibleFreeBuildingsCount;
        this.possibleFreeDiscardedBuildingsCount = inventory.possibleFreeDiscardedBuildingsCount;
        this.canPlayLastCard = inventory.canPlayLastCard;
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

    public boolean canBuild(Resource[] requiredResources) {
        int [] neededResources = new int[Resource.values().length];
        for (Resource resource: requiredResources) {
            neededResources[resource.getIndex()]++;
        }
        for (int i = 0; i < neededResources.length; i++) {
            if (neededResources[i] >= availableResources[i]) {
                return false;
            }
        }
        return true;
    }

    public int getVictoryChipsScore() {
        return victoryChipsScore;
    }

    public int getDefeatChipsCount() {
        return defeatChipsCount;
    }

    public void addVictoryJetonsScore(int victoryJetonsScore) {
        this.victoryChipsScore += victoryJetonsScore;
    }

    public void addDefeatJeton() {
        this.defeatChipsCount++;
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

    public void addScore(int score) {
        this.score += score;
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

    public int getAddedCoins() {
        return addedCoins;
    }

    public void setAddedCoins(int addedCoins) {
        this.addedCoins = addedCoins;
    }

    public int getPossibleFreeBuildingsCount() {
        return possibleFreeBuildingsCount;
    }

    public void addPossibleFreeBuildingsCount(int possibleFreeBuildingsCount) {
        this.possibleFreeBuildingsCount+= possibleFreeBuildingsCount;
    }

    public int getPossibleFreeDiscardedBuildingsCount() {
        return possibleFreeDiscardedBuildingsCount;
    }

    public void addPossibleFreeDiscardedBuildingsCount(int possibleFreeDiscardedBuildingsCount) {
        this.possibleFreeDiscardedBuildingsCount+= possibleFreeDiscardedBuildingsCount;
    }

    public boolean isCanPlayLastCard() {
        return canPlayLastCard;
    }

    public void setCanPlayLastCard(boolean canPlayLastCard) {
        this.canPlayLastCard = canPlayLastCard;
    }
}
