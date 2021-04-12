package gameelements;

import gameelements.cards.Card;
import gameelements.effects.Effect;
import gameelements.enums.Resource;
import gameelements.enums.Symbol;
import gameelements.wonders.WonderBoard;
import statistic.DetailedResults;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static gameelements.wonders.WonderBoard.initiateColossusA;

public class Inventory implements Comparable<Object> {
    private String playerURL;
    private final int playerId;

    private final int[] availableResources;
    private final int[] availableSymbols;
    private final List<Resource[]> pairResChoice;
    private final List<Effect> endGameEffects;
    private DetailedResults detailedResults;
    private List<Card> cardsInHand;
    private List<Card> playedCards;
    private WonderBoard wonderBoard;
    private int score;
    private int rank;
    private int victoryChipsScore;
    private int defeatChipsCount;
    private int coins;
    private int matieresPremieresPriceLeft;
    private int matieresPremieresPriceRight;
    private int produitsManifacturesPrice;
    private int addedCoins;
    private int anyMatierePremiereAvailableCount;
    private int anyResourceManufactureAvailableCount;
    private int possibleFreeBuildings;
    private int possibleFreeDiscardedBuildingsCount;
    private boolean canPlayLastCard;
    private int rightNeighborId;
    private int leftNeighborId;


    public Inventory() {
        this.playerId = 0;
        this.availableResources = new int[Resource.values().length];
        this.availableSymbols = new int[Symbol.values().length];
        this.pairResChoice = new ArrayList<>();
        this.cardsInHand = new ArrayList<>(7);
        this.playedCards = new ArrayList<>(7 * 3);
        this.endGameEffects = new ArrayList<>(7 * 3);
        this.detailedResults = new DetailedResults();
        this.score = 0;
        this.rank = 0;
        this.victoryChipsScore = 0;
        this.defeatChipsCount = 0;
        this.coins = 3;
        this.matieresPremieresPriceRight = 2;
        this.matieresPremieresPriceLeft = 2;
        this.produitsManifacturesPrice = 2;
        this.addedCoins = 0;

        anyMatierePremiereAvailableCount = 0;
        anyResourceManufactureAvailableCount = 0;
        this.possibleFreeBuildings = 0;
        this.possibleFreeDiscardedBuildingsCount = 0;
        this.canPlayLastCard = false;

        this.wonderBoard = initiateColossusA();
        this.wonderBoard.setAssociatedPlayerId(this.getPlayerId());
    }

    public Inventory(int playerId) {
        this.playerId = playerId;
        this.availableResources = new int[Resource.values().length];
        this.availableSymbols = new int[Symbol.values().length];
        this.pairResChoice = new ArrayList<>();
        this.cardsInHand = new ArrayList<>(7);
        this.playedCards = new ArrayList<>(7 * 3);
        this.endGameEffects = new ArrayList<>(7 * 3);
        this.detailedResults = new DetailedResults();
        this.score = 0;
        this.rank = 0;
        this.victoryChipsScore = 0;
        this.defeatChipsCount = 0;
        this.coins = 3;
        this.matieresPremieresPriceRight = 2;
        this.matieresPremieresPriceLeft = 2;
        this.produitsManifacturesPrice = 2;
        this.addedCoins = 0;

        anyMatierePremiereAvailableCount = 0;
        anyResourceManufactureAvailableCount = 0;
        this.possibleFreeBuildings = 0;
        this.possibleFreeDiscardedBuildingsCount = 0;
        this.canPlayLastCard = false;

        this.wonderBoard = initiateColossusA();
        this.wonderBoard.setAssociatedPlayerId(this.getPlayerId());
    }

    public Inventory(Inventory inventory) {
        // Consider implementing Cloneable instead...
        this.detailedResults = inventory.detailedResults;
        this.playerId = inventory.playerId;
        this.playerURL = inventory.playerURL;
        this.availableResources = Arrays.copyOf(inventory.availableResources, inventory.availableResources.length);
        this.availableSymbols = Arrays.copyOf(inventory.availableSymbols, inventory.availableSymbols.length);
        this.pairResChoice = new ArrayList<>(inventory.pairResChoice);
        this.cardsInHand = new ArrayList<>(inventory.cardsInHand);
        this.playedCards = new ArrayList<>(inventory.playedCards);
        this.endGameEffects = new ArrayList<>(inventory.endGameEffects);
        this.score = inventory.score;
        this.rank = inventory.rank;
        this.victoryChipsScore = inventory.victoryChipsScore;
        this.defeatChipsCount = inventory.defeatChipsCount;
        this.coins = inventory.coins;
        this.anyMatierePremiereAvailableCount = inventory.anyMatierePremiereAvailableCount;
        this.anyResourceManufactureAvailableCount = inventory.anyResourceManufactureAvailableCount;
        this.matieresPremieresPriceLeft = inventory.matieresPremieresPriceLeft;
        this.matieresPremieresPriceRight = inventory.matieresPremieresPriceRight;
        this.produitsManifacturesPrice = inventory.produitsManifacturesPrice;
        this.addedCoins = inventory.addedCoins;
        this.possibleFreeBuildings = inventory.possibleFreeBuildings;
        this.possibleFreeDiscardedBuildingsCount = inventory.possibleFreeDiscardedBuildingsCount;
        this.canPlayLastCard = inventory.canPlayLastCard;

        if (inventory.wonderBoard != null) {
            this.wonderBoard = new WonderBoard(inventory.wonderBoard);
        } else {
            this.wonderBoard = initiateColossusA();
        }
        this.wonderBoard.setAssociatedPlayerId(this.getPlayerId());
        this.rightNeighborId = inventory.rightNeighborId;
        this.leftNeighborId = inventory.leftNeighborId;
    }


