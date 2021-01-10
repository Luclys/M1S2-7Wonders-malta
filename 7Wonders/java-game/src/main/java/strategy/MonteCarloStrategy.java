package strategy;

import board.Board;
import gameelements.GameLogger;
import gameelements.Inventory;
import gameelements.Player;
import gameelements.cards.Card;
import gameelements.enums.Action;

import java.util.ArrayList;

public class MonteCarloStrategy implements PlayingStrategy {
    Action chosenAction;
    Board board;
    Card chosenCard;
    Inventory inv ;

    @Override
    public PlayingStrategy copy() {
        return this;
    }

    @Override
    public Card chooseCard(Inventory inventory, Board b) throws Exception {
        board = new Board(b);
        inv = inventory;
        monteCarlo();
        return chosenCard;
    }

    @Override
    public Action getAction() {
        return chosenAction;
    }

    @Override
    public Card getCard() {
        return chosenCard;
    }



    private void getBestChosenCardAndAction(ArrayList<ArrayList<Integer>> numberOfVictories, ArrayList<Card> cards) {
        int maxscore = 0;
        for (int cardIndex = 0; cardIndex < numberOfVictories.size(); cardIndex++) {
            ArrayList<Action> actions = availableActions(cards.get(cardIndex), inv);
            for (int actionIndex = 0; actionIndex < numberOfVictories.get(cardIndex).size(); actionIndex++) {
                if (numberOfVictories.get(cardIndex).get(actionIndex) > maxscore) {
                    maxscore = numberOfVictories.get(cardIndex).get(actionIndex);
                    chosenCard = cards.get(cardIndex);
                    chosenAction = actions.get(actionIndex);
                }
            }
        }
        board.log = new GameLogger(false);
    }

    public Card monteCarlo() throws Exception {
        ArrayList<Card> availableCards = cardsAvailableToPlay(inv);
        int idwinner = 0;
        if (availableCards.size() != 0){
            ArrayList<ArrayList<Integer>> numberOfVictories = new ArrayList<>();
            for (int cardIndex = 0 ; cardIndex < availableCards.size() ; cardIndex++){
                ArrayList<Action> listActions = availableActions(availableCards.get(cardIndex), inv);
                numberOfVictories.add(new ArrayList<>());
                for (int actionIndex = 0; actionIndex < listActions.size(); actionIndex++){
                    numberOfVictories.get(cardIndex).add(0);
                    Action action = listActions.get(actionIndex);
                    Inventory copyTheInventory = new Inventory(inv);
                    chosenCard = availableCards.get(cardIndex);
                    chosenAction = action;
                    Board memoriseTheBoard = new Board(board);

                    memoriseTheBoard.getCommerce().log = new GameLogger(false);
                    memoriseTheBoard.executePlayerAction(memoriseTheBoard.getPlayerInventoryList().get(inv.getPlayerId()), memoriseTheBoard.getPlayerList().get(inv.getPlayerId()));

                    for (int i = 0 ;i < 1000; i++ ){
                        // recuperer l'ancien board apres le choix de monte
                        Board memoriseTheBoard2 = new Board(memoriseTheBoard );
                        memoriseTheBoard2.getCommerce().log = new GameLogger(false);
                        idwinner = continueGame(memoriseTheBoard2,copyTheInventory);
                        if (idwinner == copyTheInventory.getPlayerId()){
                            numberOfVictories.get(cardIndex).set(actionIndex, numberOfVictories.get(cardIndex).get(actionIndex)+1); // aussi donné l'action joue
                        }
                    }
                }
            }
            getBestChosenCardAndAction(numberOfVictories, availableCards);
        }else{
            chosenCard = inv.getCardsInHand().get(0);
            chosenAction = Action.SELL;
        }
        return chosenCard;
    }

    public int continueGame(Board board, Inventory inventory) throws Exception {
        // changer notre stagerie
        board.getPlayerList().get(inventory.getPlayerId()).setStrategy(new WonderStrategy());

        // terminer la partie courant
        for(Player p : board.getPlayerList()){
            if(inventory.getPlayerId()!=p.getId()){
                p.chooseCard(board.getPlayerInventoryList().get(p.getId()),board);
            }
        }

        //chaque player joue carte sauf le player actuel
        for(Inventory p : board.getPlayerInventoryList()){
            if(inventory.getPlayerId()!=p.getPlayerId()){
                board.executePlayerAction(p, board.getPlayerList().get(p.getPlayerId()));
            }
        }

        board.endOfTurn();

        //terminer l'age
        // Each player plays a card on each turn until each has 1 card left
        while(board.getPlayerInventoryList().get(0).getCardsInHand().size()>1){
            for (Player p : board.getPlayerList()) {
                p.chooseCard(board.getPlayerInventoryList().get(p.getId()),board);
                board.executePlayerAction(board.getPlayerInventoryList().get(p.getId()), p);
            }
            board.endOfTurn();
        }
        board.endOfAge();


        //terminer toutes les ages restantes
        for (int age = board.getCurrentAge()+1; age <= Board.AGES; age++){
                board.ageSetUp(age);
                // Card dealing & resetting possibleFreeBuildingsCount
                for (Inventory playerInv : board.getPlayerInventoryList()) {
                    playerInv.setCardsInHand(board.drawCards(Board.CARDS_NUMBER));
                    //reset possibleFreeBuildingsCount because it's a new age
                    if (playerInv.getPossibleFreeBuildings() == -1) playerInv.setPossibleFreeBuildings(1);
                }

                //terminer chaque turn
                while(board.getPlayerInventoryList().get(0).getCardsInHand().size()>1){
                    for (Player p : board.getPlayerList()) {
                        p.chooseCard(board.getPlayerInventoryList().get(p.getId()),board);
                        board.executePlayerAction(board.getPlayerInventoryList().get(p.getId()), p);
                    }
                    board.endOfTurn();
                }
                board.endOfAge();
        }
        // terminer le jeu
        board.endOfGame();
        //determiner le gagnant
        Inventory winner = board.getPlayerInventoryList().get(0) ;
        for (Inventory i : board.getPlayerInventoryList()) {
            if (i.getScore()>winner.getScore())
                winner = i;
        }
        return winner.getPlayerId();
    }
}
