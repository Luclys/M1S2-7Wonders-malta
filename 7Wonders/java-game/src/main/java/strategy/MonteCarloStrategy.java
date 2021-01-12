package strategy;

import board.Board;
import board.Trade;
import gameelements.Inventory;
import gameelements.Player;
import gameelements.cards.Card;
import gameelements.enums.Action;

import java.util.ArrayList;

public class MonteCarloStrategy implements PlayingStrategy {
    Board initialBoard;
    Inventory initialInventory;

    Action chosenAction;
    Card chosenCard;

    Board curBoard;
    Inventory curInv;

    int DEPTH = 1000;
    boolean ALL_AGES_CALCULATED = false;
    boolean CHOOSE_BY_SCORE = true;

    @Override
    public PlayingStrategy copy() {
        return this;
    }

    @Override
    public void updateKnowledge(ArrayList<Inventory> censoredInvList, int age, int currentTurn, int rightNeighborId, int leftNeighborId) {

    }

    @Override
    public Card chooseCard(Inventory inventory, Board board) throws Exception {
        initialBoard = new Board(board);
        initialInventory = inventory;
        monteCarlo(DEPTH);
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

    private void getBestChosenCardAndAction(ArrayList<ArrayList<Integer>> resultsForCardAndAction, ArrayList<Card> cards) {
        int bestResult = 0;
        for (int cardIndex = 0; cardIndex < resultsForCardAndAction.size(); cardIndex++) {
            ArrayList<Action> availableActions = availableActions(cards.get(cardIndex), initialInventory);
            for (int actionIndex = 0; actionIndex < resultsForCardAndAction.get(cardIndex).size(); actionIndex++) {
                if (resultsForCardAndAction.get(cardIndex).get(actionIndex) > bestResult) {
                    bestResult = resultsForCardAndAction.get(cardIndex).get(actionIndex);
                    chosenCard = cards.get(cardIndex);
                    chosenAction = availableActions.get(actionIndex);
                }
            }
        }
    }

    public Card monteCarlo(int depth) throws Exception {
        boolean print = initialBoard.log.getBooleanPrint();
        if (print) {
            initialBoard.log.setBooleanPrint(false);
            Trade.log.setBooleanPrint(false);
        }
        ArrayList<Card> availableCards = cardsAvailableToPlay(initialInventory);
        if (availableCards.size() != 0) {
            ArrayList<ArrayList<Integer>> resultsForCardAndAction = new ArrayList<>();
            for (int cardIndex = 0; cardIndex < availableCards.size(); cardIndex++) {
                ArrayList<Action> listActions = availableActions(availableCards.get(cardIndex), initialInventory);
                resultsForCardAndAction.add(new ArrayList<>());
                for (int actionIndex = 0; actionIndex < listActions.size(); actionIndex++) {
                    resultsForCardAndAction.get(cardIndex).add(0);

                    chosenCard = availableCards.get(cardIndex);
                    chosenAction = listActions.get(actionIndex);

                    Inventory monteCarloInventory = new Inventory(initialInventory);
                    Board monteCarloBoard = new Board(initialBoard);

                    monteCarloBoard.executePlayerAction(
                            monteCarloBoard.getPlayerInventoryList().get(initialInventory.getPlayerId()),
                            monteCarloBoard.getPlayerList().get(initialInventory.getPlayerId())
                    );

                    for (int i = 0; i < depth; i++) {
                        //playerIdWithBestResult = continueGame(new Board(monteCarloBoard), monteCarloInventory);
                        continueGame(new Board(monteCarloBoard), monteCarloInventory);

                        if (CHOOSE_BY_SCORE) {
                            resultsForCardAndAction.get(cardIndex).set(
                                    actionIndex,
                                    resultsForCardAndAction.get(cardIndex).get(actionIndex) + curBoard.getPlayerInventoryList().get(monteCarloInventory.getPlayerId()).getScore()
                            );
                        } else {
                            //if current player is a winner, we increment resultsForCardAndAction for chosen card and action
                            Inventory winner = curBoard.getPlayerInventoryList().get(0);
                            for (Inventory inv : curBoard.getPlayerInventoryList()) {
                                if (inv.getScore() > winner.getScore())
                                    winner = inv;
                            }
                            if (winner.getPlayerId() == monteCarloInventory.getPlayerId()) {
                                resultsForCardAndAction.get(cardIndex).set(actionIndex, resultsForCardAndAction.get(cardIndex).get(actionIndex) + 1);
                            }
                        }
                    }
                }
            }
            getBestChosenCardAndAction(resultsForCardAndAction, availableCards);
        } else {
            chosenCard = initialInventory.getCardsInHand().get(0);
            chosenAction = Action.SELL;
        }
        if (print) {
            initialBoard.log.setBooleanPrint(true);
            Trade.log.setBooleanPrint(true);
        }
        return chosenCard;
    }

    private void finishTurn(int idToExclude) throws Exception {
        for (Player player : curBoard.getPlayerList()) {
            if (player.getId() != idToExclude) {
                Inventory playerInventory = curBoard.getPlayerInventoryList().get(player.getId());
                player.acknowledgeGameStatus((ArrayList<Inventory>) curBoard.getPlayerInventoryList(), curBoard.getCurrentAge(), curBoard.getCurrentTurn());
                player.chooseCard(playerInventory, curBoard);
                curBoard.executePlayerAction(playerInventory, player);
            }
        }
        curBoard.endOfTurn();
    }

    private void finishAge() throws Exception {
        while (curBoard.getPlayerInventoryList().get(0).getCardsInHand().size() > 1) {
            finishTurn(-1);
        }
        curBoard.endOfAge();
    }

    public void continueGame(Board board, Inventory inv) throws Exception {
        curBoard = new Board(board);
        curInv = new Inventory(inv);
        //set base strategy
        curBoard.getPlayerList().get(curInv.getPlayerId()).setStrategy(new WonderStrategy());

        //finish current turn and current age for other players
        finishTurn(curInv.getPlayerId());
        finishAge();

        if (ALL_AGES_CALCULATED) {
            //finish all the ages
            for (int age = curBoard.getCurrentAge() + 1; age <= Board.AGES; age++) {
                //ageSetUp, card dealing & resetting possibleFreeBuildingsCount
                curBoard.ageSetUp(age);
                for (Inventory playerInv : curBoard.getPlayerInventoryList()) {
                    playerInv.setCardsInHand(curBoard.drawCards(Board.CARDS_NUMBER));
                    if (playerInv.getPossibleFreeBuildings() == -1) playerInv.setPossibleFreeBuildings(1);
                }
                finishAge();
            }
            curBoard.endOfGame();
        }
    }
}
