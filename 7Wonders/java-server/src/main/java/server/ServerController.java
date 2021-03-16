package server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import statistic.DetailedResults;

import static constants.WEBSERVICES_STATS.*;

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

    @PostMapping(CONNECT_ENGINE_STATS)
    public boolean connecter(@RequestBody String url) {
        urlClient = url;
        System.out.println("***************** Connection Engine to StatsServer ******************");

        System.out.println("StatsServer > Connexion granted to the engine : " + urlClient);
        server.lancerPartie();
        return true;
    }

    @PostMapping(SEND_NB_PLAYERS)
    public boolean getNumberofplayers(@RequestBody int s) {
        System.out.println("***************** Get number of players from Engine ******************");
        System.out.println("number of players " + s);
        server.setNbPlayer(s);
        return true;
    }

    @PostMapping(SEND_STATS)
    public boolean getstats(@RequestBody DetailedResults[] results) {
        server.setStats(results);
        return true;
    }

    @PostMapping(SHOW_STATS)
    public String showStats(@RequestBody int i) {
        System.out.println("***************** Send Stats ******************");
        return server.showStatistics();
    }

    @GetMapping(DISCONNECT)
    public int disconnect() {
        System.out.println("***************** Hard Stopping Server ******************");
        return server.InitiateExit();
    }

}