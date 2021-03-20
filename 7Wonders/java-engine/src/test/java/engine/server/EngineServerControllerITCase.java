package engine.server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class EngineServerControllerITCase {
    @Autowired
    EngineServerController serverController;

    @Autowired
    EngineServer engineServer;

    EngineServer mSpy;

    @BeforeEach
    void setUp() {
        mSpy = Mockito.spy(engineServer);
        ReflectionTestUtils.setField(serverController, "engineServer", mSpy);
    }

    @Test
    void connectToEngineServerTest() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
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
}
