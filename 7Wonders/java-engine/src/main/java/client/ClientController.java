package client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {

    @Autowired
    Client client;

    @PostMapping("/stat")
    public String sendStats(@RequestBody String url) throws InterruptedException {

        while(client.stats == null){
            System.out.println("Client "+url+" wait for end of game");
            Thread.sleep(5000);
        }
        return client.sendResultsRest();
    }
}

