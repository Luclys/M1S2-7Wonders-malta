package effects;

import gameelements.Inventory;
import gameelements.Player;
import gameelements.effects.ScoreForCategoryEffect;
import gameelements.enums.Category;
import gameelements.enums.EffectDelay;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ScoreForCategoryEffectTest {
    Player player;
    Inventory inv;
    Inventory leftNeighborInv;
    Inventory rightNeighborInv;
    ScoreForCategoryEffect scoreEffect;

    @BeforeEach
    void setUp() {
        player = new Player(0);
        inv = new Inventory(0);
        leftNeighborInv = new Inventory(1);
        rightNeighborInv = new Inventory(2);
        scoreEffect = new ScoreForCategoryEffect(2, Category.BATIMENT_COMMERCIAL);
    }

    @Test
    void activateEffectNotEndGameTest() {
        assertEquals(Category.BATIMENT_COMMERCIAL.getIndex(), scoreEffect.getCategory().getIndex());
        scoreEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, false);
        assertTrue(inv.getEndGameEffects().contains(scoreEffect));
        scoreEffect.setDelay(EffectDelay.INSTANTANEOUS);
        int cardsCount = (int) inv.getPlayedCards().stream().filter(card -> card.getCategory().equals(Category.BATIMENT_COMMERCIAL)).count();
        int score = scoreEffect.getScore() * cardsCount + inv.getScore();
        scoreEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, false);
        assertEquals(score, inv.getScore());
    }

    @Test
    void activateEffectEndGameTest() {
        int cardsCount = (int) inv.getPlayedCards().stream().filter(card -> card.getCategory().equals(Category.BATIMENT_COMMERCIAL)).count();
        int score = scoreEffect.getScore() * cardsCount + inv.getScore();
        scoreEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, true);
        assertEquals(score, inv.getScore());
        scoreEffect.setDelay(EffectDelay.INSTANTANEOUS);
        cardsCount = (int) inv.getPlayedCards().stream().filter(card -> card.getCategory().equals(Category.BATIMENT_COMMERCIAL)).count();
        score = scoreEffect.getScore() * cardsCount + inv.getScore();
        scoreEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, true);
        assertEquals(score, inv.getScore());
    }
}
