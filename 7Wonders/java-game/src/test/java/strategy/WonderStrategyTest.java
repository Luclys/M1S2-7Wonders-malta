package strategy;

import gameelements.Inventory;
import gameelements.cards.Card;
import gameelements.cards.CardsSet;
import gameelements.enums.Action;
import gameelements.enums.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class WonderStrategyTest {
    @Mock
    Inventory inv;
    WonderStrategy strategy;
    ArrayList<Card> cards;

    @BeforeEach
    void setUp() {
        strategy = new WonderStrategy();
        cards = new ArrayList<>();
        cards.add(CardsSet.HOTEL_DE_VILLE);
        cards.add(CardsSet.AUTEL);
        doReturn(cards).when(inv).getCardsInHand();
    }


    @Test
    void chooseCardWonderTest() {
        doReturn(true).when(inv).canBuildNextStep(any());
        strategy.chooseCard(inv);
        assertEquals(Action.WONDER, strategy.getAction());
        doReturn(false).when(inv).canBuild(CardsSet.HOTEL_DE_VILLE.getRequiredResources());
        strategy.chooseCard(inv);
        assertEquals(Action.WONDER, strategy.getAction());
    }

    @Test
    void chooseCardBuildingTest() {
        doReturn(false).when(inv).canBuild(any());
        strategy.chooseCard(inv);
        assertEquals(Action.BUILDING, strategy.getAction());

        doReturn(true).when(inv).canBuild(CardsSet.HOTEL_DE_VILLE.getRequiredResources());
        strategy.chooseCard(inv);
        assertEquals(Action.BUILDING, strategy.getAction());
    }

    @Test
    void chooseCardSellTest() {

        doReturn(false).when(inv).canBuildNextStep(any());
        // doReturn(true).when(inv).canBuild(CardsSet.HOTEL_DE_VILLE.getRequiredResources());
        doReturn(cards).when(inv).getPlayedCards();
        strategy.chooseCard(inv);
        assertEquals(Action.SELL, strategy.getAction());
    }

    @Test
    void chooseCardBuildFreeTest() {

        doReturn(1).when(inv).getPossibleFreeBuildings();
        doReturn(false).when(inv).canBuild(any());
        strategy.chooseCard(inv);
        assertEquals(Action.BUILDING, strategy.getAction());

        Resource[] r = inv.getCardsInHand().get(0).getRequiredResources();
        doReturn(true).when(inv).canBuild(r);
        strategy.chooseCard(inv);
        assertEquals(Action.BUILDFREE, strategy.getAction());

    }


}
