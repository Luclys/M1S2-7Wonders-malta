package gameelements.wonders;

import gameelements.Card;
import gameelements.Effect;
import gameelements.Inventory;

import java.util.ArrayList;

public class WonderBoard {
    private final String name;
    private final Effect baseEffect;
    private final ArrayList<Step> steps;
    private int currentStep = 0;
    private Inventory associatedInv;

    public WonderBoard(String name, Effect baseEffect, ArrayList<Step> steps) {
        this.name = name;
        this.baseEffect = baseEffect;
        this.steps = steps;
    }

    public void claimBoard(Inventory inv) {
        inv.setWonderBoard(this);
        this.baseEffect.activateEffect(inv);
        this.associatedInv = inv;
    }

    public void buyNextStep(Card card) {
        if (currentStep != steps.size()) {
            steps.get(currentStep).build(associatedInv, card);
            currentStep++;
        } else {
            throw new Error("Every steps already built.");
        }
    }
}
