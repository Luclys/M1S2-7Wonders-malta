package client;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
public class ClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {

        return builder.build();
    }

    @Bean
    public CommandLineRunner unClient(RestTemplate restTemplate, @Autowired Client client) {
        return args -> {
            /// retrieving the value
            String serverIp = "172.28.0.253";
            Boolean val = restTemplate.postForObject("http://localhost:8080/connexion/", client.getUrl(), Boolean.class);
            System.out.println("client > la connexion est-elle acceptée ? "+val);
        };
    }
}