package server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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
        System.out.println("Serveur > connexion acceptée pour le client" + urlClient);
        return true;
    }

    public String getStatFromClient() {
        return restTemplate.postForObject(urlClient+"/stat", urlClient, String.class);
    }
}