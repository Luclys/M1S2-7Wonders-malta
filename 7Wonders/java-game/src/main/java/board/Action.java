package board;

import effects.*;
import gameelements.Card;
import gameelements.Inventory;
import gameelements.enums.Resource;
import gameelements.enums.Symbol;
import gameelements.enums.Category;

import java.util.ArrayList;

public class Action {
    public static final int NOMBRE_CARTES = 7;
    ArrayList<Player> playerList;
    ArrayList<Inventory> playerInventoryList;

    protected void leftRotation(){
        ArrayList<Inventory> tmpList= new ArrayList<>(playerInventoryList);
        int leftNeighborId ;
        ArrayList<Card> cards = playerInventoryList.get(playerInventoryList.size()-1).getCardsInHand();
        for(Inventory i : playerInventoryList){
            leftNeighborId = playerList.get(i.getPlayerId()).getLeftNeighborId();
            tmpList.get(leftNeighborId).setCardsInHand(playerInventoryList.get(i.getPlayerId()).getCardsInHand());
        }
        tmpList.get(tmpList.size()-2).setCardsInHand(cards);
        playerInventoryList = tmpList;
    }

    protected void rightRotation(){
        ArrayList<Card> temp, last;
        Inventory courant;
        int i =0 ;
        last = playerInventoryList.get(0).getCardsInHand();
        while (i < playerInventoryList.size()){
            temp = playerInventoryList.get(playerList.get(i).getRightNeighborId()).getCardsInHand();
            playerInventoryList.get(playerList.get(i).getRightNeighborId()).setCardsInHand(last);
            last = temp;
            i++;
        }
    }

    protected void fightWithNeighbor(Inventory invPlayer, Inventory invNeighbor, int conflictPoints) { // conflictPoints depends on Age
        int playerBoucliersCount = invPlayer.getSymbCount(Symbol.BOUCLIER);
        int neighborBoucliersCount = invNeighbor.getSymbCount(Symbol.BOUCLIER);
        //System.out.println("[RESOLVING WAR CONFLICTS] Player has " + playerBoucliersCount + " boucliers while neighbor has " + neighborBoucliersCount);
        if (playerBoucliersCount > neighborBoucliersCount) {
            invPlayer.updateConflictPoints(conflictPoints);
            //System.out.println("[RESOLVING WAR CONFLICTS] " + conflictPoints + " conflict points added");
        } else if (playerBoucliersCount < neighborBoucliersCount) {
            invPlayer.updateConflictPoints(-1);
            //System.out.println("[RESOLVING WAR CONFLICTS] Defeat jeton (-1 conflict point) added");
        }
        //System.out.println("[RESOLVING WAR CONFLICTS] Total player conflict points: " + this.getConflictPoints());
    }

    protected ArrayList<Player> generatePlayers(int nbPlayers) {
        playerList = new ArrayList<>(nbPlayers);
        playerInventoryList = new ArrayList<>(nbPlayers);
        for (int i = 0; i < nbPlayers; i++) {
            Player player = new Player(i);
            Inventory inv = new Inventory(i);
            // To make a sure we bind the first's left to last id
            if (i == 0) {
                player.setLeftNeighborId(nbPlayers - 1);
            } else {
                player.setLeftNeighborId(i - 1);
            }
            // To make a sure we bind the last's right to first id
            if (i == nbPlayers - 1) {
                player.setRightNeighborId(0);
            } else {
                player.setRightNeighborId(i + 1);
            }
            playerList.add(player);
            playerInventoryList.add(inv);
        }
        return playerList;
    }

