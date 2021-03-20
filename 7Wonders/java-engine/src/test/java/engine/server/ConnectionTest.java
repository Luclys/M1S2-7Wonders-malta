package engine.server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.springframework.test.web.servlet.MockMvc;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ConnectionTest {
    @Autowired
    EngineServerController serverController;

    @Autowired
    EngineServer engineServer;

    @Autowired
    private MockMvc mockMvc;

    EngineServer mSpy;

    @BeforeEach
    void setUp() {
        mSpy = Mockito.spy(engineServer);
        ReflectionTestUtils.setField(serverController, "engineServer", mSpy);
    }

    @Test
    void connectToEngineServerTest() throws Exception {
        this.mockMvc.perform(get("/connect_engine_player")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("0")));

        TimeUnit.MILLISECONDS.sleep(500);
        this.mockMvc.perform(get("/connect_engine_player")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("1")));
    }

}
