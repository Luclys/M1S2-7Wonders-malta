package board;

import gameelements.Card;
import gameelements.Resource;

import java.util.ArrayList;

public class Player {
    private final int id;
    private ArrayList<Card> cardsInHand;
    private ArrayList<Card> playedCards;
    private int score;
    private final int[] availableResources;
    private int rightNeighborId;
    private int leftNeighborId;

    private int conflictPoints;
    private int coins;
    private int priceLeft;
    private int priceRight;

    public Player(int id) {
        this.id = id;
        this.leftNeighborId = 0;
        this.rightNeighborId = 0;
        this.cardsInHand = new ArrayList<>(Board.NOMBRE_CARTES);
        this.playedCards = new ArrayList<>(Board.NOMBRE_CARTES*3);
        this.availableResources = new int[Resource.values().length];
        this.coins = 3;
        this.priceRight = 2;
        this.priceLeft = 2;
        this.conflictPoints = 0;
    }

    public String toString() {
        return "Player " + id + " wins with a score of " + score;
    }

    public Card playCard() {
        /*
         * Choices :
         * Can be affected by the resource choice on a card
         * - Buy a Card : cdt sufficient resources & same card not previously bought, effect : Add resources production
         * - Construct Wonder step : cdt sufficient resources & step not already constructed, effect : grant step effect
         *
         * To do whenever there is no other choice possible
         * - sell Card : unconditionally, effect : grant  3 coins. ⚠ The discarded cards must be remembered.
         * */
        ArrayList<Card> cardsAvailableToPlay = new ArrayList<>(cardsInHand); //player can't play the same card again
        cardsAvailableToPlay.removeIf(card -> card.isBatiment() && playedCards.contains(card));
        Card playedCard = cardsAvailableToPlay.get(0);
       // System.out.print("Player " + id + " plays the " + playedCard.getName() + " card.\t");

        /*
         * To modify when we add the action method
         * Add coins when the player plays the card that gives coins AKA TAVERNE CARD
         * **/

        switch (playedCard.getName()){
            case "TAVERNE":
                addCoins(5);
                break;
            case "COMPTOIR OUEST":
               // //System.out.println("Player "+ id +" can get from his left Neighbor the ressource that he wants for one coin ");
                this.priceLeft = 1;
                break;
            case "COMPTOIR EST":
             //   //System.out.println("Player "+ id +" can get from his right Neighbor the ressource that he wants for one coin ");
                this.priceRight = 1;
                break;
            case "MARCHE":
               // //System.out.println("Player "+ id +" can get from his right Neighbor the ressource that he wants for one coin ");
                this.priceRight = 1;
                this.priceLeft = 1;
                break;
            default:
                break;
        }
        //Updating player's score
        updateScore(playedCard);
        return playedCard;
        // return the played card to the board so taht the board can decied which decession to make(buy ressource or defausse)
    }


    protected Resource[] missingResources(Card c){
        Resource[] missing = new Resource[4];
        int i = 0;
        for(Resource r: c.getRequiredResources()){
            if(availableResources[r.getIndex()]==0){
                missing[i]=r;
                i++;
            }
        }
        return missing;
    }

    protected void updatePlayerWithPlayedCard(Card playedCard){
        //Updating player's available resources
        updateAvailableResources(playedCard);
        playedCards.add(playedCard);
        cardsInHand.remove(0);
    }


    private void updateAvailableResources(Card playedCard) {
        for (Resource r : playedCard.getGainedResources()) {
            availableResources[r.getIndex()]++;
        }
        /*System.out.println("\tA : "
                + availableResources[Resource.BOIS.getIndex()] + " | B : "
                + availableResources[Resource.ARGILE.getIndex()] + " | P : "
                + availableResources[Resource.PIERRE.getIndex()] + " | M : "
                + availableResources[Resource.MINERAI.getIndex()] + " | V : "
                + availableResources[Resource.VERRE.getIndex()] + " | P : "
                + availableResources[Resource.PAPYRUS.getIndex()] + " | T : "
                + availableResources[Resource.TISSU.getIndex()] + " | B : "
                + availableResources[Resource.BOUCLIER.getIndex()] + " | # "
                + getScore()
        );*/
    }

    private void updateScore(Card playedCard) {
        score += playedCard.getGainedVictoryPoints();
    }

    Card discardLastCard() {
        if (cardsInHand.size() == 1) {
            //System.out.println("Player " + id + " discard the " + cards.get(0).getName() + " card.");
            return cardsInHand.remove(0);
        } else {
            throw new Error("There is more than 1 card left.");
        }
    }

      void saleCard(){
        //System.out.println("Player " + id + " discard the " + cards.get(0).getName() + " card.");
        addCoins(3);
        cardsInHand.remove(0);
    }

    //Getters and setters
    public int getId(){
        return id;
    }
    public int getScore() {
        return score;
    }

    public int getRightNeighborId() {
        return this.rightNeighborId;
    }

    public int getConflictPoints() { return this.conflictPoints; }

    public void setRightNeighborId(int id) {
        this.rightNeighborId = id;
    }

    public int getLeftNeighborId() {
        return this.leftNeighborId;
    }

    public void setLeftNeighborId(int id) {
        this.leftNeighborId = id;
    }

    public ArrayList<Card> getCardsInHand() {
        return cardsInHand;
    }

    public int[] getAvailableResources() { return availableResources; }

    public int getBoucliersCount() { return availableResources[Resource.BOUCLIER.getIndex()]; }

    private void updateConflictPoints(int conflictPoints) { this.conflictPoints += conflictPoints; }

    public void fightWithNeighbor(Player neighbor, int conflictPoints) { // conflictPoints depends on Age
        int playerBoucliersCount = this.getBoucliersCount();
        //System.out.println("[RESOLVING WAR CONFLICTS] Player has " + playerBoucliersCount + " boucliers");

        int neighborBoucliersCount = neighbor.getBoucliersCount();
        //System.out.println("[RESOLVING WAR CONFLICTS] Neighbor has " + neighborBoucliersCount + " boucliers");

        if (playerBoucliersCount > neighborBoucliersCount) {
            this.updateConflictPoints(conflictPoints);
            //System.out.println("[RESOLVING WAR CONFLICTS] " + conflictPoints + " conflict points added");
        } else if (playerBoucliersCount < neighborBoucliersCount) {
            this.updateConflictPoints(-1);
            //System.out.println("[RESOLVING WAR CONFLICTS] Defeat jeton (-1 conflict point) added");
        }
        //System.out.println("[RESOLVING WAR CONFLICTS] Total player conflict points: " + this.getConflictPoints());
    }

    public void setCardsInHand(ArrayList<Card> initiateCards) {
        this.cardsInHand = initiateCards;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void setConflictPoints(int conflictPoints) {
        this.conflictPoints = conflictPoints;
    }

    public void removeCoins(int coins){
        setCoins(this.coins-coins);
    }

    public void addCoins(int coins) {
        this.coins += coins;
    }
}
