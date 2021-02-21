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
        System.out.println("Moteur > connexion acceptée pour " + urlClient);
        return true;
    }

    public String getStatFromClient() {
        return restTemplate.postForObject("http://localhost:8081/stat", urlClient, String.class);
    }
}