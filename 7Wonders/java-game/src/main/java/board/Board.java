package board;

import gameelements.Card;
import gameelements.Resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Board {
    public static final int NOMBRE_CARTES = 7;
    private final ArrayList<Player> playerList;
    private final ArrayList<Card> currentDeckCardList;
    private ArrayList<Card> discardedCardList;
    private int turn;

    public Board(int nbPlayers) {
        playerList = new ArrayList<>(nbPlayers);
        for (int i = 0; i < nbPlayers; i++) {
            Player player = new Player(i);

            // To make a tor we bind the first's left to last id
            if (i == 0) {
                player.setLeftNeighborId(nbPlayers - 1);
            } else {
                player.setLeftNeighborId(i - 1);
            }
            // To make a tor we bind the last's right to first id
            if (i == nbPlayers - 1) {
                player.setRightNeighborId(0);
            } else {
                player.setRightNeighborId(i + 1);
            }
            playerList.add(player);
        }

        currentDeckCardList = initiateCards(nbPlayers);
        Collections.shuffle(currentDeckCardList);
    }

    public static void main(String[] args) {
        System.out.println("~Starting a new game of 7 Wonders~\n");
        Board board = new Board(3); // We won't code the 2p version.
        board.play();
        board.scores();
    }

    public ArrayList<Player> getPlayerList() {
        return this.playerList;
    }

    public ArrayList<Card> getCurrentDeckCardList() {
        return this.currentDeckCardList;
    }

    public int getTurn() {
        return this.turn;
    }

    public void play() {
        boolean result;
        for (int age = 0; age < 1; age++) {

            // Card dealing
            playerList.forEach(player -> player.setCards(drawCards(NOMBRE_CARTES)));
            Card playedCard;
            // Each player plays a card on each turn
            for (int currentTurn = 0; currentTurn < NOMBRE_CARTES - 1; currentTurn++) {
                for (Player p : playerList) {
                    System.out.println("Coins of the player " + p.getCoins() + "\nResources of the player " + Arrays.toString(p.getAvailableResources())+"\n");

                    playedCard = p.playCard();
                    if(playedCard != null){
                        System.out.println("Card that the player wants to play : " + playedCard.getName() + "\n \t resource required to play this card :" + Arrays.toString(playedCard.getRequiredResources())+"\n");
                        System.out.println("** verify if the player has the required resources: ");
                        Resource[] s = p.missingResources(playedCard);
                        result = false;
                        if (s[0] != null) {
                            System.out.println("*** Missing resource to play the card " + Arrays.toString(s));
                            System.out.println("**** Verify if the player can buy missing resources ");
                            result = BuyResources(s, p);
                        } else {
                            System.out.println("*** No resource is required ");
                            result = true;
                        }

                        if (!result) {
                            System.out.println("The player can't use the card so card"+ playedCard.getName() +" is discord");
                            p.saleCard();
                        } else {
                            System.out.println("The player got the resources of the played card");
                            p.updatePlayer(playedCard);
                        }
                        System.out.println("Coins of the player " + p.getCoins() + "\n Resources of the player " + Arrays.toString(p.getAvailableResources())+"\n");

                        System.out.println("********************************************************************");
                    }


                }
                System.out.println("############################################################################");
                // The players exchange cards according to the Age's sens.

                /*
                ArrayList<Card> tempListCard = null;
                ArrayList<Card> dernierCartes = null;

                for(Player p : playerList){
                    int leftNeighborId = p.getLeftNeighborId();
                    Player leftNeighbor = playerList.get(leftNeighborId);
                    tempListCard = leftNeighbor.getCards();
                    if(p.getId() == 0){
                        leftNeighbor.setCards(p.getCards());
                    }else{
                        leftNeighbor.setCards(dernierCartes);
                    }
                    dernierCartes = tempListCard;
                }*/

                this.turn++;
            }

            // At the end of the 6th turn, we discard the remaining card
            // ⚠ The discarded cards must remembered.
            playerList.forEach(player -> discardedCardList.add(player.discardLastCard()));
            // Resolving war conflicts
            playerList.forEach(this::resolveWarConflict);
        }
    }

    private boolean BuyResources(Resource[] missingResources, Player p) {
        boolean result = false;
        Player[] playersWithResources = new Player[4];
        Player neighbor = null;
        int k = 0;
        for (Resource r : missingResources) {// check if the player has enough coins to buy resource
            if (r != null) {
                if (p.getCoins() - (2 * k) > 1) {
                    neighbor = chooseNeighbor(r, p);
                    if (neighbor == null) {// check if one of the neigbor has the resource
                        break;
                    } else {
                        System.out.println("*the player  can buy the resource " + r);
                        playersWithResources[k] = neighbor;
                        k++;
                    }
                }
            } else {
                break;
            }
        }
        if (k == 4) {// neighbors have all the missing resources
            for (int i = 0; i < 4; i++) {// buy  resources from neighbors
                buyFromNeighbor(p, playersWithResources[i]);
            }
            result = true;
        }
        return result;
    }

    protected Player chooseNeighbor(Resource missingResource, Player p) {// check price left and price right if the player can buy from both neighbor
        Player rightNeighbor = playerList.get(p.getRightNeighborId());
        Player leftNeighbor = playerList.get(p.getLeftNeighborId());
        Player neighbor = null;
        if (rightNeighbor.getAvailableResources()[missingResource.getIndex()] > 0) {
            System.out.println(" from the right neighbor the resource " + missingResource);
            neighbor = playerList.get(p.getRightNeighborId());
        } else {
            if (leftNeighbor.getAvailableResources()[missingResource.getIndex()] > 0) {
                System.out.println(" from the left neighbor the resource " + missingResource);
                neighbor = playerList.get(p.getLeftNeighborId());
                ;
            }
        }
        return neighbor;
    }

    protected void buyFromNeighbor(Player p, Player neighbor) {// add that the neighbor can't use the adding coins till next turn
        neighbor.addCoins(2);
        p.removeCoins(2);
    }

    private void resolveWarConflict(Player player) {
        player.fightWithNeighbor(playerList.get(player.getRightNeighborId()), 1);
        player.fightWithNeighbor(playerList.get(player.getLeftNeighborId()), 1);
    }


    ArrayList<Card> drawCards(int nbCards) {
        ArrayList<Card> playerDeck = new ArrayList<>(currentDeckCardList.subList(0, nbCards));
        System.out.println("NB CARTES : " + currentDeckCardList.size());
        this.currentDeckCardList.removeAll(playerDeck);
        System.out.println("NB CARTES APRES : " + currentDeckCardList.size());
        return playerDeck;
    }

    public void scores() {
        /*The player's score is calculated by doing :
         * Sum of Conflict Points
         * + (Sum coins / 3) -> each 3 coins grant 1 score point
         * + Sum of Wonders Points
         * + Sum of construction Points
         * + foreach(nb same Scientific²) + min(nb same scientific) * 7
         * + trading buildings (specific)
         * + guilds buildings (specific)
         *
         * In case of equality, the one with more coin wins, if there is still equality, they equally win.
         * */
        System.out.println("End of the game");
        for (Player p : playerList) {
            System.out.println(p);
        }
    }


    ArrayList<Card> initiateCards(int nbPlayers) {
        this.discardedCardList = new ArrayList<>(nbPlayers * 3);
        /*
         * Generate three different decks for the three ages according to the nbPlayers.
         * shuffle guild card and keep nbPlayers + 2. Add to the ThirdAge deck.
         * */
        ArrayList<Card> res = new ArrayList<>(NOMBRE_CARTES * nbPlayers);

        switch (nbPlayers) {
            case 7:
                // Blue
                res.add(new Card("PRÊTEUR SUR GAGES", new Resource[0], 3, null));
                res.add(new Card("BAINS", new Resource[0], 3, new Resource[0]));

                // Red
                res.add(new Card("PALISSADE", new Resource[]{Resource.BOUCLIER}, new Resource[0]));

                // Yellow
                res.add(new Card("COMPTOIR OUEST", new Resource[]{Resource.ARGILE, Resource.BOIS, Resource.MINERAI, Resource.PIERRE}, new Resource[0]));
                res.add(new Card("COMPTOIR EST", new Resource[]{Resource.ARGILE, Resource.BOIS, Resource.MINERAI, Resource.PIERRE}, new Resource[0]));

                res.add(new Card("TAVERNE", new Resource[]{Resource.ARGENT}, new Resource[0]));


                // Age II
                // Age III

            case 6:
                // Age I
                // Gray
                res.add(new Card("MÉTIER À TISSER", new Resource[]{Resource.TISSU}, new Resource[0]));
                res.add(new Card("VERRERIE", new Resource[]{Resource.VERRE}, new Resource[0]));
                res.add(new Card("PRESSE", new Resource[]{Resource.PAPYRUS}, new Resource[0]));

                res.add(new Card("THÉÂTRE", new Resource[0], 2, new Resource[0]));

                // Yellow
                res.add(new Card("MARCHE", new Resource[0], new Resource[0]));


                // Age II
                //res.add(new Card("MÉTIER À TISSER", new Resource[]{Resource.TISSU}));
                //res.add(new Card("VERRERIE", new Resource[]{Resource.VERRE}));
                //res.add(new Card("PRESSE", new Resource[]{Resource.PAPYRUS}));

                // Age III
            case 5:
                // Age I
                // Brown
                res.add(new Card("CAVITÉ", new Resource[]{Resource.PIERRE}, new Resource[0]));
                res.add(new Card("BASSIN ARGILEUX", new Resource[]{Resource.ARGILE}, new Resource[0]));

                // Blue
                res.add(new Card("AUTEL", new Resource[0], 2, new Resource[0]));

                // Red
                res.add(new Card("CASERNE", new Resource[]{Resource.BOUCLIER}, new Resource[0]));

                // Yellow
                res.add(new Card("TAVERNE", new Resource[0], new Resource[0]));

                // Age II
                // Gray
                //res.add(new Card("MÉTIER À TISSER", new Resource[]{Resource.TISSU}));
                //res.add(new Card("VERRERIE", new Resource[]{Resource.VERRE}));
                //res.add(new Card("PRESSE", new Resource[]{Resource.PAPYRUS}));

                // Age III
            case 4:
                // Age I
                // Brown
                res.add(new Card("CHANTIER", new Resource[]{Resource.BOIS}, new Resource[0]));
                res.add(new Card("FILON", new Resource[]{Resource.MINERAI}, new Resource[0]));

                // Blue
                res.add(new Card("PRÊTEUR SUR GAGES", new Resource[0], 3, new Resource[0]));

                // Red
                res.add(new Card("TOUR DE GARDE", new Resource[]{Resource.BOUCLIER}, new Resource[0]));


                // Yellow
                res.add(new Card("TAVERNE", new Resource[0], new Resource[0]));


                // Age II
                // Brown
                //res.add(new Card("SCIERIE", new Resource[]{Resource.BOIS, Resource.BOIS}));
                //res.add(new Card("CARRIÈRE", new Resource[]{Resource.PIERRE, Resource.PIERRE}));
                //res.add(new Card("BRIQUETERIE", new Resource[]{Resource.ARGILE, Resource.ARGILE}));
                //res.add(new Card("FONDERIE", new Resource[]{Resource.MINERAI, Resource.MINERAI}));

                // Age III

            case 3:
                // Age I
                // Brown
                res.add(new Card("CHANTIER", new Resource[]{Resource.BOIS}, new Resource[0]));
                res.add(new Card("CAVITÉ", new Resource[]{Resource.PIERRE}, new Resource[0]));
                res.add(new Card("BASSIN Argileux", new Resource[]{Resource.ARGILE}, new Resource[0]));
                res.add(new Card("FILON", new Resource[]{Resource.MINERAI}, new Resource[0]));

                // Gray
                res.add(new Card("MÉTIER À TISSER", new Resource[]{Resource.TISSU}, new Resource[0]));
                res.add(new Card("VERRERIE", new Resource[]{Resource.VERRE}, new Resource[0]));
                res.add(new Card("PRESSE", new Resource[]{Resource.PAPYRUS}, new Resource[0]));

                // Blue
                res.add(new Card("BAINS", new Resource[0], 3, new Resource[]{Resource.PIERRE}));
                res.add(new Card("AUTEL", new Resource[0], 2, new Resource[0]));
                res.add(new Card("THÉÂTRE", new Resource[0], 2, new Resource[0]));

                // Red
                res.add(new Card("PALISSADE", new Resource[]{Resource.BOUCLIER}, new Resource[0]));
                res.add(new Card("CASERNE", new Resource[]{Resource.BOUCLIER}, new Resource[0]));
                res.add(new Card("TOUR DE GARDE", new Resource[]{Resource.BOUCLIER}, new Resource[0]));

                // Green
                res.add(new Card("OFFICINE", new Resource[]{Resource.COMPAS}, new Resource[0]));
                res.add(new Card("ATELIER", new Resource[]{Resource.ROUAGE}, new Resource[0]));
                res.add(new Card("SCRIPTORIUM", new Resource[]{Resource.STELE}, new Resource[0]));

                // Yellow
                res.add(new Card("MARCHE", new Resource[0], new Resource[0]));
                res.add(new Card("COMPTOIR OUEST", new Resource[0], new Resource[0]));
                res.add(new Card("COMPTOIR EST", new Resource[0], new Resource[0]));


                // Age II
                // Brown
                //res.add(new Card("SCIERIE", new Resource[]{Resource.BOIS, Resource.BOIS}));
                //res.add(new Card("CARRIÈRE", new Resource[]{Resource.PIERRE, Resource.PIERRE}));
                //res.add(new Card("BRIQUETERIE", new Resource[]{Resource.ARGILE, Resource.ARGILE}));
                //res.add(new Card("FONDERIE", new Resource[]{Resource.MINERAI, Resource.MINERAI}));

                //res.add(new Card("MÉTIER À TISSER", new Resource[]{Resource.TISSU}));
                //res.add(new Card("VERRERIE", new Resource[]{Resource.VERRE}));
                //res.add(new Card("PRESSE", new Resource[]{Resource.PAPYRUS}));


                // Age III
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + nbPlayers);
        }

        for (int i = res.size(); i < nbPlayers * 7; i++) {
            res.add(new Card("REMPLIR VIDE" + i, new Resource[]{Resource.BOIS}, new Resource[0]));
        }
        return res;
    }

}
