package board;

import gameelements.Effect;
import gameelements.Inventory;
import gameelements.Player;
import gameelements.cards.Card;
import gameelements.cards.CardsSet;
import gameelements.effects.ResourceEffect;
import gameelements.effects.ScoreEffect;
import gameelements.enums.Category;
import gameelements.enums.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class PlayerTest {

    final ArrayList<Card> cards = new ArrayList<>(7);
    private Player player;
    private Inventory inv;
    private Board board;
    private ArrayList<Player> playerList;

    @BeforeEach
    public void setUp() {
        playerList = new ArrayList<>(3);
        for (int i = 0; i < 3; i++) {
            Player player = new Player(i);
            playerList.add(player);
        }
        board = new Board(playerList, false);
        player = board.getPlayerList().get(0);
        inv = board.getPlayerInventoryList().get(player.getId());
        for (int i = 0; i < 7; i++) {
            cards.add(new Card("DUMMY", new Effect[]{new ScoreEffect(1), new ResourceEffect(Resource.BOIS, 1)}, null, Category.BATIMENT_CIVIL));
        }
        inv.setCardsInHand(cards);
    }

    @Test
    public void playCardTest() {
        assertEquals(0, inv.getScore());
        Card playedCard = player.chooseCard(inv);
        inv.updateInventory(playedCard, player, null, null);
        assertEquals(1, inv.getScore());
        assertNotEquals(inv.getCardsInHand().size(), new Inventory(2).getCardsInHand().size());


        //test if player can't build two identical buildings
        Card taverne = CardsSet.TAVERNE;
        inv.getCardsInHand().set(0, taverne);
        inv.updateInventory(player.chooseCard(inv), player, null, null);
        inv.getCardsInHand().set(0, taverne);
        assertNotEquals(player.chooseCard(inv), taverne);
    }

    @Test
    public void updateAvailableResourcesTest() {
        assertEquals(0, inv.getAvailableResources()[Resource.BOIS.getIndex()]);
        inv.setCardsInHand(cards);
        inv.updateInventory(player.chooseCard(inv), player, null, null);
        assertEquals(1, inv.getAvailableResources()[Resource.BOIS.getIndex()]);
        assertEquals(0, inv.getAvailableResources()[Resource.MINERAI.getIndex()]);
    }

    @Test
    public void discardLastCardTest() {
        assertThrows(Error.class, () -> inv.discardLastCard());
        while (inv.getCardsInHand().size() > 1) {
            inv.updateInventory(player.chooseCard(inv), player, null, null);
        }
        Card lastCard = inv.getCardsInHand().get(0);
        assertSame(lastCard, inv.discardLastCard());
        assertTrue(inv.getCardsInHand().isEmpty());
    }


    @Test
    public void addCoinsTest() {
        inv.setCoins(0);
        inv.addCoins(5);
        assertEquals(5, inv.getCoins());
    }

    @Test
    public void removeCoinsTest() {
        inv.setCoins(5);
        inv.removeCoins(3);
        assertEquals(inv.getCoins(), 2);
    }

    @Test
    public void fightWithNeighborTest() {
        Card bouclierCard = CardsSet.PALISSADE;

        Player rightNeighbour = board.getPlayerList().get(player.getRightNeighborId());
        Inventory rightNeighbourInv = board.getPlayerInventoryList().get(rightNeighbour.getId());
        Player leftNeighbour = board.getPlayerList().get(player.getLeftNeighborId());
        Inventory leftNeighbourInv = board.getPlayerInventoryList().get(leftNeighbour.getId());

        cards.set(0, bouclierCard);
        rightNeighbourInv.setCardsInHand(cards);
        rightNeighbourInv.updateInventory(rightNeighbour.chooseCard(inv), player, null, null); //Neighbor has 1 bouclier
        cards.set(0, bouclierCard);
        leftNeighbourInv.setCardsInHand(cards);
        leftNeighbourInv.updateInventory(leftNeighbour.chooseCard(inv), player, null, null);

        board.resolveWarConflict(1);
        assertEquals(0, inv.getVictoryChipsScore());
        assertEquals(2, inv.getDefeatChipsCount());

        assertEquals(1, leftNeighbourInv.getVictoryChipsScore());
        assertEquals(0, leftNeighbourInv.getDefeatChipsCount());

        assertEquals(1, rightNeighbourInv.getVictoryChipsScore());
        assertEquals(0, leftNeighbourInv.getDefeatChipsCount());
    }

    private void addCardAndPlayIt(Player player, Card card) {
        cards.set(0, card);
        inv.setCardsInHand(cards);
        inv.updateInventory(player.chooseCard(inv), player, null, null);
    }


    @Test
    void UpdatePlayer() {
        assertEquals(0, inv.getAvailableResources()[Resource.BOIS.getIndex()]);
        inv.updateInventory(player.chooseCard(inv), player, null, null);
        assertEquals(1, inv.getAvailableResources()[Resource.BOIS.getIndex()]);
    }
}