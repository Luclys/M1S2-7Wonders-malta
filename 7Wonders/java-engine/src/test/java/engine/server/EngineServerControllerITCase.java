package engine.server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;
import statistic.DetailedResults;

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

    int nbStats;
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
           /* MockHttpServletRequest request = new MockHttpServletRequest();
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
        assertEquals(3, serverController.sendNumberOfPlayers(3));
    }

    @Test
    void sendStatsTest() {
        int nbPlayer = 3;
        serverController.sendNumberOfPlayers(nbPlayer);
        DetailedResults[] testDetailedResults = new DetailedResults[nbPlayer];
        for (int i = 0; i < nbPlayer; i++) {
            testDetailedResults[i] = new DetailedResults();
            testDetailedResults[i].setRank(i+1);
        }
        System.out.println("*********************************************************************************");
        System.out.println("* TEST ENGINE SERVER CONTROLLER > SEND STATS FROM ENGINE SERVER TO STATS SERVER *");
        System.out.println("*********************************************************************************");
        nbStats = serverController.sendStats(testDetailedResults);
        assertEquals(1,nbStats);
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
        String subString = "DATA RECEIVED FROM 1 GAMES";
        assertTrue(stats.contains(subString));
    }
}
