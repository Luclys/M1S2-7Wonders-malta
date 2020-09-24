package board;

import gameelements.Card;

import java.util.ArrayList;


public class Board {
    private final ArrayList<Player> players;


    public static void main(String[] args) {
        System.out.println("Starting 7 wonders");
        Board board = new Board();
        board.play();
        board.scores();
    }

    public Board() {
        players = new ArrayList<>(1);
        players.add(new Player(5, 3));
        // Soustract nb player * 5 to the total coin nb
    }

    public void play() {
        players.forEach(player -> player.setCard(initiateCards()));
        for (int i = 0; i < 7; i++) {
            for (Player j : players) {
                j.playCard();
            }
        }
    }

    public void scores() {
        for (Player j : players) {
            System.out.println(j);
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
