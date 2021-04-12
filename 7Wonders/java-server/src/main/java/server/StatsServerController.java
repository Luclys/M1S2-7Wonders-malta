package server;

import constants.WEBSERVICES_STATS;
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

import static constants.WEBSERVICES_STATS.*;

@RestController
public class StatsServerController {
    @Autowired
    StatsServer statsServer;

    String urlClient;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        // Do any additional configuration here
        return builder.build();
    }

    @GetMapping(CONNECT_ENGINE_STATS)
    public boolean connectEngineStats(HttpServletRequest request) {
        // request.getRemoteHost()
        // C'est chiant, le port c'est 8081 car 8080 est occupé par StatServer... Pb : comment être sûr du port ?!
        urlClient = "http://" + request.getRemoteAddr() + ":8081";
       // urlClient = "http://192.168.56.1:8081";
        System.out.println("STATS SERVER CONTROLLER > ***************** Connection Engine Server to Stats Server ******************");

        System.out.println("STATS SERVER CONTROLLER > Connexion granted to the engine : " + urlClient);

        statsServer.lancerPartie();
        return true;
    }

    @PostMapping(SEND_NB_PLAYERS)
    public int getNumberOfPlayers(@RequestBody int s) {
        System.out.println("STATS SERVER CONTROLLER > ***************** Get number of players from Engine Server ******************");
        System.out.println("STATS SERVER CONTROLLER > Number of Players is : " + s);
        statsServer.setNbPlayer(s);
        return statsServer.getNbPlayers();
    }

    @PostMapping(SEND_STATS)
    public int getStats(@RequestBody DetailedResults[] results) {
        System.out.println("STATS SERVER CONTROLLER > ***************** Get stats from Engine Server ******************");
        int currentGame = statsServer.setStats(results);
        System.out.println("STATS SERVER CONTROLLER > Current game is : "+currentGame);
        return  currentGame;
    }

    @GetMapping(SHOW_STATS)
    public String showStats() {
        System.out.println("STATS SERVER CONTROLLER > ***************** Send Stats to Engine Server  ******************");
        return statsServer.showStatistics();
    }

    @GetMapping(WEBSERVICES_STATS.DISCONNECT_ENGINE_STATS)
    public int disconnect() {
        System.out.println("STATS SERVER CONTROLLER > ***************** Hard Stopping Server ******************");
        return statsServer.InitiateExit();
    }

}