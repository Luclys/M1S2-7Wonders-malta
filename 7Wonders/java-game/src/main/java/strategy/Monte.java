package strategy;

import board.Board;
import gameelements.GameLogger;
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
        if (inv.getPossibleFreeBuildings()>0){
            list.add(Action.BUILDFREE);
        }
        return list;
    }

    private void getBestChosenCardAndAction(ArrayList<ArrayList<Integer>> numberOfVictories, ArrayList<Card> cards) {
        int maxscore = 0;
        //board.log = new GameLogger(true);
        board.log.display("GET BEST CHOSEN CARD AND ACTION!!!!");
        board.log.display("TURN IS " + board.getCurrentTurn());
        board.log.display("CARDS ARE " + cards);
        board.log.playerInformation(inv);
        for (int cardIndex = 0; cardIndex < numberOfVictories.size(); cardIndex++) {
            ArrayList<Action> actions = availableActions(cards.get(cardIndex), inv);
            board.log.display("CARD IS " + cards.get(cardIndex).getName());
            //for (Action act: actions) {
                board.log.display("AVAILABLE ACTIONS ARE " + actions);
           // }
            board.log.display("ACTION INDEXES IN NUMBEROFVICT ARE " + numberOfVictories.get(cardIndex));
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

    public Card monteCarlo(){
        ArrayList<Card> availableCards = cardsAvailableToPlay(inv);
        int idwinner = 0;
        if (availableCards.size() != 0){
            //numberOfVictories[cardIndex][actionIndex] - number of victories if we choose cardIndex and actionIndex
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

                    //board.log = new GameLogger(true);
                    board.log.display("PLAYERS INITIAL BOARD WONDER ASSOCIATED INVS");
                    for (Inventory inbi : board.getPlayerInventoryList()) {
                        board.log.playerInformation(inbi.getWonderBoard().getAssociatedInv());
                    }
                    board.log = new GameLogger(false);

                    Board memoriseTheBoard = new Board(board);

                    memoriseTheBoard.getCommerce().log = new GameLogger(false);
                    memoriseTheBoard.executePlayerAction(memoriseTheBoard.getPlayerInventoryList().get(inv.getPlayerId()), memoriseTheBoard.getPlayerList().get(inv.getPlayerId()));

                    //memoriseTheBoard.log = new GameLogger(true);
                    memoriseTheBoard.log.display("PLAYERS BOARD WONDER ASSOCIATED INVS AFTER MODIFIACTIONS");
                    for (Inventory inbi : memoriseTheBoard.getPlayerInventoryList()) {
                        memoriseTheBoard.log.playerInformation(inbi.getWonderBoard().getAssociatedInv());
                    }
                    memoriseTheBoard.log = new GameLogger(false);

                    //board.log = new GameLogger(true);
                    board.log.display("PLAYERS INITIAL BOARD WONDER ASSOCIATED INVS AFTER MODIFIACTIONS");
                    for (Inventory inbi : board.getPlayerInventoryList()) {
                        board.log.playerInformation(inbi.getWonderBoard().getAssociatedInv());
                    }
                    board.log = new GameLogger(false);


                    for (int i = 0 ;i < 100; i++ ){
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
        return null;
    }

    public int continueGame(Board board, Inventory inventory){
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

        //board.log = new GameLogger(true);
        board.endOfAge();
        board.log = new GameLogger(false);

        //terminer toutes les ages restantes
        for (int age = board.getCurrentAge()+1; age <= board.AGES; age++){
                board.ageSetUp(age);
                // Card dealing & resetting possibleFreeBuildingsCount
                for (Inventory playerInv : board.getPlayerInventoryList()) {
                    playerInv.setCardsInHand(board.drawCards(board.CARDS_NUMBER));
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
                //board.log = new GameLogger(true);
                board.log.display("INSIDE THE LOOP");
                board.endOfAge();
                board.log = new GameLogger(false);
                /*for ( int turn = 0; turn < board.CARDS_NUMBER - 1; turn++) {
                    // Each player plays a card on each turn
                    for (Player p : board.getPlayerList()) {
                        p.chooseCard(board.getPlayerInventoryList().get(p.getId()),board);
                    }
                    for (int k = 0; k < board.getPlayerList().size(); k++) {
                        board.executePlayerAction(board.getPlayerInventoryList().get(k), board.getPlayerList().get(k));
                    }
                    board.endOfTurn();
                }*/
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
