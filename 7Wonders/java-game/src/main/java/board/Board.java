package board;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import statistic.DetailedResults;
import gameelements.GameLogger;
import gameelements.Inventory;
import gameelements.Player;
import gameelements.ages.Age;
import gameelements.ages.AgeI;
import gameelements.ages.AgeII;
import gameelements.ages.AgeIII;
import gameelements.cards.Card;
import gameelements.enums.Action;
import gameelements.enums.Resource;
import gameelements.enums.Symbol;
import gameelements.wonders.WonderBoard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * This class presents the director of the game
 *
 * @author lamac
 */

public class Board {
    public static final int AGES = 3;
    public static final int CARDS_NUMBER = 7;
    private final PlayersManager playersManager;
    private final Trade commerce;
    private final ArrayList<Player> playerList;
    private final List<Inventory> playerInventoryList;
    private final List<Card> discardedDeckCardList;
    private final CardManager cardManager;
    private final GameLogger log;
    private final List<WonderBoard> availableWonderBoardList;
    private List<Card> currentDeckCardList;
    private boolean isLeftRotation;
    private int jetonVictoryValue;

    public Board(Board b) {
        this.playersManager = b.playersManager;
        this.commerce = b.commerce;
        this.playerList = b.playerList;
        this.playerInventoryList = b.playerInventoryList;
        this.discardedDeckCardList = b.discardedDeckCardList;
        this.cardManager = b.cardManager;
        this.log = b.log;
        this.availableWonderBoardList = b.availableWonderBoardList;
        this.currentDeckCardList = b.currentDeckCardList;
        this.isLeftRotation = b.isLeftRotation;
        this.jetonVictoryValue = b.jetonVictoryValue;
    }

    /**
     * the constructor allows
     * to associate the given player to inventories
     * to initialize the attributs
     *
     * @param playerList
     * @param boolPrint
     */
    public Board(List<Player> playerList, Boolean boolPrint) {
        log = new GameLogger(boolPrint);
        commerce = new Trade(log);
        playersManager = new PlayersManager(log);
        // Setup Players and their inventories
        this.playerList = (ArrayList<Player>) (getManager().associateNeighbor(playerList));
        playerInventoryList = getManager().getPlayerInventoryList();
        cardManager = new CardManager(playerList, playerInventoryList);
        // Setup Decks
        discardedDeckCardList = new ArrayList<>(playerList.size() * 7);
        availableWonderBoardList = WonderBoard.initiateWonders();
    }

    static void denseRanking(List<Inventory> playerInventoryList) {
        List<Inventory> orderedList = playerInventoryList.stream().sorted(Inventory::compareTo).collect(Collectors.toList());

        Inventory lastinv = orderedList.get(0);
        int rank = 1;
        for (Inventory inv : orderedList) {
            if (inv.compareTo(lastinv) > 0) {
                rank++;
            }
            lastinv = inv;
            inv.setRank(rank);
        }
    }

    public void ageSetUp(int numAge) {
        Age age;
        switch (numAge) {
            case 1:
                age = new AgeI();
                break;
            case 2:
                age = new AgeII();
                break;
            case 3:
                age = new AgeIII();
                break;
            default:
                throw new IllegalStateException("Unexpected age value: " + numAge);
        }
        currentDeckCardList = age.initiateCards(playerList.size());
        isLeftRotation = age.isLeftRotation();
        jetonVictoryValue = age.getVictoryJetonValue();

        Collections.shuffle(currentDeckCardList);
    }

    /**
     * this method launch the game
     * it starts by associate the cards to the players
     * ask players for their chosen cards and their actions and call an other method to excute them
     *
     * @param nbPlay
     */

