package engine.board;


import gameelements.Inventory;
import gameelements.enums.Symbol;
import io.cucumber.java8.En;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConflictSteps implements En {
    Inventory inv0 = new Inventory(0);
    Inventory inv1 = new Inventory(1);
    Inventory inv2 = new Inventory(2);

    int initialVictoryPointCount;
    int initialDefeatJetonCount;

    public ConflictSteps() {
        Given("player has three shields", () -> {
            inv0.getAvailableSymbols()[Symbol.BOUCLIER.getIndex()] = 3;

        });

        Given("right neighbor has two shields", () -> {
            inv1.getAvailableSymbols()[Symbol.BOUCLIER.getIndex()] = 2;

        });
        Given("left neighbor has four shields", () -> {
            inv2.getAvailableSymbols()[Symbol.BOUCLIER.getIndex()] = 4;
        });
        When("the players engage in the conflict", () -> {
            initialVictoryPointCount = inv0.getVictoryChipsScore();
            initialDefeatJetonCount = inv0.getDefeatChipsCount();
            PlayersManager p = new PlayersManager();
            p.fightWithNeighbor(inv0, inv1, 1);
            p.fightWithNeighbor(inv0, inv2, 1);
        });
        Then("player should win one conflict point", () -> {
            assertEquals(initialVictoryPointCount + 1, inv0.getVictoryChipsScore());
        });
        Then("the player s defeatChips is incremented", () -> {
            assertEquals(initialDefeatJetonCount + 1, inv0.getDefeatChipsCount());
        });
    }
}

