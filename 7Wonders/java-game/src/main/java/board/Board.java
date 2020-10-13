package board;

import client.Client;
import gameelements.Card;
import gameelements.Inventory;
import gameelements.Player;
import gameelements.ages.AgeI;
import gameelements.ages.AgeII;
import gameelements.ages.AgeIII;
import gameelements.enums.Action;
import gameelements.enums.Resource;
import gameelements.wonders.WonderBoard;

import java.util.ArrayList;
import java.util.Collections;

public class Board {
    public static final int AGES = 3;

    private final PlayersManager playersManager;
    private final Trade commerce;
    public static final int NOMBRE_CARTES = 7;
    private final ArrayList<Player> playerList;
    private final ArrayList<Inventory> playerInventoryList;
    private final ArrayList<Card> discardedDeckCardList;
    private int turn;
    private final SoutConsole sout;
    private final CardManager cardManager;

    private ArrayList<Card> currentDeckCardList;
    private boolean isLeftRotation;
    private int jetonVictoryValue;

    public Board(ArrayList<Player> playerList, Boolean boolPrint) {
        sout = new SoutConsole(boolPrint);
        commerce = new Trade(sout);
        playersManager = new PlayersManager(sout);
        // Setup Players and their inventories
        this.playerList = playersManager.associateNeighbor(playerList);
        playerInventoryList = playersManager.getPlayerInventoryList();
        cardManager = new CardManager(playerList, playerInventoryList);
        // Setup Decks
        discardedDeckCardList = new ArrayList<>(playerList.size() * 7);
        //display
    }

    public void ageSetUp(int age) {
        switch (age) {
            case 1:
                currentDeckCardList = AgeI.initiateCards(playerList.size());
                isLeftRotation = AgeI.isLeftRotation();
                jetonVictoryValue = AgeI.getVictoryJetonValue();
                break;
            case 2:
                currentDeckCardList = AgeII.initiateCards(playerList.size());
                isLeftRotation = AgeII.isLeftRotation();
                jetonVictoryValue = AgeII.getVictoryJetonValue();
                break;
            case 3:
                currentDeckCardList = AgeIII.initiateCards(playerList.size());
                isLeftRotation = AgeIII.isLeftRotation();
                jetonVictoryValue = AgeIII.getVictoryJetonValue();
                break;
            default:
                throw new IllegalStateException("Unexpected age value: " + age);
        }
        Collections.shuffle(currentDeckCardList);
        turn = 0;
    }


    public void play() {
        playerInventoryList.forEach(this::chooseWonderBoard);
        for (int age = 1; age <= AGES; age++) {
            ageSetUp(age);
            sout.beginningOfAge(age);
            // Card dealing
            playerInventoryList.forEach(inventory -> inventory.setCardsInHand(drawCards(NOMBRE_CARTES)));

            for (int currentTurn = 0; currentTurn < NOMBRE_CARTES - 1; currentTurn++) {
                sout.newTurn(currentTurn + 1);
                // Each player plays a card on each turn
                sout.play();
                for (Player p : playerList) {
                    p.chooseCard(new Inventory(playerInventoryList.get(p.getId())));
                    sout.chosenCards(p.getId(), p.getChosenCard());
                }
                for (int i = 0; i < playerList.size(); i++) {
                    executePlayerAction(playerInventoryList.get(i), playerList.get(i));
                }
                // The players exchange cards according to the Age's sens.
                if (isLeftRotation) {
                    cardManager.leftRotation();
                } else {
                    cardManager.rightRotation();
                }
                this.turn++;
                playersManager.updateCoins();
            }
            // At the end of the 6th turn, we discard the remaining card
            // ⚠ The discarded cards must remembered.
            playerInventoryList.forEach(inventory -> discardedDeckCardList.add(inventory.discardLastCard()));
            // Resolving war conflicts
            resolveWarConflict(jetonVictoryValue);
            sout.endOfAge(age);
        }

        scores();
        // We send data to the server
        Inventory winnerInventory = getPlayerInventoryList().get(0);
        for (Inventory inv : getPlayerInventoryList()) {
            if (inv.getScore() > winnerInventory.getScore()) {
                winnerInventory = inv;
            }
        }
        Client client = new Client("http://127.0.0.1:10101");
        //The handshake succeeds in local but is deactivated for it makes
        // the CI wait for connection to an non existing server while testing
        //client.handshake();

    }

