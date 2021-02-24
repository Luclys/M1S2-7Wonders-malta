package board;

import gameelements.Inventory;
import gameelements.cards.Card;

import java.util.ArrayList;
import java.util.List;

public class CardManager {
    List<Inventory> playerInventoryList;

    public CardManager() {
        playerInventoryList = new ArrayList<>();
    }

    public CardManager(List<Inventory> playerInventoryList) {
        this.playerInventoryList = playerInventoryList;
    }

    public CardManager(CardManager cardManager) {
        this.playerInventoryList = cardManager.playerInventoryList;
    }

    protected void leftRotation() {
        ArrayList<Inventory> tmpList = new ArrayList<>(playerInventoryList);
        int leftNeighborId;
        List<Card> cards = playerInventoryList.get(playerInventoryList.size() - 1).getCardsInHand();
        for (Inventory i : playerInventoryList) {
            leftNeighborId = i.getLeftNeighborId();
            tmpList.get(leftNeighborId).setCardsInHand(playerInventoryList.get(i.getPlayerId()).getCardsInHand());
        }
        tmpList.get(tmpList.size() - 2).setCardsInHand(cards);
        playerInventoryList = tmpList;
    }


    protected void rightRotation() {
        List<Card> temp;
        List<Card> last;
        int i = 0;
        last = playerInventoryList.get(0).getCardsInHand();
        while (i < playerInventoryList.size()) {
            Inventory inv = playerInventoryList.get(i);
            Inventory invRight = playerInventoryList.get(inv.getRightNeighborId());
            temp = invRight.getCardsInHand();
            playerInventoryList.get(invRight.getPlayerId()).setCardsInHand(last);
            last = temp;
            i++;
        }

    }

    public void playersCardsRotation(boolean isLeftRotation) {
        if (isLeftRotation) {
            leftRotation();
        } else {
            rightRotation();
        }
    }


    public List<Inventory> getPlayerInventoryList() {
        return playerInventoryList;
    }
}
