package engine.server;

import constants.WEBSERVICES_GAME;
import constants.WEBSERVICES_STATS;
import gameelements.CardActionPair;
import gameelements.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import statistic.DetailedResults;

import javax.servlet.http.HttpServletRequest;

import static constants.WEBSERVICES_GAME.CHOOSE_CARD_AND_ACTION;
import static constants.WEBSERVICES_GAME.CONNECT_ENGINE_PLAYER;
import static constants.WEBSERVICES_STATS.*;

@RestController
public class EngineServerController {

    String adresse;

    @Autowired
    EngineServer engineServer;

    @Autowired
    private RestTemplate restTemplate;


    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        // Do any additional configuration here
        return builder.build();
    }


    @PostMapping(CONNECT_ENGINE_PLAYER)
    public synchronized int connectToEngineServer(HttpServletRequest request, @RequestBody String... args) throws Exception {
        System.out.println("ENGINE SERVER CONTROLLER >  Connection Player to Engine ");
        String playerURL;
        if (args[2] == "true") {
            playerURL = "http://localhost" + ":" + args[1];
        } else {
            playerURL = "http://" + request.getRemoteAddr() + ":" + args[1];
        }
        int playerId = engineServer.addPlayerURL(playerURL);
        System.out.println("ENGINE SERVER CONTROLLER > Connexion granted to the player : " + playerURL + " , Player id = " + playerId);
        engineServer.testStart();
        engineServer.setConnected(true);
        return playerId;
    }


    public Boolean connectToStatsServer(String statsServerURL) {
        this.adresse = statsServerURL;
        System.out.print("ENGINE SERVER CONTROLLER > Send connection request to Stats Server ");
        System.out.println("Attempting to access : " + adresse + CONNECT_ENGINE_STATS);
        return restTemplate.getForObject(adresse + CONNECT_ENGINE_STATS, Boolean.class);
    }

    public int sendNumberOfPlayers(int nbr) {
        System.out.println("ENGINE SERVER CONTROLLER > Send number of players to Stats Server");
        return restTemplate.postForObject(adresse + SEND_NB_PLAYERS, nbr, int.class);
    }


    public int sendStats(DetailedResults[] results) {
        System.out.println("ENGINE SERVER CONTROLLER > Send stats to Stats Server");
        return restTemplate.postForObject(adresse + SEND_STATS, results, int.class);
    }

    public String showStats() {
        System.out.println("ENGINE SERVER CONTROLLER > Ask Stats Server to show stats");
        return restTemplate.getForObject(adresse + SHOW_STATS, String.class);
    }

    public void disconnectStatsServer() {
        System.out.println("ENGINE SERVER CONTROLLER > Ask Stats Server to disconnect");
        restTemplate.getForObject(adresse + WEBSERVICES_STATS.DISCONNECT_ENGINE_STATS, int.class);
    }

    public void disconnectPlayer(String url) {
        System.out.print("ENGINE SERVER CONTROLLER > Ask Player to disconnect ");
        System.out.println("Url of the player "+url);
        restTemplate.getForObject(url + WEBSERVICES_GAME.DISCONNECT_ENGINE_PLAYER, int.class);
    }

    public CardActionPair askCardAction(Inventory inv) {
        System.out.print("ENGINE SERVER CONTROLLER >  Ask player to choose card and action ");
        System.out.println("Url of the player "+inv.getPlayerURL());
        return restTemplate.postForObject(inv.getPlayerURL() + CHOOSE_CARD_AND_ACTION, inv, CardActionPair.class);
    }

}