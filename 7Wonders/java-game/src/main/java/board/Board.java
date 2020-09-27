package board;

import gameelements.Card;
import gameelements.Resource;

import java.util.ArrayList;
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
            player.setCoins(3);
            playerList.add(player);
        }

        currentDeckCardList = initiateCards(nbPlayers);
        Collections.shuffle(currentDeckCardList);
    }

    public static void main(String[] args) {
        System.out.println("~Starting a new game of 7 Wonders~");
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
        for (int age = 0; age < 1; age++) {

            // Card dealing
            playerList.forEach(player -> player.setCards(drawCards(NOMBRE_CARTES)));

            // Each player plays a card on each turn
            for (int currentTurn = 0; currentTurn < NOMBRE_CARTES - 1; currentTurn++) {
                for (Player p : playerList) {
                    p.playCard();
                }
                // The players exchange cards according to the Age's sens.

                this.turn++;
            }
            // At the end of the 6th turn, we discard the remaining card
            // ⚠ The discarded cards must be remembered.
            playerList.forEach(player -> discardedCardList.add(player.discardLastCard()));
            // Resolving war conflicts
            playerList.forEach(this::resolveWarConflict);
        }
    }

    private void resolveWarConflict(Player player) {
        player.fightWithNeighbor(playerList.get(player.getRightNeighborId()), 1);
        player.fightWithNeighbor(playerList.get(player.getLeftNeighborId()), 1);
    }


    ArrayList<Card> drawCards(int nbCards) {
        ArrayList<Card> playerDeck = new ArrayList<>(currentDeckCardList.subList(0, nbCards));
        this.currentDeckCardList.removeAll(playerDeck);
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
                res.add(new Card("PRÊTEUR SUR GAGES", new Resource[]{Resource.POINTSCORE, Resource.POINTSCORE, Resource.POINTSCORE}));
                res.add(new Card("BAINS", new Resource[]{Resource.POINTSCORE, Resource.POINTSCORE, Resource.POINTSCORE}));

                // Red
                res.add(new Card("PALISSADE", new Resource[]{Resource.BOUCLIER}));

                // Yellow
                res.add(new Card("COMPTOIR OUEST", new Resource[]{Resource.ARGILE,Resource.BOIS,Resource.MINERAI,Resource.PIERRE}));
                res.add(new Card("COMPTOIR EST", new Resource[]{Resource.ARGILE,Resource.BOIS,Resource.MINERAI,Resource.PIERRE}));

                res.add(new Card("TAVERNE", new Resource[]{Resource.ARGENT}));


                // Age II
                // Age III

            case 6:
                // Age I
                // Gray
                res.add(new Card("MÉTIER À TISSER", new Resource[]{Resource.TISSU}));
                res.add(new Card("VERRERIE", new Resource[]{Resource.VERRE}));
                res.add(new Card("PRESSE", new Resource[]{Resource.PAPYRUS}));

                res.add(new Card("THÉÂTRE", new Resource[]{Resource.POINTSCORE, Resource.POINTSCORE}));

                // Yellow
                res.add(new Card("MARCHE", new Resource[]{Resource.TISSU, Resource.VERRE, Resource.PAPYRUS}));


                // Age II
                //res.add(new Card("MÉTIER À TISSER", new Resource[]{Resource.TISSU}));
                //res.add(new Card("VERRERIE", new Resource[]{Resource.VERRE}));
                //res.add(new Card("PRESSE", new Resource[]{Resource.PAPYRUS}));

                // Age III
            case 5:
                // Age I
                // Brown
                res.add(new Card("CAVITÉ", new Resource[]{Resource.PIERRE}));
                res.add(new Card("BASSIN ARGILEUX", new Resource[]{Resource.ARGILE}));

                // Blue
                res.add(new Card("AUTEL", new Resource[]{Resource.POINTSCORE, Resource.POINTSCORE}));

                // Red
                res.add(new Card("CASERNE", new Resource[]{Resource.BOUCLIER}));

                // Yellow
                res.add(new Card("TAVERNE", new Resource[]{Resource.ARGENT}));

                // Age II
                // Gray
                //res.add(new Card("MÉTIER À TISSER", new Resource[]{Resource.TISSU}));
                //res.add(new Card("VERRERIE", new Resource[]{Resource.VERRE}));
                //res.add(new Card("PRESSE", new Resource[]{Resource.PAPYRUS}));

                // Age III
            case 4:
                // Age I
                // Brown
                res.add(new Card("CHANTIER", new Resource[]{Resource.BOIS}));
                res.add(new Card("FILON", new Resource[]{Resource.MINERAI}));

                // Blue
                res.add(new Card("PRÊTEUR SUR GAGES", new Resource[]{Resource.POINTSCORE, Resource.POINTSCORE, Resource.POINTSCORE}));

                // Red
                res.add(new Card("TOUR DE GARDE", new Resource[]{Resource.BOUCLIER}));


                // Yellow
                res.add(new Card("TAVERNE", new Resource[]{Resource.ARGENT}));


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
                res.add(new Card("CHANTIER", new Resource[]{Resource.BOIS}));
                res.add(new Card("CAVITÉ", new Resource[]{Resource.PIERRE}));
                res.add(new Card("BASSIN Argileux", new Resource[]{Resource.ARGILE}));
                res.add(new Card("FILON", new Resource[]{Resource.MINERAI}));

                // Gray
                res.add(new Card("MÉTIER À TISSER", new Resource[]{Resource.TISSU}));
                res.add(new Card("VERRERIE", new Resource[]{Resource.VERRE}));
                res.add(new Card("PRESSE", new Resource[]{Resource.PAPYRUS}));

                // Blue
                res.add(new Card("BAINS", new Resource[]{Resource.POINTSCORE, Resource.POINTSCORE, Resource.POINTSCORE}));
                res.add(new Card("AUTEL", new Resource[]{Resource.POINTSCORE, Resource.POINTSCORE}));
                res.add(new Card("THÉÂTRE", new Resource[]{Resource.POINTSCORE, Resource.POINTSCORE}));

                // Red
                res.add(new Card("PALISSADE", new Resource[]{Resource.BOUCLIER}));
                res.add(new Card("CASERNE", new Resource[]{Resource.BOUCLIER}));
                res.add(new Card("TOUR DE GARDE", new Resource[]{Resource.BOUCLIER}));

                // Green
                res.add(new Card("OFFICINE", new Resource[]{Resource.COMPAS}));
                res.add(new Card("ATELIER", new Resource[]{Resource.ROUAGE}));
                res.add(new Card("SCRIPTORIUM", new Resource[]{Resource.STELE}));

                // Yellow
                res.add(new Card("MARCHE", new Resource[]{Resource.TISSU, Resource.VERRE, Resource.PAPYRUS}));
                res.add(new Card("COMPTOIR OUEST", new Resource[]{Resource.ARGILE,Resource.BOIS,Resource.MINERAI,Resource.PIERRE}));
                res.add(new Card("COMPTOIR EST", new Resource[]{Resource.ARGILE,Resource.BOIS,Resource.MINERAI,Resource.PIERRE}));





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
        }

        for (int i = res.size(); i < nbPlayers * 7; i++) {
            res.add(new Card("REMPLIR VIDE" + i, new Resource[]{Resource.BOIS}));
        }
        return res;
    }

}
