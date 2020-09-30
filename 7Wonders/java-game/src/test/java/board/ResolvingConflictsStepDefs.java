package board;

import io.cucumber.java8.En;
import static org.junit.jupiter.api.Assertions.*;

public class ResolvingConflictsStepDefs implements En {
    Player p1;
    Player p2;

    public ResolvingConflictsStepDefs() {
        Given("^a player (\\d+) has (\\d+) shields$", (Integer arg0, Integer arg1) -> {
            p1 = new Player(arg0);
            p2 = new Player(arg1);
            p1.setConflictPoints(3);
            p2.setConflictPoints(2);
        });
        When("^the two players engage in the conflict$", () -> p1.fightWithNeighbor(p2, 1));
        Then("^player (\\d+) should win (\\d+) conflict point$", (Integer arg0, Integer arg1) -> assertEquals(1, p1.getConflictPoints()));
    }
}
