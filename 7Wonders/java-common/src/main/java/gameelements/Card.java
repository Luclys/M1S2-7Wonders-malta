package gameelements;

import gameelements.enums.Resource;

public class Card {
    private final String name;
    private final Resource[] gainedResources;
    private final int gainedVictoryPoints;
    private final Resource[] requiredResources;

    public Card(String name, Resource[] gainedResources, Resource[] requiredResources) {
        this.name = name;
        this.gainedResources = gainedResources;
        this.gainedVictoryPoints = 0;
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
