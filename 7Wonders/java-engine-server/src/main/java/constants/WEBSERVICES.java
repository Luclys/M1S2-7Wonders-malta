package constants;

public class WEBSERVICES {
    //ask/send the end game inventories
    public static final String SEND_NB_PLAYERS = "/sendNbPlayers/";
    public static final String CONNEXION = "/connexion/";
    public static final String SEND_STATS = "/sendStats/";
    public static final String SHOW_STATS = "/showStats/";
    public static final String DISCONNECT = "/disconnect/";

    private WEBSERVICES() {
        throw new IllegalStateException("Utility class");
    }
}
