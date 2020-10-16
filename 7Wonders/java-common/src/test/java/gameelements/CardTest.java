package gameelements;


import gameelements.cards.Card;
import gameelements.cards.CardsSet;
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
    }
}