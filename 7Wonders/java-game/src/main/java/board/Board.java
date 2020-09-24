package board;

import gameelements.Card;

import java.util.ArrayList;


public class Board {
    private final ArrayList<Player> players;


    public static void main(String[] args) {
        System.out.println("~Starting a new game of 7 Wonders~");
        Board board = new Board();
        board.play();
        board.scores();
    }

    public Board() {
        players = new ArrayList<>(1);
        players.add(new Player(5));
    }

    public void play() {
        //Card dealing
        players.forEach(player -> player.setCards(initiateCards()));
        //Each player plays a card on each turn
        for (int i = 0; i < 7; i++) {
            for (Player p : players) {
                p.playCard();
            }
        }
    }

    public void scores() {
        for (Player p : players) {
            System.out.println(p);
        }
    }

    public ArrayList<Card> initiateCards() {
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
