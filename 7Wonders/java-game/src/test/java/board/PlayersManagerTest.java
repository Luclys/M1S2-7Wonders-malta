package board;

import gameelements.Inventory;
import gameelements.Player;
import gameelements.cards.Card;
import gameelements.cards.CardsSet;
import gameelements.enums.Resource;
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
    private ArrayList<Player>  playerList;

    @BeforeEach
    public void setUp() {
        playerList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            Player player = new Player(i);
            playerList.add(player);
        }
        board = new Board(playerList, false);
        player = board.getPlayerList().get(0);
        inv = board.getPlayerInventoryList().get(player.getId());
        for (int i = 0; i < 7; i++) {
            cards.add(CardsSet.CHANTIER);
        }
        inv.setCardsInHand(cards);
    }

    @Test
    void missingResourcesTest() {
        Card c = CardsSet.PALISSADE;
        ArrayList<Resource> m = board.getManager().missingResources(inv, c);
        assertEquals(Resource.BOIS, m.get(0));
    }

    @Test
    public void associateNeighborTest() {
        int nbPlayers = 7;

        Board board = new Board(playerList, false);
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
