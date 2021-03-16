package player;

import gameelements.CardActionPair;
import gameelements.cards.Card;
import gameelements.enums.Symbol;
import gameelements.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static constants.WEBSERVICES_GAME.*;

@RestController
public class PlayerController {

    @Autowired
    Player player;
    private String engineURL;

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {

        return builder.build();
    }

    public Boolean connection(String engineURL) {
        this.engineURL = engineURL;
        System.out.println("***************** Send connection request to EngineServer ******************");
        return restTemplate.postForObject(engineURL + CONNECT_ENGINE_PLAYER, "http://localhost:8081", Boolean.class);
    }

    @PostMapping(CHOOSE_CARD_AND_ACTION)
    public CardActionPair chooseCard(@RequestBody Inventory inv) throws Exception {
        System.out.println("Player > We choose the card and the action.");
        player.chooseCard(inv);
        return player.getCardAction();
    }
    @PostMapping(ACKOWNLEDGE_STATUS)
    public Boolean acknowledgeGameStatus(@RequestBody ArrayList<Inventory> censoredInvList, int age, int currentTurn) {
        System.out.println("Player > Game Status Acknowledged.");
        player.acknowledgeGameStatus(censoredInvList, age, currentTurn);
        return true;
    }
    @PostMapping(CHOOSE_GUILD_CARD)
    public Card chooseGuildCard(@RequestBody List<Card> list, Inventory inv, Inventory leftNeighborInv, Inventory rightNeighborInv) {
        System.out.println("Player > We choose the Guild Card.");
        return player.chooseGuildCard(list, inv, leftNeighborInv, rightNeighborInv);
    }
    @PostMapping(CHOOSE_SCIENTIFIC_SYMBOL)
    public Symbol chooseScientific(@RequestBody int[] currentSymbols) {
        System.out.println("Player > We choose the scientific symbol.");
        return player.chooseScientific(currentSymbols);
    }
    @PostMapping(CHOOSE_DISCARDED_CARD_TO_BUILD)
    public Card chooseDiscardedCardToBuild(@RequestBody Inventory inventory, List<Card> discardedDeckCardList) throws Exception {
        System.out.println("Player > We choose the card we want to build amongst the discarded cards.");
        return player.chooseDiscardedCardToBuild(inventory, discardedDeckCardList);
    }
    @PostMapping(GET_PLAYER_STRATEGY)
    public String getStrategyName() throws Exception {
        //System.out.println("Player > We choose the card and the action.");
        return player.getStrategyName();
    }
}