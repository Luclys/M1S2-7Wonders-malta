package effects;

import gameelements.Inventory;
import gameelements.Player;
import gameelements.effects.ChoiceResourceEffect;
import gameelements.enums.EffectDelay;
import gameelements.enums.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChoiceResourceEffectTest {
    Player player;
    Inventory inv;
    Inventory leftNeighborInv;
    Inventory rightNeighborInv;
    ChoiceResourceEffect choiceResourceEffect;
    Resource[] resources;
    @BeforeEach
    void setUp() {
        player = new Player(0);
        inv = new Inventory(0);
        leftNeighborInv = new Inventory(1);
        rightNeighborInv = new Inventory(2);
        resources=new Resource[2];
        resources[0]=Resource.ARGILE;
        resources[1]=Resource.PAPYRUS;

        choiceResourceEffect = new ChoiceResourceEffect(resources,2);
    }

    @Test
    void activateEffectNotEndGameTest(){
        //Not end of the game
        choiceResourceEffect.activateEffect(player,inv,leftNeighborInv,rightNeighborInv,false);
        assertTrue(inv.getPairResChoice().contains(resources));
        choiceResourceEffect.setDelay(EffectDelay.END_OF_THE_GAME);
        choiceResourceEffect.activateEffect(player,inv,leftNeighborInv,rightNeighborInv,false);
        assertTrue(inv.getEndGameEffects().contains(choiceResourceEffect));
        Resource[] r=new Resource[1];
        inv = new Inventory(1);
        choiceResourceEffect =new ChoiceResourceEffect(r,2);
        assertFalse(inv.getEndGameEffects().contains(choiceResourceEffect));
        assertFalse(inv.getPairResChoice().contains(r));
        choiceResourceEffect.setDelay(EffectDelay.END_OF_THE_GAME);
        choiceResourceEffect.activateEffect(player,inv,leftNeighborInv,rightNeighborInv,false);
        assertTrue(inv.getEndGameEffects().contains(choiceResourceEffect));
    }

    @Test
    void activateEffectEndGameTest(){
        //Not end of the game
        choiceResourceEffect.activateEffect(player,inv,leftNeighborInv,rightNeighborInv,true);
        assertTrue(inv.getPairResChoice().contains(resources));
        choiceResourceEffect.setDelay(EffectDelay.END_OF_THE_GAME);
        choiceResourceEffect.activateEffect(player,inv,leftNeighborInv,rightNeighborInv,true);
        assertTrue(inv.getPairResChoice().contains(resources));
        Resource[] r=new Resource[1];
        inv = new Inventory(1);
        choiceResourceEffect =new ChoiceResourceEffect(r,2);
        assertFalse(inv.getEndGameEffects().contains(choiceResourceEffect));
        assertFalse(inv.getPairResChoice().contains(r));
        choiceResourceEffect.setDelay(EffectDelay.END_OF_THE_GAME);
        choiceResourceEffect.activateEffect(player,inv,leftNeighborInv,rightNeighborInv,true);
        assertFalse(inv.getEndGameEffects().contains(choiceResourceEffect));
    }

}
