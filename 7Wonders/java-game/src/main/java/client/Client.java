package client;

import gameelements.Inventory;
import io.socket.client.IO;
import io.socket.client.Socket;

import java.net.URISyntaxException;

public class Client {
    Socket connection;

    public Client(String serverURL) {
        try {
            connection = IO.socket(serverURL);

            connection.on("connect", objects -> {
                System.out.println("On est connecté.");
            });

            connection.on("disconnect", objects -> {
                System.out.println("On est déconnecté.");
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
        connection.emit("winner", inventory.getPlayerId());
    }

    public void stop() {
        connection.close();
    }
}