    @Override
    public int compareTo(Object o) {
        Inventory compareToInv = (Inventory) o;

        if (score == compareToInv.score) {
            return -Integer.compare(coins, compareToInv.coins);
        }
        if (score > compareToInv.score) return -1;
        return 1;
    }

    private List<Integer> getPlayedCardIds() {
        return playedCards.stream().map(Card::getId).collect(Collectors.toList());
    }

    public List<String> getPlayedCardNamesByIds(int[] ids) {
        return playedCards.stream().filter(card ->
                Arrays.stream(ids).anyMatch(i -> i == card.getId())
        ).map(Card::getName).collect(Collectors.toList());
    }

    public boolean canBuildCardForFree(Card card) {
        if (card.getRequiredBuildingsToBuildForFree() == null) {
            return false;
        } else {
            boolean result = false;
            for (int requiredBuildingId : card.getRequiredBuildingsToBuildForFree()) {
                if (getPlayedCardIds().contains(requiredBuildingId)) {
                    result = true;
                    break;
                }
            }
            return result;
        }
    }

    public Card discardLastCard() throws Exception {
        if (cardsInHand.size() == 1) {
            return cardsInHand.remove(0);
        } else if (cardsInHand.size() > 1) {
            throw new Exception("There is more than 1 card left.");
        } else {
            throw new Exception("There is no cards left.");
        }
    }


    public boolean canSell(Card card) {
        return cardsInHand.contains(card);
    }

    public void sellCard(Card card) throws Exception {
        if (canSell(card)) {
            addCoins(3);
            cardsInHand.remove(card);
        } else {
             throw new Exception("Can't sell a card you don't have.");
        }
    }

    public void updateInventory(Card playedCard, Inventory leftNeighborInv, Inventory rightNeighborInv) {
        for (Effect effect : playedCard.getEffects()) {
            effect.activateEffect(this, leftNeighborInv, rightNeighborInv);
        }
        playedCards.add(playedCard);
        cardsInHand.remove(playedCard);
       // System.out.println("cards "+cardsInHand+" deleted card"+playedCard+" -> "+cardsInHand.remove(playedCard));
    }

    public void updateInventoryCopyCard(Card playedCard, Inventory leftNeighborInv, Inventory rightNeighborInv) {
        for (Effect effect : playedCard.getEffects()) {
            effect.activateEffect(this, leftNeighborInv, rightNeighborInv);
        }
        playedCards.add(playedCard);
    }

    public void updateInventoryFreeCard(Card playedCard, Inventory leftNeighborInv, Inventory rightNeighborInv, List<Card> discardedDeckCardList) {
        for (Effect effect : playedCard.getEffects()) {
            effect.activateEffect(this, leftNeighborInv, rightNeighborInv);
        }
        playedCards.add(playedCard);
        discardedDeckCardList.remove(playedCard);
    }


    public boolean canBuildNextStep(WonderBoard wonderBoard) {
        if (wonderBoard.getCurrentStepIndex() == wonderBoard.getStepCount()) return false;
        return canBuild(wonderBoard.getCurrentStepRequiredResources());
    }

    public boolean canBuild(Resource[] requiredResources) {
        int anyMatierePremiereAvailableLeft = anyMatierePremiereAvailableCount;
        int anyResourceManufactureAvailableLeft = anyResourceManufactureAvailableCount;
        ArrayList<Integer> matieresPremieresIndexes = new ArrayList<>();
        if (requiredResources != null) {
            int[] neededResources = new int[Resource.values().length];
            for (Resource resource : requiredResources) {
                if (resource.isMatierePremiere()) {
                    matieresPremieresIndexes.add(resource.getIndex());
                }
                neededResources[resource.getIndex()]++;
            }
            for (int i = 0; i < neededResources.length; i++) {
                if (neededResources[i] > availableResources[i]) {
                    if (matieresPremieresIndexes.contains(i)) {
                        if (neededResources[i] > anyMatierePremiereAvailableLeft) {
                            return false;
                        } else {
                            anyMatierePremiereAvailableLeft -= neededResources[i];
                        }
                    } else {
                        if (neededResources[i] > anyResourceManufactureAvailableLeft) {
                            return false;
                        } else {
                            anyResourceManufactureAvailableLeft -= neededResources[i];
                        }
                    }
                }
            }
        }
        return true;
    }

