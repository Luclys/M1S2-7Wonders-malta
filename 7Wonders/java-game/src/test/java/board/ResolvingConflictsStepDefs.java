package board;

import gameelements.cards.CardsSet;
import gameelements.Inventory;
import gameelements.Player;
import io.cucumber.java8.En;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResolvingConflictsStepDefs implements En {
    Player p1;
    Player p2;
    Inventory i1;
    Board board;

    private ArrayList<Player> playerList;

    @BeforeEach
    public void setUp() {
        playerList = new ArrayList<>(3);
        for (int i = 0; i < 3; i++) {
            Player player = new Player(i);
            playerList.add(player);
        }
    }

    public ResolvingConflictsStepDefs() {
        Given("^a player (\\d+) has (\\d+) shields$", (Integer arg0, Integer arg1) -> {
            p1 = new Player(arg0);
            p2 = new Player(arg1);
            board = new Board(playerList, false);
            i1.getPlayedCards().set(0, CardsSet.PALISSADE);
        });
        When("^the two players engage in the conflict$", () -> board.resolveWarConflict(1));
        Then("^player (\\d+) should win (\\d+) victory jeton$", (Integer arg0, Integer arg1) -> assertEquals(1, i1.getVictoryChipsScore()));
    }
}
