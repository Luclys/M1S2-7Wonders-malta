package board;

import effects.ResourceEffect;
import effects.ScoreEffect;
import effects.SymbolEffect;
import gameelements.Card;
import gameelements.Effect;
import gameelements.Inventory;
import gameelements.enums.Resource;
import gameelements.enums.Symbol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
        player = new Player(3);
        inv = board.getPlayerInventoryList().get(0);
        for (int i = 0; i < 7; i++) {
            cards.add(new Card("CHANTIER", new Effect[]{new ScoreEffect("", 1), new ResourceEffect("",Resource.BOIS, 1)}, null));
        }
        inv.setCards(cards);
    }

    @Test
    public void playCardTest() {
        assertEquals(0, inv.getScore());
        Card playedCard = player.playCard(inv);
        inv.updateInventory(playedCard);
        assertEquals(1, inv.getScore());
        assertNotEquals(inv.getCards().size(), new Inventory(2).getCards().size());
    }

    @Test
    public void updateAvailableResourcesTest() {
        assertEquals(0, inv.getAvailableResources()[Resource.BOIS.getIndex()]);
        inv.updateInventory(player.playCard(inv));
        assertEquals(1, inv.getAvailableResources()[Resource.BOIS.getIndex()]);
        assertEquals(0, inv.getAvailableResources()[Resource.MINERAI.getIndex()]);
    }

    @Test
    public void discardLastCardTest() {
        assertThrows(Error.class, () -> inv.discardLastCard());
        while (inv.getCards().size() > 1) {
            inv.updateInventory(player.playCard(inv));
        }
        Card lastCard = inv.getCards().get(0);
        assertSame(lastCard, inv.discardLastCard());
        assertTrue(inv.getCards().isEmpty());
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
        Card bouclierCard = new Card("BOUCLIER", new SymbolEffect("", Symbol.BOUCLIER, 1), null);
        Card boisCard = new Card("BOIS", new ResourceEffect("", Resource.BOIS, 1), null);

        Player neighbor = new Player(2);
        Inventory neighborInv = new Inventory(2);

        cards.set(0, bouclierCard);
        neighborInv.setCards(cards);
        inv.updateInventory(neighbor.playCard(inv)); //Neighbor has 1 bouclier

        //Player has less boucliers than his neighbor
        player = new Player(3);
        addCardAndPlayIt(player, boisCard); //Player has no boucliers

        board.resolveWarConflict();
        assertEquals(inv.getConflictPoints(), -1);

        //Player has same amount of boucliers than his neighbor
        player = new Player(4);
        addCardAndPlayIt(player, bouclierCard); //Player has 1 bouclier

        board.resolveWarConflict();
        assertEquals(inv.getConflictPoints(), 0);

        //Player has more boucliers than his neighbor
        player = new Player(5);
        addCardAndPlayIt(player, bouclierCard); //Player has 1 bouclier
        addCardAndPlayIt(player, bouclierCard); //Player has 2 boucliers

        board.resolveWarConflict();
        assertEquals(inv.getConflictPoints(), 3);
    }

    private void addCardAndPlayIt(Player player, Card card) {
        cards.set(0, card);
        inv.setCards(cards);
        inv.updateInventory(player.playCard(inv));
    }

    @Test
    void missingResourcesTest() {
        Card c = new Card("CHANTIER TEST", new ResourceEffect("", Resource.BOIS, 1), new Resource[]{Resource.BOIS});
        Resource[] m = player.missingResources(inv, c);
        assertEquals(Resource.BOIS, m[0]);
    }

    @Test
    void UpdatePlayer() {
        assertEquals(0, inv.getAvailableResources()[Resource.BOIS.getIndex()]);
        inv.updateInventory(player.playCard(inv));
        assertEquals(1, inv.getAvailableResources()[Resource.BOIS.getIndex()]);
    }
}
