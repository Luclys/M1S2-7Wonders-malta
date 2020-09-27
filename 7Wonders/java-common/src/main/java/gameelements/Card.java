package gameelements;

public class Card {
    private final String name;
    private final Resource[] gainedResources;
    private final int victoryPoints;

    public Card(String name, Resource[] gainedResources) {
        this.name = name;
        this.gainedResources = gainedResources;
        this.victoryPoints = 0;
    }

    public Card(String name, Resource[] gainedResources, int victoryPoints) {
        this.name = name;
        this.gainedResources = gainedResources;
        this.victoryPoints = victoryPoints;
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
