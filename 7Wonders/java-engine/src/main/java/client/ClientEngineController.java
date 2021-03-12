package client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import statistic.DetailedResults;

import static constants.WEBSERVICES_STATS.*;


@RestController
public class ClientEngineController {

    String adresse;

    @Autowired
    ClientEngine clientEngine;

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {

        return builder.build();
    }

    public Boolean connection(String adresse) {
        this.adresse = adresse;
        System.out.println("***************** Send connection request to Server ******************");
        return restTemplate.postForObject(adresse + CONNEXION, clientEngine.getUrl(), Boolean.class);
    }

    public Boolean sendNumberOfPlayers(int nbr) {
        System.out.println("***************** Send number of players to Server ******************");
        return restTemplate.postForObject(adresse + SEND_NB_PLAYERS, nbr, Boolean.class);
    }


    public Boolean sendStats(DetailedResults[] results) {
        return restTemplate.postForObject(adresse + SEND_STATS, results, Boolean.class);
    }

    public void showStats() {
        System.out.println("***************** Ask Server for stats ******************");
        String stat = restTemplate.postForObject(adresse + SHOW_STATS, 1, String.class);
        System.out.println(stat);
    }

    public void disconnect() {
        restTemplate.getForObject(adresse + DISCONNECT, int.class);
    }
}
