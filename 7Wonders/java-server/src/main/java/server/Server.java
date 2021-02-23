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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import statistic.DetailedResults;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.logging.Logger;

@Component
@Scope("singleton")
@SpringBootApplication
public class Server {
    private final static Logger log = Logger.getLogger(Server.class.getName());
    private final HashMap<Integer, Integer> wins = new HashMap<>();
    private final HashMap<Integer, Integer> scores = new HashMap<>();
    private final HashMap<Integer, Integer> discardedCards = new HashMap<>();
    private final HashMap<Integer, Integer> stepsBuilt = new HashMap<>();
    private final HashMap<Integer, Integer> coinsAcquiredInTrade = new HashMap<>();
    private final HashMap<Integer, Integer> coinsSpentInTrade = new HashMap<>();
    private DetailedResults[] results;
    private int nbPlayers;
    private int nbStats = 0;
    private int test ;

    public Server(){
        this(42);
    }
    public Server(int t){
       test = t;
    }

    @Autowired
    ServerController crl;



    public static void main(String[] args) {
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        SpringApplication.run(Server.class);
    }
    Server partie;

    public void lancerPartie() {
        if (partie == null) {
            partie = this;
        }
        else {
            System.out.println("Moteur > Une partie est déjà commencé.");
        }
    }

    @Bean
    public CommandLineRunner run() {
        return args -> {
            // ack de connexion sur l'adresse docker
            partie = this;
            System.out.println("*****************Run Server******************");
        };
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

    public String showStatistics() {
        String stats ="";
        for (int i = 0; i < nbPlayers; i++) {
            stats+="Player " + i + " | Win rate : " + wins.get(i) / 10 +

                    " | Mean score : " + scores.get(i) / 1000 + " | Mean - Discarded cards : " + discardedCards.get(i) / 1000 +
                    " | Steps built : " + stepsBuilt.get(i) + " | Trade Mean - Money earned : " + coinsAcquiredInTrade.get(i) / 1000 +
                    " | Trade Mean : Money spent " + coinsSpentInTrade.get(i) / 1000+"\n";
        }
        stats+="DATA RECEIVED FROM " + nbStats + " GAMES";
        return stats;
    }


    public void setNbPlayer(int nb){
        nbPlayers = nb;
        for (int i = 0; i < nbPlayers; i++) {
            wins.put(i, 0);
            scores.put(i, 0);
        }
    }

    public void setStats(DetailedResults[] results){
        this.results =results;
        nbStats++;
        setData();
    }

}
