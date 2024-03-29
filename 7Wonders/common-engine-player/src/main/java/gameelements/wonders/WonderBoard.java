package gameelements.wonders;

import gameelements.Inventory;
import gameelements.cards.Card;
import gameelements.effects.*;
import gameelements.enums.Neighbor;
import gameelements.enums.Resource;
import gameelements.enums.Symbol;

import java.util.ArrayList;
import java.util.List;

public class WonderBoard {
    private final String name;
    private final Effect baseEffect;
    private final List<Step> steps;
    private int currentStepIndex = 0;
    private Integer associatedPlayerId;

    public WonderBoard(String name, Effect baseEffect, List<Step> steps) {
        this.name = name;
        this.baseEffect = baseEffect;
        this.steps = steps;
    }

    public WonderBoard(WonderBoard w) {
        this.name = w.name;
        this.baseEffect = w.baseEffect;
        this.currentStepIndex = w.currentStepIndex;
        this.steps = w.steps;
        this.associatedPlayerId = w.associatedPlayerId;
    }

    public static WonderBoard initiateColossusA() {
        List<Step> colosseStepsA = new ArrayList<>();
        colosseStepsA.add(new Step(new Resource[]{Resource.BOIS, Resource.BOIS}, new ScoreEffect(3)));
        colosseStepsA.add(new Step(new Resource[]{Resource.ARGILE, Resource.ARGILE, Resource.ARGILE}, new SymbolEffect(Symbol.BOUCLIER, 2)));
        colosseStepsA.add(new Step(new Resource[]{Resource.MINERAI, Resource.MINERAI, Resource.MINERAI, Resource.MINERAI}, new ScoreEffect(7)));
        return new WonderBoard("Le Colosse de Rhodes A", new ResourceEffect(Resource.MINERAI, 1), colosseStepsA);
    }

