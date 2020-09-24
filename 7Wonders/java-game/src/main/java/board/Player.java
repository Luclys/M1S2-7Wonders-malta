package board;

import gameelements.Card;

import java.util.ArrayList;

public class Player {
    private final int playerId;
    private int nbCoin;
    private ArrayList<Card> cardArrayList;
    private int score;

    public Player(int id, int nbCoin) {
        playerId = id;
        this.nbCoin = nbCoin;
        cardArrayList = new ArrayList<>(7);
    }

    public void playCard() {
        System.out.println("I play my card");
        score += cardArrayList.get(0).getPoint();
        cardArrayList.remove(0);
    }

    public String toString() {
        return "id player :" + playerId + " score :" + score;
    }

    public void setCard(ArrayList<Card> initiateCards) {
        this.cardArrayList = initiateCards;
    }
}
