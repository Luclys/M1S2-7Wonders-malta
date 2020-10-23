package gameelements;

import gameelements.cards.Card;
import gameelements.enums.Resource;
import gameelements.enums.Symbol;
import gameelements.wonders.WonderBoard;

import java.util.List;

public class SoutConsole {
    boolean booleanPrint;

    public SoutConsole(boolean booleanPrint) {
        this.booleanPrint = booleanPrint;
    }

    public static void display(String msg, boolean booleanPrint) {
        if (!booleanPrint) return;
        System.out.println(msg);
    }

    public void beginningOfPlay(int i) {
        if (!booleanPrint) return;
        System.out.printf("%n%n%n===========================================================================%n");
        System.out.printf("========================= INITIATING PLAY N° %d ===========================%n%n%n", i);
    }

    public void beginningOfAge(int i) {
        if (!booleanPrint) return;
        System.out.printf("%n%n%n========================= START AGE N° %d ===========================%n%n%n", i);
    }

    public void endOfAge(int i) {
        if (!booleanPrint) return;
        System.out.println("LAST CARDS ARE DISCARDED");
        System.out.printf("%n%n%n========================= END AGE N° %d =======================%n%n%n", i);
    }

    public void newTurn(int turn) {
        if (!booleanPrint) return;
        System.out.printf("%n%n================ TURN n° %d ===============%n%n", turn);
    }

    public void endOfGame() {
        if (!booleanPrint) return;
        System.out.printf("%n%n========== END OF THE GAME ==========%n%n");
    }

    public void finalResults() {
        if (!booleanPrint) return;
        System.out.printf("%n%n========== FINAL RESULTS  ==========%n");
    }

    public void play() {
        if (!booleanPrint) return;
        System.out.println("%n%n============ The chosen cards ==========%n%n");
    }

    public void chosenCards(int i, Card c) {
        if (!booleanPrint) return;
        System.out.printf("(*)Player : %d  plays card %s %n", i, c);
    }

    public void action(int i) {
        if (!booleanPrint) return;
        System.out.printf("%n%n============ The action of PLAYER %d ===============%n%n", i);
    }

    public void playersNewState(int i) {
        if (!booleanPrint) return;
        System.out.printf("%n Player %d new state: %n", i);
    }

    public void checkMissingResources(Card chosenCard) {
        if (!booleanPrint) return;
        System.out.printf("%n*Check if player has the required resources to build  %s %n", chosenCard);
    }

    public void noRequiredResources(Card chosenCard) {
        if (!booleanPrint) return;
        System.out.printf("%n**No resource is required to play  %s  %n", chosenCard);
    }

    public void missingResources(List<Resource> r) {
        if (!booleanPrint) return;
        System.out.printf("%n**The missing resources are %s %n", r);
    }

    public void cantBuyMissingResources() {
        if (!booleanPrint) return;
        System.out.printf("%n***The player cannot buy resources %n%n");
    }

    public void playerSellsCard(int playerId, Card card) {
        if (!booleanPrint) return;
        System.out.printf("%n***The player %d sells %s for 3 coins %n", playerId, card);
    }

    public void gotMissingResources() {
        if (!booleanPrint) return;
        System.out.printf("%n***The player got resources so he gets the effects of this card.%n%n");
    }

    public void playerInformation(Inventory inv) {
        if (!booleanPrint) return;
        String r = "";
        r += inv.getAvailableResources()[Resource.BOIS.getIndex()] + " BOIS , "
                + inv.getAvailableResources()[Resource.ARGILE.getIndex()] + " ARGILE , "
                + inv.getAvailableResources()[Resource.PIERRE.getIndex()] + " PIERRE , "
                + inv.getAvailableResources()[Resource.MINERAI.getIndex()] + " MINERAI , "
                + inv.getAvailableResources()[Resource.VERRE.getIndex()] + " VERRE , "
                + inv.getAvailableResources()[Resource.PAPYRUS.getIndex()] + " PAPYRUS , "
                + inv.getAvailableResources()[Resource.TISSU.getIndex()] + " TISSU ";

        String s = "";
        s += inv.getAvailableSymbols()[Symbol.BOUCLIER.getIndex()] + " BOUCLIER , "
                + inv.getAvailableSymbols()[Symbol.COMPAS.getIndex()] + " COMPAS , "
                + inv.getAvailableSymbols()[Symbol.ROUAGE.getIndex()] + " ROUAGE , "
                + inv.getAvailableSymbols()[Symbol.STELE.getIndex()] + " STELE ";

        System.out.printf("{" +
                "player : " + inv.getPlayerId() +
                ",%n\t availableResources{ " + r +
                " },%n\t availableSymbols{ " + s +
                "},%n\t victory chips score = " + inv.getVictoryChipsScore() +
                ", %n\t defeat chips count = " + inv.getDefeatChipsCount() +
                ", %n\t score = " + inv.getScore() +
                ", %n\t coins = " + inv.getCoins() +
                ".%n}");
    }

