package strategy;

import board.Board;
import gameelements.Inventory;
import gameelements.Player;
import gameelements.cards.Card;
import gameelements.enums.Action;

import java.util.ArrayList;
import java.util.Optional;

public class MonteCarloStrategy implements PlayingStrategy {
    Action action;
    Board board;
    int age;
    int turn;

    public MonteCarloStrategy(Board board, int age, int turn) {
        this.board = board;
        this.age = age;
        this.turn = turn;
    }

    @Override
    public Card chooseCard(Inventory inventory) {
        return null;
    }

    @Override
    public Action getAction() {
        return null;
    }


    public Card monteCarlo(Inventory inventory) {
        int max = 0;
        Card card = null;
        Action a = Action.SELL;
        ArrayList<Card> available = cardsAvailableToPlay(inventory);
        for (Card c : available) {
            int gain = 0;
            // play this card ;
            board.executePlayerAction(inventory, board.getPlayerList().get(inventory.getPlayerId()));
            // memorise plateau;
            Board tmpBoard = new Board(board);
            int winner = -1;
            for (Action act : Action.values()) {
                for (int i = 0; i < 100; i++) {
                    //  continue le jeu.
                    winner = continuePlay(inventory);
                    if (inventory.getPlayerId() == winner)
                        gain++;
                    //revenir au plateau de jeu memoriser
                    board = tmpBoard;
                }
                if (gain > max) {
                    max = gain;
                    card = c;
                    action = a;
                }
            }
        }
        return card;
    }

    public int continuePlay(Inventory inv) {
        int idWinner = 0;
        for (Player p: board.getPlayerList()){
            p.setStrategy(new FirstCardStrategy());
        }
        for (int j = turn; j < Board.CARDS_NUMBER - 1; j++) {
            for (int p = inv.getPlayerId()+1; p < board.getPlayerList().size(); p++) {
                board.getPlayerList().get(p).chooseCard(new Inventory(board.getPlayerInventoryList().get(p)));
            }
            for (int p = 0; p < board.getPlayerList().size(); p++) {
                board.executePlayerAction(board.getPlayerInventoryList().get(p), board.getPlayerList().get(p));
            }
            board.endOfTurn();
        }

        for (int i = age + 1; i < board.AGES; i++) {
            board.ageSetUp(i);
            for (Inventory inventory : board.getPlayerInventoryList()) {
                inventory.setCardsInHand(board.drawCards(board.CARDS_NUMBER));
                if (inventory.getPossibleFreeBuildings() == -1) inventory.setPossibleFreeBuildings(1);
            }
            for (int currentTurn = 0; currentTurn < Board.CARDS_NUMBER - 1; currentTurn++) {

                // Each player plays a card on each turn
                for (Player p : board.getPlayerList()) {
                    p.chooseCard(new Inventory(board.getPlayerInventoryList().get(p.getId())));
                }
                for (int p = 0; p < board.getPlayerList().size(); p++) {
                    board.executePlayerAction(board.getPlayerInventoryList().get(p), board.getPlayerList().get(p));
                }

                board.endOfTurn();
            }
            board.endOfTurn();
        }
        board.endOfGame();

        Optional<Inventory> i =board.getPlayerInventoryList().stream()
                .sorted((p1,p2)-> Integer.compare(p1.getScore(),p2.getScore()))
                .findFirst();

        return i.get().getPlayerId();
    }
}