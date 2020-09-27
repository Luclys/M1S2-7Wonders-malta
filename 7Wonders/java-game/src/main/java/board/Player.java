package board;

import gameelements.Card;
import gameelements.Resource;

import java.util.ArrayList;

public class Player {
    private final int id;
    private ArrayList<Card> cards;
    private int score;
    private final int[] availableResources;
    private int rightNeighborId;
    private int leftNeighborId;

    private int victoryPoints;
    private int coins;
    private int priceLeft;
    private int priceRight;

    public Player(int id) {
        this.id = id;
        this.leftNeighborId = 0;
        this.rightNeighborId = 0;
        this.cards = new ArrayList<>(Board.NOMBRE_CARTES);
        this.availableResources = new int[Resource.values().length];
        this.coins = 0;
        this.priceRight = 2;
        this.priceLeft = 2;
        this.victoryPoints = 0;
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

        /**
         * To modify when we add the action method
         * Add coins when the player plays the card that gives coins AKA TAVERNE CARD
         * **/

        switch (playedCard.getName()){
            case "TAVERNE":
                addCoins(5);
                break;
            case "COMPTOIR OUEST":
                System.out.println("Player "+ id +" can get from his left Neighbor the ressource that he wants for one coin ");
                this.priceLeft = 1;
                break;
            case "COMPTOIR EST":
                System.out.println("Player "+ id +" can get from his right Neighbor the ressource that he wants for one coin ");
                this.priceRight = 1;
                break;
            case "MARCHE":
                System.out.println("Player "+ id +" can get from his right Neighbor the ressource that he wants for one coin ");
                this.priceRight = 1;
                this.priceLeft = 1;
                break;
        }



        //Updating player's score
        score += playedCard.getVictoryPoints();
        //Updating player's available resources
        updateAvailableResources(playedCard);
        //Card is removed from hand
        cards.remove(0);
    }

    private void buyRessource(Resource  r, Player p){
        /**
         * if player p have the ressource then the courant player can pay it (for the moment)
         * **/
        if(p.acceptToSale(r)){
            availableResources[r.getIndex()]++;
            int n = 2;
            /**
             * if(p is rightNeighborId) n = priceRight else n= priceLeft
             *
             * */
            p.addCoins(n);
            p.removeRessource(r);
            this.removeCoins(n);
        }
    }

    private boolean acceptToSale(Resource  r){
        return availableResources[r.getIndex()] > 0;
    }

    private void removeRessource(Resource r){
        availableResources[r.getIndex()]--;
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

    public int getVictoryPoints() { return this.victoryPoints; }

    public void addVictoryPoints(int victoryPoints) { this.victoryPoints = this.victoryPoints + victoryPoints; }

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

    public int[] getAvailableResources() { return availableResources; }

    public int getBoucliersCount() { return availableResources[Resource.BOUCLIER.getIndex()]; }


    public void fightWithNeighbor(Player neighbor, int victoryPoints) { // victoryPoints depends on Age
        int playerBoucliersCount = this.getBoucliersCount();
        int neighborBoucliersCount = neighbor.getBoucliersCount();
        if (playerBoucliersCount > neighborBoucliersCount) {
            this.addVictoryPoints(victoryPoints);
        } else if (playerBoucliersCount < neighborBoucliersCount) {
            this.addVictoryPoints(-1);
        }
    }

    public void setCards(ArrayList<Card> initiateCards) {
        this.cards = initiateCards;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void removeCoins(int coins){
        setCoins(this.coins+coins);
    }

    public void addCoins(int coins) {
        setCoins(this.coins+coins);
    }
}
