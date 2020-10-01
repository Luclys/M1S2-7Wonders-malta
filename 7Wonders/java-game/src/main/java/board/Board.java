package board;

import gameelements.Card;
import gameelements.Inventory;
import gameelements.enums.Resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Board {
    private final Action action;
    private final Trade commerce;
    public static final int NOMBRE_CARTES = 7;
    private final ArrayList<Player> playerList;
    private final ArrayList<Inventory> playerInventoryList;
    private final ArrayList<Card> currentDeckCardList;
    private final ArrayList<Card> discardedDeckCardList;
    private int turn;
    private String outputText;

    public Board(int nbPlayers) {
        commerce = new Trade();
        action = new Action();

        // Setup Players and their inventories
        playerList = action.generatePlayers(nbPlayers);
        playerInventoryList = action.getPlayerInventoryList();

        // Setup Decks
        discardedDeckCardList = new ArrayList<>(nbPlayers * 7);
        currentDeckCardList = action.initiateCards(nbPlayers);
        Collections.shuffle(currentDeckCardList);

        outputText = "";
    }

    public static void main(String[] args) {
        //Default settings
        int nbPlayers = 3; //Default = 4
        int nbGame = 1; //Default = 1
        boolean boolPrint = true; //Default = true

        //Maven's arguments
        if (args.length >= 3) {
            nbPlayers = Integer.parseInt(args[0]);
            nbGame = Integer.parseInt(args[1]);
            boolPrint = Boolean.parseBoolean(args[2]);
        }

        Board board = new Board(nbPlayers); // We won't code the 2p version.
        board.play();
        board.scores();
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
        for (int age = 0; age < 1; age++) {
            // Card dealing
            playerInventoryList.forEach(inventory -> inventory.setCards(drawCards(NOMBRE_CARTES)));

            for (int currentTurn = 0; currentTurn < NOMBRE_CARTES - 1; currentTurn++) {
                // Each player plays a card on each turn
                for (Player p : playerList) {
                    Inventory trueInv = playerInventoryList.get(p.getId());

                    outputText += "\nCoins of the player " + trueInv.getCoins() ;
                    outputText += "\nResources of the player " + Arrays.toString(trueInv.getAvailableResources())+"\n";

                    p.ChooseCard(new Inventory(playerInventoryList.get(p.getId())));
                }

                for (int i = 0; i < playerList.size(); i++) {
                    playCard(playerInventoryList.get(i), new Inventory(playerInventoryList.get(i)), playerList.get(i));
                }

                // The players exchange cards according to the Age's sens.
                /*if(getTurn()<6){
                    leftRotation();
                }*/
                this.turn++;
            }
            display();
            // At the end of the 6th turn, we discard the remaining card
            // ⚠ The discarded cards must remembered.
            playerInventoryList.forEach(inventory -> discardedDeckCardList.add(inventory.discardLastCard()));
            // Resolving war conflicts
            resolveWarConflict();
        }
    }

    protected void playCard(Inventory trueInv, Inventory fakeInv, Player player) {
        boolean result;
        Card choosenCard = player.getChoosenCard();

        if (choosenCard != null) {
            outputText += "Card that the player wants to play : " + choosenCard.getName() + "\n \t resource required to play this card :" + Arrays.toString(choosenCard.getRequiredResources())+"\n";
            outputText += "\n** verify if the player has the required resources: ";

            Resource[] s = player.missingResources(fakeInv, choosenCard);
            if (s[0] != null) {
                outputText += "\n*** Missing resource to play the card " + Arrays.toString(s);
                outputText += "\n**** Verify if the player can buy missing resources ";

                result = commerce.saleResources(s, trueInv, playerInventoryList.get(player.getRightNeighborId()), playerInventoryList.get(player.getLeftNeighborId()));
            } else {
                outputText += "\n*** No resource is required ";
                result = true;
            }

            if (!result) {
                outputText += "\nThe player can't use the card so card"+ choosenCard.getName() +" is discord";

                discardedDeckCardList.add(choosenCard);
                trueInv.sellCard(choosenCard);
            } else {
                outputText += "\nThe player got the resources of the played card";
                trueInv.updateInventory(choosenCard);
            }
            outputText += "\nCoins of the player : "+player.getId() + " : " + trueInv.getCoins() + "\n Resources of the player " + Arrays.toString(trueInv.getAvailableResources())+"\n";
        }

    }

    public Action getAction() {
        return action;
    }

    public Trade getCommerce() {
        return commerce;
    }

    public void resolveWarConflict() {
        for (int i = 0; i < playerInventoryList.size(); i++) {
            Player player = playerList.get(i);
            int getRightNeighborId = player.getRightNeighborId();
            int getLeftNeighborId = player.getLeftNeighborId();
            action.fightWithNeighbor(playerInventoryList.get(i), playerInventoryList.get(getRightNeighborId), 1);
            action.fightWithNeighbor(playerInventoryList.get(i), playerInventoryList.get(getLeftNeighborId), 1);
        }
    }


    ArrayList<Card> drawCards(int nbCards) {
        ArrayList<Card> playerDeck = new ArrayList<>(currentDeckCardList.subList(0, nbCards));
        outputText += "\nNB CARTES : " + currentDeckCardList.size();
        this.currentDeckCardList.removeAll(playerDeck);
        outputText += "\nNB CARTES APRES : " + currentDeckCardList.size();
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
        outputText += "End of the game";
        for (Player p : playerList) {
            outputText += p;
        }
    }

    public void display() {
        System.out.println(outputText);
    }
}
