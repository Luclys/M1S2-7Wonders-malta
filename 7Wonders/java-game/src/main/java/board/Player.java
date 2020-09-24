package board;

import gameelements.Card;

import java.util.ArrayList;

public class Player {
    private final int playerId;
    private ArrayList<Card> cardArrayList;
    private int score;

    public Player(int id) {
        playerId = id;
        cardArrayList = new ArrayList<>(7);
        for(int i = 0; i < 7; i++) {
            cardArrayList.add(new Card());
        }
    }

    //Player
    public void playCard() {
        System.out.println("Player " + playerId + " plays a card");
        //Updating player's score
        score += cardArrayList.get(0).getVictoryPoints();
        //Card is removed from hand
        cardArrayList.remove(0);
    }

    public String toString() {
        return "Player " + playerId + " wins with a score of " + score;
    }

    public void setCards(ArrayList<Card> initiateCards) {
        this.cardArrayList = initiateCards;
    }

    public int getScore() {
        return score;
    }

    public ArrayList<Card> getCardArrayList() {
        return cardArrayList;
    }
}
