package gameelements;

import gameelements.enums.Resource;

public class Card {
    private final String name;
    private Effect[] effects;
    private final Resource[] requiredResources;

    public Card(String name, Effect[] effects, Resource[] requiredResources) {
        this.name = name;
        this.effects = effects;
        this.requiredResources = requiredResources;
    }

    public Card(String name, Effect effect, Resource[] requiredResources) {
        this.name = name;
        this.effects = new Effect[]{effect};
        this.requiredResources = requiredResources;
    }

    @Override
    public String toString() {
        return "Card{" +
                "name='" + name + '\'' +
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
}
