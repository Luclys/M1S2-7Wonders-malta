package client;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class ClientTest {
    Client instance = new Client("http://127.0.0.1:10101");

    @Test
    public void CloseTest() throws Exception {
        instance.stop();
        assertFalse(instance.isConnected());
    }
}