    public static List<WonderBoard> initiateWonders() {
        List<WonderBoard> res = new ArrayList<>(14);

        // Le Colosse de Rhodes
        // Face A
        res.add(initiateColossusA());

        // Face B
        List<Step> colosseStepsB = new ArrayList<>();
        colosseStepsB.add(new Step(new Resource[]{Resource.PIERRE, Resource.PIERRE, Resource.PIERRE},
                new Effect[]{new SymbolEffect(Symbol.BOUCLIER, 1), new ScoreEffect(3), new CoinEffect(3)}));
        colosseStepsB.add(new Step(new Resource[]{Resource.MINERAI, Resource.MINERAI, Resource.MINERAI, Resource.MINERAI},
                new Effect[]{new SymbolEffect(Symbol.BOUCLIER, 1), new ScoreEffect(4), new CoinEffect(4)}));
        res.add(new WonderBoard("Le Colosse de Rhodes B", new ResourceEffect(Resource.MINERAI, 1), colosseStepsB));

        // Le phare d’Alexandrie
        // Face A
        List<Step> alexandrieStepsA = new ArrayList<>();
        alexandrieStepsA.add(new Step(new Resource[]{Resource.PIERRE, Resource.PIERRE}, new ScoreEffect(3)));
        alexandrieStepsA.add(new Step(new Resource[]{Resource.MINERAI, Resource.MINERAI}, new ChoiceAnyResourceFromCategoryEffect(true)));
        alexandrieStepsA.add(new Step(new Resource[]{Resource.VERRE, Resource.VERRE}, new ScoreEffect(7)));
        res.add(new WonderBoard("Le phare d’Alexandrie A", new ResourceEffect(Resource.VERRE, 1), alexandrieStepsA));

        // Face B
        List<Step> alexandrieStepsB = new ArrayList<>();
        alexandrieStepsB.add(new Step(new Resource[]{Resource.ARGILE, Resource.ARGILE}, new ChoiceAnyResourceFromCategoryEffect(true)));
        alexandrieStepsB.add(new Step(new Resource[]{Resource.BOIS, Resource.BOIS}, new ChoiceAnyResourceFromCategoryEffect(false)));
        alexandrieStepsB.add(new Step(new Resource[]{Resource.PIERRE, Resource.PIERRE, Resource.PIERRE}, new ScoreEffect(7)));
        res.add(new WonderBoard("Le phare d’Alexandrie B", new ResourceEffect(Resource.VERRE, 1), alexandrieStepsB));

        // Le temple d’Artémis à Ephèse
        // Face A
        ArrayList<Step> artemisStepsA = new ArrayList<>();
        artemisStepsA.add(new Step(new Resource[]{Resource.PIERRE, Resource.PIERRE}, new ScoreEffect(3)));
        artemisStepsA.add(new Step(new Resource[]{Resource.BOIS, Resource.BOIS}, new CoinEffect(9)));
        artemisStepsA.add(new Step(new Resource[]{Resource.PAPYRUS, Resource.PAPYRUS}, new ScoreEffect(7)));
        res.add(new WonderBoard("Le temple d’Artémis à Ephèse A", new ResourceEffect(Resource.PAPYRUS, 1), artemisStepsA));

        // Face B
        ArrayList<Step> artemisStepsB = new ArrayList<>();
        artemisStepsB.add(new Step(new Resource[]{Resource.PIERRE, Resource.PIERRE}, new Effect[]{new ScoreEffect(2), new CoinEffect(4)}));
        artemisStepsB.add(new Step(new Resource[]{Resource.BOIS, Resource.BOIS}, new Effect[]{new ScoreEffect(3), new CoinEffect(4)}));
        artemisStepsB.add(new Step(new Resource[]{Resource.PAPYRUS, Resource.TISSU, Resource.VERRE}, new Effect[]{new ScoreEffect(5), new CoinEffect(4)}));
        res.add(new WonderBoard("Le temple d’Artémis à Ephèse B", new ResourceEffect(Resource.PAPYRUS, 1), artemisStepsB));

        // Les jardins suspendus de Babylone
        // Face A
        ArrayList<Step> babyloneA = new ArrayList<>();
        babyloneA.add(new Step(new Resource[]{Resource.ARGILE, Resource.ARGILE}, new ScoreEffect(3)));
        babyloneA.add(new Step(new Resource[]{Resource.BOIS, Resource.BOIS, Resource.BOIS}, new ChoiceScientificEffect()));
        babyloneA.add(new Step(new Resource[]{Resource.ARGILE, Resource.ARGILE, Resource.ARGILE, Resource.ARGILE}, new ScoreEffect(7)));
        res.add(new WonderBoard("Les jardins suspendus de Babylone A", new ResourceEffect(Resource.ARGILE, 1), babyloneA));

        // Face B
        ArrayList<Step> babyloneB = new ArrayList<>();
        babyloneB.add(new Step(new Resource[]{Resource.TISSU, Resource.ARGILE}, new ScoreEffect(3)));
        babyloneB.add(new Step(new Resource[]{Resource.VERRE, Resource.BOIS, Resource.BOIS}, new PlayLastCardEffect()));
        babyloneB.add(new Step(new Resource[]{Resource.ARGILE, Resource.ARGILE, Resource.ARGILE, Resource.PAPYRUS}, new ChoiceScientificEffect()));
        res.add(new WonderBoard("Les jardins suspendus de Babylone B", new ResourceEffect(Resource.ARGILE, 1), babyloneB));


        // La statue de Zeus à Olympie
        // Face A
        ArrayList<Step> zeusA = new ArrayList<>();
        zeusA.add(new Step(new Resource[]{Resource.BOIS, Resource.BOIS}, new ScoreEffect(3)));
        zeusA.add(new Step(new Resource[]{Resource.PIERRE, Resource.PIERRE}, new FreeBuildingEffect()));
        zeusA.add(new Step(new Resource[]{Resource.MINERAI, Resource.MINERAI}, new ScoreEffect(7)));
        res.add(new WonderBoard("La statue de Zeus à Olympie A", new ResourceEffect(Resource.BOIS, 1), zeusA));

        // Face B
        ArrayList<Step> zeusB = new ArrayList<>();
        zeusB.add(new Step(new Resource[]{Resource.BOIS, Resource.BOIS}, new Effect[]{new ReductCommerceEffect(Neighbor.BOTH, true), new CoinEffect(3)}));
        zeusB.add(new Step(new Resource[]{Resource.PIERRE, Resource.PIERRE}, new ScoreEffect(5)));
        zeusB.add(new Step(new Resource[]{Resource.TISSU, Resource.MINERAI, Resource.MINERAI}, new CopyNeighborGuildEffect()));
        res.add(new WonderBoard("La statue de Zeus à Olympie B", new ResourceEffect(Resource.BOIS, 1), zeusB));


        // Le mausolée d’Halicarnasse
        // Face A
        ArrayList<Step> halicarnasseA = new ArrayList<>();
        halicarnasseA.add(new Step(new Resource[]{Resource.ARGILE, Resource.ARGILE}, new ScoreEffect(3)));
        halicarnasseA.add(new Step(new Resource[]{Resource.MINERAI, Resource.MINERAI, Resource.MINERAI}, new FreeDiscardedBuildingEffect()));
        halicarnasseA.add(new Step(new Resource[]{Resource.TISSU, Resource.TISSU}, new ScoreEffect(7)));
        res.add(new WonderBoard("Le mausolée d’Halicarnasse A", new ResourceEffect(Resource.TISSU, 1), halicarnasseA));

        // Face B
        ArrayList<Step> halicarnasseB = new ArrayList<>();
        halicarnasseB.add(new Step(new Resource[]{Resource.MINERAI, Resource.MINERAI}, new Effect[]{new ScoreEffect(2), new FreeDiscardedBuildingEffect()}));
        halicarnasseB.add(new Step(new Resource[]{Resource.ARGILE, Resource.ARGILE, Resource.ARGILE}, new Effect[]{new ScoreEffect(1), new FreeDiscardedBuildingEffect()}));
        halicarnasseB.add(new Step(new Resource[]{Resource.VERRE, Resource.PAPYRUS, Resource.TISSU}, new FreeDiscardedBuildingEffect()));
        res.add(new WonderBoard("Le mausolée d’Halicarnasse B", new ResourceEffect(Resource.TISSU, 1), halicarnasseB));

        // La grande pyramide de Gizeh
        // Face A
        ArrayList<Step> gizehA = new ArrayList<>();
        gizehA.add(new Step(new Resource[]{Resource.PIERRE, Resource.PIERRE}, new ScoreEffect(3)));
        gizehA.add(new Step(new Resource[]{Resource.BOIS, Resource.BOIS, Resource.BOIS}, new ScoreEffect(5)));
        gizehA.add(new Step(new Resource[]{Resource.PIERRE, Resource.PIERRE, Resource.PIERRE, Resource.PIERRE}, new ScoreEffect(7)));
        res.add(new WonderBoard("La grande pyramide de Gizeh A", new ResourceEffect(Resource.PIERRE, 1), gizehA));

        // Face B
        ArrayList<Step> gizehB = new ArrayList<>();
        gizehB.add(new Step(new Resource[]{Resource.BOIS, Resource.BOIS}, new ScoreEffect(3)));
        gizehB.add(new Step(new Resource[]{Resource.PIERRE, Resource.PIERRE, Resource.PIERRE}, new ScoreEffect(5)));
        gizehB.add(new Step(new Resource[]{Resource.ARGILE, Resource.ARGILE, Resource.ARGILE}, new ScoreEffect(5)));
        gizehB.add(new Step(new Resource[]{Resource.PIERRE, Resource.PIERRE, Resource.PIERRE, Resource.PIERRE, Resource.PAPYRUS}, new ScoreEffect(7)));
        res.add(new WonderBoard("La grande pyramide de Gizeh B", new ResourceEffect(Resource.PIERRE, 1), gizehB));


        return res;
    }

