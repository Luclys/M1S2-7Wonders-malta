package gameelements;

public class Card {
    private final String name;
    private final Resource[] gainedResources;
    private final int gainedVictoryPoints;
    private final Resource[] requiredResources;
    private final Category category;

    public Card(String name, Resource[] gainedResources, Resource[] requiredResources, Category category) {
        this.name = name;
        this.gainedResources = gainedResources;
        this.gainedVictoryPoints = 0;
        this.requiredResources = requiredResources;
        this.category = category;
    }

    public Card(String name, Resource[] gainedResources, int gainedVictoryPoints, Resource[] requiredResources, Category category) {
        this.name = name;
        this.gainedResources = gainedResources;
        this.gainedVictoryPoints = gainedVictoryPoints;
        this.requiredResources = requiredResources;
        this.category = category;
    }

    public boolean isBatiment() {
        return (
                this.category == Category.BATIMENT_CIVIL ||
                this.category == Category.BATIMENT_SCIENTIFIQUE ||
                this.category == Category.BATIMENT_COMMERCIEAU ||
                this.category == Category.BATIMENT_MILITAIRE
        );
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

    public Category getCategory() {
        return category;
    }

    public Resource[] getGainedResources() {
        return gainedResources;
    }

    public Resource[] getRequiredResources() {
        return requiredResources;
    }

    @Override
    public String toString() {
        return "Card{" +
                "name='" + name + '\'' +
                '}';
    }
}
