package player;

import gameelements.CardActionPair;
import gameelements.Inventory;
import gameelements.cards.Card;
import gameelements.enums.Action;
import gameelements.enums.Symbol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import strategy.FirstCardStrategy;
import strategy.PlayingStrategy;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * This class describe a player
 *
 * @author lamac
 */
@Component
@Scope("singleton")
@SpringBootApplication
public class Player {
    private final static Logger log = Logger.getLogger(Player.class.getName());


    @Autowired
    PlayerController ctrl;

    @Autowired
    private ApplicationContext appContext;

    private int id;
    private PlayingStrategy strategy;
    private int rightNeighborId;
    private int leftNeighborId;


    public Player() {
        this.strategy = new FirstCardStrategy();
    }

    public Player(Player p) {
        this.id = p.id;
        this.strategy = p.strategy.copy();
        rightNeighborId = p.rightNeighborId;
        leftNeighborId = p.leftNeighborId;
    }

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        SpringApplication.run(Player.class, args);
    }

    @Bean
    public CommandLineRunner run() {
        return args -> {
            String engineURL = args.length == 1 ? "http://" + args[0] + ":8081" : "http://localhost:8081";
            // ack de connexion sur l'adresse docker
            System.out.println("***************** Player initiating... ******************");
            // Renvoyer l'ID une fois la connection établie pour le stocker.
            this.id = ctrl.connection(engineURL,args);
        };
    }

    public String toString() {
        return "player.Player " + id;
    }

    /**
     * This method returns a card according to the strategy used
     *
     * @param inv
     * @return chosen card
     */
    public Card chooseCard(Inventory inv) {
        strategy.chooseCard(inv);
        return strategy.getCard();
    }

    public void acknowledgeGameStatus(ArrayList<Inventory> censoredInvList, int age, int currentTurn) {
        strategy.updateKnowledge(censoredInvList, age, currentTurn, rightNeighborId, leftNeighborId);
    }

    public Card chooseGuildCard(List<Card> list, Inventory inv, Inventory leftNeighborInv, Inventory rightNeighborInv) {
        return list.get(0);
    }

    public Symbol chooseScientific(int[] currentSymbols) {
        // scientific score rule :
        // foreach(nb same Scientific²) + min(nb same scientific) * 7
        return Symbol.COMPAS;
    }

    public Card chooseDiscardedCardToBuild(Inventory inventory, List<Card> discardedDeckCardList) {
        return discardedDeckCardList.get(0);
    }

    public String getStrategyName() {
        return strategy.getClass().getName();
    }

    public PlayingStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(PlayingStrategy s) {
        this.strategy = s;
    }

    //Getters and setters
    public int getId() {
        return id;
    }

    public Card getChosenCard() {
        return strategy.getCard();
    }

    public Action getAction() {
        return strategy.getAction();
    }

    public CardActionPair getCardAction() {
        return new CardActionPair(getChosenCard(), getAction());
    }

    public int InitiateExit() {
        return SpringApplication.exit(appContext, () -> 0);
    }
}
