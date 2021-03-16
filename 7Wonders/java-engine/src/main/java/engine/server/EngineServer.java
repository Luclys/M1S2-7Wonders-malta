package engine.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Component
@Scope("singleton")
@SpringBootApplication
public class EngineServer {
    private final static Logger log = Logger.getLogger(EngineServer.class.getName());

    @Autowired
    EngineServerController ctrl;

    String url;
    String stats;
    List<String> playerURLList;

    public EngineServer() {
        playerURLList = new ArrayList<>(10);
        url = "http://localhost:8081";
        stats = null;
    }

    int addPlayerURL(String url){
        this.playerURLList.add(url);
        return this.playerURLList.size();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getPlayerURLList() {
        return playerURLList;
    }

    public void setPlayerURLList(List<String> playerURLList) {
        this.playerURLList = playerURLList;
    }
}
