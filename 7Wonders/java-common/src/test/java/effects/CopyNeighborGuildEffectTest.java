package effects;

import gameelements.Inventory;
import gameelements.Player;
import gameelements.cards.Card;
import gameelements.cards.CardsSet;
import gameelements.effects.CopyNeighborGuildEffect;
import gameelements.enums.EffectDelay;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

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
        cards =new ArrayList<>();
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
        //guildEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, false);

        cards.add(CardsSet.GUILDE_DES_TRAVAILLEURS);
        leftNeighborInv.setPlayedCards(cards);
        guildEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, false);
    }


    @Test
    void activateEffectEndGameTest() {
        // end of the game
//        guildEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, true);
        cards.add(CardsSet.GUILDE_DES_TRAVAILLEURS);
        leftNeighborInv.setPlayedCards(cards);
        guildEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, true);

        guildEffect.setDelay(EffectDelay.INSTANTANEOUS);

        guildEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, true);
        cards.remove(CardsSet.GUILDE_DES_TRAVAILLEURS);
        leftNeighborInv.setPlayedCards(cards);
        //guildEffect.activateEffect(player, inv, leftNeighborInv, rightNeighborInv, true);

    }

    
}
