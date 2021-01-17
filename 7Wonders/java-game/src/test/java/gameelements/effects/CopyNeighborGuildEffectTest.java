package gameelements.effects;

import gameelements.Inventory;
import gameelements.Player;
import gameelements.cards.Card;
import gameelements.cards.CardsSet;
import gameelements.enums.EffectDelay;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CopyNeighborGuildEffectTest {
    Player player;
    Inventory inv;
    Inventory leftNeighborInv;
    Inventory rightNeighborInv;
    CopyNeighborGuildEffect guildEffect;
    ArrayList<Card> cards;

    @BeforeEach
    void setUp() {
        player = new Player(0);
        inv = new Inventory(0);
        leftNeighborInv = new Inventory(1);
        rightNeighborInv = new Inventory(2);
        guildEffect = new CopyNeighborGuildEffect();
        cards = new ArrayList<>();
        cards.add(CardsSet.CASERNE);
        cards.add(CardsSet.HOTEL_DE_VILLE);
        leftNeighborInv.setPlayedCards(cards);
    }

    @Test
    void activateEffectNotEndGameTest() {
        guildEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, false);
        assertTrue(inv.getEndGameEffects().contains(guildEffect));

        guildEffect.setDelay(EffectDelay.INSTANTANEOUS);
        //Not end of the game
        // list is empty
        guildEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, false);
        assertFalse(inv.getPlayedCards().contains(CardsSet.GUILDE_DES_TRAVAILLEURS));
        cards.add(CardsSet.GUILDE_DES_TRAVAILLEURS);

        // list not empty
        leftNeighborInv.setPlayedCards(cards);
        guildEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, false);
        assertTrue(inv.getPlayedCards().contains(CardsSet.GUILDE_DES_TRAVAILLEURS));
    }


    @Test
    void activateEffectEndGameTest() {
        // end of the game
        guildEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, true);
        assertFalse(inv.getPlayedCards().contains(CardsSet.GUILDE_DES_TRAVAILLEURS));
        cards.add(CardsSet.GUILDE_DES_TRAVAILLEURS);
        leftNeighborInv.setPlayedCards(cards);
        guildEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, true);
        assertTrue(inv.getPlayedCards().contains(CardsSet.GUILDE_DES_TRAVAILLEURS));
    }

}
