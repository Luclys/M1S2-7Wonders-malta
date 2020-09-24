package gameelements;

public class Card {
    private final int idCard;
    private final int victoryPoints;

    public Card() {
        idCard = 1;
        victoryPoints = 1;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }
}
