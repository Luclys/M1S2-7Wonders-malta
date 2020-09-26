package gameelements;

public class Card {
    private final String name;
    private final Resource[] gainedResources;
    private final int victoryPoints;

    public Card(String name, Resource[] gainedResources) {
        this.name = name;
        this.gainedResources = gainedResources;
        victoryPoints = 1;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public String getName() {
        return name;
    }

    public Resource[] getGainedResources() {
        return gainedResources;
    }
}