    protected ArrayList<Card> initiateCards(int nbPlayers) {
        /*
         * Generate three different decks for the three ages according to the nbPlayers.
         * shuffle guild card and keep nbPlayers + 2. Add to the ThirdAge deck.
         * */
        ArrayList<Card> res = new ArrayList<>(NOMBRE_CARTES * nbPlayers);
        switch (nbPlayers) {
            case 7:
                // Blue
                res.add(new Card("PRÊTEUR SUR GAGES", new ScoreEffect("Instant Score", 3), null, Category.BATIMENT_CIVIL));
                res.add(new Card("BAINS", new ScoreEffect("Instant Score", 3), new Resource[]{Resource.PIERRE}, Category.BATIMENT_CIVIL));

                // Red
                res.add(new Card("PALISSADE", new SymbolEffect("", Symbol.BOUCLIER, 1), new Resource[]{Resource.BOIS}, Category.BATIMENT_MILITAIRE));

                //green
                res.add(new Card("ATELIER", new SymbolEffect("", Symbol.ROUAGE, 1), new Resource[]{Resource.VERRE}, Category.BATIMENT_SCIENTIFIQUE));

                // Yellow
                res.add(new Card("COMPTOIR OUEST", new ReductCommerce("COMPTOIR OUEST", 0, true), null, Category.BATIMENT_COMMERCIEAU));
                res.add(new Card("COMPTOIR EST", new ReductCommerce("COMPTOIR EST", 1, true), null, Category.BATIMENT_COMMERCIEAU));
                res.add(new Card("MARCHÉ", new ReductCommerce("MARCHÉ", 2, false), null, Category.BATIMENT_COMMERCIEAU));
                res.add(new Card("TAVERNE", new CoinEffect("", 5), null, Category.BATIMENT_COMMERCIEAU));


                // Age II
                // Age III

            case 6:
                // Age I
                // Gray
                res.add(new Card("MÉTIER À TISSER", new ResourceEffect("", Resource.TISSU, 1), null, Category.PRODUIT_MANUFACTURE));
                res.add(new Card("VERRERIE", new ResourceEffect("", Resource.VERRE, 1), null, Category.PRODUIT_MANUFACTURE));
                res.add(new Card("PRESSE", new ResourceEffect("", Resource.PAPYRUS, 1), null, Category.PRODUIT_MANUFACTURE));
                res.add(new Card("THÉÂTRE", new ScoreEffect("Instant Score", 2), null, Category.PRODUIT_MANUFACTURE));

                // Yellow
                res.add(new Card("MARCHÉ", new ReductCommerce("MARCHÉ", 2, false), null, Category.BATIMENT_COMMERCIEAU));


                // Age II
                //Gray
                res.add(new Card("MÉTIER À TISSER", new ResourceEffect("", Resource.TISSU, 1), null, Category.PRODUIT_MANUFACTURE));
                res.add(new Card("VERRERIE", new ResourceEffect("", Resource.VERRE, 1), null, Category.PRODUIT_MANUFACTURE));
                res.add(new Card("PRESSE", new ResourceEffect("", Resource.PAPYRUS, 1), null, Category.PRODUIT_MANUFACTURE));

                // Age III
            case 5:
                // Age I
                // Brown
                res.add(new Card("CAVITÉ", new ResourceEffect("", Resource.PIERRE, 1), null, Category.MATIERE_PREMIERE));
                res.add(new Card("BASSIN ARGILEUX", new ResourceEffect("", Resource.ARGILE, 1), null, Category.MATIERE_PREMIERE));

                // Blue
                res.add(new Card("AUTEL", new ScoreEffect("Instant Score", 2), null, Category.BATIMENT_SCIENTIFIQUE));

                // Red
                res.add(new Card("CASERNE", new SymbolEffect("", Symbol.BOUCLIER, 1), new Resource[]{Resource.MINERAI}, Category.BATIMENT_MILITAIRE));

                //green
                res.add(new Card("OFFICINE", new SymbolEffect("", Symbol.COMPAS, 1), new Resource[]{Resource.TISSU}, Category.BATIMENT_SCIENTIFIQUE));

                // Yellow
                res.add(new Card("TAVERNE", new CoinEffect("", 5), null, Category.BATIMENT_COMMERCIEAU));

                // Age II
                // Gray
                //res.add(new Card("MÉTIER À TISSER", new ResourceEffect("", Resource.TISSU, 1), null, Category.PRODUIT_MANUFACTURE));
                //res.add(new Card("VERRERIE", new ResourceEffect("", Resource.VERRE, 1), null, Category.PRODUIT_MANUFACTURE));
                //res.add(new Card("PRESSE", new ResourceEffect("", Resource.PAPYRUS, 1), null, Category.PRODUIT_MANUFACTURE));

                // Age III
            case 4:
                // Age I
                // Brown
                res.add(new Card("CHANTIER", new ResourceEffect("", Resource.BOIS, 1), null, Category.MATIERE_PREMIERE));
                res.add(new Card("FILON", new ResourceEffect("", Resource.MINERAI, 1), null, Category.MATIERE_PREMIERE));

                // Blue
                res.add(new Card("PRÊTEUR SUR GAGES", new ScoreEffect("Instant Score", 3), null, Category.BATIMENT_CIVIL));

                // Red
                res.add(new Card("TOUR DE GARDE", new SymbolEffect("", Symbol.BOUCLIER, 1), new Resource[]{Resource.ARGILE}, Category.BATIMENT_MILITAIRE));

                //green
                res.add(new Card("SCRIPTORIUM", new SymbolEffect("", Symbol.STELE, 1), new Resource[]{Resource.PAPYRUS}, Category.BATIMENT_SCIENTIFIQUE));

                // Yellow
                res.add(new Card("TAVERNE", new CoinEffect("", 5), null, Category.BATIMENT_COMMERCIEAU));


                // Age II
                //res.add(new Card("SCIERIE", new ResourceEffect("", Resource.BOIS, 2), new Ressource[]{Ressource.ARGENT}));
                //res.add(new Card("CARRIÈRE", new ResourceEffect("", Resource.PIERRE, 2), new Ressource[]{Ressource.ARGENT}));
                //res.add(new Card("BRIQUETERIE", new ResourceEffect("", Resource.ARGILE, 2), new Ressource[]{Ressource.ARGENT}));
                //res.add(new Card("FONDERIE", new ResourceEffect("", Resource.MINERAI, 2), new Ressource[]{Ressource.ARGENT}));

                // Age III

            case 3:
                // Age I
                // Brown
                res.add(new Card("CHANTIER", new ResourceEffect("BOIS", Resource.BOIS, 1), null, Category.MATIERE_PREMIERE));
                res.add(new Card("CAVITÉ", new ResourceEffect("PIERRE", Resource.PIERRE, 1), null, Category.MATIERE_PREMIERE));
                res.add(new Card("BASSIN Argileux", new ResourceEffect("ARGILE", Resource.ARGILE, 1), null, Category.MATIERE_PREMIERE));
                res.add(new Card("FILON", new ResourceEffect("MINERAI", Resource.MINERAI, 1), null, Category.MATIERE_PREMIERE));

                // Gray
                res.add(new Card("MÉTIER À TISSER", new ResourceEffect("TISSU", Resource.TISSU, 1), null, Category.PRODUIT_MANUFACTURE));
                res.add(new Card("VERRERIE", new ResourceEffect("VERRE", Resource.VERRE, 1), null, Category.PRODUIT_MANUFACTURE));
                res.add(new Card("PRESSE", new ResourceEffect("PAPYRUS", Resource.PAPYRUS, 1), null, Category.PRODUIT_MANUFACTURE));

                // Blue
                res.add(new Card("BAINS", new ScoreEffect("", 3), new Resource[]{Resource.PIERRE}, Category.BATIMENT_CIVIL));
                res.add(new Card("AUTEL", new ScoreEffect("", 2), null, Category.BATIMENT_CIVIL));
                res.add(new Card("THÉÂTRE", new ScoreEffect("", 2), null, Category.BATIMENT_CIVIL));

                // Red
                res.add(new Card("PALISSADE", new SymbolEffect("", Symbol.BOUCLIER, 1), new Resource[]{Resource.BOIS}, Category.BATIMENT_MILITAIRE));
                res.add(new Card("CASERNE", new SymbolEffect("", Symbol.BOUCLIER, 1), new Resource[]{Resource.MINERAI}, Category.BATIMENT_MILITAIRE));
                res.add(new Card("TOUR DE GARDE", new SymbolEffect("", Symbol.BOUCLIER, 1), new Resource[]{Resource.ARGILE}, Category.BATIMENT_MILITAIRE));

                // Green
                res.add(new Card("OFFICINE", new SymbolEffect("", Symbol.COMPAS, 1), new Resource[]{Resource.TISSU}, Category.BATIMENT_SCIENTIFIQUE));
                res.add(new Card("ATELIER", new SymbolEffect("", Symbol.ROUAGE, 1), new Resource[]{Resource.VERRE}, Category.BATIMENT_SCIENTIFIQUE));
                res.add(new Card("SCRIPTORIUM", new SymbolEffect("", Symbol.STELE, 1), new Resource[]{Resource.PAPYRUS}, Category.BATIMENT_SCIENTIFIQUE));

                // Yellow
                res.add(new Card("COMPTOIR OUEST", new ReductCommerce("COMPTOIR OUEST", 0, true), null, Category.BATIMENT_COMMERCIEAU));
                res.add(new Card("COMPTOIR EST", new ReductCommerce("COMPTOIR EST", 1, true), null, Category.BATIMENT_COMMERCIEAU));
                res.add(new Card("MARCHÉ", new ReductCommerce("MARCHÉ", 2, false), null, Category.BATIMENT_COMMERCIEAU));


                // Age II
                // Brown
                //res.add(new Card("SCIERIE", new ResourceEffect("", Resource.BOIS, 2), null));
                //res.add(new Card("CARRIÈRE", new ResourceEffect("", Resource.PIERRE, 2), null));
                //res.add(new Card("BRIQUETERIE", new ResourceEffect("", Resource.ARGILE, 2), null));
                //res.add(new Card("FONDERIE", new ResourceEffect("", Resource.MINERAI, 2), null));

                //res.add(new Card("MÉTIER À TISSER", new ResourceEffect("", Resource.TISSU, 1), null));
                //res.add(new Card("VERRERIE", new ResourceEffect("", Resource.VERRE, 1), null));
                //res.add(new Card("PRESSE", new ResourceEffect("", Resource.PAPYRUS, 1), null));


                // Age III
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + nbPlayers);
        }
        for (int i = res.size(); i < nbPlayers * 7; i++) {
            res.add(new Card("REMPLIR VIDE" + i, new ResourceEffect("FillerEffect", Resource.BOIS, 1), null, Category.MATIERE_PREMIERE));
        }
        return res;
    }

    public ArrayList<Inventory> getPlayerInventoryList() {
        return playerInventoryList;
    }
}
