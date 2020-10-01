package board;

import gameelements.Card;
import gameelements.Inventory;
import gameelements.enums.Resource;

import java.util.ArrayList;
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

    public void play() {
        boolean result;
        for (int age = 0; age < 1; age++) {
            // Card dealing
            playerInventoryList.forEach(inventory -> inventory.setCards(drawCards(NOMBRE_CARTES)));
            Card playedCard;

            for (int currentTurn = 0; currentTurn < NOMBRE_CARTES - 1; currentTurn++) {
                // Each player plays a card on each turn
                for (Player p : playerList) {
                    Inventory trueInv = playerInventoryList.get(p.getId());
                    Inventory fakeInv = new Inventory(trueInv);
                    playedCard = p.playCard(fakeInv);

                    if (playedCard != null) {
                        Resource[] s = p.missingResources(fakeInv, playedCard);
                        if (s[0] != null) {
                            result = commerce.saleResources(s, trueInv, playerInventoryList.get(p.getRightNeighborId()), playerInventoryList.get(p.getLeftNeighborId()));
                        } else {
                            result = true;
                        }
                        if (!result) {
                            discardedDeckCardList.add(playedCard);
                            trueInv.sellCard(playedCard);
                        } else {
                            trueInv.updateInventory(playedCard);
                        }
                    }
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

    public Action getAction() {
        return action;
    }

    public Trade getCommerce() {
        return commerce;
    }

    private void resolveWarConflict() {
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
