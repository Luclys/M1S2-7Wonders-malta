package gameelements;

import gameelements.cards.Card;
import gameelements.cards.CardsSet;
import gameelements.enums.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {
    Inventory inventory;

    @BeforeEach
    void setUp() {
        inventory = new Inventory(0);
    }

    @Test
    void discardLastCardTest() {
        // the method throw an erreur if the number of cards in hand of the
        // inventory is more then 1
        assertThrows(Error.class, () -> inventory.discardLastCard());
        // test if the number of cards is 1
        setCards();
        assertSame(inventory.getCardsInHand().get(0), inventory.discardLastCard());
        assertTrue(inventory.getCardsInHand().isEmpty());
    }

    @Test
    void sellCardTest() {
        // if the inventory try to sell a card that he doesn't have an erreur is thrown
        assertThrows(Error.class, () -> inventory.sellCard(CardsSet.CHANTIER));
        int coins = inventory.getCoins();
        setCards();
        // if the inventory try to sell a card that he has then he gets 3 coins
        inventory.sellCard(CardsSet.CHANTIER);
        assertEquals(coins + 3, inventory.getCoins());
    }

    @Test
    void updateInventoryTest() {
        setCards();
        assertEquals(CardsSet.CHANTIER, inventory.getCardsInHand().get(0));
        inventory.updateInventory(CardsSet.CHANTIER, null, null, null);
        assertEquals(0, inventory.getCardsInHand().size());
        assertEquals(CardsSet.CHANTIER, inventory.getPlayedCards().get(0));
    }

    @Test
    void addCoinsTest() {
        inventory.setCoins(0);
        inventory.addCoins(5);
        assertEquals(5, inventory.getCoins());
    }

    @Test
    void removeCoinsTest() {
        inventory.setCoins(5);
        inventory.removeCoins(3);
        assertEquals(2, inventory.getCoins());
    }

    void setCards() {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(CardsSet.CHANTIER);
        inventory.setCardsInHand(cards);
    }

    /**
     * Player wants to build FORUM and player has COMPTOIR EST already built
     *
     * @result Player can build FORUM for free
     */
    @Test
    void canPlayCardForFree() {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(CardsSet.COMPTOIR_EST);
        inventory.setPlayedCards(cards);
        assertTrue(inventory.canBuildCardForFree(CardsSet.FORUM));
    }

    /**
     * Player wants to build FORUM and player don't have any buildings which allow to build for free
     *
     * @result Player cannot build FORUM for free
     */
    @Test
    void cannotPlayCardForFreeNoBuildingsBuilt() {
        assertFalse(inventory.canBuildCardForFree(CardsSet.FORUM));
    }

    /**
     * Player wants to build MARCHE which doesn't have any buildings which allow to build it for free
     *
     * @result Player cannot build MARCHE for free
     */
    @Test
    void cannotPlayCardForFreeCardDoesNotHaveRequiredBuildings() {
        assertFalse(inventory.canBuildCardForFree(CardsSet.MARCHE));
    }

    /**
     * Player wants to build a card with required resources and he has all resources available
     *
     * @result Player can build
     */
    @Test
    void canBuildHasAllResources() {
        Resource[] resources = new Resource[]{Resource.ARGILE, Resource.BOIS};
        inventory.getAvailableResources()[Resource.ARGILE.getIndex()]++;
        inventory.getAvailableResources()[Resource.BOIS.getIndex()]++;
        assertTrue(inventory.canBuild(resources));
    }

    /**
     * Player wants to build a card with required resources but he doesn't have all resources available
     * and has no resources for choice
     *
     * @result Player can't build
     */
    @Test
    void cantBuildNotAllResources() {
        Resource[] resources = new Resource[]{Resource.ARGILE, Resource.BOIS};
        inventory.getAvailableResources()[Resource.ARGILE.getIndex()]++;
        assertFalse(inventory.canBuild(resources));
    }

    /**
     * Player wants to build a card with required resources but he doesn't have one matiere premiere in available resources
     * but has one matiere premiere for choice
     *
     * @result Player can build
     */
    @Test
    void canBuildUseMatierePremiereForChoice() {
        Resource[] resources = new Resource[]{Resource.ARGILE, Resource.BOIS};
        inventory.getAvailableResources()[Resource.ARGILE.getIndex()]++;
        inventory.setAnyMatierePremiereAvailableCount(1);
        assertTrue(inventory.canBuild(resources));
    }

    /**
     * Player wants to build a card with required resources but he doesn't have one produit manifacture in available resources
     * but has one produit manifacture for choice
     *
     * @result Player can build
     */
    @Test
    void canBuildUseProduitManufactureForChoice() {
        Resource[] resources = new Resource[]{Resource.VERRE, Resource.TISSU};
        inventory.getAvailableResources()[Resource.VERRE.getIndex()]++;
        inventory.setAnyResourceManufactureAvailableCount(1);
        assertTrue(inventory.canBuild(resources));
    }

    /**
     * Player wants to build a card with required resources but he doesn't have several matieres premieres in available resources
     * but has enough matieres premieres for choice
     *
     * @result Player can build
     */
    @Test
    void canBuildUseMatieresPremieresForChoice() {
        Resource[] resources = new Resource[]{Resource.ARGILE, Resource.BOIS, Resource.MINERAI};
        inventory.setAnyMatierePremiereAvailableCount(3);
        assertTrue(inventory.canBuild(resources));
    }

    /**
     * Player wants to build a card with required resources but he doesn't have several produits manifactures in available resources
     * but has enough produits manifactures for choice
     *
     * @result Player can build
     */
    @Test
    void canBuildUseProduitsManufacturesForChoice() {
        Resource[] resources = new Resource[]{Resource.TISSU, Resource.TISSU, Resource.VERRE};
        inventory.setAnyResourceManufactureAvailableCount(3);
        assertTrue(inventory.canBuild(resources));
    }

    /**
     * Player wants to build a card with required resources but he doesn't have several produits manifactures
     * and matieres premieres in available resources but has enough resources for choice
     *
     * @result Player can build
     */
    @Test
    void canBuildUseBothCategoriesForChoice() {
        Resource[] resources = new Resource[]{Resource.PIERRE, Resource.TISSU, Resource.VERRE, Resource.PAPYRUS, Resource.BOIS};
        inventory.getAvailableResources()[Resource.PIERRE.getIndex()]++;
        inventory.getAvailableResources()[Resource.TISSU.getIndex()]++;
        inventory.setAnyMatierePremiereAvailableCount(1);
        inventory.setAnyResourceManufactureAvailableCount(2);
        assertTrue(inventory.canBuild(resources));
    }

    /**
     * Player wants to build a card with required resources but he doesn't have several produits manifactures
     * and matieres premieres in available resources and doesn't have enough resources for choice
     *
     * @result Player can't build
     */
    @Test
    void cantBuildUseBothCategoriesForChoice() {
        Resource[] resources = new Resource[]{Resource.PIERRE, Resource.TISSU, Resource.VERRE, Resource.PAPYRUS, Resource.BOIS};
        inventory.getAvailableResources()[Resource.PIERRE.getIndex()]++;
        inventory.getAvailableResources()[Resource.TISSU.getIndex()]++;
        inventory.setAnyMatierePremiereAvailableCount(1);
        inventory.setAnyResourceManufactureAvailableCount(1);
        assertFalse(inventory.canBuild(resources));
    }
}
