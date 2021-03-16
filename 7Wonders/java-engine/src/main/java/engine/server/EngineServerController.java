package engine.server;

import engine.server.EngineServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import statistic.DetailedResults;

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

    public Boolean connection(String adresse) {
        this.adresse = adresse;
        System.out.println("***************** Send connection request to StatsServer ******************");
        return restTemplate.postForObject(adresse + CONNEXION, engineServer.getUrl(), Boolean.class);
    }

    public Boolean sendNumberOfPlayers(int nbr) {
        System.out.println("***************** Send number of players to StatsServer ******************");
        return restTemplate.postForObject(adresse + SEND_NB_PLAYERS, nbr, Boolean.class);
    }


    public Boolean sendStats(DetailedResults[] results) {
        return restTemplate.postForObject(adresse + SEND_STATS, results, Boolean.class);
    }

    public void showStats() {
        System.out.println("***************** Ask StatsServer for stats ******************");
        String stat = restTemplate.postForObject(adresse + SHOW_STATS, 1, String.class);
        System.out.println(stat);
    }

    public void disconnect() {
        restTemplate.getForObject(adresse + DISCONNECT, int.class);
    }
}