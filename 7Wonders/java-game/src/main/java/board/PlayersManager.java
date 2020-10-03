package board;

import effects.*;
import gameelements.Card;
import gameelements.Effect;
import gameelements.Inventory;
import gameelements.enums.Resource;
import gameelements.enums.Symbol;
import gameelements.wonders.Step;
import gameelements.wonders.WonderBoard;

import java.util.ArrayList;

public class PlayersManager {
    ArrayList<Player> playerList;
    ArrayList<Inventory> playerInventoryList;

    protected void fightWithNeighbor(Inventory invPlayer, Inventory invNeighbor, int conflictPoints) { // conflictPoints depends on Age
        int playerBoucliersCount = invPlayer.getSymbCount(Symbol.BOUCLIER);
        int neighborBoucliersCount = invNeighbor.getSymbCount(Symbol.BOUCLIER);
        //System.out.println("[RESOLVING WAR CONFLICTS] Player has " + playerBoucliersCount + " boucliers while neighbor has " + neighborBoucliersCount);
        if (playerBoucliersCount > neighborBoucliersCount) {
            invPlayer.updateConflictPoints(conflictPoints);
            //System.out.println("[RESOLVING WAR CONFLICTS] " + conflictPoints + " conflict points added");
        } else if (playerBoucliersCount < neighborBoucliersCount) {
            invPlayer.updateConflictPoints(-1);
            //System.out.println("[RESOLVING WAR CONFLICTS] Defeat jeton (-1 conflict point) added");
        }
        //System.out.println("[RESOLVING WAR CONFLICTS] Total player conflict points: " + this.getConflictPoints());
    }

    protected ArrayList<Player> generatePlayers(int nbPlayers) {
        playerList = new ArrayList<>(nbPlayers);
        playerInventoryList = new ArrayList<>(nbPlayers);
        for (int i = 0; i < nbPlayers; i++) {
            Player player = new Player(i);
            Inventory inv = new Inventory(i);
            // To make a sure we bind the first's left to last id
            if (i == 0) {
                player.setLeftNeighborId(nbPlayers - 1);
            } else {
                player.setLeftNeighborId(i - 1);
            }
            // To make a sure we bind the last's right to first id
            if (i == nbPlayers - 1) {
                player.setRightNeighborId(0);
            } else {
                player.setRightNeighborId(i + 1);
            }
            playerList.add(player);
            playerInventoryList.add(inv);
        }
        return playerList;
    }

    public ArrayList<Inventory> getPlayerInventoryList() {
        return playerInventoryList;
    }

    protected ArrayList<Resource> missingResources(Inventory inv, Card c) {
        ArrayList<Resource> missing = new ArrayList<>();
        if (c.getRequiredResources() == null) {
            return null;
        }
        for (Resource r : c.getRequiredResources()) {
            if (inv.getAvailableResources()[r.getIndex()] == 0) {
                missing.add(r);
            }
        }
        return missing;
    }

    protected WonderBoard initiateColossus() {
        ArrayList<Step> colosseStepsA = new ArrayList<>();
        colosseStepsA.add(new Step(new Resource[]{Resource.BOIS, Resource.BOIS}, new ScoreEffect("", 3)));
        colosseStepsA.add(new Step(new Resource[]{Resource.ARGILE, Resource.ARGILE, Resource.ARGILE}, new SymbolEffect("", Symbol.BOUCLIER, 2)));
        colosseStepsA.add(new Step(new Resource[]{Resource.MINERAI, Resource.MINERAI, Resource.MINERAI, Resource.MINERAI}, new ScoreEffect("", 7)));
        return new WonderBoard("Le Colosse de Rhodes A", new ResourceEffect("", Resource.MINERAI, 1), colosseStepsA);
    }

