package board;

import gameelements.Inventory;
import io.cucumber.java8.En;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResolvingConflictsStepDefs implements En {
    Player p1;
    Player p2;
    Inventory i1;
    Inventory i2;
    Board board;

    public ResolvingConflictsStepDefs() {
        Given("^a player (\\d+) has (\\d+) shields$", (Integer arg0, Integer arg1) -> {
            p1 = new Player(arg0);
            p2 = new Player(arg1);
            board = new Board(3, false);
            i1.setConflictPoints(3);
            i2.setConflictPoints(2);
        });
        When("^the two players engage in the conflict$", () -> board.resolveWarConflict());
        Then("^player (\\d+) should win (\\d+) conflict point$", (Integer arg0, Integer arg1) -> assertEquals(1, i1.getConflictPoints()));
    }
}
