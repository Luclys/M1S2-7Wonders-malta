package board;

import gameelements.Inventory;
import io.cucumber.java8.En;

import static org.junit.jupiter.api.Assertions.*;

public class ResolvingConflictsStepDefs implements En {
    Inventory inv1;
    Inventory inv2;
/*
    public ResolvingConflictsStepDefs() {
        Given("^a player (\\d+) has (\\d+) shields$", (Integer arg0, Integer arg1) -> {
            inv1 = new Inventory(arg0);
            inv2 = new Inventory(arg1);
            inv1.setConflictPoints(3);
            inv2.setConflictPoints(2);
        });
        When("^the two players engage in the conflict$", () -> inv1.fightWithNeighbor(p2, 1));
        Then("^player (\\d+) should win (\\d+) conflict point$", (Integer arg0, Integer arg1) -> assertEquals(1, p1.getConflictPoints()));
    }*/
}
