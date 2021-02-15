package client;

import constants.NET;
import io.socket.client.IO;
import io.socket.client.Socket;

import java.util.logging.Logger;

public class Client {
    private static final Logger log = Logger.getLogger(Client.class.getName());
    Socket connection;

    public Client(String serverURL) throws Exception {
        try {
            connection = IO.socket(serverURL);

            connection.on("connect", objects -> log.info("We are connected"));

            connection.on("disconnect", objects -> {
                log.info("We are disconnected");
                connection.disconnect();
                connection.close();
            });

        } catch (Exception ignored) {
            throw new Exception("Client problem");
        }

    }

    public void start() {
        connection.connect();
    }

    public void sendNumberOfPlayers(int numberOfPlayers) {
        connection.emit(NET.PLAYERS, numberOfPlayers);
    }

    public void stop() {
        connection.disconnect();
        connection.close();
    }

    public void sendResults(String s) {
        connection.emit(NET.RESULTS, s);
    }

    public Boolean isConnected() {
        return connection.connected();
    }

}
