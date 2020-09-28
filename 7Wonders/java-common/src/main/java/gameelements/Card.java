package gameelements;

public class Card {
    private final String name;
    private final Resource[] gainedResources;
    private final int gainedVictoryPoints;
<<<<<<< HEAD
=======
    private final Resource[] requiredResources;
>>>>>>> 1697b1af85d0b9615bb512ee0a2a09104391b6f8

    public Card(String name, Resource[] gainedResources, Resource[] requiredResources) {
        this.name = name;
        this.gainedResources = gainedResources;
        this.gainedVictoryPoints = 0;
<<<<<<< HEAD
    }

    public Card(String name, Resource[] gainedResources, int gainedVictoryPoints) {
        this.name = name;
        this.gainedResources = gainedResources;
        this.gainedVictoryPoints = gainedVictoryPoints;
    }
    public int getGainedVictoryPoints() {
        return gainedVictoryPoints;
=======
        this.requiredResources = requiredResources;
    }

    public Card(String name, Resource[] gainedResources, int gainedVictoryPoints, Resource[] requiredResources) {
        this.name = name;
        this.gainedResources = gainedResources;
        this.gainedVictoryPoints = gainedVictoryPoints;
        this.requiredResources = requiredResources;
    }
    public int getGainedVictoryPoints() {
        return gainedVictoryPoints;
    }

    public int getVictoryPoints() {
        return gainedVictoryPoints;
>>>>>>> 1697b1af85d0b9615bb512ee0a2a09104391b6f8
    }

    public String getName() {
        return name;
    }

    public Resource[] getGainedResources() {
        return gainedResources;
    }

    public Resource[] getRequiredResources() {
        return requiredResources;
    }

}
