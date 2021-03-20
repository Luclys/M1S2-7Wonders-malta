import org.junit.jupiter.api.Test;

public class BoardTestITCase {
/*
    Board engine.board;
    Player player;
    PlayingStrategy strategy = new RuleBasedAI();

    @BeforeEach
    void setUp() {
        player = new Player(1, strategy);
        List<Player> playerList = new ArrayList<>(1);
        playerList.add(player);
        engine.board = new Board(playerList, false);
    }

    @Test
    public void executePlayerActionTest() throws Exception {
        Inventory inventory = engine.board.getPlayerInventoryList().get(0);
        List<Card> cards = new ArrayList<Card>();
        cards.add(CardsSet.BIBLIOTHEQUE);
        inventory.setCardsInHand(cards);
        int coins = inventory.getCoins();
        player.getStrategy().setAction(Action.SELL);
        player.getStrategy().setCard(inventory.getCardsInHand().get(0));
        engine.board.executePlayerAction(inventory, player);
        assertEquals(coins+3, inventory.getCoins());
    }
*/
    @Test
    public void testOfTests(){
        System.out.println("Le test est correctement lancé !");
    }
}
