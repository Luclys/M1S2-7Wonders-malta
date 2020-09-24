package board;

import gameelements.Card;

import java.util.ArrayList;

public class Player {
    private final int playerId;
    private ArrayList<Card> cards;
    private int score;

    public Player(int id) {
        playerId = id;
        cards = new ArrayList<>(7);
    }

    //Player
    public void playCard() {
        System.out.println("Player " + playerId + " plays a card");
        //Updating player's score
        score += cards.get(0).getVictoryPoints();
        //Card is removed from hand
        cards.remove(0);
    }

    public String toString() {
        return "Player " + playerId + " wins with a score of " + score;
    }

    public void setCards(ArrayList<Card> initiateCards) {
        this.cards = initiateCards;
    }

    public int getScore() {
        return score;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }
}
