package gameelements.cards;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CardTest {

    @Test
    void isBatiment() {
        Card card = CardsSet.PRETEUR_SUR_GAGES;
        assertTrue(card.isBuilding());
        card = CardsSet.SCIERIE;
        assertFalse(card.isBuilding());
        card = CardsSet.FORUM;
        assertTrue(card.isBuilding());
        card = CardsSet.DISPENSAIRE;
        assertTrue(card.isBuilding());
        card = CardsSet.PALISSADE;
        assertTrue(card.isBuilding());
        card = CardsSet.GUILDE_DES_TRAVAILLEURS;
        assertFalse(card.isBuilding());
    }
}