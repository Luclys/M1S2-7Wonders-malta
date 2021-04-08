package engine.server;

import constants.WEBSERVICES_GAME;
import constants.WEBSERVICES_STATS;
import gameelements.CardActionPair;
import gameelements.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
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
    public synchronized int connectToEngineServer(HttpServletRequest request,@RequestBody String... args) throws Exception {
        System.out.println("Engine > ***************** Connection Player to Engine ******************");
        String playerURL ;
        request.getParameter("");
        if(args[2] == "true"){
            playerURL = "http://localhost"+":"+args[1];
        }else {
            playerURL = "http://" + request.getRemoteAddr() +":"+args[1];
        }

        int playerId = engineServer.addPlayerURL(playerURL);

        System.out.println("Engine > Connexion granted to the player : " + playerURL + ", Player id = " + playerId);

        engineServer.testStart();

        return playerId;
    }


    public Boolean connectToStatsServer(String statsServerURL) {
        this.adresse = statsServerURL;
        System.out.println("Engine > ***************** Send connection request to StatsServer ******************");
        System.out.println("Attempting to access : " + adresse + CONNECT_ENGINE_STATS);
        return restTemplate.getForObject(adresse + CONNECT_ENGINE_STATS, Boolean.class);
    }

    public Boolean sendNumberOfPlayers(int nbr) {
        System.out.println("Engine > ***************** Send number of players to StatsServer ******************");
        return restTemplate.postForObject(adresse + SEND_NB_PLAYERS, nbr, Boolean.class);
    }


    public Boolean sendStats(DetailedResults[] results) {
        return restTemplate.postForObject(adresse + SEND_STATS, results, Boolean.class);
    }

    public void showStats() {
        System.out.println("Engine > ***************** Ask StatsServer for stats ******************");
        String stat = restTemplate.postForObject(adresse + SHOW_STATS, 1, String.class);
        System.out.println(stat);
    }

    public void disconnectStatsServer() {
        restTemplate.getForObject(adresse + WEBSERVICES_STATS.DISCONNECT_ENGINE_STATS, int.class);
    }

    public void disconnectPlayer(String url) {
        restTemplate.getForObject(url + WEBSERVICES_GAME.DISCONNECT_ENGINE_PLAYER, int.class);
    }

    public CardActionPair askCardAction(Inventory inv) {
        System.out.println("ASK FOR CARD AND ACTION url: " + inv.getPlayerURL());
        return restTemplate.postForObject(inv.getPlayerURL() + CHOOSE_CARD_AND_ACTION, inv, CardActionPair.class);
    }

}