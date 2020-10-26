package server;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.fasterxml.jackson.databind.ObjectMapper;
import constants.NET;
import gameelements.DetailedResults;

import java.util.HashMap;
import java.util.logging.Logger;

public class Server {
    SocketIOServer server;
    private final static Logger log = Logger.getLogger(Server.class.getName());
    private final HashMap<Integer, Integer> wins = new HashMap<>();
    private final HashMap<Integer, Integer> scores = new HashMap<>();
    private DetailedResults[] results;
    private int nbPlayers;
    private int nbStats = 0;

    public Server(Configuration configuration) {
        // Creation of the server
        server = new SocketIOServer(configuration);

        // We accept the connection
        server.addConnectListener(socketIOClient -> log.info("Connection of " + socketIOClient.getRemoteAddress()));

        server.addDisconnectListener(new DisconnectListener() {
            @Override
            public void onDisconnect(SocketIOClient socketIOClient) {
                log.info("Client disconnected");
            }
        });

        // Receiving of inventory and declaring the winner
        server.addEventListener("winner", Integer.class, (socketIOClient, playerId, ackRequest) -> {
            wins.merge(playerId, 1, Integer::sum);
        });

        server.addEventListener(NET.PLAYERS, Integer.class, new DataListener<Integer>() {
            @Override
            public void onData(SocketIOClient socketIOClient, Integer integer, AckRequest ackRequest) throws Exception {
                nbPlayers = integer;
                for (int i = 0; i < nbPlayers; i++) {
                    wins.put(i, 0);
                    scores.put(i, 0);
                }
            }
        });

        server.addEventListener(NET.RESULTS, String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient socketIOClient, String s, AckRequest ackRequest) throws Exception {
                ObjectMapper objectMapper = new ObjectMapper();
                results = objectMapper.readValue(s, DetailedResults[].class);
                nbStats++;
                setData();
            }
        });

        // Show the win rate of each player
        server.addPingListener(this::showStatistics);
    }

    private void setData() {
        for (int i = 0; i < nbPlayers; i++) {
            //Win rate of each player
            if (results[i].getRank() == 1) {
                wins.merge(i, 1, Integer::sum);
            }
            // Mean score of each player
            scores.merge(i, results[i].getTotalScore(), Integer::sum);
        }

    }

    private void showStatistics(SocketIOClient socketIOClient) {
        for (int i = 0; i < nbPlayers; i++) {
            log.info("Player " + i + " | Win rate : " + wins.get(i) / 10 +
                    " | Mean score : " + scores.get(i) / 1000);
        }
        log.info("DATA RECEIVED FROM " + nbStats + " GAMES");
    }

    private void sendDisconnectSignal(SocketIOClient socketIOClient) {
        socketIOClient.sendEvent("disconnect");
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
