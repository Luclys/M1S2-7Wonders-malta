package client;

import gameelements.Inventory;
import io.socket.client.IO;
import io.socket.client.Socket;

import java.net.URISyntaxException;
import java.util.logging.Logger;

import static constants.NET.WINNER;

public class Client {
    Socket connection;

    private final static Logger log = Logger.getLogger(Client.class.getName());

    public Client(String serverURL) {
        try {
            connection = IO.socket(serverURL);

            connection.on("connect", objects -> {
                log.info("We are connected");
            });

            connection.on("disconnect", objects -> {
                log.info("We are disconnected");
                connection.disconnect();
                connection.close();
            });

        } catch (URISyntaxException ignored) {
        }

    }

    private void makeConnection() {
        connection.connect();
    }

    public void start() {
        makeConnection();
    }

    public void sendWinner(Inventory inventory) {
        connection.emit(WINNER, inventory);
    }

    public void sendNumberOfPlayers(int numberOfPlayers) {
        connection.emit("players", numberOfPlayers);
    }

    public void sendScores(Integer[] scores) {
        
        connection.emit("scores", scores);
    }

    public void showWinRates() {
        connection.emit("ping");
    }
}
