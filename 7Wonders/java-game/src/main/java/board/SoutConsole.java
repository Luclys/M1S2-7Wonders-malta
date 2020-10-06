package board;

import gameelements.Card;
import gameelements.Inventory;
import gameelements.enums.Resource;
import gameelements.enums.Symbol;

import java.util.ArrayList;

public class SoutConsole {
    boolean booleanPrint;

    public SoutConsole(boolean booleanPrint) {
        this.booleanPrint = booleanPrint;
    }

    public void beginingOfAge(int i) {
        if (!booleanPrint) {
            return;
        }
        System.out.printf("\n\n\n========================= START AGE N° %d ===========================\n\n\n", i);
    }

    public void endOfAge(int i) {
        if (!booleanPrint) {
            return;
        }
        System.out.println("LAST CARDS ARE DEFEAT");
        System.out.printf("\n\n\n========================= END AGE N° %d =======================\n\n\n", i);
    }

    public void newTurn(int turn) {
        if (!booleanPrint) {
            return;
        }
        System.out.printf("\n\n================ TURN n° %d ===============\n\n", turn);
    }

    public void endOfGame() {
        if (!booleanPrint) {
            return;
        }
        System.out.print("\n\n========== END OF THE GAME ==========\n\n");
    }

    public void FinalResults() {
        if (!booleanPrint) {
            return;
        }
        System.out.print("\n\n========== FINAL RESULTS  ==========\n");
    }

    public void play() {
        if (!booleanPrint) {
            return;
        }
        System.out.println("\n\n============ The chosen cards ==========\n\n");
    }

    public void chosenCards(int i, Card c) {
        if (!booleanPrint) {
            return;
        }
        System.out.printf("(*)Player : %d  plays card %s \n", i, c);
    }

    public void action(int i) {
        if (!booleanPrint) {
            return;
        }
        System.out.printf("\n\n============ The action of PLAYER %d ===============\n\n", i);
    }

    public void checkMissingResources(Card choosenCard) {
        if (!booleanPrint) {
            return;
        }
        System.out.printf("\n*Check if player has the required resources to build  %s \n", choosenCard);
    }

    public void noRequiredResources(Card choosenCard) {
        if (!booleanPrint) {
            return;
        }
        System.out.printf("\n**No resource is required to play  %s  \n", choosenCard);
    }

    public void missingResources(ArrayList<Resource> r) {
        if (!booleanPrint) {
            return;
        }
        System.out.printf("\n**The missing resources are %s", r);
    }

    public void cantBuyMissingResources() {
        if (!booleanPrint) {
            return;
        }
        System.out.printf("\n***The player cannot buy resources, so he sells the card for 3 coins.\n\n");
    }

    public void gotMissingResources() {
        if (!booleanPrint) {
            return;
        }
        System.out.printf("\n***The player got resources so he gets the effects of this card.\n\n");
    }

    public void informationOfPlayer(Inventory p) {
        if (!booleanPrint) {
            return;
        }
        String r = "";
        r += p.getAvailableResources()[Resource.BOIS.getIndex()] + " BOIS , "
                + p.getAvailableResources()[Resource.ARGILE.getIndex()] + " ARGILE , "
                + p.getAvailableResources()[Resource.PIERRE.getIndex()] + " PIERRE , "
                + p.getAvailableResources()[Resource.MINERAI.getIndex()] + " MINERAI , "
                + p.getAvailableResources()[Resource.VERRE.getIndex()] + " VERRE , "
                + p.getAvailableResources()[Resource.PAPYRUS.getIndex()] + " PAPYRUS , "
                + p.getAvailableResources()[Resource.TISSU.getIndex()] + " TISSU ";
        String s = "";

        s += p.getAvailableSymbols()[Symbol.BOUCLIER.getIndex()] + " BOUCLIER , "
                + p.getAvailableSymbols()[Symbol.COMPAS.getIndex()] + " COMPAS , "
                + p.getAvailableSymbols()[Symbol.ROUAGE.getIndex()] + " ROUAGE , "
                + p.getAvailableSymbols()[Symbol.STELE.getIndex()] + " STELE ";

        System.out.println("{" +
                "player : " + p.getPlayerId() +
                ",\n\t availableResources{ " + r +
                " },\n\t availableSymbols{ " + s +
                " },\n\t score=" + p.getScore() +
                ", \n\t victory jetons score = " + p.getVictoryJetonsScore() +
                ", \n\t defeat jetons count = " + p.getDefeatJetonsCount() +
                ", \n\t coins" + p.getCoins() +
                "\n}");
    }

    public void conflicts(Inventory invPlayer, Inventory invNeighborPlayer){
        if (!booleanPrint) {
            return;
        }
        System.out.println("================= [RESOLVING WAR CONFLICTS] =================");
        System.out.println("Conflicts between PLAYER "+invPlayer.getPlayerId()+" and his neighbor PLAYER "+invNeighborPlayer.getPlayerId());
    }
    public void checkBoucliers(int playerBoucliersCount,int neighborBoucliersCount){
        if (!booleanPrint) {
            return;
        }
        System.out.println("Player has " + playerBoucliersCount + " boucliers while neighbor has " + neighborBoucliersCount);
    }
    public void addConflictsPoint(int victoryJetonValue){
        if (!booleanPrint) {
            return;
        }
        System.out.println(victoryJetonValue + " conflict points added");
    }

    public void defeatJeton(){
        if (!booleanPrint) {
            return;
        }
        System.out.println("Defeat jeton (-1 conflict point) added");
    }

    public void resolvedConflicts(Inventory invPlayer){
        if (!booleanPrint) {
            return;
        }
        System.out.println("Total player victory jeton score: " + invPlayer.getVictoryJetonsScore() + ", defeat jetons count: " + invPlayer.getDefeatJetonsCount());
    }

    public void display(String msg){
        if (!booleanPrint) {
            return;
        }
        System.out.println(msg);
    }

}
