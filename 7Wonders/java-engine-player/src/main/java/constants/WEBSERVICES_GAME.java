package constants;

public class WEBSERVICES_GAME {
    public static final String CHOOSE_CARD_AND_ACTION = "/choose_card_and_action";
    public static final String CONNECT_ENGINE_PLAYER= "/connexion/";
    public static final String ACKOWNLEDGE_STATUS= "/ackStatus/";
    public static final String CHOOSE_GUILD_CARD= "/choose_guild_card/";
    public static final String CHOOSE_SCIENTIFIC_SYMBOL= "/choose_scientific_symbol/";
    public static final String CHOOSE_DISCARDED_CARD_TO_BUILD= "/choose_discarded_card_to_build/";
    public static final String GET_PLAYER_STRATEGY= "/get_player_strategy/";
    public static final String GET_PLAYER_ID= "/get_player_id/";


    private WEBSERVICES_GAME() {
        throw new IllegalStateException("Utility class");
    }
}
