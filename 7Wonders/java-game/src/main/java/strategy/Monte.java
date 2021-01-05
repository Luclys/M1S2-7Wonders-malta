package strategy;

import board.Board;
import gameelements.Inventory;
import gameelements.Player;
import gameelements.cards.Card;
import gameelements.enums.Action;

import java.util.ArrayList;

public class Monte implements PlayingStrategy {
    Action chosenAction;
    Board board;
    Card chosenCard;
    Inventory inv ;

    @Override
    public PlayingStrategy copy() {

        return this;
    }

    @Override
    public Card chooseCard(Inventory inventory, Board b) {
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

    public ArrayList<Action> availableActions(Card c, Inventory inv){
        ArrayList<Action> list = new ArrayList<>();
        list.add(Action.SELL);
        // if can build step
        if (inv.canBuildNextStep(inv.getWonderBoard())) {
            list.add(Action.WONDER);
        }
        if (inv.canBuild(c.getRequiredResources())){
            list.add(Action.BUILDING);
        }
        if (inv.canBuildCardForFree(c)){
            list.add(Action.BUILDFREE);
        }
        return list;
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
    }

    public Card monteCarlo(){
        ArrayList<Card> availableCards = cardsAvailableToPlay(inv);
        int idwinner = 0;
        if (availableCards.size() != 0){
            ArrayList<ArrayList<Integer>> numberofvictory = new ArrayList<>();
            for (int cardIndex = 0 ; cardIndex < availableCards.size() ; cardIndex++){
                ArrayList<Action> listActions = availableActions(availableCards.get(cardIndex),inv);
                numberofvictory.add(new ArrayList<>());
                for ( int actionIndex = 0; actionIndex < listActions.size(); actionIndex++){
                    numberofvictory.get(cardIndex).add(0);
                    Action action = listActions.get(actionIndex);
                    Inventory copyTheInventory = new Inventory(inv);
                    chosenCard = availableCards.get(cardIndex);
                    chosenAction  = action;
                    Board memoriseTheBoard = new Board(board);
                    memoriseTheBoard.executePlayerAction(memoriseTheBoard.getPlayerInventoryList().get(inv.getPlayerId()), memoriseTheBoard.getPlayerList().get(inv.getPlayerId()));
                    for (int i = 0 ;i < 10 ; i++ ){
                        // recuperer l'ancien board apres le choix de monte
                        Board memoriseTheBoard2 = new Board(memoriseTheBoard);
                        idwinner = continueGame(memoriseTheBoard2,copyTheInventory);
                        if (idwinner == copyTheInventory.getPlayerId()){
                            numberofvictory.get(cardIndex).set(actionIndex, numberofvictory.get(cardIndex).get(actionIndex)+1); // aussi donné l'action joue
                        }
                    }
                }

            }
            for (int n = 0; n < numberofvictory.size(); n++) {
                ArrayList<Action> listActions = availableActions(availableCards.get(n),inv);
                for (int a = 0; a < listActions.size(); a++){
                    board.log.display("CARD " + availableCards.get(n) + " and action "+listActions.get(a) +" permits "+numberofvictory.get(n).get(a));
                }
            }
            getBestChosenCardAndAction(numberofvictory,availableCards);
        }else{
            chosenCard = inv.getCardsInHand().get(0);
            chosenAction = Action.SELL;
        }
        board.log.display("End of monte carlo");
        return null;
    }

    public int continueGame(Board board, Inventory inventory){
        // changer notre stagerie

        board.getPlayerList().get(inventory.getPlayerId()).setStrategy(new FirstCardStrategy());
        int currentAge = board.getCurrentAge();

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

        //termer l'age
        for ( int currentTurn = board.getCurrentTurn()+1; currentTurn < inventory.getCardsInHand().size()-1; currentTurn++) {
            // Each player plays a card on each turn

            for (Player p : board.getPlayerList()) {
                p.chooseCard(board.getPlayerInventoryList().get(p.getId()),board);
            }
            for (int i = 0; i < board.getPlayerList().size(); i++) {
                board.executePlayerAction(board.getPlayerInventoryList().get(i), board.getPlayerList().get(i));
            }
        }

        for (int i = board.getCurrentAge()+1; i <= board.AGES; i++){
                board.ageSetUp(currentAge);
                // Card dealing & resetting possibleFreeBuildingsCount
                for (Inventory playerInv : board.getPlayerInventoryList()) {
                    playerInv.setCardsInHand(board.drawCards(board.CARDS_NUMBER));
                    if (playerInv.getPossibleFreeBuildings() == -1) playerInv.setPossibleFreeBuildings(1);
                }

                for ( int j = 0; j < board.CARDS_NUMBER - 1; j++) {
                    // Each player plays a card on each turn
                    for (Player p : board.getPlayerList()) {
                        p.chooseCard(board.getPlayerInventoryList().get(p.getId()),board);
                    }
                    for (int k = 0; k < board.getPlayerList().size(); k++) {
                        board.executePlayerAction(board.getPlayerInventoryList().get(k), board.getPlayerList().get(k));
                    }
                }
        }

        // terminer le jeu
        //terminer la partie courante et l'age courant et passer aux autres ages
        // la carte chosie elle doit permettre au joueur de gagné plus de fois et aussi donner son meilleur score
        // 10 gagne
        // 40 perdu
        // si nbr gagne avec card1  = nbr gagne avec card2
        // verifie le score
        Inventory winner = board.getPlayerInventoryList().get(0) ;
        for (Inventory i : board.getPlayerInventoryList()) {
            if (i.getScore()>winner.getScore())
                winner = i;
        }
        return winner.getPlayerId();

    }
}