    protected ArrayList<WonderBoard> initiateWonders() {
        ArrayList<WonderBoard> res = new ArrayList<>(14);

        // Le Colosse de Rhodes
        // Face A
        ArrayList<Step> colosseStepsA = new ArrayList<>();
        colosseStepsA.add(new Step(new Resource[]{Resource.BOIS, Resource.BOIS}, new ScoreEffect("", 3)));
        colosseStepsA.add(new Step(new Resource[]{Resource.ARGILE, Resource.ARGILE, Resource.ARGILE}, new SymbolEffect("", Symbol.BOUCLIER, 2)));
        colosseStepsA.add(new Step(new Resource[]{Resource.MINERAI, Resource.MINERAI, Resource.MINERAI, Resource.MINERAI}, new ScoreEffect("", 7)));
        res.add(new WonderBoard("Le Colosse de Rhodes A", new ResourceEffect("", Resource.MINERAI, 1), colosseStepsA));

        // Face B
        ArrayList<Step> colosseStepsB = new ArrayList<>();
        colosseStepsB.add(new Step(new Resource[]{Resource.PIERRE, Resource.PIERRE, Resource.PIERRE},
                new Effect[]{new SymbolEffect("", Symbol.BOUCLIER, 1), new ScoreEffect("", 3), new CoinEffect("", 3)}));
        colosseStepsB.add(new Step(new Resource[]{Resource.MINERAI, Resource.MINERAI, Resource.MINERAI, Resource.MINERAI},
                new Effect[]{new SymbolEffect("", Symbol.BOUCLIER, 1), new ScoreEffect("", 4), new CoinEffect("", 4)}));
        res.add(new WonderBoard("Le Colosse de Rhodes B", new ResourceEffect("", Resource.MINERAI, 1), colosseStepsB));

        // Le phare d’Alexandrie
        // Face A
        ArrayList<Step> alexandrieStepsA = new ArrayList<>();
        alexandrieStepsA.add(new Step(new Resource[]{Resource.BOIS, Resource.BOIS}, new ScoreEffect("", 3)));
        alexandrieStepsA.add(new Step(new Resource[]{Resource.MINERAI, Resource.MINERAI}, new ChoiceAllTypeResourceEffect("", true)));
        alexandrieStepsA.add(new Step(new Resource[]{Resource.VERRE, Resource.VERRE}, new ScoreEffect("", 7)));
        res.add(new WonderBoard("Le phare d’Alexandrie A", new ResourceEffect("", Resource.VERRE, 1), alexandrieStepsA));

        // Face B
        ArrayList<Step> alexandrieStepsB = new ArrayList<>();
        alexandrieStepsB.add(new Step(new Resource[]{Resource.ARGILE, Resource.ARGILE}, new ChoiceAllTypeResourceEffect("", true)));
        alexandrieStepsB.add(new Step(new Resource[]{Resource.BOIS, Resource.BOIS}, new ChoiceAllTypeResourceEffect("", false)));
        alexandrieStepsB.add(new Step(new Resource[]{Resource.VERRE, Resource.VERRE}, new ScoreEffect("", 7)));
        res.add(new WonderBoard("Le phare d’Alexandrie B", new ResourceEffect("", Resource.VERRE, 1), alexandrieStepsB));

        // Le temple d’Artémis à Ephèse
        // Face A
        ArrayList<Step> ArtemisStepsA = new ArrayList<>();
        ArtemisStepsA.add(new Step(new Resource[]{Resource.PIERRE, Resource.PIERRE}, new ScoreEffect("", 3)));
        ArtemisStepsA.add(new Step(new Resource[]{Resource.BOIS, Resource.BOIS}, new CoinEffect("", 9)));
        ArtemisStepsA.add(new Step(new Resource[]{Resource.PAPYRUS, Resource.PAPYRUS}, new ScoreEffect("", 7)));
        res.add(new WonderBoard("Le temple d’Artémis à Ephèse A", new ResourceEffect("", Resource.PAPYRUS, 1), ArtemisStepsA));

        // Face B
        ArrayList<Step> ArtemisStepsB = new ArrayList<>();
        ArtemisStepsB.add(new Step(new Resource[]{Resource.PIERRE, Resource.PIERRE}, new Effect[]{new ScoreEffect("", 2), new CoinEffect("", 4)}));
        ArtemisStepsB.add(new Step(new Resource[]{Resource.BOIS, Resource.BOIS}, new Effect[]{new ScoreEffect("", 3), new CoinEffect("", 4)}));
        ArtemisStepsB.add(new Step(new Resource[]{Resource.PAPYRUS, Resource.TISSU, Resource.VERRE}, new Effect[]{new ScoreEffect("", 5), new CoinEffect("", 4)}));
        res.add(new WonderBoard("Le temple d’Artémis à Ephèse B", new ResourceEffect("", Resource.PAPYRUS, 1), ArtemisStepsB));

        // Les jardins suspendus de Babylone
        // Face A
        ArrayList<Step> BabyloneA = new ArrayList<>();
        BabyloneA.add(new Step(new Resource[]{Resource.ARGILE, Resource.ARGILE}, new ScoreEffect("", 3)));
        BabyloneA.add(new Step(new Resource[]{Resource.BOIS, Resource.BOIS, Resource.BOIS}, new ChoiceScientificEffect("")));
        BabyloneA.add(new Step(new Resource[]{Resource.ARGILE, Resource.ARGILE, Resource.ARGILE, Resource.ARGILE}, new ScoreEffect("", 7)));
        res.add(new WonderBoard("Les jardins suspendus de Babylone A", new ResourceEffect("", Resource.ARGILE, 1), BabyloneA));

        // Face B
        ArrayList<Step> BabyloneB = new ArrayList<>();
        BabyloneB.add(new Step(new Resource[]{Resource.TISSU, Resource.ARGILE}, new ScoreEffect("", 3)));
        BabyloneB.add(new Step(new Resource[]{Resource.VERRE, Resource.BOIS, Resource.BOIS}, new PlayLastCardEffect("")));
        BabyloneB.add(new Step(new Resource[]{Resource.ARGILE, Resource.ARGILE, Resource.ARGILE, Resource.PAPYRUS}, new ChoiceScientificEffect("")));
        res.add(new WonderBoard("Les jardins suspendus de Babylone B", new ResourceEffect("", Resource.ARGILE, 1), BabyloneB));


        // La statue de Zeus à Olympie
        // Face A
        ArrayList<Step> ZeusA = new ArrayList<>();
        ZeusA.add(new Step(new Resource[]{Resource.ARGILE, Resource.ARGILE}, new ScoreEffect("", 3)));
        ZeusA.add(new Step(new Resource[]{Resource.BOIS, Resource.BOIS, Resource.BOIS}, new FreeBuildingEffect("")));
        ZeusA.add(new Step(new Resource[]{Resource.ARGILE, Resource.ARGILE, Resource.ARGILE, Resource.ARGILE}, new ScoreEffect("", 7)));
        res.add(new WonderBoard("La statue de Zeus à Olympie A", new ResourceEffect("", Resource.BOIS, 1), ZeusA));

        // Face B
        ArrayList<Step> ZeusB = new ArrayList<>();
        ZeusB.add(new Step(new Resource[]{Resource.ARGILE, Resource.ARGILE}, new Effect[]{new ReductCommerce("", 2, true), new CoinEffect("", 3)}));
        ZeusB.add(new Step(new Resource[]{Resource.BOIS, Resource.BOIS, Resource.BOIS}, new ScoreEffect("", 5)));
        ZeusB.add(new Step(new Resource[]{Resource.ARGILE, Resource.ARGILE, Resource.ARGILE, Resource.ARGILE}, new ScoreEffect("", 7)));
        res.add(new WonderBoard("La statue de Zeus à Olympie B", new ResourceEffect("", Resource.BOIS, 1), ZeusB));


        // Le mausolée d’Halicarnasse
        // Face A
        ArrayList<Step> mausoléeA = new ArrayList<>();
        mausoléeA.add(new Step(new Resource[]{Resource.ARGILE, Resource.ARGILE}, new ScoreEffect("", 3)));
        mausoléeA.add(new Step(new Resource[]{Resource.MINERAI, Resource.MINERAI, Resource.MINERAI}, new FreeBuildingEffect("")));
        mausoléeA.add(new Step(new Resource[]{Resource.TISSU, Resource.TISSU}, new ScoreEffect("", 7)));
        res.add(new WonderBoard("La statue de Zeus à Olympie A", new ResourceEffect("", Resource.TISSU, 1), mausoléeA));

        // Face B
        ArrayList<Step> mausoléeB = new ArrayList<>();
        mausoléeB.add(new Step(new Resource[]{Resource.MINERAI, Resource.MINERAI}, new Effect[]{new ScoreEffect("", 2), new FreeDiscardedBuildingEffect("")}));
        mausoléeB.add(new Step(new Resource[]{Resource.ARGILE, Resource.ARGILE, Resource.ARGILE}, new Effect[]{new ScoreEffect("", 1), new FreeDiscardedBuildingEffect("")}));
        mausoléeB.add(new Step(new Resource[]{Resource.VERRE, Resource.PAPYRUS, Resource.TISSU}, new FreeDiscardedBuildingEffect("")));
        res.add(new WonderBoard("La statue de Zeus à Olympie A", new ResourceEffect("", Resource.TISSU, 1), mausoléeB));

        // La grande pyramide de Gizeh
        // Face A
        ArrayList<Step> GizehA = new ArrayList<>();
        GizehA.add(new Step(new Resource[]{Resource.PIERRE, Resource.PIERRE}, new ScoreEffect("", 3)));
        GizehA.add(new Step(new Resource[]{Resource.BOIS, Resource.BOIS, Resource.BOIS}, new ScoreEffect("", 5)));
        GizehA.add(new Step(new Resource[]{Resource.PIERRE, Resource.PIERRE, Resource.PIERRE, Resource.PIERRE}, new ScoreEffect("", 7)));
        res.add(new WonderBoard("La grande pyramide de Gizeh A", new ResourceEffect("", Resource.PIERRE, 1), GizehA));

        // Face B
        ArrayList<Step> GizehB = new ArrayList<>();
        GizehB.add(new Step(new Resource[]{Resource.BOIS, Resource.BOIS}, new ScoreEffect("", 3)));
        GizehB.add(new Step(new Resource[]{Resource.PIERRE, Resource.PIERRE, Resource.PIERRE}, new ScoreEffect("", 5)));
        GizehB.add(new Step(new Resource[]{Resource.ARGILE, Resource.ARGILE, Resource.ARGILE}, new ScoreEffect("", 5)));
        GizehB.add(new Step(new Resource[]{Resource.PIERRE, Resource.PIERRE, Resource.PIERRE, Resource.PIERRE, Resource.PAPYRUS}, new ScoreEffect("", 7)));
        res.add(new WonderBoard("La grande pyramide de Gizeh B", new ResourceEffect("", Resource.PIERRE, 1), GizehB));


        return res;
    }
}
