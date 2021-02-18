package player;
import gameelements.CardActionPair;
import gameelements.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayerController {

    @Autowired
    Player player;

    @PostMapping("/choose_card_and_action")
    public CardActionPair chooseCard(@RequestBody Inventory inv) throws Exception {
        System.out.println("Player > We choose the card and the action.");
        player.chooseCard(inv);
        return player.getCardAction();
    }

}