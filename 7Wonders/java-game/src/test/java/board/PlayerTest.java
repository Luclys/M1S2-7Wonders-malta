package board;

import gameelements.Card;
import gameelements.CardsSet;
import gameelements.Effect;
import gameelements.Inventory;
import gameelements.effects.ResourceEffect;
import gameelements.effects.ScoreEffect;
import gameelements.enums.Category;
import gameelements.enums.Resource;
import org.junit.jupiter.api.Assertions;
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
        playerList = new ArrayList<>();
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
        inv.updateInventory(playedCard);
        assertEquals(1, inv.getScore());
        assertNotEquals(inv.getCardsInHand().size(), new Inventory(2).getCardsInHand().size());


        //test if player can't build two identical buildings
        Card taverne = CardsSet.TAVERNE;
        inv.getCardsInHand().set(0, taverne);
        inv.updateInventory(player.chooseCard(inv));
        inv.getCardsInHand().set(0, taverne);
        assertNotEquals(taverne,player.chooseCard(inv));
    }

    @Test
    public void updateAvailableResourcesTest() {
        assertEquals(0, inv.getAvailableResources()[Resource.BOIS.getIndex()]);
        inv.setCardsInHand(cards);
        inv.updateInventory(player.chooseCard(inv));
        assertEquals(1, inv.getAvailableResources()[Resource.BOIS.getIndex()]);
        assertEquals(0, inv.getAvailableResources()[Resource.MINERAI.getIndex()]);
    }

    @Test
    public void discardLastCardTest() {
        assertThrows(Error.class, () -> inv.discardLastCard());
        while (inv.getCardsInHand().size() > 1) {
            inv.updateInventory(player.chooseCard(inv));
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
        assertEquals(2, inv.getCoins());
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
        rightNeighbourInv.updateInventory(rightNeighbour.chooseCard(inv)); //Neighbor has 1 bouclier
        cards.set(0, bouclierCard);
        leftNeighbourInv.setCardsInHand(cards);
        leftNeighbourInv.updateInventory(leftNeighbour.chooseCard(inv));

        board.resolveWarConflict(1);
        assertEquals(0, inv.getVictoryJetonsScore());
        assertEquals(2, inv.getDefeatJetonsCount());

        assertEquals(1, leftNeighbourInv.getVictoryJetonsScore());
        assertEquals(0, leftNeighbourInv.getDefeatJetonsCount());

        assertEquals(1, rightNeighbourInv.getVictoryJetonsScore());
        assertEquals(0, leftNeighbourInv.getDefeatJetonsCount());
    }

    @Test
    void updateInventoryTest() {
        assertEquals(0, inv.getAvailableResources()[Resource.BOIS.getIndex()]);
        inv.updateInventory(player.chooseCard(inv));
        assertEquals(1, inv.getAvailableResources()[Resource.BOIS.getIndex()]);
    }
}