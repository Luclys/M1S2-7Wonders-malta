package board;

import gameelements.Card;

import java.util.ArrayList;

public class Player {
    private final int playerId;
    private ArrayList<Card> cards;
    private int score;
    private int rightNeighborId;
    private int leftNeighborId;

    public Player(int id) {
        playerId = id;
        this.leftNeighborId = 0;
        this.rightNeighborId = 0;
        cards = new ArrayList<>(7);
    }

    //Player
    public void playCard() {
        /*
         * Choices :
         * Can be affected by the ressource choice on a card
         * - Buy a Card : cdt sufficient ressources & same card not previously bought, effect : Add ressources production
         * - Construct Wonder step : cdt sufficient ressources & step not already constructed, effect : grant step effect
         *
         * To do whenever there is no other choice possible
         * - sell Card : unconditionally, effect : grant  3 coins. ⚠ The discarded cards must be remembered.
         * */
        System.out.println("Player " + playerId + " plays a card");
        //Updating player's score
        score += cards.get(0).getVictoryPoints();
        //Card is removed from hand
        cards.remove(0);
    }

    public String toString() {
        return "Player " + playerId + " wins with a score of " + score;
    }

    public void setleftNeighborId (int id) {
        this.leftNeighborId = id;
    }
    public void setrightNeighborId (int id) {
        this.rightNeighborId = id;
    }

    public int getScore() {
        return score;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> initiateCards) {
        this.cards = initiateCards;
    }

    public Card discardLastCard() {
        if (cards.size() == 1) {
            return cards.remove(0);
        }
        else {
            throw new Error("There is more than 1 card left.");
        }
    }

    public int getRightNeighborId() {
        return this.rightNeighborId;
    }

    public int getLeftNeighborId() {
        return this.leftNeighborId;
    }
}
