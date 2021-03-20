package constants;

public class WEBSERVICES_STATS {
    //ask/send the end game inventories
    public static final String CONNECT_ENGINE_STATS = "/connectEngineStats";
    public static final String DISCONNECT_ENGINE_STATS = "/disconnectEngineStats";
    public static final String SEND_NB_PLAYERS = "/sendNbPlayers";
    public static final String SEND_STATS = "/sendStats";
    public static final String SHOW_STATS = "/showStats";

    private WEBSERVICES_STATS() {
        throw new IllegalStateException("Utility class");
    }
}
