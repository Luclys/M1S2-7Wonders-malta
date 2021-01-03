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

    public Card monteCarlo(){
        //board.log.display("Inventory initial\n");
        //board.log.playerInformation(inv);
        // l'inventaire et le board initial
        //Inventory copyTheInventory = new Inventory(inv);
        //Board memoriseTheBoard = new Board(board);
       // board.log.display("Inventory copy\n");
       // board.log.playerInformation(copyInventory);
        ArrayList<Card> availableCards = cardsAvailableToPlay(inv);

        int idwinner = 0;


        if (availableCards.size() != 0){

            ArrayList<Integer> numberofvictory = new ArrayList<>();
          //  for (Card c : availableCards){
            for (int j = 0 ; j < availableCards.size() ; j++){

                numberofvictory.add(0);
               // copyInventory = new Inventory(inv);
                ArrayList<Action> listActions = availableActions(availableCards.get(j),inv);
                for (Action action : listActions ){

                    Inventory copyTheInventory = new Inventory(inv);
                    Board memoriseTheBoard = new Board(board);
                   // memoriseTheBoard.log.display("[CARDS TO PLAY Monte ]"+availableCards);
                   // memoriseTheBoard.log.display("[CARDS TO PLAY Monte ]"+listActions);
                   /* for (int i = 0 ; i < board.getPlayerInventoryList().size() ;i++){
                        memoriseTheBoard.log.display("[Verify]"+(memoriseTheBoard.getPlayerInventoryList().get(i) == board.getPlayerInventoryList().get(i)));
                    }*/




                    //board = memoriseTheBoard ;
                    chosenCard = availableCards.get(j);
                    chosenAction  = action;
                    memoriseTheBoard.log.display("[Chosen card monte] "+ chosenCard);
                    memoriseTheBoard.log.display("[Chosen card monte by board] "+ board.getPlayerList().get(inv.getPlayerId()).getChosenCard());
                    memoriseTheBoard.log.display("[Chosen Action monte] "+ chosenAction);
                    memoriseTheBoard.log.display("[Chosen card monte by board] "+ board.getPlayerList().get(inv.getPlayerId()).getAction());
                    memoriseTheBoard.log.display("Inv monte initaal "+inv.getPlayerId());
                    memoriseTheBoard.log.display("Inv monte avant "+memoriseTheBoard.getPlayerInventoryList().get(inv.getPlayerId()).getPlayerId());

                   /* board.log.display("[Execute Monte] Inventory inv\n");
                    board.log.playerInformation(inv);
                    board.log.display("[Execute Monte] Inventory copyTheInventory\n");
                    board.log.playerInformation(copyTheInventory);*/
                    //inv = new Inventory( copyTheInventory);
                    //board.log.display("[Execute Monte] card "+chosenCard+" action "+chosenAction);
                    board.log.display("[Before Execute Monte] \n");
                    board.log.display("[Available Cadrs before monte ]"+availableCards);
                    memoriseTheBoard.log.playerInformation(memoriseTheBoard.getPlayerInventoryList().get(inv.getPlayerId()));

                    memoriseTheBoard.log.display("[Chosen card monte by player] "+ memoriseTheBoard.getPlayerList().get(inv.getPlayerId()).getChosenCard());
                    memoriseTheBoard.log.display("[Chosen Action monte by player] "+ memoriseTheBoard.getPlayerList().get(inv.getPlayerId()).getAction());

                    memoriseTheBoard.executePlayerAction(memoriseTheBoard.getPlayerInventoryList().get(inv.getPlayerId()), memoriseTheBoard.getPlayerList().get(inv.getPlayerId()));
                    board.log.display("[After Execute Monte] \n");
                    memoriseTheBoard.log.playerInformation(memoriseTheBoard.getPlayerInventoryList().get(inv.getPlayerId()));

                    /*
                    for (Inventory n : board.getPlayerInventoryList()){
                        board.log.display("[Execute Monte board before monte] \n");
                        board.log.playerInformation(n);
                    }*/
                    /*for (Inventory n : memoriseTheBoard.getPlayerInventoryList()){
                        memoriseTheBoard.log.display("[Execute Monte Memorise After monte] \n");
                        memoriseTheBoard.log.playerInformation(n);
                    }*/
                    /*
                    memoriseTheBoard.log.display("[Execute Monte] Inventory inv\n");
                    memoriseTheBoard.log.playerInformation(inv);
                    memoriseTheBoard.log.display("[Execute Monte] Inventory copyTheInventory\n");
                    memoriseTheBoard.log.playerInformation(copyTheInventory);*/
                    // la il faut memoriser le board et l'inventaire.
                   // memoriseInventory = new Inventory(inv);
                    /*for (Inventory n : memoriseTheBoard.getPlayerInventoryList()){
                        memoriseTheBoard.log.display("[Execute Monte Invetories] \n");
                        memoriseTheBoard.log.playerInformation(n);
                    }*/

                    // calculer le nombre de fois que cette card nous a permis de gagner
                    // for ici
                    for (int i = 0 ;i < 1 ; i++ ){
                        // recuperer l'ancien board apres le choix de monte
                        idwinner = continueGame(memoriseTheBoard,copyTheInventory);
                        if (idwinner == copyTheInventory.getPlayerId()){
                            numberofvictory.set(j,numberofvictory.get(j)+1); // aussi donné l'action joue
                        }
                      //  memoriseTheBoard.log.display("[Execute Monte Fin] \n");
                    }
                   /* memoriseTheBoard = new Board(board);
                    //board = (Board)((memoriseTheBoard.clone();
                    copyTheInventory =  new Inventory(inv);*/

                }
            }
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
        board.log.display("[##############################################]");

        //chaque player joue carte sauf le player actuel
        for(Inventory p : board.getPlayerInventoryList()){
            if(inventory.getPlayerId()!=p.getPlayerId()){
                board.log.playerInformation(p);
                board.executePlayerAction(p, board.getPlayerList().get(p.getPlayerId()));
                board.log.playerInformation(p);
            }
        }
        board.endOfTurn();
        board.log.display("LEFT ROTATION TURN " +board.isLeftRotation());
        board.log.display("End of Turn");
        for (Inventory i : board.getPlayerInventoryList()){
            board.log.playerInformation(i);
        }
        //termer l'age
        for ( int currentTurn = board.getCurrentTurn()+1; currentTurn < board.CARDS_NUMBER - 1; currentTurn++) {
            // Each player plays a card on each turn
            board.log.newTurn(currentTurn);
            for (Player p : board.getPlayerList()) {
                p.chooseCard(board.getPlayerInventoryList().get(p.getId()),board);
            }
            for (int i = 0; i < board.getPlayerList().size(); i++) {
                board.log.playerInformation(board.getPlayerInventoryList().get(i));
                board.executePlayerAction(board.getPlayerInventoryList().get(i), board.getPlayerList().get(i));
                board.log.playerInformation(board.getPlayerInventoryList().get(i));
            }
            board.endOfTurn();
            board.log.display("LEFT ROTATION " +board.isLeftRotation()); // la mmem chose
            board.log.display("End of Turn");
            for (Inventory i : board.getPlayerInventoryList()){
                board.log.playerInformation(i);
            }

        }
        board.endOfAge();
        board.log.display("End of Age");
        board.log.display("LEFT ROTATION AGE " +board.isLeftRotation()); // de

        // terminer les ages
        board.log.endOfAge(board.getCurrentAge());
        for (int i = board.getCurrentAge()+1; i <= board.AGES; i++){
                board.ageSetUp(currentAge);
                board.log.beginningOfAge(i);
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
                        board.log.display("[Before Execute player] \n");
                        board.log.playerInformation(board.getPlayerInventoryList().get(k));
                        board.executePlayerAction(board.getPlayerInventoryList().get(k), board.getPlayerList().get(k));
                        board.log.display("[After Execute player] \n");
                        board.log.playerInformation(board.getPlayerInventoryList().get(k));
                    }
                    board.endOfTurn();
                    board.log.display("LEFT ROTATION " +board.isLeftRotation()); // la mmem chose
                    board.log.display("End of Turn");
                    for (Inventory a : board.getPlayerInventoryList()){
                        board.log.playerInformation(a);
                    }
                }

            board.endOfAge();
            board.log.display("End of Age");
            board.log.display("LEFT ROTATION AGE " +board.isLeftRotation()); // de
        }
        board.endOfGame();

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
        board.log.display("[Execute Monte Fin Continue] \n");
        return winner.getPlayerId();

    }
}
