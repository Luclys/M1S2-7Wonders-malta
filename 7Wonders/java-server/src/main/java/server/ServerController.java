package server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import statistic.DetailedResults;

@RestController
public class ServerController {
    @Autowired
    Server server;

    String urlClient;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        // Do any additional configuration here
        return builder.build();
    }
    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/connexion/")
    public boolean connecter(@RequestBody String url) {
        urlClient = url;
        System.out.println("*****************Connection  Client Server ******************");

        System.out.println("Serveur > connexion acceptée pour le client" + urlClient);
        server.lancerPartie();
        return true;
    }

    @PostMapping("/sendnumberplayers/")
    public boolean getNumberofplayers(@RequestBody int s) {
        System.out.println("***************** Get number of players from Client ******************");
        System.out.println("number of players " + s);
        server.setNbPlayer(s);
        return true;
    }

    @PostMapping("/sendStats/")
    public boolean getstats(@RequestBody DetailedResults[] results) {
        server.setStats(results);
        return true;
    }

    @PostMapping("/showStats/")
    public String showStats(@RequestBody int i) {
        System.out.println("***************** Send Stats ******************");
        return server.showStatistics();
    }


}