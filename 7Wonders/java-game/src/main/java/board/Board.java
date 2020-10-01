package board;

import gameelements.Card;
import gameelements.enums.Resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Board {
    public static final int NOMBRE_CARTES = 7;
    private final ArrayList<Player> playerList;
    private final ArrayList<Card> currentDeckCardList;
    private ArrayList<Card> discardedCardList;
    private int turn;
    private final Action action;
    private final Trade commerce;
    private String outputText;

    public Board(int nbPlayers) {
        action = new Action();
        playerList = action.generetePlayers(nbPlayers);
        discardedCardList = new ArrayList<>(nbPlayers * 3);
        currentDeckCardList = action.initiateCards(nbPlayers);
        Collections.shuffle(currentDeckCardList);
        commerce = new Trade();
        outputText ="";
    }

    public static void main(String[] args) {
        System.out.println("~Starting a new game of 7 Wonders~\n");
        Board board = new Board(3); // We won't code the 2p version.
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
            playerList.forEach(player -> player.setCards(drawCards(NOMBRE_CARTES)));
            Card playedCard;
            // Each player plays a card on each turn
            for (int currentTurn = 0; currentTurn < NOMBRE_CARTES - 1; currentTurn++) {
                for (Player p : playerList) {
                    outputText += "\nCoins of the player " + p.getCoins() ;
                    outputText += "\nResources of the player " + Arrays.toString(p.getAvailableResources())+"\n";
                    playedCard = p.playCard();
                    if(playedCard != null){
                        outputText += "Card that the player wants to play : " + playedCard.getName() + "\n \t resource required to play this card :" + Arrays.toString(playedCard.getRequiredResources())+"\n";
                        outputText += "\n** verify if the player has the required resources: ";
                        Resource[] s = p.missingResources(playedCard);
                        if (s[0] != null) {
                            outputText += "\n*** Missing resource to play the card " + Arrays.toString(s);
                            outputText += "\n**** Verify if the player can buy missing resources ";

                            Player rightNeighbor = playerList.get(p.getRightNeighborId());
                            Player leftNeighbor = playerList.get(p.getLeftNeighborId());
                            result = commerce.saleResources(s,p,rightNeighbor, leftNeighbor);
                        } else {
                            outputText += "\n*** No resource is required ";
                            result = true;
                        }
                        if (!result) {
                            outputText += "\nThe player can't use the card so card"+ playedCard.getName() +" is discord";
                            discardedCardList.add(playedCard);
                            p.saleCard();
                        } else {
                            outputText += "\nThe player got the resources of the played card";
                            p.updatePlayer(playedCard);
                        }
                        outputText += "\nCoins of the player " + p.getCoins() + "\n Resources of the player " + Arrays.toString(p.getAvailableResources())+"\n";
                        outputText += "\n********************************************************************";
                    }
                }
                outputText += "\n############################################################################";
                // The players exchange cards according to the Age's sens.
                /*if(getTurn()<6){
                    leftRotation();
                }*/
                this.turn++;
            }
            display();
            // At the end of the 6th turn, we discard the remaining card
            // ⚠ The discarded cards must remembered.
            playerList.forEach(player -> discardedCardList.add(player.discardLastCard()));
            // Resolving war conflicts
            playerList.forEach(this::resolveWarConflict);
        }
    }

    public Action getAction() {
        return action;
    }

    public Trade getCommerce() {
        return commerce;
    }

    private void resolveWarConflict(Player player) {
        player.fightWithNeighbor(playerList.get(player.getRightNeighborId()), 1);
        player.fightWithNeighbor(playerList.get(player.getLeftNeighborId()), 1);
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

    public void display(){
        System.out.println(outputText);
    }
}
