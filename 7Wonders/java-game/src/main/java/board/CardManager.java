package board;

import gameelements.Card;
import gameelements.Inventory;
import gameelements.Player;

import java.util.ArrayList;

public class CardManager {
    ArrayList<Player> playerList;
    ArrayList<Inventory> playerInventoryList;

    public CardManager(ArrayList<Player> playerList, ArrayList<Inventory> playerInventoryList) {
        this.playerList = playerList;
        this.playerInventoryList = playerInventoryList;
    }

    protected void leftRotation() {
        ArrayList<Inventory> tmpList = new ArrayList<>(playerInventoryList);
        int leftNeighborId;
        ArrayList<Card> cards = playerInventoryList.get(playerInventoryList.size() - 1).getCardsInHand();
        for (Inventory i : playerInventoryList) {
            leftNeighborId = playerList.get(i.getPlayerId()).getLeftNeighborId();
            tmpList.get(leftNeighborId).setCardsInHand(playerInventoryList.get(i.getPlayerId()).getCardsInHand());
        }
        tmpList.get(tmpList.size() - 2).setCardsInHand(cards);
        playerInventoryList = tmpList;
    }


    protected void rightRotation() {
        ArrayList<Card> temp, last;
        int i = 0;
        last = playerInventoryList.get(0).getCardsInHand();
        while (i < playerInventoryList.size()) {
            temp = playerInventoryList.get(playerList.get(i).getRightNeighborId()).getCardsInHand();
            playerInventoryList.get(playerList.get(i).getRightNeighborId()).setCardsInHand(last);
            last = temp;
            i++;
        }
    }
}
