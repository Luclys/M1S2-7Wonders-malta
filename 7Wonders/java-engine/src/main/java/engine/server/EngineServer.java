package engine.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import statistic.DetailedResults;

import java.util.logging.Logger;

@Component
@Scope("singleton")
@SpringBootApplication
public class EngineServer {
    private final static Logger log = Logger.getLogger(EngineServer.class.getName());

    EngineServer partie;

    @Autowired
    EngineServerController crl;

    String url;
    String stats;

    public EngineServer() {
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
