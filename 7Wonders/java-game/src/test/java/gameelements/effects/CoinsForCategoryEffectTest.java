package gameelements.effects;

import gameelements.Inventory;
import gameelements.Player;
import gameelements.enums.Category;
import gameelements.enums.EffectDelay;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CoinsForCategoryEffectTest {

    Player player;
    Inventory inv;
    Inventory leftNeighborInv;
    Inventory rightNeighborInv;
    CoinsForCategoryEffect coinsForCategoryEffect;


    @BeforeEach
    void setUp() {
        player = new Player(0);
        inv = new Inventory(0);
        leftNeighborInv = new Inventory(1);
        rightNeighborInv = new Inventory(2);
        coinsForCategoryEffect = new CoinsForCategoryEffect(1, Category.MATIERE_PREMIERE);
    }

    @Test
    void activateEffectNotEndGameTest() {
        assertEquals(Category.MATIERE_PREMIERE, coinsForCategoryEffect.getCategory());
        int cardsCount = (int) inv.getPlayedCards().stream().filter(card -> card.getCategory().equals(coinsForCategoryEffect.getCategory())).count();
        int coins = inv.getCoins() + cardsCount * coinsForCategoryEffect.getNbCoins();
        //Not end of the game
        coinsForCategoryEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, false);
        assertEquals(coins, inv.getCoins());
        coinsForCategoryEffect.setDelay(EffectDelay.END_OF_THE_GAME);
        coinsForCategoryEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, false);
        assertEquals(coins, inv.getCoins());
        assertTrue(inv.getEndGameEffects().contains(coinsForCategoryEffect));
    }

    @Test
    void activateEffectEndGameTest() {
        int cardsCount = (int) inv.getPlayedCards().stream().filter(card -> card.getCategory().equals(coinsForCategoryEffect.getCategory())).count();
        int coins = inv.getCoins() + cardsCount * coinsForCategoryEffect.getNbCoins();
        //Not end of the game
        coinsForCategoryEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, true);
        assertEquals(coins, inv.getCoins());
        coinsForCategoryEffect.setDelay(EffectDelay.END_OF_THE_GAME);
        cardsCount = (int) inv.getPlayedCards().stream().filter(card -> card.getCategory().equals(coinsForCategoryEffect.getCategory())).count();
        coins = inv.getCoins() + cardsCount * coinsForCategoryEffect.getNbCoins();
        coinsForCategoryEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, true);
        assertEquals(coins, inv.getCoins());
    }
}
