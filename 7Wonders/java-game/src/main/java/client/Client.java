package client;

import constants.NET;
import io.socket.client.IO;
import io.socket.client.Socket;

import java.net.URISyntaxException;
import java.util.logging.Logger;

public class Client {
    private final static Logger log = Logger.getLogger(Client.class.getName());
    Socket connection;

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

    public void sendNumberOfPlayers(int numberOfPlayers) {
        connection.emit(NET.PLAYERS, numberOfPlayers);
    }

    public void showStats() {
        connection.emit("ping");
    }

    public void stop() {
        connection.disconnect();
        connection.close();
    }

    public void sendResults(String s) {
        connection.emit(NET.RESULTS, s);
    }
}
