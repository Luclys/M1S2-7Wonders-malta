package player;

import gameelements.CardActionPair;
import gameelements.Inventory;
import gameelements.cards.Card;
import gameelements.enums.Symbol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static constants.WEBSERVICES_GAME.*;

@RestController
public class PlayerController {

    @Autowired
    Player player;

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    public int connection(String engineURL, String... playerArgs) {
        System.out.println("PLAYER > ***************** Send connection request to EngineServer : " + engineURL + " ******************");
        try {
            TimeUnit.MILLISECONDS.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return restTemplate.postForObject(engineURL + CONNECT_ENGINE_PLAYER, playerArgs, Integer.class);
    }

    @PostMapping(CHOOSE_CARD_AND_ACTION)
    public CardActionPair chooseCard(@RequestBody Inventory inv) {
       // System.out.println("PLAYER > We choose the card and the action. url"+ inv.getPlayerURL());
        player.chooseCard(inv);
        return player.getCardAction();
    }

    @PostMapping(ACKNOWLEDGE_STATUS)
    public Boolean acknowledgeGameStatus(@RequestBody ArrayList<Inventory> censoredInvList, int age, int currentTurn) {
        System.out.println("PLAYER > Game Status Acknowledged.");
        player.acknowledgeGameStatus(censoredInvList, age, currentTurn);
        return true;
    }

    @PostMapping(CHOOSE_GUILD_CARD)
    public Card chooseGuildCard(@RequestBody List<Card> list, Inventory inv, Inventory leftNeighborInv, Inventory rightNeighborInv) {
        System.out.println("PLAYER "+player.getId()+" > choose the Guild Card.");
        return player.chooseGuildCard(list, inv, leftNeighborInv, rightNeighborInv);
    }

    @PostMapping(CHOOSE_SCIENTIFIC_SYMBOL)
    public Symbol chooseScientific(@RequestBody int[] currentSymbols) {
        System.out.println("PLAYER "+player.getId()+" > choose the scientific symbol.");
        return player.chooseScientific(currentSymbols);
    }

    @PostMapping(CHOOSE_DISCARDED_CARD_TO_BUILD)
    public Card chooseDiscardedCardToBuild(@RequestBody Inventory inventory, List<Card> discardedDeckCardList) {
        System.out.println("PLAYER "+player.getId()+" > choose the card to build among the discarded cards.");
        return player.chooseDiscardedCardToBuild(inventory, discardedDeckCardList);
    }

    @PostMapping(GET_PLAYER_STRATEGY)
    public String getStrategyName() {
        //System.out.println("PLAYER > choose the card and the action.");
        return player.getStrategyName();
    }

    @PostMapping(GET_PLAYER_ID)
    public int getPlayerId() {
        //System.out.println("PLAYER > choose the card and the action.");
        return player.getId();
    }

    @GetMapping(DISCONNECT_ENGINE_PLAYER)
    public int disconnect() {
        System.out.println("***************** Hard Stopping Player ******************");
        return player.InitiateExit();
    }
}