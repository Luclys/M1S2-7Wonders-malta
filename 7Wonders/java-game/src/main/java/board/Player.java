package board;

import gameelements.Card;
import gameelements.Inventory;
import gameelements.enums.Resource;

import java.util.ArrayList;

public class Player {
    private final int id;
    private int rightNeighborId;
    private int leftNeighborId;

    private Card chosenCard;

    public Player(int id) {
        this.id = id;
    }

    public String toString() {
        return "Player " + id + " wins.";
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


    protected Resource[] missingResources(Inventory inv, Card c) {
        Resource[] missing = new Resource[4];
        int i = 0;
        if (c.getRequiredResources() == null){
            return new Resource[]{null};
        }
        for (Resource r : c.getRequiredResources()) {
            if (inv.getAvailableResources()[r.getIndex()] == 0) {
                missing[i] = r;
                i++;
            }
        }
        return missing;
    }

    /*private void updateAvailableResources(Card playedCard) {
        System.out.println("\tA : "
                + availableResources[Resource.BOIS.getIndex()] + " | B : "
                + availableResources[Resource.ARGILE.getIndex()] + " | P : "
                + availableResources[Resource.PIERRE.getIndex()] + " | M : "
                + availableResources[Resource.MINERAI.getIndex()] + " | V : "
                + availableResources[Resource.VERRE.getIndex()] + " | P : "
                + availableResources[Resource.PAPYRUS.getIndex()] + " | T : "
                + availableResources[Resource.TISSU.getIndex()] + " | B : "
                + availableResources[Resource.BOUCLIER.getIndex()] + " | # "
                + getScore()
        );
    }*/


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
