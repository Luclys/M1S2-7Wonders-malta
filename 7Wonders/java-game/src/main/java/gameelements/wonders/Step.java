package gameelements.wonders;

import gameelements.Inventory;
import gameelements.cards.Card;
import gameelements.effects.Effect;
import gameelements.enums.Resource;

public class Step {
    private final Resource[] requiredResources;
    private final Effect[] effects;
    private Boolean built = false;
    private Card marker;

    public Step(Resource[] requiredResources, Effect[] effects) {
        this.requiredResources = requiredResources;
        this.effects = effects;
    }

    public Step(Resource[] requiredResources, Effect effect) {
        this.requiredResources = requiredResources;
        this.effects = new Effect[]{effect};
    }

    void build(Inventory inv, Card sacrifice, Inventory leftNeighborInv, Inventory rightNeighborInv) {
        this.built = true;
        this.marker = sacrifice;

        for (Effect effect : getEffects()) {
            effect.activateEffect(inv, leftNeighborInv, rightNeighborInv);
        }
    }


    // GETTERS & SETTERS
    public Resource[] getRequiredResources() {
        return requiredResources;
    }

    public Effect[] getEffects() {
        return effects;
    }

    public Boolean getBuilt() {
        return built;
    }


}
