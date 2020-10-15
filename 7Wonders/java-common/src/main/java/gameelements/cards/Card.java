package gameelements.cards;

import gameelements.Effect;
import gameelements.enums.Category;
import gameelements.enums.Resource;

public class Card {
    private final int id;
    private final String name;
    private Effect[] effects;
    private final Resource[] requiredResources;
    private final Category category;
    private final int cost;
    private final int[] requiredBuildingIdsToBuildForFree;
    private final int[] buildingIdsCanBeBuiltForFree;

    public Card(int id, String name, Effect[] effects, Resource[] requiredResources, Category category) {
        this.id = id;
        this.name = name;
        this.effects = effects;
        this.requiredResources = requiredResources;
        this.category = category;
        this.cost = 0;
        this.requiredBuildingIdsToBuildForFree = null;
        this.buildingIdsCanBeBuiltForFree = null;
    }

    public Card(int id, String name, Effect effect, Resource[] requiredResources, Category category) {
        this.id = id;
        this.name = name;
        this.effects = new Effect[]{effect};
        this.requiredResources = requiredResources;
        this.category = category;
        this.cost = 0;
        this.requiredBuildingIdsToBuildForFree = null;
        this.buildingIdsCanBeBuiltForFree = null;
    }

    public Card(int id, String name, Effect[] effects, Resource[] requiredResources, Category category, int cost) {
        this.id = id;
        this.name = name;
        this.effects = effects;
        this.requiredResources = requiredResources;
        this.category = category;
        this.cost = cost;
        this.requiredBuildingIdsToBuildForFree = null;
        this.buildingIdsCanBeBuiltForFree = null;
    }

    public Card(int id, String name, Effect effect, Resource[] requiredResources, Category category, int cost) {
        this.id = id;
        this.name = name;
        this.effects = new Effect[]{effect};
        this.requiredResources = requiredResources;
        this.category = category;
        this.cost = cost;
        this.requiredBuildingIdsToBuildForFree = null;
        this.buildingIdsCanBeBuiltForFree = null;
    }

    public Card(int id, String name, Effect effect, Resource[] requiredResources, Category category, int[] requiredBuildingsToBuildForFree, int[] buildingsCanBeBuiltForFree) {
        this.id = id;
        this.name = name;
        this.effects = new Effect[]{effect};
        this.requiredResources = requiredResources;
        this.category = category;
        this.cost = 0;
        this.requiredBuildingIdsToBuildForFree = requiredBuildingsToBuildForFree;
        this.buildingIdsCanBeBuiltForFree = buildingsCanBeBuiltForFree;
    }

    public Card(int id, String name, Effect[] effects, Resource[] requiredResources, Category category, int[] requiredBuildingsToBuildForFree, int[] buildingsCanBeBuiltForFree) {
        this.id = id;
        this.name = name;
        this.effects = effects;
        this.requiredResources = requiredResources;
        this.category = category;
        this.cost = 0;
        this.requiredBuildingIdsToBuildForFree = requiredBuildingsToBuildForFree;
        this.buildingIdsCanBeBuiltForFree = buildingsCanBeBuiltForFree;
    }

    public Card(int id, String name, Effect[] effects, Resource[] requiredResources, Category category, int cost, int[] requiredBuildingsToBuildForFree, int[] buildingsCanBeBuiltForFree) {
        this.id = id;
        this.name = name;
        this.effects = effects;
        this.requiredResources = requiredResources;
        this.category = category;
        this.cost = cost;
        this.requiredBuildingIdsToBuildForFree = requiredBuildingsToBuildForFree;
        this.buildingIdsCanBeBuiltForFree = buildingsCanBeBuiltForFree;
    }

    public Card(int id, String name, Effect effect, Resource[] requiredResources, Category category, int cost, int[] requiredBuildingsToBuildForFree, int[] buildingsCanBeBuiltForFree) {
        this.id = id;
        this.name = name;
        this.effects = new Effect[]{effect};
        this.requiredResources = requiredResources;
        this.category = category;
        this.cost = cost;
        this.requiredBuildingIdsToBuildForFree = requiredBuildingsToBuildForFree;
        this.buildingIdsCanBeBuiltForFree = buildingsCanBeBuiltForFree;
    }

    public boolean isBuilding() {
        return (
                this.category == Category.BATIMENT_CIVIL ||
                        this.category == Category.BATIMENT_SCIENTIFIQUE ||
                        this.category == Category.BATIMENT_COMMERCIAL ||
                        this.category == Category.BATIMENT_MILITAIRE
        );
    }

    @Override
    public String toString() {
        return "Card{\"" + name + "\"}";
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
