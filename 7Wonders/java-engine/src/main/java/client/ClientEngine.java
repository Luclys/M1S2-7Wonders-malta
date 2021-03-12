package client;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class ClientEngine {


    String url;
    @Autowired
    ClientEngineController crl;

    String stats;

    public ClientEngine() {
        url = "http://localhost:8081";
        stats = null;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
