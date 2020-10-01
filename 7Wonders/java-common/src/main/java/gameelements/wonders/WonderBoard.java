package gameelements.wonders;

import gameelements.Card;
import gameelements.Effect;

import java.util.ArrayList;

public class WonderBoard {
    private String name;
    private Effect baseEffect;
    private ArrayList<Step> steps;
    private int currentStep = 0;

    public WonderBoard(String name, Effect baseEffect, ArrayList<Step> steps) {
        this.name = name;
        this.baseEffect = baseEffect;
        this.steps = steps;
    }

    public void claimBoard() {
        baseEffect.activateEffect();
    }

    void buySpecificStep(Step step, Card card) {
        int indiceStepToBuild = steps.indexOf(step);
        if (indiceStepToBuild == 0 || steps.get(indiceStepToBuild - 1).getBuilt()) {
            // No more checks, we can buy if given the sufficient amount of resources.
            step.build(card);
        } else {
            throw new Error("Can't build step in the wrong order");
        }
    }

    void buyNextStep(Card card) {
        if (currentStep != steps.size()) {
            steps.get(currentStep).build(card);
            currentStep++;
        } else {
            throw new Error("Every steps already built.");
        }
    }
}
