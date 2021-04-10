package server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import statistic.DetailedResults;

import java.io.PrintStream;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.logging.Logger;

@Component
@Scope("singleton")
@SpringBootApplication
public class StatsServer {
    private final static Logger log = Logger.getLogger(StatsServer.class.getName());
    private final HashMap<Integer, Integer> wins = new HashMap<>();
    private final HashMap<Integer, Integer> scores = new HashMap<>();
    private final HashMap<Integer, Integer> discardedCards = new HashMap<>();
    private final HashMap<Integer, Integer> stepsBuilt = new HashMap<>();
    private final HashMap<Integer, Integer> coinsAcquiredInTrade = new HashMap<>();
    private final HashMap<Integer, Integer> coinsSpentInTrade = new HashMap<>();
    StatsServer partie;
    private DetailedResults[] results;
    private int nbPlayers;
    private int nbStats = 0;

    @Autowired
    StatsServerController crl;
    @Autowired
    private ApplicationContext appContext;

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        SpringApplication.run(StatsServer.class);
    }

    public void lancerPartie() {
        if (partie == null) {
            partie = this;
        } else {
            System.out.println("STATS SERVER > A game is already started.");
        }
    }

    @Bean
    public CommandLineRunner run() {
        return args -> {
            // ack de connexion sur l'adresse docker
            partie = this;
            System.out.println("STATS SERVER > ***************** Server running... ******************");
            System.out.println("STATS SERVER > StatsServer IP : http:/" + InetAddress.getLocalHost().getHostAddress());
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
        StringBuilder stats = new StringBuilder();
        for (int i = 0; i < nbPlayers; i++) {
            int winRate = wins.get(i) * 100 / nbStats;
            stats.append("> Player ").append(i).append(" | Win rate : ").append(winRate).append("%")
                    .append(" | Mean score : ").append((float) scores.get(i) / nbStats)
                    .append(" | Mean - Discarded cards : ").append((float) discardedCards.get(i) / nbStats)
                    .append(" | Steps built : ").append((float) stepsBuilt.get(i))
                    .append(" | Trade Mean - Money earned : ").append((float) coinsAcquiredInTrade.get(i) / nbStats)
                    .append(" | Trade Mean : Money spent ").append((float) coinsSpentInTrade.get(i) / nbStats)
                    .append("\n");
        }
        stats.append("DATA RECEIVED FROM ").append(nbStats).append(" GAMES");
        return stats.toString();
    }

    public void setNbPlayer(int nb) {
        nbPlayers = nb;
        for (int i = 0; i < nbPlayers; i++) {
            wins.put(i, 0);
            scores.put(i, 0);
        }
    }

    public int setStats(DetailedResults[] results) {
        this.results = results;
        nbStats += results.length / nbPlayers;
        setData();
        return nbStats;
    }

    public int InitiateExit() {
        return SpringApplication.exit(appContext, () -> 0);
    }

    public int getNbPlayers() {
        return nbPlayers;
    }

}
