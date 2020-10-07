package gameelements.wonders;

import gameelements.Card;
import gameelements.Effect;
import gameelements.Inventory;
import gameelements.Player;
import gameelements.effects.*;
import gameelements.enums.Neighbor;
import gameelements.enums.Resource;
import gameelements.enums.Symbol;

import java.util.ArrayList;

public class WonderBoard {
    private final String name;
    private final Effect baseEffect;
    private final ArrayList<Step> steps;
    private int currentStepIndex = 0;
    private Inventory associatedInv;

    public WonderBoard(String name, Effect baseEffect, ArrayList<Step> steps) {
        this.name = name;
        this.baseEffect = baseEffect;
        this.steps = steps;
    }

    public void claimBoard(Player player, Inventory inv) {
        inv.setWonderBoard(this);
        this.baseEffect.activateEffect(player, inv, null, null);
        this.associatedInv = inv;
    }

    public void buyNextStep(Player player, Card card, Inventory leftNeighborInv, Inventory rightNeighborInv) {
        if (currentStepIndex < steps.size()) {
            steps.get(currentStepIndex).build(player, associatedInv, card, leftNeighborInv, rightNeighborInv);
            currentStepIndex++;
        } else {
            throw new Error("Every steps already built.");
        }
    }

    public Step getCurrentStep() {
        return steps.get(currentStepIndex);
    }

    public int getCurrentStepIndex() {
        return currentStepIndex;
    }

    static public WonderBoard initiateColossus() {
        ArrayList<Step> colosseStepsA = new ArrayList<>();
        colosseStepsA.add(new Step(new Resource[]{Resource.BOIS, Resource.BOIS}, new ScoreEffect( 3)));
        colosseStepsA.add(new Step(new Resource[]{Resource.ARGILE, Resource.ARGILE, Resource.ARGILE}, new SymbolEffect(Symbol.BOUCLIER, 2)));
        colosseStepsA.add(new Step(new Resource[]{Resource.MINERAI, Resource.MINERAI, Resource.MINERAI, Resource.MINERAI}, new ScoreEffect(7)));
        return new WonderBoard("Le Colosse de Rhodes A", new ResourceEffect(Resource.MINERAI, 1), colosseStepsA);
    }

