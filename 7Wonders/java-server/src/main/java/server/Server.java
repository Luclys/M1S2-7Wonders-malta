package server;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;

import java.util.HashMap;
import java.util.logging.Logger;

import static constants.NET.WINNER;

public class Server {
    SocketIOServer server;
    private final static Logger log = Logger.getLogger(Server.class.getName());
    private final HashMap<Integer, Integer> wins = new HashMap<>();
    private final HashMap<Integer, Integer> scores = new HashMap<>();
    private int nbPlayers;

    public Server(Configuration configuration) {
        // Creation of the server
        server = new SocketIOServer(configuration);

        // We accept the connection
        server.addConnectListener(socketIOClient -> log.info("Connection of " + socketIOClient.getRemoteAddress()));

        // Receiving of inventory and declaring the winner
        server.addEventListener(WINNER, Integer.class, (socketIOClient, playerId, ackRequest) -> {
            wins.put(playerId, wins.get(playerId) + 1);
        });

        server.addEventListener("players", Integer.class, new DataListener<Integer>() {
            @Override
            public void onData(SocketIOClient socketIOClient, Integer integer, AckRequest ackRequest) throws Exception {
                nbPlayers = integer;
                for (int i = 0; i < nbPlayers; i++) {
                    wins.put(i, 0);
                    scores.put(i, 0);
                }
            }
        });

        // Show the win rate of each player
        server.addPingListener(socketIOClient -> {
            for (int i = 0; i < nbPlayers; i++) {
                log.info("The player " + i + " won " + wins.get(i) / 10 + "% of the games\n");
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
