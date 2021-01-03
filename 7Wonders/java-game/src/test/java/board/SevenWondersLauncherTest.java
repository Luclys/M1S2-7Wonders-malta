package board;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SevenWondersLauncherTest {
    @Disabled
    @Test
    void fetchPlayersTest() {
        assertEquals(3, SevenWondersLauncher.fetchPlayers(3).size());
    }
}
