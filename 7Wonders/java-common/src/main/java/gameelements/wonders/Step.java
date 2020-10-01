package gameelements.wonders;

import gameelements.Card;
import gameelements.enums.Resource;
import gameelements.Effect;

public class Step {
    private final Resource[] requiredResources;
    private final Effect[] effects;
    private Boolean built = false;
    private Card marker;

    public Step(Resource[] requiredResources, Effect[] effects) {
        this.requiredResources = requiredResources;
        this.effects = effects;
    }

    void build(Card sacrifice) {
        this.built = true;
        this.marker = sacrifice;

        for (Effect effect : effects) {
            effect.activateEffect();
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