    public void buyNextStep(Card card, Inventory inv, Inventory leftNeighborInv, Inventory rightNeighborInv) throws Exception {
        if (currentStepIndex < steps.size()) {
            steps.get(currentStepIndex).build(inv, card, leftNeighborInv, rightNeighborInv);
            inv.getCardsInHand().remove(card);
            currentStepIndex++;
        } else {
            throw new Exception("No step left to build");
        }
    }

    public int getStepCount() {
        return steps.size();
    }

    public Step getCurrentStep() {
        return steps.get(currentStepIndex);
    }

    public int getCurrentStepIndex() {
        return currentStepIndex;
    }

    public void setCurrentStepIndex(int i) {
        currentStepIndex = i;
    }

    public void claimBoard(Inventory inv) throws Exception {
        if (this.associatedPlayerId == null) {
            inv.setWonderBoard(this);
            this.baseEffect.activateEffect(inv, null, null);
            this.associatedPlayerId = inv.getPlayerId();
        } else throw new Exception("WonderBoard already claimed.");
    }

    public Resource[] getCurrentStepRequiredResources() {
        return steps.get(currentStepIndex).getRequiredResources();
    }

    public String getName() {
        return name;
    }

    public Integer getAssociatedPlayerId() {
        return associatedPlayerId;
    }

    public void setAssociatedPlayerId(Integer id) {
        this.associatedPlayerId = id;
    }
}
