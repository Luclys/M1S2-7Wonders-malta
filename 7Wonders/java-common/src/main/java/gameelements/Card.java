package gameelements;

import gameelements.enums.Category;
import gameelements.enums.Resource;

public class Card {
    private final String name;
    private Effect[] effects;
    private final Resource[] requiredResources;
    private final Category category;
    private int cost;

    public Card(String name, Effect[] effects, Resource[] requiredResources, Category category) {
        this.name = name;
        this.effects = effects;
        this.requiredResources = requiredResources;
        this.category = category;
        this.cost = 0;
    }

    public Card(String name, Effect effect, Resource[] requiredResources, Category category) {
        this.name = name;
        this.effects = new Effect[]{effect};
        this.requiredResources = requiredResources;
        this.category = category;
        this.cost = 0;
    }

    public Card(String name, Effect[] effects, Resource[] requiredResources, Category category, int cost) {
        this.name = name;
        this.effects = effects;
        this.requiredResources = requiredResources;
        this.category = category;
        this.cost = cost;
    }

    public Card(String name, Effect effect, Resource[] requiredResources, Category category, int cost) {
        this.name = name;
        this.effects = new Effect[]{effect};
        this.requiredResources = requiredResources;
        this.category = category;
        this.cost = cost;
    }

    public boolean isBatiment() {
        return (
                this.category == Category.BATIMENT_CIVIL ||
                this.category == Category.BATIMENT_SCIENTIFIQUE ||
                this.category == Category.BATIMENT_COMMERCIEAU ||
                this.category == Category.BATIMENT_MILITAIRE
        );
    }

    @Override
    public String toString() {
        return "Card{" +
                name + '\'' +
                '}';
    }

    // GETTERS & SETTERS
    public String getName() {
        return name;
    }

    public Effect[] getEffects() {
        return effects;
    }

    public void setEffects(Effect[] effects) {
        this.effects = effects;
    }

    public Resource[] getRequiredResources() {
        return requiredResources;
    }

    public Category getCategory() {
        return category;
    }
}
