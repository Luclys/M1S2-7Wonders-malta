package board;

import effects.ResourceEffect;
import effects.ScoreEffect;
import gameelements.Card;
import gameelements.Effect;
import gameelements.Inventory;
import gameelements.enums.Category;
import gameelements.enums.Resource;
import io.cucumber.java8.Ca;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class PlayersManagerTest {

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
    void missingResourcesTest() {
        Card c = new Card("CHANTIER", new Effect[]{new ScoreEffect("", 1), new ResourceEffect("", Resource.BOIS, 1)}, new Resource[]{Resource.BOIS}, Category.BATIMENT_CIVIL);
        ArrayList<Resource> m = board.getManager().missingResources(inv, c);
        assertEquals(Resource.BOIS, m.get(0));
    }

    @Test
    public void generetePlayersTest() {
        int nbPlayers = 7;

        Board board = new Board(nbPlayers);
        ArrayList<Player> playerList = board.getPlayerList();

        int playersCount = playerList.size();
        assertEquals(7, playersCount);

        Player firstPlayer = playerList.get(0);
        Player secondPlayer = playerList.get(1);
        Player lastPlayer = playerList.get(nbPlayers - 1);

        // We test the neighborhood tor to the left then to the right
        assertSame(playerList.get(firstPlayer.getLeftNeighborId()), lastPlayer);
        assertSame(playerList.get(lastPlayer.getRightNeighborId()), firstPlayer);

        // We test the left neighbor then the right
        assertSame(playerList.get(secondPlayer.getLeftNeighborId()), firstPlayer);
        assertSame(playerList.get(firstPlayer.getRightNeighborId()), secondPlayer);
    }


}
