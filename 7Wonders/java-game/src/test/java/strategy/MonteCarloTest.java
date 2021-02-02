package strategy;

import board.Board;
import board.SevenWondersLauncher;
import gameelements.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MonteCarloTest {

    List<Player> playerList;
    Board board;
    @Mock
    Player player;


    @BeforeEach
    void setUp() throws Exception {
        int nbPlayers = 3;
        playerList = new ArrayList<>(nbPlayers);
        for (int i = 0; i < nbPlayers; i++) {
            Player player = new Player(i, new WonderStrategy());
            playerList.add(player);
        }
        board = new Board(playerList, false);
        SevenWondersLauncher.startClient();
        SevenWondersLauncher.client.sendNumberOfPlayers(nbPlayers);
    }

    @Test
    void chooseCardTest() throws Exception {
        Player p = board.getPlayerList().get(2);
        p.setStrategy(new MonteCarloStrategy(true));
        board.play(1);

        assertEquals(0,board.getPlayerInventoryList().get(2).getCardsInHand().size() );

        /*
        MonteCarloStrategy strategy = (MonteCarloStrategy)p.getStrategy();


        strategy.continueGame(board,inv);*/

        /*
        assertEquals(CardsSet.HOTEL_DE_VILLE, strategy.chooseCard(inv,board));

        assertEquals(Action.BUILDING, strategy.getAction());
        inv.setPlayedCards(cards);
        inv.getCardsInHand().remove(1);
        assertEquals(CardsSet.HOTEL_DE_VILLE, strategy.chooseCard(inv,board));
        assertEquals(Action.SELL, strategy.getAction());
        */

    }

}
