package engine.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import statistic.DetailedResults;

import javax.servlet.http.HttpServletRequest;

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
    public int connectToEngineServer(@RequestBody String url, HttpServletRequest request) {
        System.out.println("Engine > ***************** Connection Player to Engine ******************");

        String playerURL = "http://" + request.getRequestURI() + ":8080";

        int playerId = engineServer.addPlayerURL(playerURL);

        System.out.println("Engine > Connexion granted to the player : " + playerURL + ", Player id = " + playerId);

        return playerId;
    }


    public Boolean connectToStatsServer(String statsServerURL) {
        this.adresse = statsServerURL;
        System.out.println("Engine > ***************** Send connection request to StatsServer ******************");
        return restTemplate.postForObject(statsServerURL + CONNECT_ENGINE_STATS, engineServer.getUrl(), Boolean.class);
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

    public void disconnect() {
        restTemplate.getForObject(adresse + DISCONNECT, int.class);
    }
}