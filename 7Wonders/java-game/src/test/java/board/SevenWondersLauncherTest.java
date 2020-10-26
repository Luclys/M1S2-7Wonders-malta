package board;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SevenWondersLauncherTest {

    @Test
    void fetchPlayersTest() {
        assertEquals(3, SevenWondersLauncher.fetchPlayers(3).size());
    }

    @Test
    void clientest()throws InterruptedException, JsonProcessingException {

        SevenWondersLauncher.main();
        assertTrue(!SevenWondersLauncher.client.isConnected());
    }
}
