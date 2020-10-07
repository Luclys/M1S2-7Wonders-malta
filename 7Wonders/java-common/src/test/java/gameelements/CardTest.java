package gameelements;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CardTest {

    @Test
    public void isBatiment(){
        Card card = CardsSet.PRÊTEUR_SUR_GAGES;
        assertTrue(card.isBatiment());
        card = CardsSet.SCIERIE;
        assertFalse(card.isBatiment());
    }
}