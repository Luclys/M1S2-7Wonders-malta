package server;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import gameelements.Inventory;

public class Server {
    SocketIOServer server;

    public Server(Configuration configuration) {
        // Création du serveur
        server = new SocketIOServer(configuration);

        // On accepte une connexion
        server.addConnectListener(socketIOClient -> System.out.println("Connexion de " + socketIOClient.getRemoteAddress()));

        // Réception d'un inventaire et déclaration du gagnant, puis le serveur se ferme
        server.addEventListener("winner", Integer.class, new DataListener<Integer>() {
            @Override
            public void onData(SocketIOClient socketIOClient, Integer playerId, AckRequest ackRequest) throws Exception {
                System.out.println("Le joueur gagnant est " + playerId + " !");
            }
        });
    }

    private void start() {
        server.start();
        System.out.println("En attente d'une connexion...");
    }

    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.setHostname("127.0.0.1");
        configuration.setPort(10101);

        Server server = new Server(configuration);
        server.start();
    }
}