    public void conflicts(Inventory invPlayer, Inventory invNeighborPlayer) {
        if (!booleanPrint) return;
        System.out.println("================= [RESOLVING WAR CONFLICTS] =================");
        System.out.println("Conflicts between PLAYER " + invPlayer.getPlayerId() + " and his neighbor PLAYER " + invNeighborPlayer.getPlayerId());
    }

    public void checkShields(int playerShieldsCount, int neighborShieldsCount) {
        if (!booleanPrint) return;
        System.out.println("Player has " + playerShieldsCount + " shields while neighbor has " + neighborShieldsCount);
    }

    public void addConflictsPoint(int victoryChipValue) {
        if (!booleanPrint) return;
        System.out.println(victoryChipValue + " conflict points added");
    }

    public void defeatChip() {
        if (!booleanPrint) return;
        System.out.println("Defeat chip (-1 conflict point) added");
    }

    public void resolvedConflicts(Inventory invPlayer) {
        if (!booleanPrint) return;
        System.out.println("Total player victory chip score: " + invPlayer.getVictoryChipsScore() + ", defeat chip count: " + invPlayer.getDefeatChipsCount());
    }

    public void display(String msg) {
        if (!booleanPrint) return;
        System.out.println(msg);
    }

    public void pricesOfResources(Inventory player) {
        if (!booleanPrint) return;

        System.out.printf("%n**Player %d price for Produits manufacturés: %d; Matières premiers left: %d right: %d %n%n",
                player.getPlayerId(),
                player.getProduitsManifacturesPrice(),
                player.getMatieresPremieresPriceLeft(),
                player.getMatieresPremieresPriceRight()
        );
    }

    public void chooseWonderBoard(int id, WonderBoard wb) {
        if (!booleanPrint) return;
        System.out.println("The player " + id + " choose " + wb.getName() + " wonder");
    }

    public void playerPaysCoins(int playerId, int neighborId, int totalCoins) {
        if (!booleanPrint) return;
        System.out.printf("%n**Player %d pays %d coins to player %d %n", playerId, totalCoins, neighborId);
    }

    public void playerCanBuyFromNeighbor(int playerId, int neighborId, String neighborSide, String missingResource) {
        if (!booleanPrint) return;
        System.out.printf("%n**Player %d can buy %s from %s neighbor with id %d %n", playerId, missingResource, neighborSide, neighborId);
    }

    public void notEnoughCoinsToBuyResource(int playerId, String neighborSide, String missingResource) {
        if (!booleanPrint) return;
        System.out.printf("%n**Player %d doesn't have enough coins to buy %s from %s neighbor %n", playerId, missingResource, neighborSide);
    }

    public void noneOfTheNeighborsHasResource(String missingResource) {
        if (!booleanPrint) return;
        System.out.printf("%n**None of the neighbors has resource %s %n", missingResource);
    }

    public void playerCanBuildCardForFree(int playerId, Card card, List<String> cardsAllowingToBuildForFree) {
        if (!booleanPrint) return;
        System.out.printf("%n**Player %d can build card %s for free thanks to one of the cards build in previous ages: %s %n%n", playerId, card.getName(), cardsAllowingToBuildForFree);
    }

    public void playerBuildsCard(int playerId, Card card) {
        if (!booleanPrint) return;
        System.out.printf("%n**Player %d builds card %s %n%n", playerId, card.getName());
    }

    public void playerBuildsWonderStep(int playerId) {
        if (!booleanPrint) return;
        System.out.printf("%n**Player %d builds wonder step %n%n", playerId);
    }

    public void finalGameRanking(List<Inventory> inventoryList) {
        if (!booleanPrint) return;
        inventoryList.forEach(inv -> System.out.printf("Player %d is ranked %d with the score of %d and %d coins.%n", inv.getPlayerId(), inv.getRank(), inv.getScore(), inv.getCoins()));
    }
}
