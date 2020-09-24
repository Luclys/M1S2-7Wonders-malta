package board;

import gameelements.Card;

import java.util.ArrayList;
import java.util.Collections;


public class Board {
    private final ArrayList<Player> playerList;
    private final ArrayList<Card> deckCardList;
    private int tour;

    public ArrayList<Player> getPlayerList() {
        return this.playerList;
    }

    public ArrayList<Card> getDeckCardList() {
        return this.deckCardList;
    }

    public int getTour() {
        return this.tour;
    }

    public Board(int nbPlayers) {
        playerList = new ArrayList<>(nbPlayers);
        for (int i = 0; i < nbPlayers; i++) {
            playerList.add(new Player(i));
        }

        deckCardList = initiateCards(nbPlayers);
        Collections.shuffle(deckCardList);
    }

    public static void main(String[] args) {
        System.out.println("~Starting a new game of 7 Wonders~");
        Board board = new Board(1);
        board.play();
        board.scores();
    }

    public void play() {
        //Card dealing
        playerList.forEach(player -> player.setCards(drawCards(7)));
        //Each player plays a card on each turn
        for (int i = 0; i < 7; i++) {
            for (Player p : playerList) {
                p.playCard();
            }
            tour++;
        }
    }

    private ArrayList<Card> drawCards(int nbCards) {
        return new ArrayList<>(deckCardList.subList(0, nbCards));
    }

    public void scores() {
        for (Player p : playerList) {
            System.out.println(p);
        }
    }

    public ArrayList<Card> initiateCards(int nbPlayers) {
        ArrayList<Card> res = new ArrayList<>();
        res.add(new Card());
        res.add(new Card());
        res.add(new Card());
        res.add(new Card());
        res.add(new Card());
        res.add(new Card());
        res.add(new Card());
        return res;
    }

}
