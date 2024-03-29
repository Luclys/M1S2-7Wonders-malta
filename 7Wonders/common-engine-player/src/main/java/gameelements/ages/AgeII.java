package gameelements.ages;

import gameelements.cards.Card;
import gameelements.cards.CardsSet;

import java.util.ArrayList;
import java.util.List;

/**
 * The class describe the second Age
 *
 * @author lamac
 */
public class AgeII extends Age {
    public AgeII() {
        vicoryJetonValue = 3;
        isLeftRotation = false;
    }

    public List<Card> initiateCards(int playersCount) {
        if (playersCount <= 2 || playersCount > 7) {
            throw new IllegalStateException("Unexpected playersCount value: " + playersCount);
        }

        List<Card> cards = new ArrayList<>();

        //Brown
        cards.add(CardsSet.SCIERIE);
        cards.add(CardsSet.CARRIERE);
        cards.add(CardsSet.BRIQUETERIE);
        cards.add(CardsSet.FONDERIE);

        //Gray
        cards.add(CardsSet.METIER_A_TISSER);
        cards.add(CardsSet.VERRERIE);
        cards.add(CardsSet.PRESSE);

        //Blue
        cards.add(CardsSet.AQUEDUC);
        cards.add(CardsSet.TEMPLE);
        cards.add(CardsSet.STATUE);
        cards.add(CardsSet.TRIBUNAL);

        //Yellow
        cards.add(CardsSet.FORUM);
        cards.add(CardsSet.CARAVANSERAIL);
        cards.add(CardsSet.VIGNOBLE);

        //Red
        cards.add(CardsSet.MURAILLE);
        cards.add(CardsSet.ECURIES);
        cards.add(CardsSet.CHAMPS_DE_TIR);

        //Green
        cards.add(CardsSet.DISPENSAIRE);
        cards.add(CardsSet.LABORATOIRE);
        cards.add(CardsSet.BIBLIOTHEQUE);
        cards.add(CardsSet.ECOLE);

        if (playersCount >= 4) {
            //Brown
            cards.add(CardsSet.SCIERIE);
            cards.add(CardsSet.CARRIERE);
            cards.add(CardsSet.BRIQUETERIE);
            cards.add(CardsSet.FONDERIE);

            //Yellow
            cards.add(CardsSet.BAZAR);

            //Red
            cards.add(CardsSet.PLACE_D_ARMES);

            //Green
            cards.add(CardsSet.DISPENSAIRE);
        }

        if (playersCount >= 5) {
            //Gray
            cards.add(CardsSet.METIER_A_TISSER);
            cards.add(CardsSet.VERRERIE);
            cards.add(CardsSet.PRESSE);

            //Yellow
            cards.add(CardsSet.CARAVANSERAIL);

            //Red
            cards.add(CardsSet.ECURIES);

            //Green
            cards.add(CardsSet.LABORATOIRE);

            //Blue
            cards.add(CardsSet.TRIBUNAL);
        }

        if (playersCount >= 6) {
            //Blue
            cards.add(CardsSet.TEMPLE);

            //Yellow
            cards.add(CardsSet.FORUM);
            cards.add(CardsSet.CARAVANSERAIL);
            cards.add(CardsSet.VIGNOBLE);

            //Red
            cards.add(CardsSet.PLACE_D_ARMES);
            cards.add(CardsSet.CHAMPS_DE_TIR);

            //Green
            cards.add(CardsSet.BIBLIOTHEQUE);
        }

        if (playersCount >= 7) {
            //Blue
            cards.add(CardsSet.AQUEDUC);
            cards.add(CardsSet.STATUE);

            //Yellow
            cards.add(CardsSet.FORUM);
            cards.add(CardsSet.BAZAR);

            //Red
            cards.add(CardsSet.MURAILLE);
            cards.add(CardsSet.PLACE_D_ARMES);

            //Green
            cards.add(CardsSet.ECOLE);
        }

        return cards;
    }
}