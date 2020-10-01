package board;

import gameelements.Card;
import gameelements.enums.Resource;

import java.util.ArrayList;

public class Action {

    public static final int NOMBRE_CARTES = 7;
    ArrayList<Player> playerList;


    private void leftRotation() {
        ArrayList<Card> CardOfTheFirstPlayer = playerList.get(playerList.get(0).getId()).getCards();
        int leftNeighborId;
        Player leftNeighbor, lastPlayerOnList, p;
        int i = 0;
        while (i < playerList.size() - 1) {
            p = playerList.get(i);
            leftNeighborId = p.getLeftNeighborId();
            leftNeighbor = playerList.get(leftNeighborId);
            leftNeighbor.setCards(p.getCards());
            i++;
        }
        lastPlayerOnList = playerList.get(playerList.size() - 2);
        lastPlayerOnList.setCards(CardOfTheFirstPlayer);
    }


    protected ArrayList<Player> generatePlayers(int nbPlayers) {
        playerList = new ArrayList<>(nbPlayers);
        for (int i = 0; i < nbPlayers; i++) {
            Player player = new Player(i);
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
                res.add(new Card("PRÊTEUR SUR GAGES", new Resource[0], 3, null));
                res.add(new Card("BAINS", new Resource[0], 3, new Resource[0]));

                // Red
                res.add(new Card("PALISSADE", new Resource[]{Resource.BOUCLIER}, new Resource[]{Resource.BOIS}));

                //green
                res.add(new Card("ATELIER", new Resource[]{Resource.ROUAGE}, new Resource[]{Resource.VERRE}));

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
                res.add(new Card("CASERNE", new Resource[]{Resource.BOUCLIER}, new Resource[]{Resource.MINERAI}));

                //green
                res.add(new Card("OFFICINE", new Resource[]{Resource.COMPAS}, new Resource[]{Resource.TISSU}));

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
                res.add(new Card("TOUR DE GARDE", new Resource[]{Resource.BOUCLIER}, new Resource[]{Resource.ARGILE}));

                //green
                res.add(new Card("SCRIPTORIUM", new Resource[]{Resource.STELE}, new Resource[]{Resource.PAPYRUS}));

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
                res.add(new Card("PALISSADE", new Resource[]{Resource.BOUCLIER}, new Resource[]{Resource.BOIS}));
                res.add(new Card("CASERNE", new Resource[]{Resource.BOUCLIER}, new Resource[]{Resource.MINERAI}));
                res.add(new Card("TOUR DE GARDE", new Resource[]{Resource.BOUCLIER}, new Resource[]{Resource.ARGILE}));

                // Green
                res.add(new Card("OFFICINE", new Resource[]{Resource.COMPAS}, new Resource[]{Resource.TISSU}));
                res.add(new Card("ATELIER", new Resource[]{Resource.ROUAGE}, new Resource[]{Resource.VERRE}));
                res.add(new Card("SCRIPTORIUM", new Resource[]{Resource.STELE}, new Resource[]{Resource.PAPYRUS}));

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
