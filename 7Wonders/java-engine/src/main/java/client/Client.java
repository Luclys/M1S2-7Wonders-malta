package client;

import constants.NET;
import io.socket.client.IO;
import io.socket.client.Socket;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
@Scope("singleton")
public class Client {
    private static final Logger log = Logger.getLogger(Client.class.getName());
    Socket connection;
    String  stats;
    String url;


    public Client(){
        url = "http://localhost:8081";
        stats = null;
    }
    public Client(String serverURL) throws Exception {
        try {
            stats = null;
            url = "http://localhost:8081";
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
        stats = s;
        connection.emit(NET.RESULTS, s);
    }

    public String sendResultsRest(){
        return stats;
    }

    public Boolean isConnected() {
        return connection.connected();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
