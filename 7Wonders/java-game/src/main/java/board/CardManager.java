package board;

import gameelements.Inventory;
import gameelements.Player;
import gameelements.cards.Card;

import java.util.ArrayList;
import java.util.List;

public class CardManager {
    List<Player> playerList;
    List<Inventory> playerInventoryList;

    public CardManager() {
        playerInventoryList = new ArrayList<>();
        playerList = new ArrayList<>();
    }

    public CardManager(List<Player> playerList, List<Inventory> playerInventoryList) {
        this.playerList = playerList;
        this.playerInventoryList = playerInventoryList;
    }

    protected void leftRotation() {
        ArrayList<Inventory> tmpList = new ArrayList<>(playerInventoryList);
        int leftNeighborId;
        List<Card> cards = playerInventoryList.get(playerInventoryList.size() - 1).getCardsInHand();
        for (Inventory i : playerInventoryList) {
            leftNeighborId = playerList.get(i.getPlayerId()).getLeftNeighborId();
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
            temp = playerInventoryList.get(playerList.get(i).getRightNeighborId()).getCardsInHand();
            playerInventoryList.get(playerList.get(i).getRightNeighborId()).setCardsInHand(last);
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

    public List<Player> getPlayerList() {
        return playerList;
    }

    public List<Inventory> getPlayerInventoryList() {
        return playerInventoryList;
    }
}
