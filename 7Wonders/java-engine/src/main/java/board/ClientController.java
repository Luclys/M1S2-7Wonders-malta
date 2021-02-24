package board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import statistic.DetailedResults;

@RestController
public class ClientController {

    String adresse;

    @Autowired
    Client client;

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {

        return builder.build();
    }

    public Boolean connection(String adresse){
        this.adresse = adresse;
        System.out.println("***************** Send connection to Server ******************");
        return restTemplate.postForObject(adresse +  "/connexion/", client.getUrl(), Boolean.class);

    }

    public Boolean sendNumberOfPlayers(int nbr){
        System.out.println("***************** Send number of players to Server ******************");
        return restTemplate.postForObject(adresse + "/sendnumberplayers/", nbr, Boolean.class);
    }


    public Boolean sendStats(DetailedResults[] results){
        return restTemplate.postForObject(adresse + "/sendStats/", results, Boolean.class);
    }

    public void showStats(){
        System.out.println("***************** Ask for  stats ******************");
        String stat =  restTemplate.postForObject(adresse + "/showStats/", 1, String.class);
        System.out.println(stat);
    }

    public void disconnect(){
        restTemplate.postForObject(adresse + "/disconnect/", 1, Boolean.class);
    }
}
