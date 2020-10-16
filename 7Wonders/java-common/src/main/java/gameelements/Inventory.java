package gameelements;

import gameelements.cards.Card;
import gameelements.enums.Resource;
import gameelements.enums.Symbol;
import gameelements.wonders.WonderBoard;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private final int playerId;
    private final int[] availableResources;
    private final int[] availableSymbols;
    private final List<Resource[]> pairResChoice;
    private List<Card> cardsInHand;
    private List<Card> playedCards;
    private final List<Effect> endGameEffects;
    private WonderBoard wonderBoard;

    private int score;
    private int victoryChipsScore;
    private int defeatChipsCount;
    private int coins;
    private int matieresPremieresPriceLeft;
    private int matieresPremieresPriceRight;
    private int produitsManifacturesPrice;
    private int addedCoins;

    private int allResPremChoice;
    private int allResManuChoice;
    private int possibleFreeBuildingsCount;
    private int possibleFreeDiscardedBuildingsCount;
    private boolean canPlayLastCard;
    private final SoutConsole sout;
    public Inventory(int playerId) {
        this.sout = new SoutConsole(true);
        this.playerId = playerId;
        this.availableResources = new int[Resource.values().length];
        this.availableSymbols = new int[Symbol.values().length];
        this.pairResChoice = new ArrayList<>();
        this.cardsInHand = new ArrayList<>(7);
        this.playedCards = new ArrayList<>(7 * 3);
        this.endGameEffects = new ArrayList<>(7 * 3);

        this.score = 0;
        this.victoryChipsScore = 0;
        this.defeatChipsCount = 0;
        this.coins = 3;
        this.matieresPremieresPriceRight = 2;
        this.matieresPremieresPriceLeft = 2;
        this.produitsManifacturesPrice = 2;
        this.addedCoins = 0;

        allResPremChoice = 0;
        allResManuChoice = 0;
        this.possibleFreeBuildingsCount = 0;
        this.possibleFreeDiscardedBuildingsCount = 0;
        this.canPlayLastCard = false;


    }

    public Inventory(Inventory inventory) {
        this.sout = new SoutConsole(true);
        // Consider implementing Cloneable instead...
        this.playerId = inventory.playerId;
        this.availableResources = inventory.availableResources;
        this.availableSymbols = inventory.availableSymbols;
        this.pairResChoice = inventory.pairResChoice;
        this.cardsInHand = inventory.cardsInHand;
        this.playedCards = inventory.playedCards;
        this.endGameEffects = inventory.endGameEffects;
        this.wonderBoard = inventory.wonderBoard;

        this.score = inventory.score;
        this.victoryChipsScore = inventory.victoryChipsScore;
        this.defeatChipsCount = inventory.defeatChipsCount;
        this.coins = inventory.coins;
        this.allResPremChoice = inventory.allResPremChoice;
        this.allResManuChoice = inventory.allResManuChoice;
        this.matieresPremieresPriceLeft = inventory.matieresPremieresPriceLeft;
        this.matieresPremieresPriceRight = inventory.matieresPremieresPriceRight;
        this.produitsManifacturesPrice = inventory.produitsManifacturesPrice;
        this.addedCoins = inventory.addedCoins;
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

    public void updateInventory(Card playedCard, Player player, Inventory leftNeighborInv, Inventory rightNeighborInv) {
        for (Effect effect : playedCard.getEffects()) {
            effect.activateEffect(player, this, leftNeighborInv, rightNeighborInv);
        }
        playedCards.add(playedCard);
        cardsInHand.remove(playedCard);
    }

    public boolean canBuild(Resource[] requiredResources) {
        int[] neededResources = new int[Resource.values().length];
        for (Resource resource : requiredResources) {
            neededResources[resource.getIndex()]++;
        }
        for (int i = 0; i < neededResources.length; i++) {
            if (neededResources[i] >= availableResources[i]) {
                return false;
            }
        }
        return true;
    }

    public void addPairResChoice(Resource[] resources) {
        this.pairResChoice.add(resources);
    }

    public void incAllResChoice(Boolean primaryResource) {
        if (Boolean.TRUE.equals(primaryResource)) {
            this.allResPremChoice++;
        } else {
            this.allResManuChoice++;
        }
    }

    public void addVictoryJetonsScore(int victoryJetonsScore) {
        this.victoryChipsScore += victoryJetonsScore;
    }

    public void addEndGameEffect(Effect effect) {
        this.endGameEffects.add(effect);
    }

    public void addDefeatJeton() {
        this.defeatChipsCount++;
    }

    public void removeCoins(int coins) {
        this.coins -= coins;
    }

    public void addCoins(int coins) {
        this.coins += coins;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public void addPossibleFreeBuildingsCount(int possibleFreeBuildingsCount) {
        this.possibleFreeBuildingsCount += possibleFreeBuildingsCount;
    }

    public void addPossibleFreeDiscardedBuildingsCount(int possibleFreeDiscardedBuildingsCount) {
        this.possibleFreeDiscardedBuildingsCount += possibleFreeDiscardedBuildingsCount;
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

    public List<Resource[]> getPairResChoice() {
        return pairResChoice;
    }

    public List<Card> getCardsInHand() {
        return cardsInHand;
    }

    public void setCardsInHand(List<Card> cardsInHand) {
        this.cardsInHand = cardsInHand;
    }

    public List<Card> getPlayedCards() {
        return playedCards;
    }

    public void setPlayedCards(List<Card> playedCards) {
        this.playedCards = playedCards;
    }

    public List<Effect> getEndGameEffects() {
        return endGameEffects;
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

    public int getVictoryChipsScore() {
        return victoryChipsScore;
    }

    public void setVictoryChipsScore(int victoryChipsScore) {
        this.victoryChipsScore = victoryChipsScore;
    }

    public int getDefeatChipsCount() {
        return defeatChipsCount;
    }

    public void setDefeatChipsCount(int defeatChipsCount) {
        this.defeatChipsCount = defeatChipsCount;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getMatieresPremieresPriceLeft() {
        return matieresPremieresPriceLeft;
    }

    public void setMatieresPremieresPriceLeft(int matieresPremieresPriceLeft) {
        this.matieresPremieresPriceLeft = matieresPremieresPriceLeft;
    }

    public int getMatieresPremieresPriceRight() {
        return matieresPremieresPriceRight;
    }

    public void setMatieresPremieresPriceRight(int matieresPremieresPriceRight) {
        this.matieresPremieresPriceRight = matieresPremieresPriceRight;
    }

    public int getProduitsManifacturesPrice() {
        return produitsManifacturesPrice;
    }

    public void setProduitsManifacturesPrice(int produitsManifacturesPrice) {
        this.produitsManifacturesPrice = produitsManifacturesPrice;
    }

    public int getAddedCoins() {
        return addedCoins;
    }

    public void setAddedCoins(int addedCoins) {
        this.addedCoins = addedCoins;
    }

    public int getAllResPremChoice() {
        return allResPremChoice;
    }

    public void setAllResPremChoice(int allResPremChoice) {
        this.allResPremChoice = allResPremChoice;
    }

    public int getAllResManuChoice() {
        return allResManuChoice;
    }

    public void setAllResManuChoice(int allResManuChoice) {
        this.allResManuChoice = allResManuChoice;
    }

    public int getPossibleFreeBuildingsCount() {
        return possibleFreeBuildingsCount;
    }

    public void setPossibleFreeBuildingsCount(int possibleFreeBuildingsCount) {
        this.possibleFreeBuildingsCount = possibleFreeBuildingsCount;
    }

    public int getPossibleFreeDiscardedBuildingsCount() {
        return possibleFreeDiscardedBuildingsCount;
    }

    public void setPossibleFreeDiscardedBuildingsCount(int possibleFreeDiscardedBuildingsCount) {
        this.possibleFreeDiscardedBuildingsCount = possibleFreeDiscardedBuildingsCount;
    }

    public boolean isCanPlayLastCard() {
        return canPlayLastCard;
    }

    public void setCanPlayLastCard(boolean canPlayLastCard) {
        this.canPlayLastCard = canPlayLastCard;
    }
}