    public List<Resource> missingResources(Resource[] requiredResources) {
        ArrayList<Resource> missing = new ArrayList<>();
        if (requiredResources == null) {
            return Collections.emptyList();
        }
        for (Resource r : requiredResources) {
            if (getAvailableResources()[r.getIndex()] == 0) {
                missing.add(r);
            }
        }
        return missing;
    }

    // TWEAKED GETTERS
    public int getSymbolCount(Symbol symbol) {
        return this.availableSymbols[symbol.getIndex()];
    }

    public int getResCount(Resource resource) {
        return this.availableResources[resource.getIndex()];
    }

    public Resource[] getCurrentStepRequiredResources() {
        return wonderBoard.getCurrentStepRequiredResources();
    }

    // GETTERS & SETTERS
    public DetailedResults getDetailedResults() {
        return detailedResults;
    }

    public void setDetailedResults(DetailedResults detailedResults) {
        this.detailedResults = detailedResults;
    }

    public int getVictoryChipsScore() {
        return victoryChipsScore;
    }

    public void setVictoryChipsScore(int victoryChipsScore) {
        this.victoryChipsScore = victoryChipsScore;
    }

    public void addPairResChoice(Resource[] resources) {
        this.pairResChoice.add(resources);
    }

    public void incAllResChoice(Boolean primaryResource) {
        if (Boolean.TRUE.equals(primaryResource)) {
            this.anyMatierePremiereAvailableCount++;
        } else {
            this.anyResourceManufactureAvailableCount++;
        }
    }

    public void addVictoryJetonsScore(int victoryJetonsScore) {
        this.victoryChipsScore += victoryJetonsScore;
        this.detailedResults.setScoreFromVictoryConflict(this.victoryChipsScore);
    }

    public void addEndGameEffect(Effect effect) {
        this.endGameEffects.add(effect);
    }

    public void addDefeatJeton() {
        this.defeatChipsCount++;
        this.detailedResults.setNbDefeatConflict(this.defeatChipsCount);
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

    public void addPossibleFreeBuildings(int possibleFreeBuildingsCount) {
        this.possibleFreeBuildings += possibleFreeBuildingsCount;
    }

    public void addPossibleFreeDiscardedBuildingsCount(int possibleFreeDiscardedBuildingsCount) {
        this.possibleFreeDiscardedBuildingsCount += possibleFreeDiscardedBuildingsCount;
    }

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

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
        this.detailedResults.setRank(rank);
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

    public void addAddedCoins(int totalCoins) {
        this.addedCoins += totalCoins;
    }

    public int getAnyMatierePremiereAvailableCount() {
        return anyMatierePremiereAvailableCount;
    }

    public void setAnyMatierePremiereAvailableCount(int anyMatierePremiereAvailableCount) {
        this.anyMatierePremiereAvailableCount = anyMatierePremiereAvailableCount;
    }

    public int getAnyProduitManufactureAvailableCount() {
        return anyResourceManufactureAvailableCount;
    }

    public void setAnyResourceManufactureAvailableCount(int anyResourceManufactureAvailableCount) {
        this.anyResourceManufactureAvailableCount = anyResourceManufactureAvailableCount;
    }

    public int getPossibleFreeBuildings() {
        return possibleFreeBuildings;
    }

    public void setPossibleFreeBuildings(int possibleFreeBuildings) {
        this.possibleFreeBuildings = possibleFreeBuildings;
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

    public boolean payIfPossible(int cost) {
        boolean canPay = false;
        if (this.getCoins() >= cost) {
            removeCoins(cost);
            canPay = true;
        }
        return canPay;
    }

    public void set(Inventory inv) {
        new Inventory(inv);
    }

    public ArrayList<Card> cardsAvailableToPlay() {
        //We remove from playable cards the cards the player already played, you can't play the same card twice
        ArrayList<Card> cardsAvailableToPlay = new ArrayList<>(this.getCardsInHand());
        cardsAvailableToPlay.removeIf(card -> this.getPlayedCards().contains(card) && card.isBuilding());
        return cardsAvailableToPlay;
    }

    public int getRightNeighborId() {
        return rightNeighborId;
    }

    public void setRightNeighborId(int rightNeighborId) {
        this.rightNeighborId = rightNeighborId;
    }

    public int getLeftNeighborId() {
        return leftNeighborId;
    }

    public void setLeftNeighborId(int leftNeighborId) {
        this.leftNeighborId = leftNeighborId;
    }

    public void setPlayerURL(String url) {
        this.playerURL = url;
    }

    public String getPlayerURL() {
        return playerURL;
    }
}