    private void chooseWonderBoard(Inventory inventory) {
        // For now, Player is assigned this Wonder Board by default, later it will be able to choose.
        WonderBoard colossus = WonderBoard.initiateColossus();
        colossus.claimBoard(inventory);
    }

    protected void executePlayerAction(Inventory inv, Player player) {
        Card chosenCard = player.getChosenCard();
        Action action = player.getAction();

        switch (action) {
            case BUILDING:
                Resource[] chosenCardRequiredResources = chosenCard.getRequiredResources();
                if (inv.payIfPossible(chosenCard.getCost())) {
                    if (inv.canBuild(chosenCardRequiredResources) ){
                        buildCard(inv, chosenCard, player);
                    }
                    else {
                        if (buyResourcesIfPossible(inv, chosenCardRequiredResources, player)) {
                            buildCard(inv, chosenCard, player);
                        }
                        else {
                            sellCard(inv, chosenCard);
                        }
                    }
                }
                else {
                    sellCard(inv, chosenCard);
                }
                break;

            case WONDER:
                Resource[] wonderRequiredResources = inv.getWonderRequiredResources();
                if (inv.canBuild(wonderRequiredResources)){
                    buildWonder(inv, chosenCard, player);
                }
                else {
                    if (buyResourcesIfPossible(inv, wonderRequiredResources, player)) {
                        buildWonder(inv, chosenCard, player);
                    }
                    else {
                        sellCard(inv, chosenCard);
                    }
                }
                break;

            default:
                sellCard(inv, chosenCard);
                break;
        }
    }

    private boolean buyResourcesIfPossible(Inventory trueInv, Resource[] requiredResources, Player player) {
        boolean canBuy;
        ArrayList<Resource> missingResources = trueInv.missingResources(requiredResources);
        sout.missingResources(missingResources);
        canBuy = commerce.buyResources(missingResources, trueInv, playerInventoryList.get(player.getRightNeighborId()), playerInventoryList.get(player.getLeftNeighborId()));
        if (canBuy) {
            sout.gotMissingResources();
        }
        else {
            sout.cantBuyMissingResources();
        }
        return canBuy;
    }

    private void buildCard(Inventory trueInv, Card chosenCard, Player player) {
        if (chosenCard != null) {
            sout.action(player.getId());
            sout.playerInformation(playerInventoryList.get(player.getId()));
            trueInv.updateInventory(chosenCard);
        }
    }

    private void buildWonder(Inventory trueInv, Card chosenCard, Player player) {
        WonderBoard wonder = trueInv.getWonderBoard();
        wonder.buyNextStep(chosenCard);
    }

    private void sellCard(Inventory trueInv, Card chosenCard) {
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

    ArrayList<Card> drawCards(int nbCards) {
        ArrayList<Card> playerDeck = new ArrayList<>(currentDeckCardList.subList(0, nbCards));
        this.currentDeckCardList.removeAll(playerDeck);
        return playerDeck;
    }

    public void scores() {
        /*The player's score is calculated by doing :
         * Sum of Conflict Points
         * + (Sum coins / 3) -> each 3 coins grant 1 score point
         * + Sum of Wonders Points
         * + Sum of construction Points
         * + foreach(nb same Scientific²) + min(nb same scientific) * 7
         * + trading buildings (specific)
         * + guilds buildings (specific)
         *
         * In case of equality, the one with more coin wins, if there is still equality, they equally win.
         * */
        sout.endOfGame();
        sout.booleanPrint = true;
        sout.FinalResults();
        for (Inventory p : playerInventoryList) {
            sout.playerInformation(p);
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

    public ArrayList<Player> getPlayerList() {
        return this.playerList;
    }

    public ArrayList<Card> getCurrentDeckCardList() {
        return this.currentDeckCardList;
    }

    public int getJetonVictoryValue() {
        return jetonVictoryValue;
    }

    public boolean isLeftRotation() {
        return isLeftRotation;
    }

    public int getTurn() {
        return this.turn;
    }

    public ArrayList<Inventory> getPlayerInventoryList() {
        return playerInventoryList;
    }


}
