package engine.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.client.MockRestServiceServer;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(EngineServerController.class)
public class EngineServerControllerTest {
    @Autowired
    EngineServerController serverController;

    @MockBean
    EngineServer engineServer;

    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private ObjectMapper objectMapper;

    private String engineServerURL;


    @BeforeEach
    void setUp() {
        engineServerURL = "http://172.28.0.253:8080";
    }

    @Test
    void connectToEngineServerTest() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();

        try {
            TimeUnit.MILLISECONDS.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        server.expect(requestTo(engineServerURL+"/connect_engine_player")).andExpect(method(HttpMethod.GET)).andRespond(
                withSuccess("0", MediaType.APPLICATION_JSON));

        assertEquals(0, serverController.connectToEngineServer(request));
    }
}