    protected ArrayList<WonderBoard> initiateWonders() {
        ArrayList<WonderBoard> res = new ArrayList<>(14);

        // Le Colosse de Rhodes
        // Face A
        ArrayList<Step> colosseStepsA = new ArrayList<>();
        colosseStepsA.add(new Step(new Resource[]{Resource.BOIS, Resource.BOIS}, new ScoreEffect( 3)));
        colosseStepsA.add(new Step(new Resource[]{Resource.ARGILE, Resource.ARGILE, Resource.ARGILE}, new SymbolEffect( Symbol.BOUCLIER, 2)));
        colosseStepsA.add(new Step(new Resource[]{Resource.MINERAI, Resource.MINERAI, Resource.MINERAI, Resource.MINERAI}, new ScoreEffect(7)));
        res.add(new WonderBoard("Le Colosse de Rhodes A", new ResourceEffect( Resource.MINERAI, 1), colosseStepsA));

        // Face B
        ArrayList<Step> colosseStepsB = new ArrayList<>();
        colosseStepsB.add(new Step(new Resource[]{Resource.PIERRE, Resource.PIERRE, Resource.PIERRE},
                new Effect[]{new SymbolEffect( Symbol.BOUCLIER, 1), new ScoreEffect( 3), new CoinEffect( 3)}));
        colosseStepsB.add(new Step(new Resource[]{Resource.MINERAI, Resource.MINERAI, Resource.MINERAI, Resource.MINERAI},
                new Effect[]{new SymbolEffect(Symbol.BOUCLIER, 1), new ScoreEffect( 4), new CoinEffect( 4)}));
        res.add(new WonderBoard("Le Colosse de Rhodes B", new ResourceEffect(Resource.MINERAI, 1), colosseStepsB));

        // Le phare d’Alexandrie
        // Face A
        ArrayList<Step> alexandrieStepsA = new ArrayList<>();
        alexandrieStepsA.add(new Step(new Resource[]{Resource.PIERRE, Resource.PIERRE}, new ScoreEffect(3)));
        alexandrieStepsA.add(new Step(new Resource[]{Resource.MINERAI, Resource.MINERAI}, new ChoiceAllTypeResourceEffect(true)));
        alexandrieStepsA.add(new Step(new Resource[]{Resource.VERRE, Resource.VERRE}, new ScoreEffect( 7)));
        res.add(new WonderBoard("Le phare d’Alexandrie A", new ResourceEffect( Resource.VERRE, 1), alexandrieStepsA));

        // Face B
        ArrayList<Step> alexandrieStepsB = new ArrayList<>();
        alexandrieStepsB.add(new Step(new Resource[]{Resource.ARGILE, Resource.ARGILE}, new ChoiceAllTypeResourceEffect( true)));
        alexandrieStepsB.add(new Step(new Resource[]{Resource.BOIS, Resource.BOIS}, new ChoiceAllTypeResourceEffect(false)));
        alexandrieStepsB.add(new Step(new Resource[]{Resource.PIERRE, Resource.PIERRE, Resource.PIERRE}, new ScoreEffect(7)));
        res.add(new WonderBoard("Le phare d’Alexandrie B", new ResourceEffect(Resource.VERRE, 1), alexandrieStepsB));

        // Le temple d’Artémis à Ephèse
        // Face A
        ArrayList<Step> ArtemisStepsA = new ArrayList<>();
        ArtemisStepsA.add(new Step(new Resource[]{Resource.PIERRE, Resource.PIERRE}, new ScoreEffect( 3)));
        ArtemisStepsA.add(new Step(new Resource[]{Resource.BOIS, Resource.BOIS}, new CoinEffect( 9)));
        ArtemisStepsA.add(new Step(new Resource[]{Resource.PAPYRUS, Resource.PAPYRUS}, new ScoreEffect( 7)));
        res.add(new WonderBoard("Le temple d’Artémis à Ephèse A", new ResourceEffect( Resource.PAPYRUS, 1), ArtemisStepsA));

        // Face B
        ArrayList<Step> ArtemisStepsB = new ArrayList<>();
        ArtemisStepsB.add(new Step(new Resource[]{Resource.PIERRE, Resource.PIERRE}, new Effect[]{new ScoreEffect( 2), new CoinEffect( 4)}));
        ArtemisStepsB.add(new Step(new Resource[]{Resource.BOIS, Resource.BOIS}, new Effect[]{new ScoreEffect( 3), new CoinEffect(4)}));
        ArtemisStepsB.add(new Step(new Resource[]{Resource.PAPYRUS, Resource.TISSU, Resource.VERRE}, new Effect[]{new ScoreEffect( 5), new CoinEffect( 4)}));
        res.add(new WonderBoard("Le temple d’Artémis à Ephèse B", new ResourceEffect( Resource.PAPYRUS, 1), ArtemisStepsB));

        // Les jardins suspendus de Babylone
        // Face A
        ArrayList<Step> BabyloneA = new ArrayList<>();
        BabyloneA.add(new Step(new Resource[]{Resource.ARGILE, Resource.ARGILE}, new ScoreEffect( 3)));
        BabyloneA.add(new Step(new Resource[]{Resource.BOIS, Resource.BOIS, Resource.BOIS}, new ChoiceScientificEffect()));
        BabyloneA.add(new Step(new Resource[]{Resource.ARGILE, Resource.ARGILE, Resource.ARGILE, Resource.ARGILE}, new ScoreEffect( 7)));
        res.add(new WonderBoard("Les jardins suspendus de Babylone A", new ResourceEffect( Resource.ARGILE, 1), BabyloneA));

        // Face B
        ArrayList<Step> BabyloneB = new ArrayList<>();
        BabyloneB.add(new Step(new Resource[]{Resource.TISSU, Resource.ARGILE}, new ScoreEffect(3)));
        BabyloneB.add(new Step(new Resource[]{Resource.VERRE, Resource.BOIS, Resource.BOIS}, new PlayLastCardEffect()));
        BabyloneB.add(new Step(new Resource[]{Resource.ARGILE, Resource.ARGILE, Resource.ARGILE, Resource.PAPYRUS}, new ChoiceScientificEffect()));
        res.add(new WonderBoard("Les jardins suspendus de Babylone B", new ResourceEffect(Resource.ARGILE, 1), BabyloneB));


        // La statue de Zeus à Olympie
        // Face A
        ArrayList<Step> ZeusA = new ArrayList<>();
        ZeusA.add(new Step(new Resource[]{Resource.BOIS, Resource.BOIS}, new ScoreEffect(3)));
        ZeusA.add(new Step(new Resource[]{Resource.PIERRE, Resource.PIERRE}, new FreeBuildingEffect()));
        ZeusA.add(new Step(new Resource[]{Resource.MINERAI, Resource.MINERAI}, new ScoreEffect(7)));
        res.add(new WonderBoard("La statue de Zeus à Olympie A", new ResourceEffect(Resource.BOIS, 1), ZeusA));

        // Face B
        ArrayList<Step> ZeusB = new ArrayList<>();
        ZeusB.add(new Step(new Resource[]{Resource.BOIS, Resource.BOIS}, new Effect[]{new ReductCommerceEffect(Neighbor.BOTH, true), new CoinEffect(3)}));
        ZeusB.add(new Step(new Resource[]{Resource.PIERRE, Resource.PIERRE}, new ScoreEffect(5)));
        ZeusB.add(new Step(new Resource[]{Resource.TISSU, Resource.MINERAI, Resource.MINERAI}, new CopyNeighborGuildEffect()));
        res.add(new WonderBoard("La statue de Zeus à Olympie B", new ResourceEffect(Resource.BOIS, 1), ZeusB));


        // Le mausolée d’Halicarnasse
        // Face A
        ArrayList<Step> HalicarnasseA = new ArrayList<>();
        HalicarnasseA.add(new Step(new Resource[]{Resource.ARGILE, Resource.ARGILE}, new ScoreEffect(3)));
        HalicarnasseA.add(new Step(new Resource[]{Resource.MINERAI, Resource.MINERAI, Resource.MINERAI}, new FreeBuildingEffect()));
        HalicarnasseA.add(new Step(new Resource[]{Resource.TISSU, Resource.TISSU}, new ScoreEffect(7)));
        res.add(new WonderBoard("La statue de Zeus à Olympie A", new ResourceEffect(Resource.TISSU, 1), HalicarnasseA));

        // Face B
        ArrayList<Step> HalicarnasseB = new ArrayList<>();
        HalicarnasseB.add(new Step(new Resource[]{Resource.MINERAI, Resource.MINERAI}, new Effect[]{new ScoreEffect(2), new FreeDiscardedBuildingEffect()}));
        HalicarnasseB.add(new Step(new Resource[]{Resource.ARGILE, Resource.ARGILE, Resource.ARGILE}, new Effect[]{new ScoreEffect(1), new FreeDiscardedBuildingEffect()}));
        HalicarnasseB.add(new Step(new Resource[]{Resource.VERRE, Resource.PAPYRUS, Resource.TISSU}, new FreeDiscardedBuildingEffect()));
        res.add(new WonderBoard("La statue de Zeus à Olympie A", new ResourceEffect(Resource.TISSU, 1), HalicarnasseB));

        // La grande pyramide de Gizeh
        // Face A
        ArrayList<Step> GizehA = new ArrayList<>();
        GizehA.add(new Step(new Resource[]{Resource.PIERRE, Resource.PIERRE}, new ScoreEffect(3)));
        GizehA.add(new Step(new Resource[]{Resource.BOIS, Resource.BOIS, Resource.BOIS}, new ScoreEffect(5)));
        GizehA.add(new Step(new Resource[]{Resource.PIERRE, Resource.PIERRE, Resource.PIERRE, Resource.PIERRE}, new ScoreEffect(7)));
        res.add(new WonderBoard("La grande pyramide de Gizeh A", new ResourceEffect(Resource.PIERRE, 1), GizehA));

        // Face B
        ArrayList<Step> GizehB = new ArrayList<>();
        GizehB.add(new Step(new Resource[]{Resource.BOIS, Resource.BOIS}, new ScoreEffect(3)));
        GizehB.add(new Step(new Resource[]{Resource.PIERRE, Resource.PIERRE, Resource.PIERRE}, new ScoreEffect( 5)));
        GizehB.add(new Step(new Resource[]{Resource.ARGILE, Resource.ARGILE, Resource.ARGILE}, new ScoreEffect( 5)));
        GizehB.add(new Step(new Resource[]{Resource.PIERRE, Resource.PIERRE, Resource.PIERRE, Resource.PIERRE, Resource.PAPYRUS}, new ScoreEffect( 7)));
        res.add(new WonderBoard("La grande pyramide de Gizeh B", new ResourceEffect( Resource.PIERRE, 1), GizehB));


        return res;
    }
}