    public void play(int nbPlay) throws JsonProcessingException {
        log.beginningOfPlay(nbPlay);
        assignWBToPlayers();
        for (int age = 1; age <= AGES; age++) {
            ageSetUp(age);
            log.beginningOfAge(age);
            // Card dealing & resetting possibleFreeBuildingsCount
            for (Inventory inventory : playerInventoryList) {
                inventory.setCardsInHand(drawCards(CARDS_NUMBER));
                if (inventory.getPossibleFreeBuildings() == -1) inventory.setPossibleFreeBuildings(1);
            }

            for (int currentTurn = 0; currentTurn < CARDS_NUMBER - 1; currentTurn++) {
                log.newTurn(currentTurn + 1);
                log.play();

                // Each player plays a card on each turn
                for (Player p : playerList) {
                    p.chooseCard(new Inventory(playerInventoryList.get(p.getId())));
                    log.chosenCards(p.getId(), p.getChosenCard());
                }
                log.playersStartToPlayCards();
                for (int i = 0; i < getPlayerList().size(); i++) {
                    executePlayerAction(playerInventoryList.get(i), getPlayerList().get(i));
                }
                endOfTurn();
            }

            endOfAge();
            log.endOfAge(age);
        }

        endOfGame();
        log.finalGameRanking(playerInventoryList);
        retrieveResults();
    }

    public void endOfAge(){
        handleLastTurnCard();
        resolveWarConflict(getJetonVictoryValue());
    }
    public void endOfTurn(){
        playersManager.updateCoins();
        playersManager.freeBuildFromDiscarded(discardedDeckCardList);
        getCardManager().playersCardsRotation(isLeftRotation());
    }

    public void endOfGame(){
        scores();
        denseRanking(playerInventoryList);
        updateLastDetailedResultsValues();
    }


    private void retrieveResults() throws JsonProcessingException {
        int size = playerInventoryList.size();
        DetailedResults[] results = new DetailedResults[size];
        for (int i = 0; i < size; i++) {
            results[i] = playerInventoryList.get(i).getDetailedResults();
        }
        sendToServer(results);
    }

