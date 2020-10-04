package gameelements.ages;

import gameelements.Card;

import java.util.ArrayList;

interface Age {
    static int getConflictPoints() {
        return 0;
    }

    static boolean isIsLeftRotation() {
        return false;
    }

    static ArrayList<Card> initiateCards(int playersCount) {
        return null;
    }
}
