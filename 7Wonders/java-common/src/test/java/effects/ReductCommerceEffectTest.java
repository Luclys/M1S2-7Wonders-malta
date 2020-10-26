package effects;

import gameelements.Inventory;
import gameelements.Player;
import gameelements.effects.ReductCommerceEffect;
import gameelements.enums.EffectDelay;
import gameelements.enums.Neighbor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReductCommerceEffectTest {
    Player player;
    Inventory inv;
    Inventory leftNeighborInv;
    Inventory rightNeighborInv;
    ReductCommerceEffect commerceEffect;

    @BeforeEach
    void setUp() {
        player = new Player(0);
        inv = new Inventory(0);
        leftNeighborInv = new Inventory(1);
        rightNeighborInv = new Inventory(2);
        commerceEffect = new ReductCommerceEffect(Neighbor.LEFT, true);
    }

    @Test
    void activateEffectNotEndGamePrimaryRessourcesLeftNeighborTest() {
        assertEquals(Neighbor.LEFT.getIndex(), commerceEffect.getWhichNeighbor().getIndex());
        int priceRight = inv.getMatieresPremieresPriceRight();
        int pricemanifacture = inv.getProduitsManifacturesPrice();
        //Not end of the game and left neihghbor, primaryRessource
        commerceEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, false);
        assertEquals(1, inv.getMatieresPremieresPriceLeft());
        assertEquals(priceRight, inv.getMatieresPremieresPriceRight());
        assertEquals(pricemanifacture, inv.getProduitsManifacturesPrice());
        commerceEffect.setDelay(EffectDelay.END_OF_THE_GAME);
        commerceEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, false);
        assertTrue(inv.getEndGameEffects().contains(commerceEffect));
    }

    @Test
    void activateEffectNotEndGameManifactureProductTest() {
        commerceEffect = new ReductCommerceEffect(Neighbor.LEFT, false);
        int priceLeft = inv.getMatieresPremieresPriceLeft();
        int priceRight = inv.getMatieresPremieresPriceRight();
        int pricemanifacture = inv.getProduitsManifacturesPrice();
        //Not end of the game and left neihghbor, primaryRessource
        commerceEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, false);
        assertEquals(priceLeft, inv.getMatieresPremieresPriceLeft());
        assertEquals(priceRight, inv.getMatieresPremieresPriceRight());
        assertEquals(1, inv.getProduitsManifacturesPrice());
        pricemanifacture = inv.getProduitsManifacturesPrice();
        commerceEffect.setDelay(EffectDelay.END_OF_THE_GAME);
        commerceEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, false);
        assertTrue(inv.getEndGameEffects().contains(commerceEffect));
        assertEquals(pricemanifacture, inv.getProduitsManifacturesPrice());
    }

    @Test
    void activateEffectNotEndGamePrimaryRessourcesRightNeighborTest() {
        commerceEffect = new ReductCommerceEffect(Neighbor.RIGHT, true);
        int priceLeft = inv.getMatieresPremieresPriceLeft();
        int pricemanifacture = inv.getProduitsManifacturesPrice();
        //Not end of the game and left neihghbor, primaryRessource
        commerceEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, false);
        assertEquals(priceLeft, inv.getMatieresPremieresPriceLeft());
        assertEquals(1, inv.getMatieresPremieresPriceRight());
        assertEquals(pricemanifacture, inv.getProduitsManifacturesPrice());
        commerceEffect.setDelay(EffectDelay.END_OF_THE_GAME);
        commerceEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, false);
        assertTrue(inv.getEndGameEffects().contains(commerceEffect));
    }

    @Test
    void activateEffectEndGamePrimaryRessourcesRightNeighborTest() {
        commerceEffect = new ReductCommerceEffect(Neighbor.RIGHT, true);
        int priceLeft = inv.getMatieresPremieresPriceLeft();
        int pricemanifacture = inv.getProduitsManifacturesPrice();
        commerceEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, true);
        assertEquals(priceLeft, inv.getMatieresPremieresPriceLeft());
        assertEquals(1, inv.getMatieresPremieresPriceRight());
        assertEquals(pricemanifacture, inv.getProduitsManifacturesPrice());
        commerceEffect.setDelay(EffectDelay.END_OF_THE_GAME);
        commerceEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, true);
        assertEquals(priceLeft, inv.getMatieresPremieresPriceLeft());
        assertEquals(1, inv.getMatieresPremieresPriceRight());
        assertEquals(pricemanifacture, inv.getProduitsManifacturesPrice());
    }

    @Test
    void activateEffectEndGamePrimaryRessourcesLeftNeighborTest() {
        int priceRight = inv.getMatieresPremieresPriceRight();
        int pricemanifacture = inv.getProduitsManifacturesPrice();
        commerceEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, true);
        assertEquals(1, inv.getMatieresPremieresPriceLeft());
        assertEquals(priceRight, inv.getMatieresPremieresPriceRight());
        assertEquals(pricemanifacture, inv.getProduitsManifacturesPrice());
        commerceEffect.setDelay(EffectDelay.END_OF_THE_GAME);
        commerceEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, true);
        assertEquals(1, inv.getMatieresPremieresPriceLeft());
        assertEquals(priceRight, inv.getMatieresPremieresPriceRight());
        assertEquals(pricemanifacture, inv.getProduitsManifacturesPrice());
    }

    @Test
    void activateEffectEndGameManifactureProductTest() {
        commerceEffect = new ReductCommerceEffect(Neighbor.RIGHT, false);
        int priceLeft = inv.getMatieresPremieresPriceLeft();
        int priceRight = inv.getMatieresPremieresPriceRight();
        int pricemanifacture = inv.getProduitsManifacturesPrice();
        commerceEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, true);
        assertEquals(priceLeft, inv.getMatieresPremieresPriceLeft());
        assertEquals(priceRight, inv.getMatieresPremieresPriceRight());
        assertEquals(1, inv.getProduitsManifacturesPrice());
        pricemanifacture = inv.getProduitsManifacturesPrice();
        commerceEffect.setDelay(EffectDelay.END_OF_THE_GAME);
        commerceEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, true);
        assertEquals(priceLeft, inv.getMatieresPremieresPriceLeft());
        assertEquals(priceRight, inv.getMatieresPremieresPriceRight());
        assertEquals(1, inv.getProduitsManifacturesPrice());
        assertEquals(pricemanifacture, inv.getProduitsManifacturesPrice());
    }

    @Test
    void activateEffectEndGamePrimaryRessourcesBothNeighborsTest() {
        commerceEffect = new ReductCommerceEffect(Neighbor.BOTH, true);
        int pricemanifacture = inv.getProduitsManifacturesPrice();
        commerceEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, true);
        assertEquals(1, inv.getMatieresPremieresPriceLeft());
        assertEquals(1, inv.getMatieresPremieresPriceRight());
        assertEquals(pricemanifacture, inv.getProduitsManifacturesPrice());
        commerceEffect.setDelay(EffectDelay.END_OF_THE_GAME);
        commerceEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, true);
        assertEquals(1, inv.getMatieresPremieresPriceLeft());
        assertEquals(1, inv.getMatieresPremieresPriceRight());
        assertEquals(pricemanifacture, inv.getProduitsManifacturesPrice());
    }
}
