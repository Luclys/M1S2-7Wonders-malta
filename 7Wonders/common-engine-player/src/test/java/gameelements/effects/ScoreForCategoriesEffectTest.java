package gameelements.effects;

import gameelements.Inventory;
import gameelements.enums.Category;
import gameelements.enums.EffectDelay;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ScoreForCategoriesEffectTest {
    Inventory inv;
    Inventory leftNeighborInv;
    Inventory rightNeighborInv;
    ScoreForCategoriesEffect scoreEffect;
    ArrayList<Category> categories;

    @BeforeEach
    void setUp() {
        inv = new Inventory(0);
        leftNeighborInv = new Inventory(1);
        rightNeighborInv = new Inventory(2);
        scoreEffect = new ScoreForCategoriesEffect(5);

        categories = new ArrayList<>();
        categories.add(Category.MATIERE_PREMIERE);
        categories.add(Category.PRODUIT_MANUFACTURE);
        categories.add(Category.GUILDE);
    }

    @Test
    void activateEffectNotEndGameTest() {
        assertEquals(categories, scoreEffect.getCategories());
        scoreEffect.activateEffect(inv, leftNeighborInv, rightNeighborInv, false);
        assertTrue(inv.getEndGameEffects().contains(scoreEffect));
        scoreEffect.setDelay(EffectDelay.INSTANTANEOUS);
        int cardsCount = (int) inv.getPlayedCards().stream().filter(card -> scoreEffect.getCategories().contains(card.getCategory())).count();
        int score = scoreEffect.getScore() * cardsCount + inv.getScore();
        scoreEffect.activateEffect(inv, leftNeighborInv, rightNeighborInv, false);
        assertEquals(score, inv.getScore());
    }

    @Test
    void activateEffectEndGameTest() {
        int cardsCount = (int) inv.getPlayedCards().stream().filter(card -> scoreEffect.getCategories().contains(card.getCategory())).count();
        int score = scoreEffect.getScore() * cardsCount + inv.getScore();
        scoreEffect.activateEffect(inv, leftNeighborInv, rightNeighborInv, true);
        assertEquals(score, inv.getScore());
        scoreEffect.setDelay(EffectDelay.INSTANTANEOUS);
        cardsCount = (int) inv.getPlayedCards().stream().filter(card -> scoreEffect.getCategories().contains(card.getCategory())).count();
        score = scoreEffect.getScore() * cardsCount + inv.getScore();
        scoreEffect.activateEffect(inv, leftNeighborInv, rightNeighborInv, true);
        assertEquals(score, inv.getScore());
    }
}