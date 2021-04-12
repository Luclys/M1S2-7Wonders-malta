package engine.server;

import gameelements.CardActionPair;
import gameelements.Inventory;
import gameelements.cards.Card;
import gameelements.cards.CardsSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import statistic.DetailedResults;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ExtendWith(SpringExtension.class)
public class EngineServerControllerITCase {
    @Autowired
    EngineServerController serverController;

    EngineServerController spyserverController;

    @Autowired
    EngineServer engineServer;

    EngineServer mSpy;


    @BeforeEach
    void setUp() {
        mSpy = Mockito.spy(engineServer);
        ReflectionTestUtils.setField(serverController, "engineServer", mSpy);
        spyserverController = Mockito.spy(serverController);
        ReflectionTestUtils.setField(mSpy, "ctrl", spyserverController);
    }

    /*
        @Test
        void connectToEngineServerTest() throws Exception {
            // MockHttpServletRequest request = new MockHttpServletRequest();
            serverController.connectToEngineServer(request);

            try {
                TimeUnit.MILLISECONDS.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String playerURL = "http://" + request.getRemoteAddr() + ":8080";

            verify(mSpy, times(1)).testStart();
            verify(mSpy, times(1)).addPlayerURL(playerURL);
            assertTrue(mSpy.getUrls().containsValue(playerURL));
            assertEquals(mSpy.getUrls().size(), 1);
        }
    */
    @Test
    void connectToServerTest() {
        System.out.println("*************************************************************************");
        System.out.println("* TEST ENGINE SERVER CONTROLLER > CONNECT ENGINE SERVER TO STATS SERVER *");
        System.out.println("*************************************************************************");
        assertTrue(mSpy.isConnectToServer());
    }

    @Test
    void sendNumberOfPlayersTest() {
        System.out.println("*********************************************************************************************");
        System.out.println("* TEST ENGINE SERVER CONTROLLER > SEND NUMBER OF PLAYERS FROM ENGINE SERVER TO STATS SERVER *");
        System.out.println("*********************************************************************************************");
        int nbPlayer = 3;
        assertEquals(nbPlayer, serverController.sendNumberOfPlayers(nbPlayer));
    }

    @Test
    void sendStatsTest() {
        int nbPlayer = 3;
        DetailedResults[] testDetailedResults = new DetailedResults[nbPlayer];
        for (int i = 0; i < nbPlayer; i++) {
            testDetailedResults[i] = new DetailedResults();
            testDetailedResults[i].setRank(i + 1);
        }
        System.out.println("*********************************************************************************");
        System.out.println("* TEST ENGINE SERVER CONTROLLER > SEND STATS FROM ENGINE SERVER TO STATS SERVER *");
        System.out.println("*********************************************************************************");

        serverController.sendNumberOfPlayers(nbPlayer);
        int nbStatsBefore = serverController.sendStats(testDetailedResults);
        int nbStatsAfter = serverController.sendStats(testDetailedResults);
        // We test that only one Stat has been sent.
        assertEquals(1, nbStatsAfter - nbStatsBefore);
    }

    @Test
    void showStatsTest() {
        try {
            TimeUnit.MILLISECONDS.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("********************************************************************************");
        System.out.println("* TEST ENGINE SERVER CONTROLLER > ENGINE SERVER ASK STATS SERVER TO SHOW STATS *");
        System.out.println("********************************************************************************");
        String stats = serverController.showStats();
        System.out.println(stats);
        String subString = "DATA RECEIVED FROM 4 GAMES";
        assertTrue(stats.contains(subString));
    }


    @Test
    void sendStatsTest2() {
        int nbPlayer = 3;
        DetailedResults[] testDetailedResults = new DetailedResults[nbPlayer];
        DetailedResults[] testDetailedResults2 = new DetailedResults[nbPlayer];
        for (int i = 0; i < nbPlayer; i++) {
            testDetailedResults[i] = new DetailedResults();
            testDetailedResults2[i] = new DetailedResults();
        }
        testDetailedResults[0].setRank(1);
        testDetailedResults2[1].setRank(1);
        serverController.sendNumberOfPlayers(nbPlayer);
        serverController.sendStats(testDetailedResults);
        serverController.sendStats(testDetailedResults2);

        String stats = serverController.showStats();
        System.out.println(stats);

        String subString = "Player 0 | Win rate : 50%";
        String subString2 = "Player 1 | Win rate : 50%";
        assertTrue(stats.contains(subString));
        assertTrue(stats.contains(subString2));
    }


    @Disabled
    @Test
    void askCardActionTest() {
        // launch one player in background
        System.out.println("*************************************************************************************");
        System.out.println("* TEST ENGINE SERVER CONTROLLER > ENGINE SERVER ASK PLAYER TO CHOSE CARD AND ACTION *");
        System.out.println("*************************************************************************************");
        Inventory inv = new Inventory();
        inv.setPlayerURL(mSpy.mapPlayerID_URL.get(0));
        // add cards
        Card card1 = CardsSet.ACADEMIE;
        Card card2 = CardsSet.ARENE;
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(card1);
        cards.add(card2);
        inv.setCardsInHand(cards);
        CardActionPair chosen = serverController.askCardAction(inv);
        assertEquals(card1, chosen.getCard());
    }

}
