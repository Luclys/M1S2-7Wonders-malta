package server;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;

import java.util.logging.Logger;

import static constants.NET.ENDGAME_INVENTORIES;

public class Server {
    private final static Logger log = Logger.getLogger(Server.class.getName());
    SocketIOServer server;

    public Server(Configuration configuration) {
        // Creation of server
        server = new SocketIOServer(configuration);

        // We accept the connection
        server.addConnectListener(socketIOClient -> log.info("Connection of " + socketIOClient.getRemoteAddress()));

        // Receiving of inventory and declaring the winner, then the server is closed
        server.addEventListener(ENDGAME_INVENTORIES, Integer.class, new DataListener<Integer>() {
            @Override
            public void onData(SocketIOClient socketIOClient, Integer playerId, AckRequest ackRequest) throws Exception {
                log.info("The winner is " + playerId + " !");
            }
        });
    }

    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.setHostname("127.0.0.1");
        configuration.setPort(10101);

        Server server = new Server(configuration);
        server.start();
    }

    private void start() {
        server.start();
        log.info("Waiting for connexion...");
    }
}
