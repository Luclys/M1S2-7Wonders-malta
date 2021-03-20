package gameelements.cards;

import gameelements.effects.Effect;
import gameelements.enums.Category;
import gameelements.enums.Resource;

/**
 * This class describe a Card
 *
 * @author lamac
 */

public class Card {
    private final int id;
    private final String name;
    private final Resource[] requiredResources;
    private final Category category;
    private final int cost;
    private final int[] requiredBuildingsToBuildForFree;
    private final int[] buildingIdsCanBeBuiltForFree;
    private Effect[] effects;

    public Card(int id, String name, Effect[] effects, Resource[] requiredResources, Category category, int cost, int[] requiredBuildingsToBuildForFree, int[] buildingsCanBeBuiltForFree) {
        this.id = id;
        this.name = name;
        this.effects = effects;
        this.requiredResources = requiredResources;
        this.category = category;
        this.cost = cost;
        this.requiredBuildingsToBuildForFree = requiredBuildingsToBuildForFree;
        this.buildingIdsCanBeBuiltForFree = buildingsCanBeBuiltForFree;
    }

    /**
     * This method returns if the current card is a building or not
     *
     * @return
     */
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

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() == this.getClass()){
            return this.id == ((Card) obj).id;
        }
        return false;
    }
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

    public int getCost() {
        return this.cost;
    }

    public int[] getRequiredBuildingsToBuildForFree() {
        return requiredBuildingsToBuildForFree;
    }

    public int getId() {
        return id;
    }


}