    private void sendToServer(DetailedResults[] results) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(results);
        SevenWondersLauncher.client.sendResults(s);
    }

    private void updateLastDetailedResultsValues() {
        for (Inventory inv : playerInventoryList) {
            inv.getDetailedResults().setNbShield(inv.getSymbolCount(Symbol.BOUCLIER));
            inv.getDetailedResults().setNbStepBuilt(inv.getWonderBoard().getCurrentStepIndex());
            inv.getDetailedResults().setTotalScore(inv.getScore());
        }
    }

    /**
     * this method checks if it the end on the age so that the last cards in the hands of
     * the players can be discard
     */
    void handleLastTurnCard() {
        // At the end of the 6th turn, we discard the remaining card
        // ⚠ The discarded cards must remembered.
        for (Inventory inv : getPlayerInventoryList()) {
            if (!inv.isCanPlayLastCard()) {
                discardedDeckCardList.add(inv.discardLastCard());
            } else {
                Player player = playerList.get(inv.getPlayerId());
                player.chooseCard(new Inventory(inv));
                executePlayerAction(inv, player);
            }
        }
    }

    /**
     * this method allows to associate the wonder boards to the players
     */
    private void assignWBToPlayers() {
        Random r = new Random();
        for (int i = 0; i < playerInventoryList.size(); i++) {
            Player player = playerList.get(i);
            Inventory inv = playerInventoryList.get(i);

            int random = r.nextInt(availableWonderBoardList.size());
            WonderBoard chosenWB = availableWonderBoardList.get(random);
            chosenWB.claimBoard(player, inv);

            int index = availableWonderBoardList.indexOf(chosenWB);
            int otherFaceIndex = chosenWB.getName().endsWith("A") ? index + 1 : index - 1;
            availableWonderBoardList.remove(otherFaceIndex);
            availableWonderBoardList.remove(chosenWB);

            inv.getDetailedResults().setWbName(chosenWB.getName());
            log.chosenWonderBoard(player.getId(), inv.getWonderBoard());
        }
    }

    /**
     * this method execute the action of the player
     *
     * @param inv
     * @param player
     */
    public void executePlayerAction(Inventory inv, Player player) {
        Card chosenCard = player.getChosenCard();
        Action action = player.getAction();

        log.action(player.getId());
        log.playerInformation(playerInventoryList.get(player.getId()));
        switch (action) {
            case BUILDFREE:
                int nbFreeBuildings = inv.getPossibleFreeBuildings();
                if (nbFreeBuildings > 0) {
                    buildCard(inv, chosenCard, player);
                    log.playerBuildCardFreeBuildingEffect(player.getId(), chosenCard);
                    inv.setPossibleFreeBuildings(-1);
                    break;
                }
                // If FreeBuildingsCount < 0, we try to build normally
            case BUILDING:
                Resource[] chosenCardRequiredResources = chosenCard.getRequiredResources();
                if (inv.canBuildCardForFree(chosenCard)) {
                    log.playerCanBuildCardForFree(player.getId(), chosenCard, inv.getPlayedCardNamesByIds(chosenCard.getRequiredBuildingsToBuildForFree()));
                    buildCard(inv, chosenCard, player);
                } else if (inv.payIfPossible(chosenCard.getCost())) {
                    if (inv.canBuild(chosenCardRequiredResources)) {
                        buildCard(inv, chosenCard, player);
                    } else {
                        if (buyResourcesIfPossible(inv, chosenCardRequiredResources, player)) {
                            buildCard(inv, chosenCard, player);
                        } else {
                            initSellCard(inv, chosenCard);
                        }
                    }
                } else {
                    initSellCard(inv, chosenCard);
                }
                break;

            case WONDER:
                Resource[] wonderRequiredResources = inv.getCurrentStepRequiredResources();
                if (inv.canBuildNextStep(inv.getWonderBoard())) {
                    buildWonder(inv, chosenCard, player);
                } else {
                    if (buyResourcesIfPossible(inv, wonderRequiredResources, player)) {
                        buildWonder(inv, chosenCard, player);
                    } else {
                        initSellCard(inv, chosenCard);
                    }
                }
                break;

            default:
                initSellCard(inv, chosenCard);
                break;
        }
        log.playersNewState(inv.getPlayerId());
        log.playerInformation(playerInventoryList.get(player.getId()));
    }


    /**
     * this method allows to check if the player can buy resources
     *
     * @param trueInv
     * @param requiredResources
     * @param player
     * @return
     */
    private boolean buyResourcesIfPossible(Inventory trueInv, Resource[] requiredResources, Player player) {
        boolean canBuy;
        List<Resource> missingResources = trueInv.missingResources(requiredResources);
        log.startTrade();
        log.pricesOfResources(trueInv);
        log.missingResources(missingResources);
        canBuy = getCommerce().buyResources(missingResources, trueInv, playerInventoryList.get(player.getRightNeighborId()), playerInventoryList.get(player.getLeftNeighborId()));
        if (canBuy) {
            log.gotMissingResources();
        } else {
            log.cantBuyMissingResources();
        }
        return canBuy;
    }

    /**
     * this method alowws to build chosen card
     *
     * @param trueInv
     * @param chosenCard
     * @param player
     */
    private void buildCard(Inventory trueInv, Card chosenCard, Player player) {
        if (chosenCard != null) {
            log.playerBuildsCard(trueInv.getPlayerId(), chosenCard);
            trueInv.updateInventory(chosenCard, player, playerInventoryList.get(player.getRightNeighborId()), playerInventoryList.get(player.getLeftNeighborId()));
        }
    }

    /**
     * this method alowws to build a step of the wonder assocaite to the player by using
     * the chosen card
     *
     * @param trueInv
     * @param chosenCard
     * @param player
     */
    private void buildWonder(Inventory trueInv, Card chosenCard, Player player) {
        log.playerBuildsWonderStep(trueInv.getPlayerId());
        WonderBoard wonder = trueInv.getWonderBoard();
        wonder.buyNextStep(player, chosenCard, playerInventoryList.get(player.getRightNeighborId()), playerInventoryList.get(player.getLeftNeighborId()));
    }


    /**
     * this method allows to sell the chosen card
     *
     * @param trueInv
     * @param chosenCard
     */
    private void initSellCard(Inventory trueInv, Card chosenCard) {
        log.playerSellsCard(trueInv.getPlayerId(), chosenCard);
        trueInv.sellCard(chosenCard);
        trueInv.getDetailedResults().incNbSoldCard();
    }


    /**
     * this method allows to resolve the war conflict between a players and their neighbors
     *
     * @param victoryJetonValue
     */
    public void resolveWarConflict(int victoryJetonValue) {
        for (int i = 0; i < playerInventoryList.size(); i++) {
            Player player = playerList.get(i);
            int getRightNeighborId = player.getRightNeighborId();
            int getLeftNeighborId = player.getLeftNeighborId();
            playersManager.fightWithNeighbor(playerInventoryList.get(i), playerInventoryList.get(getRightNeighborId), victoryJetonValue);
            playersManager.fightWithNeighbor(playerInventoryList.get(i), playerInventoryList.get(getLeftNeighborId), victoryJetonValue);
        }
    }

    public List<Card> drawCards(int nbCards) {
        List<Card> playerDeck = new ArrayList<>(currentDeckCardList.subList(0, nbCards));
        this.currentDeckCardList = this.currentDeckCardList.subList(nbCards, currentDeckCardList.size());
        return playerDeck;
    }

    /**
     * this method calculate the scores of the player at the end of the game
     */
    public void scores() {
        log.endOfGame();
        /*The player's score is calculated by doing :
         * In case of equality, the one with more coin wins, if there is still equality, they equally win.
         * */
        for (Inventory inv : playerInventoryList) {
            int scoreBefore = inv.getScore();
            // End Game Effects (guilds buildings)
            Player player = playerList.get(inv.getPlayerId());
            Inventory leftNeighborInv = playerInventoryList.get(player.getLeftNeighborId());
            Inventory rightNeighborInv = playerInventoryList.get(player.getRightNeighborId());
            inv.getEndGameEffects().forEach(effect -> effect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, true));
            int guildScore = inv.getScore() - scoreBefore;
            inv.getDetailedResults().setScoreFromGuilds(guildScore);

            // Sum of Conflict Points
            inv.addScore(inv.getVictoryChipsScore() - inv.getDefeatChipsCount());

            // (Sum coins / 3) -> each 3 coins grant 1 score point
            int scoreEGCoins = inv.getCoins() / 3;
            inv.addScore(scoreEGCoins);
            inv.getDetailedResults().setScoreFromEndGameCoins(scoreEGCoins);

            scoreBefore = inv.getScore();
            //foreach(nb same Scientific²) + min(nb same scientific) * 7
            List<Integer> list = new ArrayList<>();
            list.add(inv.getAvailableSymbols()[Symbol.COMPAS.getIndex()]);
            list.add(inv.getAvailableSymbols()[Symbol.ROUAGE.getIndex()]);
            list.add(inv.getAvailableSymbols()[Symbol.STELE.getIndex()]);

            int nbSameScientific = Collections.min(list);

            list.forEach(integer -> inv.addScore(integer * integer));
            inv.addScore(nbSameScientific * 7);
            int scientificScore = inv.getScore() - scoreBefore;
            inv.getDetailedResults().setScoreFromScientificBuildings(scientificScore);

            log.playerInformation(inv);
        }
    }

    // GETTERS & SETTERS
    public PlayersManager getManager() {
        return playersManager;
    }

    public Trade getCommerce() {
        return commerce;
    }

    public CardManager getCardManager() {
        return cardManager;
    }

    public List<Player> getPlayerList() {
        return this.playerList;
    }

    public List<Card> getCurrentDeckCardList() {
        return this.currentDeckCardList;
    }

    public int getJetonVictoryValue() {
        return jetonVictoryValue;
    }

    public boolean isLeftRotation() {
        return isLeftRotation;
    }

    public List<Inventory> getPlayerInventoryList() {
        return playerInventoryList;
    }

    public List<Card> getDiscardedDeckCardList() {
        return discardedDeckCardList;
    }

    public List<WonderBoard> getAvailableWonderBoardList() {
        return availableWonderBoardList;
    }
}
