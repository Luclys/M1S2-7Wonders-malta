package board;

import gameelements.Card;
import gameelements.Resource;

import java.util.ArrayList;
import java.util.Collections;

public class Board {
    public static final int NOMBRE_CARTES = 7;
    private final ArrayList<Player> playerList;
    private final ArrayList<Card> currentDeckCardList;
    private int tour;

    public Board(int nbPlayers) {
        playerList = new ArrayList<>(nbPlayers);
        for (int i = 0; i < nbPlayers; i++) {
            Player player = new Player(i);

            // To make a tor we bind the first's left to last id
            if (i == 0) {
                player.setLeftNeighbor(nbPlayers-1);
            } else {
                player.setLeftNeighbor(i-1);
            }
            // To make a tor we bind the last's right to first id
            if (i == nbPlayers-1) {
                player.setRightNeighbor(0);
            }else {
                player.setRightNeighbor(i+1);
            }

            playerList.add(player);
        }

        currentDeckCardList = initiateCards(nbPlayers);
        Collections.shuffle(currentDeckCardList);
    }

    public static void main(String[] args) {
        System.out.println("~Starting a new game of 7 Wonders~");
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

    public int getTour() {
        return this.tour;
    }

    public void play() {
        // Card dealing
        playerList.forEach(player -> player.setCards(drawCards(NOMBRE_CARTES)));

        // Each player plays a card on each turn
        for (int i = 0; i < NOMBRE_CARTES - 1; i++) {
            for (Player p : playerList) {
                p.playCard();
            }
            tour++;
        }
        //⚠ The discarded cards must be remembered.
    }

    private ArrayList<Card> drawCards(int nbCards) {
        return new ArrayList<>(currentDeckCardList.subList(0, nbCards));
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

        for (Player p : playerList) {
            System.out.println(p);
        }
    }

    public ArrayList<Card> initiateCards(int nbPlayers) {
        /*
         * Generate three different decks for the three ages according to the nbPlayers.
         * shuffle guild card and keep nbPlayers + 2. Add to the ThirdAge deck.
         * */
        ArrayList<Card> res = new ArrayList<>(NOMBRE_CARTES);
        for (int i = 0; i < NOMBRE_CARTES; i++) {
            res.add(new Card("BATIMENT", new Resource[] {Resource.BOIS, Resource.ARGILE}));
        }
        return res;
    }

}
