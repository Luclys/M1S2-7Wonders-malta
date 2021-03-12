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
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.logging.Logger;

@Component
@Scope("singleton")
@SpringBootApplication
public class EngineServer {
    private final static Logger log = Logger.getLogger(EngineServer.class.getName());
    private final HashMap<Integer, Integer> wins = new HashMap<>();
    private final HashMap<Integer, Integer> scores = new HashMap<>();
    private final HashMap<Integer, Integer> discardedCards = new HashMap<>();
    private final HashMap<Integer, Integer> stepsBuilt = new HashMap<>();
    private final HashMap<Integer, Integer> coinsAcquiredInTrade = new HashMap<>();
    private final HashMap<Integer, Integer> coinsSpentInTrade = new HashMap<>();
    private DetailedResults[] results;
    private int nbPlayers;
    private final int nbStats = 0;

    EngineServer partie;

    @Autowired
    EngineServerController crl;

    @Autowired
    private ApplicationContext appContext;

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        SpringApplication.run(EngineServer.class);
    }

    public void lancerPartie() {
        if (partie == null) {
            partie = this;
        } else {
            System.out.println("Engine > A game is already started.");
        }
    }

    @Bean
    public CommandLineRunner run() {
        return args -> {
            // ack de connexion sur l'adresse docker
            partie = this;
            System.out.println("***************** Server running... ******************");
        };
    }

}
