package board;

import gameelements.Card;
import gameelements.Resource;

import java.util.ArrayList;
import java.util.Collections;

public class Board {
    public static final int NOMBRE_CARTES = 7;
    private final ArrayList<Player> playerList;
    private final ArrayList<Card> currentDeckCardList;
    private ArrayList<Card> discardedCardList;
    private int turn;

    public Board(int nbPlayers) {
        playerList = new ArrayList<>(nbPlayers);
        for (int i = 0; i < nbPlayers; i++) {
            Player player = new Player(i);

            // To make a tor we bind the first's left to last id
            if (i == 0) {
                player.setLeftNeighborId(nbPlayers-1);
            } else {
                player.setLeftNeighborId(i-1);
            }
            // To make a tor we bind the last's right to first id
            if (i == nbPlayers-1) {
                player.setRightNeighborId(0);
            }else {
                player.setRightNeighborId(i+1);
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

    public int getTurn() {
        return this.turn;
    }

    public void play() {
        for (int age = 0; age < 1; age++) {

            // Card dealing
            playerList.forEach(player -> player.setCards(drawCards(NOMBRE_CARTES)));

            // Each player plays a card on each turn
            for (int currentTurn = 0; currentTurn < NOMBRE_CARTES - 1; currentTurn++) {
                for (Player p : playerList) {
                    p.playCard();
                }
                // The players exchange cards according to the Age's sens.

                this.turn++;
            }
            // At the end of the 6th turn, we discard the remaining card
            // ⚠ The discarded cards must be remembered.
            playerList.forEach(player -> discardedCardList.add(player.discardLastCard()));
            // Resolving war conflicts
            playerList.forEach(this::resolveWarConflict);
        }
    }

    private void resolveWarConflict(Player player) {
        Player rightNeighbor = playerList.get(player.getRightNeighborId()) ;
        Player leftNeighbor = playerList.get(player.getLeftNeighborId()) ;
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

        for (Player p : playerList) {
            System.out.println(p);
        }
    }

    ArrayList<Card> initiateCards(int nbPlayers) {
        this.discardedCardList = new ArrayList<>();
        /*
         * Generate three different decks for the three ages according to the nbPlayers.
         * shuffle guild card and keep nbPlayers + 2. Add to the ThirdAge deck.
         * */
        ArrayList<Card> res = new ArrayList<>(NOMBRE_CARTES * nbPlayers);

        for (int i = 0; i < nbPlayers; i++) {
            res.add(new Card("Wood"+i, new Resource[] {Resource.BOIS}));
            res.add(new Card("Clay"+i, new Resource[] {Resource.ARGILE}));
            res.add(new Card("stone"+i, new Resource[] {Resource.PIERRE}));
            res.add(new Card("ore"+i, new Resource[] {Resource.MINERAI}));
            res.add(new Card("ClayWood"+i, new Resource[] {Resource.BOIS, Resource.ARGILE}));
            res.add(new Card("ClayStone"+i, new Resource[] {Resource.ARGILE, Resource.PIERRE}));
            res.add(new Card("StoneOre"+i, new Resource[] {Resource.PIERRE, Resource.MINERAI}));
        }
        return res;
    }

}
