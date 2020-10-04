package gameelements.ages;

import gameelements.Card;
import gameelements.CardsSet;

import java.util.ArrayList;

public class AgeII extends Age {
    public static final int conflictPoints = 3;

    ArrayList<Card> initiateCards(int playersCount) {
        ArrayList<Card> cards = new ArrayList<>();

        //Brown
        cards.add(CardsSet.SCIERIE);
        cards.add(CardsSet.CARRIÈRE);
        cards.add(CardsSet.BRIQUETERIE);
        cards.add(CardsSet.FONDERIE);

        //Gray
        cards.add(CardsSet.MÉTIER_A_TISSER);
        cards.add(CardsSet.VERRERIE);
        cards.add(CardsSet.PRESSE);

        //Blue
        cards.add(CardsSet.AQUEDUC);
        cards.add(CardsSet.TEMPLE);
        cards.add(CardsSet.STATUE);
        cards.add(CardsSet.TRIBUNAL);

        //Yellow
        cards.add(CardsSet.FORUM);
        cards.add(CardsSet.CARAVANSÉRAIL);
        cards.add(CardsSet.VIGNOBLE);

        //Red
        cards.add(CardsSet.MURAILLE);
        cards.add(CardsSet.ÉCURIES);
        cards.add(CardsSet.CHAMPS_DE_TIR);

        //Green
        cards.add(CardsSet.DISPENSAIRE);
        cards.add(CardsSet.LABORATOIRE);
        cards.add(CardsSet.BIBLIOTHÈQUE);
        cards.add(CardsSet.ÉCOLE);

        if (playersCount >= 4) {
            //Brown
            cards.add(CardsSet.SCIERIE);
            cards.add(CardsSet.CARRIÈRE);
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
            cards.add(CardsSet.MÉTIER_A_TISSER);
            cards.add(CardsSet.VERRERIE);
            cards.add(CardsSet.PRESSE);

            //Yellow
            cards.add(CardsSet.CARAVANSÉRAIL);

            //Red
            cards.add(CardsSet.ÉCURIES);

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
            cards.add(CardsSet.CARAVANSÉRAIL);
            cards.add(CardsSet.VIGNOBLE);

            //Red
            cards.add(CardsSet.PLACE_D_ARMES);
            cards.add(CardsSet.CHAMPS_DE_TIR);

            //Green
            cards.add(CardsSet.BIBLIOTHÈQUE);
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
            cards.add(CardsSet.ÉCOLE);
        }

        return cards;
    }
}