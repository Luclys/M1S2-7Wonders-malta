package board;

import gameelements.Card;
import gameelements.Resource;

import java.util.ArrayList;

public class Player {
    private final int id;
    private ArrayList<Card> cards;
    private int score;
    private int[] availableResources;
    private int rightNeighborId;
    private int leftNeighborId;

    public Player(int id) {
        this.id = id;
        this.leftNeighborId = 0;
        this.rightNeighborId = 0;
        this.cards = new ArrayList<>(7);
        this.availableResources = new int[12];
    }

    public String toString() {
        return "Player " + id + " wins with a score of " + score;
    }

    public void playCard() {
        /*
         * Choices :
         * Can be affected by the resource choice on a card
         * - Buy a Card : cdt sufficient resources & same card not previously bought, effect : Add resources production
         * - Construct Wonder step : cdt sufficient resources & step not already constructed, effect : grant step effect
         *
         * To do whenever there is no other choice possible
         * - sell Card : unconditionally, effect : grant  3 coins. ⚠ The discarded cards must be remembered.
         * */
        Card playedCard = cards.get(0);
        System.out.print("Player " + id + " plays the " + playedCard.getName() + " card.\t");
        //Updating player's score
        score += playedCard.getVictoryPoints();
        //Updating player's available resources
        updateAvailableResources(playedCard);
        //Card is removed from hand
        cards.remove(0);
    }

    private void updateAvailableResources(Card playedCard) {
        for (Resource r : playedCard.getGainedResources()) {
            availableResources[r.getIndex()]++;
        }
        System.out.println("\tHe now has "
                + availableResources[Resource.BOIS.getIndex()] + " bois and "
                + availableResources[Resource.ARGILE.getIndex()] + " argile and "
                + availableResources[Resource.PIERRE.getIndex()] + " pierre and "
                + availableResources[Resource.MINERAI.getIndex()] + " minerai and"
                + availableResources[Resource.VERRE.getIndex()] + " verre and "
                + availableResources[Resource.PAPYRUS.getIndex()] + " papyrus and "
                + availableResources[Resource.TISSU.getIndex()] + " tissu and "
                + availableResources[Resource.BOUCLIER.getIndex()] + " bouclier and "
                + availableResources[Resource.POINTSCORE.getIndex()] + " point score. "
        );
    }

    Card discardLastCard() {
        if (cards.size() == 1) {
            System.out.println("Player " + id + " discard the " + cards.get(0).getName() + " card.");
            return cards.remove(0);
        } else {
            throw new Error("There is more than 1 card left.");
        }
    }

    //Getters and setters

    public int getScore() {
        return score;
    }

    public int getRightNeighborId() {
        return this.rightNeighborId;
    }

    public void setRightNeighborId(int id) {
        this.rightNeighborId = id;
    }

    public int getLeftNeighborId() {
        return this.leftNeighborId;
    }

    public void setLeftNeighborId(int id) {
        this.leftNeighborId = id;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> initiateCards) {
        this.cards = initiateCards;
    }
}
