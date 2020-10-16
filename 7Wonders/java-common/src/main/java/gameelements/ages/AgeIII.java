package gameelements.ages;

import gameelements.cards.Card;
import gameelements.cards.CardsSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AgeIII extends Age {
    public AgeIII() {
        vicoryJetonValue = 1;
        isLeftRotation = true;
    }

    private static List<Card> initiateGuilds(int playersCount) {
        List<Card> cards = new ArrayList<>();
        cards.add(CardsSet.GUILDE_DES_TRAVAILLEURS);
        cards.add(CardsSet.GUILDE_DES_ARTISANS);
        cards.add(CardsSet.GUILDE_DES_COMMERCANTS);
        cards.add(CardsSet.GUILDE_DES_PHILOSOPHES);
        cards.add(CardsSet.GUILDE_DES_ESPIONS);
        cards.add(CardsSet.GUILDE_DES_STRATEGES);
        cards.add(CardsSet.GUILDE_DES_ARMATEURS);
        cards.add(CardsSet.GUILDE_DES_SCIENTIFIQUES);
        cards.add(CardsSet.GUILDE_DES_MAGISTRATS);
        cards.add(CardsSet.GUILDE_DES_BATISSEURS);

        Collections.shuffle(cards);
        int lastIndex = Math.min(playersCount + 2, cards.size() - 1);
        return new ArrayList<>(cards.subList(0, lastIndex));
    }

    public List<Card> initiateCards(int playersCount) {
        if (playersCount <= 2 || playersCount > 7) {
            throw new IllegalStateException("Unexpected playersCount value: " + playersCount);
        }

        List<Card> cards = new ArrayList<>();

        //Blue
        cards.add(CardsSet.PANTHEON);
        cards.add(CardsSet.JARDINS);
        cards.add(CardsSet.HOTEL_DE_VILLE);
        cards.add(CardsSet.PALACE);
        cards.add(CardsSet.SENAT);

        //Yellow
        cards.add(CardsSet.PORT);
        cards.add(CardsSet.PHARE);
        cards.add(CardsSet.ARENE);

        //Red
        cards.add(CardsSet.FORTIFICATIONS);
        cards.add(CardsSet.ARSENAL);
        cards.add(CardsSet.ATELIER_DE_SIEGE);

        //Green
        cards.add(CardsSet.LOGE);
        cards.add(CardsSet.OBSERVATOIRE);
        cards.add(CardsSet.UNIVERSITE);
        cards.add(CardsSet.ACADEMIE);
        cards.add(CardsSet.ETUDE);

        if (playersCount >= 4) {
            //Blue
            cards.add(CardsSet.JARDINS);

            //Yellow
            cards.add(CardsSet.PORT);
            cards.add(CardsSet.CHAMBRE_DE_COMMERCE);

            //Red
            cards.add(CardsSet.CIRQUE);
            cards.add(CardsSet.ARSENAL);

            //Green
            cards.add(CardsSet.UNIVERSITE);
        }

        if (playersCount >= 5) {
            //Blue
            cards.add(CardsSet.HOTEL_DE_VILLE);
            cards.add(CardsSet.SENAT);

            //Yellow
            cards.add(CardsSet.ARENE);

            //Red
            cards.add(CardsSet.CIRQUE);
            cards.add(CardsSet.ATELIER_DE_SIEGE);

            //Green
            cards.add(CardsSet.ETUDE);
        }

        if (playersCount >= 6) {
            //Blue
            cards.add(CardsSet.PANTHEON);
            cards.add(CardsSet.HOTEL_DE_VILLE);

            //Yellow
            cards.add(CardsSet.PHARE);
            cards.add(CardsSet.CHAMBRE_DE_COMMERCE);

            //Red
            cards.add(CardsSet.CIRQUE);

            //Green
            cards.add(CardsSet.LOGE);
        }

        if (playersCount >= 7) {
            //Blue
            cards.add(CardsSet.PALACE);

            //Yellow
            cards.add(CardsSet.ARENE);

            //Red
            cards.add(CardsSet.FORTIFICATIONS);
            cards.add(CardsSet.ARSENAL);

            //Green
            cards.add(CardsSet.OBSERVATOIRE);
            cards.add(CardsSet.ACADEMIE);
        }

        cards.addAll(initiateGuilds(playersCount));

        return cards;
    }
}
