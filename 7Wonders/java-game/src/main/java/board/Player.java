package board;

import gameelements.Card;
import gameelements.Inventory;
import gameelements.enums.Resource;

import java.util.ArrayList;

public class Player {
    private final int id;
    private int rightNeighborId;
    private int leftNeighborId;

    private Card choosenCard;

    public Player(int id) {
        this.id = id;
    }

    public String toString() {
        return "Player " + id ;
    }

    public Card chooseCard(Inventory inv) {
        /*
         * Choices :
         * Can be affected by the resource choice on a card
         * - Buy a Card : cdt sufficient resources & same card not previously bought, effect : Add resources production
         * - Construct Wonder step : cdt sufficient resources & step not already constructed, effect : grant step effect
         *
         * To do whenever there is no other choice possible
         * - sell Card : unconditionally, effect : grant  3 coins. ⚠ The discarded cards must be remembered.
         * */
        ArrayList<Card> cards = inv.getCards();
        chosenCard = cards.get(0);

        return chosenCard;
        // return the played card to the board so that the board can decide which decession to make(buy ressource or discard)
    }

    protected ArrayList<Resource> missingResources(Inventory inv, Card c) {
        ArrayList<Resource> missing = new ArrayList<Resource>();
        if (c.getRequiredResources() == null){
            return null;
        }
        for (Resource r : c.getRequiredResources()) {
            if (inv.getAvailableResources()[r.getIndex()] == 0) {
                missing.add(r);
            }
        }
        return missing;
    }

    //Getters and setters
    public int getId() {
        return id;
    }


    public Card getChosenCard() {
        return this.chosenCard;
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
}
