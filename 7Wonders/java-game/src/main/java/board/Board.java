package board;

import gameelements.Card;
import gameelements.Inventory;
import gameelements.enums.Resource;
import gameelements.wonders.WonderBoard;

import java.util.ArrayList;
import java.util.Collections;

public class Board {
    public static final int PLAYERS_NUMBER = 3;
    public static final int AGES = 1;

    private final PlayersManager playersManager;
    private final Trade commerce;
    public static final int NOMBRE_CARTES = 7;
    private final ArrayList<Player> playerList;
    private final ArrayList<Inventory> playerInventoryList;
    private final ArrayList<Card> currentDeckCardList;
    private final ArrayList<Card> discardedDeckCardList;
    private int turn;
    private final SoutConsole sout;
    private final CardManager cardManager;

    public Board(int nbPlayers, Boolean boolPrint) {
        commerce = new Trade();
        playersManager = new PlayersManager();
        // Setup Players and their inventories
        playerList = playersManager.generatePlayers(nbPlayers);
        playerInventoryList = playersManager.getPlayerInventoryList();
        cardManager = new CardManager(playerList, playerInventoryList);
        // Setup Decks
        discardedDeckCardList = new ArrayList<>(nbPlayers * 7);
        currentDeckCardList = cardManager.initiateCards(nbPlayers);
        Collections.shuffle(currentDeckCardList);

        //display
        sout = new SoutConsole(false);
    }

    public static void main(String[] args) {
        //Default settings
        int nbPlayers = PLAYERS_NUMBER; //Default = 4
        int nbGame = 1; //Default = 1
        boolean boolPrint = true; //Default = true

        //Maven's arguments
        if (args.length >= 3) {
            nbPlayers = Integer.parseInt(args[0]);
            nbGame = Integer.parseInt(args[1]);
            boolPrint = Boolean.parseBoolean(args[2]);
        }

        Board board = new Board(nbPlayers, boolPrint); // We won't code the 2p version.
        board.play();
        board.scores();
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

    public int getTurn() {
        return this.turn;
    }

    public ArrayList<Inventory> getPlayerInventoryList() {
        return playerInventoryList;
    }

    public void play() {
        playerInventoryList.forEach(this::chooseWonderBoard);
        for (int age = 0; age < AGES; age++) {
            sout.beginingOfAge(age + 1);
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
                    playCard(playerInventoryList.get(i), new Inventory(playerInventoryList.get(i)), playerList.get(i));
                }
                // The players exchange cards according to the Age's sens.
                if (age == 1) {
                    cardManager.rightRotation();
                } else {
                    cardManager.leftRotation();
                }
                this.turn++;
            }
            // At the end of the 6th turn, we discard the remaining card
            // ⚠ The discarded cards must remembered.
            playerInventoryList.forEach(inventory -> discardedDeckCardList.add(inventory.discardLastCard()));
            // Resolving war conflicts
            resolveWarConflict();
            sout.endOfAge(age + 1);
        }
    }

    private void chooseWonderBoard(Inventory inventory) {
        // For now, Player is assigned this Wonder Board by default, later it will be able to choose.
        WonderBoard colossus = playersManager.initiateColossus();
        colossus.claimBoard(inventory);
    }

    protected void playCard(Inventory trueInv, Inventory fakeInv, Player player) {
        boolean result;
        Card choosenCard = player.getChosenCard();

        if (choosenCard != null) {
            sout.action(player.getId());
            sout.informationOfPlayer(playerInventoryList.get(player.getId()));
            ArrayList<Resource> s = getManager().missingResources(fakeInv, choosenCard);
            sout.checkMissingResources(choosenCard);
            if (s != null) {
                sout.missingResources(s);
                result = commerce.saleResources(s, trueInv, playerInventoryList.get(player.getRightNeighborId()), playerInventoryList.get(player.getLeftNeighborId()));
            } else {
                sout.noRequiredResources(choosenCard);
                result = true;
            }
            if (!result) {
                sout.cantBuyMissingResources();
                discardedDeckCardList.add(choosenCard);
                trueInv.sellCard(choosenCard);
            } else {
                sout.gotMissingResources();
                trueInv.updateInventory(choosenCard);
            }
            sout.informationOfPlayer(playerInventoryList.get(player.getId()));
        }
    }

    public PlayersManager getManager() {
        return playersManager;
    }

    public Trade getCommerce() {
        return commerce;
    }

    public void resolveWarConflict() {
        for (int i = 0; i < playerInventoryList.size(); i++) {
            Player player = playerList.get(i);
            int getRightNeighborId = player.getRightNeighborId();
            int getLeftNeighborId = player.getLeftNeighborId();
            playersManager.fightWithNeighbor(playerInventoryList.get(i), playerInventoryList.get(getRightNeighborId), 1);
            playersManager.fightWithNeighbor(playerInventoryList.get(i), playerInventoryList.get(getLeftNeighborId), 1);
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
            sout.informationOfPlayer(p);
        }
    }
}
