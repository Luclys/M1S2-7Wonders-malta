package gameelements.ages;

import gameelements.cards.Card;
import gameelements.cards.CardsSet;

import java.util.ArrayList;
import java.util.Collections;

public class AgeIII implements Age {
    private static final int VICTORY_JETON_VALUE = 5;
    private static final boolean IS_LEFT_ROTATION = true;

    public static int getVictoryJetonValue() {
        return VICTORY_JETON_VALUE;
    }

    public static boolean isLeftRotation() {
        return IS_LEFT_ROTATION;
    }

    public static ArrayList<Card> initiateCards(int playersCount) {
        if (playersCount <= 2 || playersCount > 7) {
            throw new IllegalStateException("Unexpected playersCount value: " + playersCount);
        }

        ArrayList<Card> cards = new ArrayList<>();

        //Blue
        cards.add(CardsSet.PANTHÉON);
        cards.add(CardsSet.JARDINS);
        cards.add(CardsSet.HÔTEL_DE_VILLE);
        cards.add(CardsSet.PALACE);
        cards.add(CardsSet.SÉNAT);

        //Yellow
        cards.add(CardsSet.PORT);
        cards.add(CardsSet.PHARE);
        cards.add(CardsSet.ARÈNE);

        //Red
        cards.add(CardsSet.FORTIFICATIONS);
        cards.add(CardsSet.ARSENAL);
        cards.add(CardsSet.ATELIER_DE_SIÈGE);

        //Green
        cards.add(CardsSet.LOGE);
        cards.add(CardsSet.OBSERVATOIRE);
        cards.add(CardsSet.UNIVERSITÉ);
        cards.add(CardsSet.ACADEMIE);
        cards.add(CardsSet.ÉTUDE);

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
            cards.add(CardsSet.UNIVERSITÉ);
        }

        if (playersCount >= 5) {
            //Blue
            cards.add(CardsSet.HÔTEL_DE_VILLE);
            cards.add(CardsSet.SÉNAT);

            //Yellow
            cards.add(CardsSet.ARÈNE);

            //Red
            cards.add(CardsSet.CIRQUE);
            cards.add(CardsSet.ATELIER_DE_SIÈGE);

            //Green
            cards.add(CardsSet.ÉTUDE);
        }

        if (playersCount >= 6) {
            //Blue
            cards.add(CardsSet.PANTHÉON);
            cards.add(CardsSet.HÔTEL_DE_VILLE);

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
            cards.add(CardsSet.ARÈNE);

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

    private static ArrayList<Card> initiateGuilds(int playersCount) {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(CardsSet.GUILDE_DES_TRAVAILLEURS);
        cards.add(CardsSet.GUILDE_DES_ARTISANS);
        cards.add(CardsSet.GUILDE_DES_COMMERÇANTS);
        cards.add(CardsSet.GUILDE_DES_PHILOSOPHES);
        cards.add(CardsSet.GUILDE_DES_ESPIONS);
        cards.add(CardsSet.GUILDE_DES_STRATÈGES);
        cards.add(CardsSet.GUILDE_DES_ARMATEURS);
        cards.add(CardsSet.GUILDE_DES_SCIENTIFIQUES);
        cards.add(CardsSet.GUILDE_DES_MAGISTRATS);
        cards.add(CardsSet.GUILDE_DES_BÂTISSEURS);

        Collections.shuffle(cards);
        int lastIndex = Math.min(playersCount + 2, cards.size() - 1);
        return new ArrayList<>(cards.subList(0, lastIndex));
    }
}
