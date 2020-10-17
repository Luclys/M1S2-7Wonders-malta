package gameelements.ages;

import gameelements.cards.Card;
import gameelements.cards.CardsSet;

import java.util.ArrayList;
import java.util.List;

public class AgeI extends Age {
    public AgeI() {
        vicoryJetonValue = 1;
        isLeftRotation = true;
    }

    public List<Card> initiateCards(int playersCount) {
        if (playersCount <= 2 || playersCount > 7) {
            throw new IllegalStateException("Unexpected playersCount value: " + playersCount);
        }

        List<Card> cards = new ArrayList<>();

        //Brown
        cards.add(CardsSet.CHANTIER);
        cards.add(CardsSet.CAVITE);
        cards.add(CardsSet.BASSIN_ARGILEUX);
        cards.add(CardsSet.FILON);
        cards.add(CardsSet.FOSSE_ARGILEUSE);
        cards.add(CardsSet.EXPLOITATION_FORESTIERE);

        //Gray
        cards.add(CardsSet.METIER_A_TISSER);
        cards.add(CardsSet.VERRERIE);
        cards.add(CardsSet.PRESSE);

        //Blue
        cards.add(CardsSet.BAINS);
        cards.add(CardsSet.AUTEL);
        cards.add(CardsSet.THEATRE);

        //Yellow
        cards.add(CardsSet.COMPTOIR_EST);
        cards.add(CardsSet.COMPTOIR_OUEST);
        cards.add(CardsSet.MARCHE);

        //Green
        cards.add(CardsSet.OFFICINE);
        cards.add(CardsSet.ATELIER);
        cards.add(CardsSet.SCRIPTORIUM);

        //Red
        cards.add(CardsSet.PALISSADE);
        cards.add(CardsSet.CASERNE);
        cards.add(CardsSet.TOUR_DE_GARDE);

        if (playersCount >= 4) {
            //Brown
            cards.add(CardsSet.CHANTIER);
            cards.add(CardsSet.FILON);
            cards.add(CardsSet.EXCAVATION);

            //Blue
            cards.add(CardsSet.PRETEUR_SUR_GAGES);

            //Yellow
            cards.add(CardsSet.TAVERNE);

            //Red
            cards.add(CardsSet.TOUR_DE_GARDE);

            //Green
            cards.add(CardsSet.SCRIPTORIUM);
        }

        if (playersCount >= 5) {
            //Brown
            cards.add(CardsSet.CAVITE);
            cards.add(CardsSet.BASSIN_ARGILEUX);
            cards.add(CardsSet.GISEMENT);

            //Blue
            cards.add(CardsSet.AUTEL);

            //Yellow
            cards.add(CardsSet.TAVERNE);

            //Red
            cards.add(CardsSet.CASERNE);

            //Green
            cards.add(CardsSet.OFFICINE);
        }

        if (playersCount >= 6) {
            //Brown
            cards.add(CardsSet.FRICHE);
            cards.add(CardsSet.MINE);

            //Gray
            cards.add(CardsSet.METIER_A_TISSER);
            cards.add(CardsSet.VERRERIE);
            cards.add(CardsSet.PRESSE);

            //Blue
            cards.add(CardsSet.THEATRE);
            cards.add(CardsSet.MARCHE);
        }

        if (playersCount == 7) {
            //Blue
            cards.add(CardsSet.PRETEUR_SUR_GAGES);
            cards.add(CardsSet.BAINS);

            //Yellow
            cards.add(CardsSet.COMPTOIR_EST);
            cards.add(CardsSet.COMPTOIR_OUEST);
            cards.add(CardsSet.TAVERNE);

            //Red
            cards.add(CardsSet.PALISSADE);

            //Green
            cards.add(CardsSet.ATELIER);
        }

        return cards;
    }
}
