package board;

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
import java.util.stream.Collectors;

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
    private int turn;
    private List<Card> currentDeckCardList;
    private boolean isLeftRotation;
    private int jetonVictoryValue;

    public Board(List<Player> playerList, Boolean boolPrint) {
        log = new GameLogger(boolPrint);
        commerce = new Trade(log);
        playersManager = new PlayersManager(log);
        // Setup Players and their inventories
        this.playerList = (ArrayList<Player>) (playersManager.associateNeighbor(playerList));
        playerInventoryList = playersManager.getPlayerInventoryList();
        cardManager = new CardManager(playerList, playerInventoryList);
        // Setup Decks
        discardedDeckCardList = new ArrayList<>(playerList.size() * 7);
        //display
    }

    public void ageSetUp(int numAge) {
        Age age = null;
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

    public void play(int nbPlay) {
        log.beginningOfPlay(nbPlay);
        playerInventoryList.forEach(inventory -> chooseWonderBoard(playerList.get(inventory.getPlayerId()), inventory));
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
                for (int i = 0; i < playerList.size(); i++) {
                    executePlayerAction(playerInventoryList.get(i), playerList.get(i));
                }

                playersManager.updateCoins();
                playersManager.freeBuildFromDiscarded(discardedDeckCardList);

                cardManager.playersCardsRotation(isLeftRotation);
            }
            handleLastTurnCard();
            resolveWarConflict(jetonVictoryValue);
            log.endOfAge(age);
        }

        scores();
        denseRanking(playerInventoryList);
        log.finalGameRanking(playerInventoryList);
        // We send data to the server
        sendWinner(playerInventoryList);
    }

    void handleLastTurnCard() {
        // At the end of the 6th turn, we discard the remaining card
        // ⚠ The discarded cards must remembered.
        for (Inventory inv : playerInventoryList) {
            if (!inv.isCanPlayLastCard()) {
                System.out.println(inv.getCardsInHand());
                discardedDeckCardList.add(inv.discardLastCard());
            } else {
                Player player = playerList.get(inv.getPlayerId());
                player.chooseCard(new Inventory(inv));
                executePlayerAction(inv, player);
            }
        }
    }

    private void sendWinner(List<Inventory> playerInventoryList) {
        Inventory winnerInventory = getPlayerInventoryList().get(0);
        for (Inventory inv : getPlayerInventoryList()) {
            if (inv.getScore() > winnerInventory.getScore()) {
                winnerInventory = inv;
            }
        }
        SevenWondersLauncher.client.sendWinner(winnerInventory);
    }

    private void chooseWonderBoard(Player player, Inventory inventory) {
        // For now, Player is assigned this Wonder Board by default, later it will be able to choose.
        WonderBoard colossus = WonderBoard.initiateColossus();
        log.chooseWonderBoard(player.getId(), colossus);
        colossus.claimBoard(player, inventory);
    }

    protected void executePlayerAction(Inventory inv, Player player) {
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
                // If FreeBuildingsCount == 0, we try to build normally
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
                            sellCard(inv, chosenCard);
                        }
                    }
                } else {
                    sellCard(inv, chosenCard);
                }
                break;

            case WONDER:
                Resource[] wonderRequiredResources = inv.getWonderRequiredResources();
                if (inv.canBuild(wonderRequiredResources)) {
                    buildWonder(inv, chosenCard, player);
                } else {
                    if (buyResourcesIfPossible(inv, wonderRequiredResources, player)) {
                        buildWonder(inv, chosenCard, player);
                    } else {
                        sellCard(inv, chosenCard);
                    }
                }
                break;

            default:
                sellCard(inv, chosenCard);
                break;
        }
        log.playersNewState(inv.getPlayerId());
        log.playerInformation(playerInventoryList.get(player.getId()));
    }

    private boolean buyResourcesIfPossible(Inventory trueInv, Resource[] requiredResources, Player player) {
        boolean canBuy;
        List<Resource> missingResources = trueInv.missingResources(requiredResources);
        log.startTrade();
        log.pricesOfResources(trueInv);
        log.missingResources(missingResources);
        canBuy = commerce.buyResources(missingResources, trueInv, playerInventoryList.get(player.getRightNeighborId()), playerInventoryList.get(player.getLeftNeighborId()));
        if (canBuy) {
            log.gotMissingResources();
        } else {
            log.cantBuyMissingResources();
        }
        return canBuy;
    }

    private void buildCard(Inventory trueInv, Card chosenCard, Player player) {
        if (chosenCard != null) {
            log.playerBuildsCard(trueInv.getPlayerId(), chosenCard);
            trueInv.updateInventory(chosenCard, player, playerInventoryList.get(player.getRightNeighborId()), playerInventoryList.get(player.getLeftNeighborId()));
        }
    }

    private void buildWonder(Inventory trueInv, Card chosenCard, Player player) {
        log.playerBuildsWonderStep(trueInv.getPlayerId());
        WonderBoard wonder = trueInv.getWonderBoard();
        wonder.buyNextStep(player, chosenCard, playerInventoryList.get(player.getRightNeighborId()), playerInventoryList.get(player.getLeftNeighborId()));
    }

    private void sellCard(Inventory trueInv, Card chosenCard) {
        log.playerSellsCard(trueInv.getPlayerId(), chosenCard);
        trueInv.sellCard(chosenCard);
    }

    public void resolveWarConflict(int victoryJetonValue) {
        for (int i = 0; i < playerInventoryList.size(); i++) {
            Player player = playerList.get(i);
            int getRightNeighborId = player.getRightNeighborId();
            int getLeftNeighborId = player.getLeftNeighborId();
            playersManager.fightWithNeighbor(playerInventoryList.get(i), playerInventoryList.get(getRightNeighborId), victoryJetonValue);
            playersManager.fightWithNeighbor(playerInventoryList.get(i), playerInventoryList.get(getLeftNeighborId), victoryJetonValue);
        }
    }

    List<Card> drawCards(int nbCards) {
        List<Card> playerDeck = new ArrayList<>(currentDeckCardList.subList(0, nbCards));
        this.currentDeckCardList = this.currentDeckCardList.subList(nbCards, currentDeckCardList.size());
        return playerDeck;
    }

    public void scores() {
        log.endOfGame();
        /*The player's score is calculated by doing :
         * In case of equality, the one with more coin wins, if there is still equality, they equally win.
         * */
        for (Inventory inv : playerInventoryList) {
            // End Game Effects (guilds buildings)
            Player player = playerList.get(inv.getPlayerId());
            Inventory leftNeighborInv = playerInventoryList.get(player.getLeftNeighborId());
            Inventory rightNeighborInv = playerInventoryList.get(player.getRightNeighborId());
            inv.getEndGameEffects().forEach(effect -> effect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, true));

            // Sum of Conflict Points
            inv.addScore(inv.getVictoryChipsScore() - inv.getDefeatChipsCount());

            // (Sum coins / 3) -> each 3 coins grant 1 score point
            inv.addScore(inv.getCoins() / 3);

            //foreach(nb same Scientific²) + min(nb same scientific) * 7
            List<Integer> list = new ArrayList<>();
            list.add(inv.getAvailableSymbols()[Symbol.COMPAS.getIndex()]);
            list.add(inv.getAvailableSymbols()[Symbol.ROUAGE.getIndex()]);
            list.add(inv.getAvailableSymbols()[Symbol.STELE.getIndex()]);

            int nbSameScientific = Collections.min(list);

            list.forEach(integer -> inv.addScore(integer * integer));
            inv.addScore(nbSameScientific * 7);

            log.playerInformation(inv);
        }
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
}
