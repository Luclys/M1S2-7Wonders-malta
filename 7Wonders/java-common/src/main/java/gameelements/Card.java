package gameelements;

public class Card {
    private final String name;
    private final Resource[] gainedResources;
    private final int gainedVictoryPoints;

    public Card(String name, Resource[] gainedResources) {
        this.name = name;
        this.gainedResources = gainedResources;
        this.gainedVictoryPoints = 0;
    }

    public Card(String name, Resource[] gainedResources, int gainedVictoryPoints) {
        this.name = name;
        this.gainedResources = gainedResources;
        this.gainedVictoryPoints = gainedVictoryPoints;
    }
    public int getGainedVictoryPoints() {
        return gainedVictoryPoints;
    }

    public String getName() {
        return name;
    }

    public Resource[] getGainedResources() {
        return gainedResources;
    }
}
