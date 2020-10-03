package client;

import io.socket.client.IO;
import io.socket.client.Socket;

import java.net.URISyntaxException;

public class Client {
    Socket connection;

    // Objet de synchronisation
    final Object waitConnection = new Object();

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

                synchronized (waitConnection) {
                    waitConnection.notify();
                }
            });

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    private void makeConnection() {
        connection.connect();

        System.out.println("En attente de déconnexion.");
        synchronized (waitConnection) {
            try {
                waitConnection.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void handshake() {
        makeConnection();
    }
}
