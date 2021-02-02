package server;

import com.corundumstudio.socketio.Configuration;
import org.junit.jupiter.api.Test;

class SeverTest {
    Server server;

    @Test
    void setDataSet() {
        Configuration configuration = new Configuration();
        configuration.setHostname("127.0.0.1");
        configuration.setPort(10101);
        Server server = new Server(configuration);
        //server.socketServer.start();
    }

}
