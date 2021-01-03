package strategy;

import board.Board;
import gameelements.Inventory;
import gameelements.Player;
import gameelements.cards.Card;
import gameelements.enums.Action;

import java.util.ArrayList;

public class MonteCarloStrategy implements PlayingStrategy {
    Action action;
    Board board;
    Card chosen;
    Inventory inv ;


    public MonteCarloStrategy(Board board) {
       // this.board = new Board(board);
    }

    @Override
    public Card chooseCard(Inventory inventory, Board b) {
        this.board = new Board(b);
        inv = new Inventory(inventory);
        return monteCarlo(inv);
    }

    public Inventory getInv() {
        return inv;
    }

    @Override
    public Action getAction() {
        board.log.display("[TEST] GET ACTION "+ action);
        return action;
    }


    public Card monteCarlo(Inventory inv) {
        int max = 0;
        chosen = inv.getCardsInHand().get(0) ;
        action = Action.SELL;
        ArrayList<Card> available = cardsAvailableToPlay(inv);
        board.log.display("Availlable card to play "+available );
        Inventory inventory;
        for (Card c : available) {
            //card <- list des possible qctions
            ArrayList<Action> listActions = availableActions(c,inv);
            board.log.display("Availlable action to play with card  "+listActions+" "+ c );
            chosen = c;
            for (Action act : listActions) {
                board.log.display(" action to play with card  "+act+" "+ c );
                action = act;
               // chosen = c;
                Board tmpBoard = new Board(board);
                tmpBoard.getPlayerInventoryList().get(inv.getPlayerId()).set(inv);
                board.log.display("Avant");
                board.log.playerInformation(inv); // stable
                board.log.playerInformation(tmpBoard.getPlayerInventoryList().get(inv.getPlayerId())); // have to change
                board.log.playerInformation(board.getPlayerInventoryList().get(inv.getPlayerId())); // should be stable

                tmpBoard.executePlayerAction(tmpBoard.getPlayerInventoryList().get(inv.getPlayerId()), tmpBoard.getPlayerList().get(inv.getPlayerId()));
                board.log.display("Apres");
                board.log.playerInformation(inv);
                board.log.playerInformation(tmpBoard.getPlayerInventoryList().get(inv.getPlayerId()));
                board.log.playerInformation(board.getPlayerInventoryList().get(inv.getPlayerId()));

/*              Board tmpBoard = new Board(board);
                inventory = new Inventory(this.inv);
                action = act;
                tmpBoard.log.display(" TRY ACTION with CARD "+ act + c );
                int gain = 0;
                // play this card ;
                // 2 chose card des players
                tmpBoard.log.display("Avant");
                tmpBoard.log.playerInformation(this.inv);
                tmpBoard.executePlayerAction(tmpBoard.getPlayerInventoryList().get(inventory.getPlayerId()), tmpBoard.getPlayerList().get(inventory.getPlayerId()));
                tmpBoard.log.display("Apres");
                tmpBoard.log.playerInformation(this.inv);
                // memorise plateau;
                // tmpBoard2 = new Board(board);
                int winner = -1;
                    for (int i = 0; i < 10; i++) {
                        //  continue le jeu.

                        //winner = continuePlay(inventory);
                        winner = 0 + (int)(Math.random() * ((3 - 1) + 1));
                        board.log.display("[TEST] WINNERR "+winner);
                        if (inventory.getPlayerId() == winner)
                            gain++;
                        //revenir au plateau de jeu memoriser
                    //    board = new Board(tmpBoard);
                    }
                    if (gain > max) {
                        board.log.display("[TEST] MAX EST CHANGE");
                        max = gain;
                        card = c;
                        max_action = act;
                        board.log.display("[TEST] CHOSEN ACTION "+max_action);
                        board.log.display("[TEST] CHOSEN CARD "+card);
                    }

 */
                }

        }
        board.log.display("TTTTTTTTTTTTTTTTTTTT");
        board.log.playerInformation(this.inv);
        return chosen;
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

    public Card getCard(){
        board.log.display("[TEST] GET CARD "+ chosen);
        return chosen;
    }

    public int continuePlay(Inventory inv) {
        int idWinner = 0;
        for (Player p: board.getPlayerList()){
            p.setStrategy(new FirstCardStrategy());
        }

        // finish the current turn
        for (int j = board.getCurrentTurn(); j < Board.CARDS_NUMBER - 1; j++) {
            board.log.display("[TEST] current turn "+j);
            for(Player p : board.getPlayerList()){
                if(inv.getPlayerId()!=p.getId()){
                    board.log.display("[TEST] player choose "+ p);
                    p.chooseCard(new Inventory(board.getPlayerInventoryList().get(p.getId())),board);
                }
            }
            for(Player p : board.getPlayerList()){
                board.log.display("[TEST] EXCUTE "+ p);
                if(inv.getPlayerId()!=p.getId()){
                    board.log.display("[TEST] ACTION " + p.getChosenCard());
                    board.executePlayerAction(board.getPlayerInventoryList().get(p.getId()), board.getPlayerList().get(p.getId()));
            }}
            board.endOfTurn();
            board.log.display("[TEST] end turn "+j);
        }
        /*
        // finish next ages
        for (int i = board.getCurrentAge() + 1; i < board.AGES+1; i++) {
            board.log.display("STRAT AGEEE "+i);
            board.ageSetUp(i);
            for (Inventory inventory : board.getPlayerInventoryList()) {
                inventory.setCardsInHand(board.drawCards(board.CARDS_NUMBER));
                if (inventory.getPossibleFreeBuildings() == -1) inventory.setPossibleFreeBuildings(1);
            }
            // do all turns
            for (int currentTurn = 0; currentTurn < Board.CARDS_NUMBER - 1; currentTurn++) {
                board.log.display("START TUUUUUUUUUUURN"+currentTurn+1);
                // Each player plays a card on each turn
                for (Player p : board.getPlayerList()) {
                    p.chooseCard(new Inventory(board.getPlayerInventoryList().get(p.getId())));
                }
                // execute the action of players
                for (int p = 0; p < board.getPlayerList().size(); p++) {
                    board.executePlayerAction(board.getPlayerInventoryList().get(p), board.getPlayerList().get(p));
                }
                board.log.display("END TUUUUUUUUUUURN"+currentTurn+1);
                // , freebuilding, update coins , rotation card
                board.endOfTurn();
            }
            board.log.display("End AAAAAAAAAAAGE");
            // discard last card , conflict
            board.endOfAge();
        }
        // score
        board.endOfGame();

        Optional<Inventory> i =board.getPlayerInventoryList().stream()
                .sorted((p1,p2)-> Integer.compare(p1.getScore(),p2.getScore()))
                .findFirst();

        return i.get().getPlayerId();*/
        return 2;
    }
}