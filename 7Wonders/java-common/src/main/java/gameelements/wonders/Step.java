package gameelements.wonders;

import gameelements.Card;
import gameelements.Effect;
import gameelements.Inventory;
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

    void build(Inventory inv, Card sacrifice) {
        this.built = true;
        this.marker = sacrifice;

        for (Effect effect : effects) {
            effect.activateEffect(inv);
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

    public void setBuilt(Boolean built) {
        this.built = built;
    }

    public Card getMarker() {
        return marker;
    }

    public void setMarker(Card marker) {
        this.marker = marker;
    }
}
