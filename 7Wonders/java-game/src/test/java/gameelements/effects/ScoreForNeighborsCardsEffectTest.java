package gameelements.effects;

import gameelements.Inventory;
import gameelements.Player;
import gameelements.enums.Category;
import gameelements.enums.EffectDelay;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ScoreForNeighborsCardsEffectTest {
    Player player;
    Inventory inv;
    Inventory leftNeighborInv;
    Inventory rightNeighborInv;
    ScoreForNeighborsCardsEffect scoreEffect;


    @BeforeEach
    void setUp() {
        player = new Player(0);
        inv = new Inventory(0);
        leftNeighborInv = new Inventory(1);
        rightNeighborInv = new Inventory(2);
        scoreEffect = new ScoreForNeighborsCardsEffect(5, Category.BATIMENT_COMMERCIAL);
    }

    @Test
    void activateEffectNotEndGameTest() {
        scoreEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, false);
        assertTrue(inv.getEndGameEffects().contains(scoreEffect));
        scoreEffect.setDelay(EffectDelay.INSTANTANEOUS);
        int leftNeighborCardsCount = (int) leftNeighborInv.getPlayedCards().stream().filter(card -> card.getCategory().equals(Category.BATIMENT_COMMERCIAL)).count();
        int rightNeighborCardsCount = (int) rightNeighborInv.getPlayedCards().stream().filter(card -> card.getCategory().equals(Category.BATIMENT_COMMERCIAL)).count();
        int score = (leftNeighborCardsCount + rightNeighborCardsCount) * scoreEffect.getNb();
        scoreEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, false);
        assertEquals(score, inv.getScore());
    }

    @Test
    void activateEffectEndGameTest() {
        int leftNeighborCardsCount = (int) leftNeighborInv.getPlayedCards().stream().filter(card -> card.getCategory().equals(Category.BATIMENT_COMMERCIAL)).count();
        int rightNeighborCardsCount = (int) rightNeighborInv.getPlayedCards().stream().filter(card -> card.getCategory().equals(Category.BATIMENT_COMMERCIAL)).count();
        int score = (leftNeighborCardsCount + rightNeighborCardsCount) * scoreEffect.getNb();
        scoreEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, true);
        assertEquals(score, inv.getScore());
        scoreEffect.setDelay(EffectDelay.INSTANTANEOUS);
        leftNeighborCardsCount = (int) leftNeighborInv.getPlayedCards().stream().filter(card -> card.getCategory().equals(Category.BATIMENT_COMMERCIAL)).count();
        rightNeighborCardsCount = (int) rightNeighborInv.getPlayedCards().stream().filter(card -> card.getCategory().equals(Category.BATIMENT_COMMERCIAL)).count();
        score = (leftNeighborCardsCount + rightNeighborCardsCount) * scoreEffect.getNb();
        scoreEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, true);
        assertEquals(score, inv.getScore());
    }
}
