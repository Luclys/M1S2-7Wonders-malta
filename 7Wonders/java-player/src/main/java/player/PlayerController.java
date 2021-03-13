package player;

import gameelements.CardActionPair;
import gameelements.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static constants.WEBSERVICES_GAME.*;

@RestController
public class PlayerController {

    @Autowired
    Player player;
    private String engineURL;

    public Boolean connection(String engineURL) {
        this.engineURL = engineURL;
        System.out.println("***************** Send connection request to EngineServer ******************");
        return restTemplate.postForObject(engineURL + CONNEXION, clientEngine.getUrl(), Boolean.class);
    }

    @PostMapping(CHOOSE_CARD_AND_ACTION)
    public CardActionPair chooseCard(@RequestBody Inventory inv) throws Exception {
        System.out.println("Player > We choose the card and the action.");
        player.chooseCard(inv);
        return player.getCardAction();
    }

}