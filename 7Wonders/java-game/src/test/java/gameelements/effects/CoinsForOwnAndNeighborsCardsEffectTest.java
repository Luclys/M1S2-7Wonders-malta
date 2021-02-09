package gameelements.effects;

import gameelements.Inventory;
import gameelements.enums.Category;
import gameelements.enums.EffectDelay;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CoinsForOwnAndNeighborsCardsEffectTest {
    Inventory inv;
    Inventory leftNeighborInv;
    Inventory rightNeighborInv;
    CoinsForOwnAndNeighborsCardsEffect coinsEffect;


    @BeforeEach
    void setUp() {
        inv = new Inventory(0);
        leftNeighborInv = new Inventory(1);
        rightNeighborInv = new Inventory(2);
        coinsEffect = new CoinsForOwnAndNeighborsCardsEffect(6, Category.BATIMENT_COMMERCIAL);
    }

    @Test
    void activateEffectNotEndGameTest() {
        coinsEffect.activateEffect(inv, leftNeighborInv, rightNeighborInv, false);
        assertTrue(inv.getEndGameEffects().contains(coinsEffect));
        coinsEffect.setDelay(EffectDelay.INSTANTANEOUS);
        int ownCardsCount = (int) inv.getPlayedCards().stream().filter(card -> card.getCategory().equals(Category.BATIMENT_COMMERCIAL)).count();
        int leftNeighborCardsCount = (int) leftNeighborInv.getPlayedCards().stream().filter(card -> card.getCategory().equals(Category.BATIMENT_COMMERCIAL)).count();
        int rightNeighborCardsCount = (int) rightNeighborInv.getPlayedCards().stream().filter(card -> card.getCategory().equals(Category.BATIMENT_COMMERCIAL)).count();
        int coins = inv.getCoins() + (ownCardsCount + leftNeighborCardsCount + rightNeighborCardsCount) * coinsEffect.getNb();
        coinsEffect.activateEffect(inv, leftNeighborInv, rightNeighborInv, false);
        assertEquals(coins, inv.getCoins());
    }

    @Test
    void activateEffectEndGameTest() {
        int ownCardsCount = (int) inv.getPlayedCards().stream().filter(card -> card.getCategory().equals(Category.BATIMENT_COMMERCIAL)).count();
        int leftNeighborCardsCount = (int) leftNeighborInv.getPlayedCards().stream().filter(card -> card.getCategory().equals(Category.BATIMENT_COMMERCIAL)).count();
        int rightNeighborCardsCount = (int) rightNeighborInv.getPlayedCards().stream().filter(card -> card.getCategory().equals(Category.BATIMENT_COMMERCIAL)).count();
        int coins = inv.getCoins() + (ownCardsCount + leftNeighborCardsCount + rightNeighborCardsCount) * coinsEffect.getNb();
        coinsEffect.activateEffect(inv, leftNeighborInv, rightNeighborInv, true);
        assertEquals(coins, inv.getCoins());
        coinsEffect.setDelay(EffectDelay.INSTANTANEOUS);
        ownCardsCount = (int) inv.getPlayedCards().stream().filter(card -> card.getCategory().equals(Category.BATIMENT_COMMERCIAL)).count();
        leftNeighborCardsCount = (int) leftNeighborInv.getPlayedCards().stream().filter(card -> card.getCategory().equals(Category.BATIMENT_COMMERCIAL)).count();
        rightNeighborCardsCount = (int) rightNeighborInv.getPlayedCards().stream().filter(card -> card.getCategory().equals(Category.BATIMENT_COMMERCIAL)).count();
        coins = inv.getCoins() + (ownCardsCount + leftNeighborCardsCount + rightNeighborCardsCount) * coinsEffect.getNb();
        coinsEffect.activateEffect(inv, leftNeighborInv, rightNeighborInv, true);
        assertEquals(coins, inv.getCoins());
    }

}
