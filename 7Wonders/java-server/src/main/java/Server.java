import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import gameelements.Inventory;

public class Server {
    SocketIOServer server;
    final Object waitConnection = new Object();

    public Server(Configuration configuration) {
        // Création du serveur
        server = new SocketIOServer(configuration);

        // On accepte une connexion
        server.addConnectListener(socketIOClient -> System.out.println("Connexion de " + socketIOClient.getRemoteAddress()));

        // Réception d'un inventaire et déclaration du gagnant
        server.addEventListener("inventory", Inventory.class, new DataListener<Inventory>() {
            @Override
            public void onData(SocketIOClient socketIOClient, Inventory inventory, AckRequest ackRequest) throws Exception {
                System.out.println("Le joueur gagnant est " + inventory.getPlayerId() + " !");
            }
        });

        // Test
        server.addEventListener("test", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient socketIOClient, String s, AckRequest ackRequest) throws Exception {
                System.out.println(s);
            }
        });
    }

    private void start() {
        server.start();

        System.out.println("En attente d'une connexion...");

        synchronized (waitConnection) {
            try {
                waitConnection.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("Erreur dans l'attente.");
            }
        }

        System.out.println("Une connexion est arrivée, on arrête.");
        server.stop();
    }
}
