package board;

import effects.CoinEffect;
import effects.ResourceEffect;
import effects.ScoreEffect;
import effects.SymbolEffect;
import gameelements.Card;
import gameelements.Effect;
import gameelements.Inventory;
import gameelements.enums.Category;
import gameelements.enums.Resource;
import gameelements.enums.Symbol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class PlayerTest {

    final ArrayList<Card> cards = new ArrayList<>(7);
    private Player player;
    private Inventory inv;
    private Board board;

    @BeforeEach
    public void setUp() {
        board = new Board(3);
        player = board.getPlayerList().get(0);
        inv = board.getPlayerInventoryList().get(player.getId());
        for (int i = 0; i < 7; i++) {
            cards.add(new Card("CHANTIER", new Effect[]{new ScoreEffect("", 1), new ResourceEffect("", Resource.BOIS, 1)}, null, Category.BATIMENT_CIVIL));
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
        Card taverne = new Card("TAVERNE", new CoinEffect("", 5), null, Category.BATIMENT_COMMERCIEAU);
        inv.getCardsInHand().set(0, taverne);
        inv.updateInventory(player.chooseCard(inv));
        inv.getCardsInHand().set(0, taverne);
        assertNotEquals(player.chooseCard(inv), taverne);
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
        assertEquals(inv.getCoins(), 2);
    }

    @Test
    public void fightWithNeighborTest() {
        Card bouclierCard = new Card("BOUCLIER", new SymbolEffect("", Symbol.BOUCLIER, 1), null, Category.BATIMENT_MILITAIRE);

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

        board.resolveWarConflict();
        assertEquals(-2, inv.getConflictPoints());
        assertEquals(1, leftNeighbourInv.getConflictPoints());
        assertEquals(1, rightNeighbourInv.getConflictPoints());
    }

    private void addCardAndPlayIt(Player player, Card card) {
        cards.set(0, card);
        inv.setCardsInHand(cards);
        inv.updateInventory(player.chooseCard(inv));
    }

    @Test
    void missingResourcesTest() {
        Card c = new Card("CHANTIER", new Effect[]{new ScoreEffect("", 1), new ResourceEffect("", Resource.BOIS, 1)}, new Resource[]{Resource.BOIS}, Category.BATIMENT_CIVIL);
        ArrayList<Resource> m = player.missingResources(inv, c);
        assertEquals(Resource.BOIS, m.get(0));
    }

    @Test
    void UpdatePlayer() {
        assertEquals(0, inv.getAvailableResources()[Resource.BOIS.getIndex()]);
        inv.updateInventory(player.chooseCard(inv));
        assertEquals(1, inv.getAvailableResources()[Resource.BOIS.getIndex()]);
    }
}