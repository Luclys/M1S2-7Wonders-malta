package server;

import gameelements.CardActionPair;
import gameelements.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import static constants.WEBSERVICES_GAME.CHOOSE_CARD_AND_ACTION;
import static constants.WEBSERVICES_STATS.CONNEXION;
import static constants.WEBSERVICES_STATS.SHOW_STATS;

@RestController
public class EngineServerController {
    @Autowired
    EngineServer engineServer;

    String urlClient;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        // Do any additional configuration here
        return builder.build();
    }

    @PostMapping(CONNEXION)
    public boolean connecter(@RequestBody String url) {
        urlClient = url;
        System.out.println("***************** Connection Client Server ******************");

        System.out.println("Server > Connexion granted to the client : " + urlClient);
        engineServer.lancerPartie();
        return true;
    }


    public void showStats() {
        System.out.println("***************** Ask Server for stats ******************");
        String stat = restTemplate.postForObject(adresse + SHOW_STATS, 1, String.class);
        System.out.println(stat);
    }


    @PostMapping(CHOOSE_CARD_AND_ACTION)
    public CardActionPair chooseCard(@RequestBody Inventory inv) {
        System.out.println("***************** Get number of players from Client ******************");
        System.out.println("number of players " + s);
        engineServer.setNbPlayer(s);
        return true;
    }
}