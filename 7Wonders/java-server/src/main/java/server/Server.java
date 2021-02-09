package server;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.fasterxml.jackson.databind.ObjectMapper;
import constants.NET;
import statistic.DetailedResults;

import java.util.HashMap;
import java.util.logging.Logger;

public class Server {
    private final static Logger log = Logger.getLogger(Server.class.getName());
    private final HashMap<Integer, Integer> wins = new HashMap<>();
    private final HashMap<Integer, Integer> scores = new HashMap<>();
    private final HashMap<Integer, Integer> discardedCards = new HashMap<>();
    private final HashMap<Integer, Integer> stepsBuilt = new HashMap<>();
    private final HashMap<Integer, Integer> coinsAcquiredInTrade = new HashMap<>();
    private final HashMap<Integer, Integer> coinsSpentInTrade = new HashMap<>();
    SocketIOServer socketServer;
    private DetailedResults[] results;
    private int nbPlayers;
    private int nbStats = 0;

    public Server(Configuration configuration) {
        // Creation of the server
        socketServer = new SocketIOServer(configuration);

        // We accept the connection
        socketServer.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient socketIOClient) {
                log.info("Connection of " + socketIOClient.getRemoteAddress());
            }
        });
        socketServer.addDisconnectListener(new DisconnectListener() {
            @Override
            public void onDisconnect(SocketIOClient socketIOClient) {
                log.info("Client disconnected");
            }
        });

        socketServer.addEventListener(NET.PLAYERS, Integer.class, new DataListener<Integer>() {
            @Override
            public void onData(SocketIOClient socketIOClient, Integer integer, AckRequest ackRequest) throws Exception {
                nbPlayers = integer;
                for (int i = 0; i < nbPlayers; i++) {
                    wins.put(i, 0);
                    scores.put(i, 0);
                }
            }
        });

        socketServer.addEventListener(NET.RESULTS, String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient socketIOClient, String s, AckRequest ackRequest) throws Exception {
                ObjectMapper objectMapper = new ObjectMapper();
                results = objectMapper.readValue(s, DetailedResults[].class);
                nbStats++;
                setData();
            }
        });

        // Show the win rate of each player
        socketServer.addPingListener(this::showStatistics);
    }

    private void setData() {
        for (int i = 0; i < nbPlayers; i++) {
            //Wins of each player
            if (results[i].getRank() == 1) {
                wins.merge(i, 1, Integer::sum);
            }
            // Cumulated score of each player
            scores.merge(i, results[i].getTotalScore(), Integer::sum);

            // Discarded cards
            discardedCards.merge(i, results[i].getNbSoldCard(), Integer::sum);

            // Cumulated number of steps built
            stepsBuilt.merge(i, results[i].getNbStepBuilt(), Integer::sum);

            // Coins acquired during trade
            coinsAcquiredInTrade.merge(i, results[i].getNbCoinsAcquiredInTrade(), Integer::sum);

            // Coins spent during trade
            coinsSpentInTrade.merge(i, results[i].getNbCoinsSpentInTrade(), Integer::sum);
        }

    }

    private void showStatistics(SocketIOClient socketIOClient) {
        for (int i = 0; i < nbPlayers; i++) {
            log.info("Player " + i + " | Win rate : " + wins.get(i) / 10 +
                    " | Mean score : " + scores.get(i) / 1000 + " | Mean - Discarded cards : " + discardedCards.get(i) / 1000 +
                    " | Steps built : " + stepsBuilt.get(i) + " | Trade Mean - Money earned : " + coinsAcquiredInTrade.get(i) / 1000 +
                    " | Trade Mean : Money spent " + coinsSpentInTrade.get(i) / 1000);
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
        socketServer.start();
        log.info("Waiting for connexion...");
    }
}
