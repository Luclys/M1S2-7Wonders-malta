package gameelements.ages;

import gameelements.cards.Card;

import java.util.ArrayList;

interface Age {
    static int getVictoryJetonValue() {
        return 0;
    }

    static boolean isLeftRotation() {
        return false;
    }

    static ArrayList<Card> initiateCards(int playersCount) {
        return null;
    }
}
