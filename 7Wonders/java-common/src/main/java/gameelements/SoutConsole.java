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

    public void beginningOfPlay(int i) {
        if (!booleanPrint) return;
        System.out.printf(
                        "%n==========================================================================%n" +
                        "========================= INITIATING GAME N° %d ===========================%n" +
                        "==========================================================================%n%n",
                        i
                );
    }

    public void chooseWonderBoard(int id, WonderBoard wb) {
        if (!booleanPrint) return;
        System.out.printf("%n[WONDER CHOOSING] The player %d chooses %s wonder %n", id, wb.getName());
    }

    public void beginningOfAge(int i) {
        if (!booleanPrint) return;
        System.out.printf(
                "%n==========================================================================%n" +
                "========================== START AGE N° %d ================================%n" +
                "==========================================================================%n",
                i
        );
    }

    public void newTurn(int turn) {
        if (!booleanPrint) return;
        System.out.printf("%n========================== TURN N° %d ====================================%n", turn);
    }

    public void play() {
        if (!booleanPrint) return;
        System.out.printf("%n===================== PLAYERS CHOOSE CARDS TO PLAY ====================%n");
    }

    public void chosenCards(int i, Card c) {
        if (!booleanPrint) return;
        System.out.printf("%n[CARD CHOOSING] Player %d chose card %s %n", i, c.getName());
    }

    public void playersStartToPlayCards() {
        if (!booleanPrint) return;
        System.out.printf("%n========================= PLAYERS PLAY CARDS ==========================%n");
    }

    public void action(int i) {
        if (!booleanPrint) return;
        System.out.printf("%n======================== THE ACTION OF PLAYER %d =======================%n%n", i);
    }

    public void playerInformation(Inventory inv) {
        if (!booleanPrint) return;
        String avaibaleResources = String.format("[BOIS : %d, ARGILE: %d, PIERRE: %d, MINERAI: %d, VERRE: %d, PAPYRUS: %d, TISSU: %d]",
                inv.getAvailableResources()[Resource.BOIS.getIndex()],
                inv.getAvailableResources()[Resource.ARGILE.getIndex()],
                inv.getAvailableResources()[Resource.PIERRE.getIndex()],
                inv.getAvailableResources()[Resource.MINERAI.getIndex()],
                inv.getAvailableResources()[Resource.VERRE.getIndex()],
                inv.getAvailableResources()[Resource.PAPYRUS.getIndex()],
                inv.getAvailableResources()[Resource.TISSU.getIndex()]
        );

        String availableSymbols = String.format("[BOUCLIER: %d, COMPAS: %d, ROUAGE: %d, STELE: %d]",
                inv.getAvailableSymbols()[Symbol.BOUCLIER.getIndex()],
                inv.getAvailableSymbols()[Symbol.COMPAS.getIndex()],
                inv.getAvailableSymbols()[Symbol.ROUAGE.getIndex()],
                inv.getAvailableSymbols()[Symbol.STELE.getIndex()]
        );
        System.out.printf("%n========================== PLAYER %d STATE =============================%n" +
                        "AVAILABLE RESOURCES: %s,%n" +
                        "AVAILABLE SYMBOLS: %s,%n" +
                        "VICTORY CHIPS SCORE: %d,%n" +
                        "DEFEAT CHIPS COUNT: %d,%n" +
                        "SCORE: %d,%n" +
                        "COINS: %d %n",
                inv.getPlayerId(),
                avaibaleResources,
                availableSymbols,
                inv.getVictoryChipsScore(),
                inv.getDefeatChipsCount(),
                inv.getScore(),
                inv.getCoins()
        );
    }

    public void playerBuildsCard(int playerId, Card card) {
        if (!booleanPrint) return;
        System.out.printf("%n[ACTION] Player %d builds card %s%n", playerId, card.getName());
    }

    public void playerCanBuildCardForFree(int playerId, Card card, List<String> cardsAllowingToBuildForFree) {
        if (!booleanPrint) return;
        System.out.printf("%n[ACTION] Player %d can build card %s for free thanks to one of the cards build in previous ages: %s%n",
                playerId,
                card.getName(),
                cardsAllowingToBuildForFree
        );
    }

    public void playerBuildCardFreeBuildingEffect(int playerId, Card card) {
        if (!booleanPrint) return;
        System.out.printf(String.format("%n[ACTION] Player %d can build card %s for free thanks to the Free Building Effect %n",
                playerId,
                card.getName()
        ));
    }

    public void playerBuildsWonderStep(int playerId) {
        if (!booleanPrint) return;
        System.out.printf("%n[ACTION] Player %d builds wonder step %n", playerId);
    }

    public void startTrade() {
        if (!booleanPrint) return;
        System.out.printf("%n============== PLAYER HAS SOME MISSING RESOURCES: START TRADE ================%n%n");
    }

    public void pricesOfResources(Inventory player) {
        if (!booleanPrint) return;

        System.out.printf("%n[TRADE] Player %d prices for resources: " +
                        "Produits manufacturés: %d, " +
                        "Matières premiers left neighbor: %d, " +
                        "Matières premiers right neighbor: %d%n",
                player.getPlayerId(),
                player.getProduitsManifacturesPrice(),
                player.getMatieresPremieresPriceLeft(),
                player.getMatieresPremieresPriceRight()
        );
    }

    public void missingResources(List<Resource> r) {
        if (!booleanPrint) return;
        System.out.printf("%n[TRADE] The missing resources are %s%n", r);
    }

    public void cantBuyMissingResources() {
        if (!booleanPrint) return;
        System.out.printf("%n[TRADE] The player cannot buy missing resources %n");
    }

    public void coinsLeft(int playerId, int coinsLeft) {
        if (!booleanPrint) return;
        System.out.printf("%n[TRADE] Player %d has %d coins left %n", playerId, coinsLeft);
    }

    public void gotMissingResources() {
        if (!booleanPrint) return;
        System.out.printf("%n[TRADE] The player bought the missing resources so he gets the effects of the played card.%n");
    }

    public void playerPaysCoins(int playerId, int neighborId, int totalCoins) {
        if (!booleanPrint) return;
        System.out.printf("%n[TRADE] player %d pays %d coins to player %d %n", playerId, totalCoins, neighborId);
    }

    public void playerCanBuyFromNeighbor(int playerId, int neighborId, String neighborSide, String missingResource) {
        if (!booleanPrint) return;
        System.out.printf("%n[TRADE] Player %d can buy %s from %s neighbor with id %d %n",
                playerId,
                missingResource, neighborSide, neighborId);
    }

    public void notEnoughCoinsToBuyResource(int playerId, String neighborSide, String missingResource) {
        if (!booleanPrint) return;
        System.out.printf("%n[TRADE] Player %d doesn't have enough coins to buy %s from %s neighbor %n", playerId, missingResource, neighborSide);
    }

    public void noneOfTheNeighborsHasResource(String missingResource) {
        if (!booleanPrint) return;
        System.out.printf("%n[TRADE] None of the neighbors has resource %s %n", missingResource);
    }

    public void playerSellsCard(int playerId, Card card) {
        if (!booleanPrint) return;
        System.out.printf("%n[CARD SALE] The player %d sells %s for 3 coins %n", playerId, card);
    }

    public void playersNewState(int i) {
        if (!booleanPrint) return;
        System.out.printf("%nPlayer %d state after the card was played:%n", i);
    }

    public void conflicts(Inventory invPlayer, Inventory invNeighborPlayer) {
        if (!booleanPrint) return;
        System.out.printf("%n============ [RESOLVING WAR CONFLICTS BETWEEN PLAYER %d AND NEIGHBOR %d] ============%n", invPlayer.getPlayerId(), invNeighborPlayer.getPlayerId());
    }

    public void checkShields(int playerShieldsCount, int neighborShieldsCount) {
        if (!booleanPrint) return;
        System.out.printf("%n[RESOLVING CONFLICTS] Player has %d shields while neighbor has %d shields %n", playerShieldsCount, neighborShieldsCount);
    }

    public void addConflictsPoint(int victoryChipValue, int playerId) {
        if (!booleanPrint) return;
        System.out.printf("%n[RESOLVING CONFLICTS] %d conflict points added to player %d %n", victoryChipValue, playerId);
    }

    public void defeatChip(int playerId) {
        if (!booleanPrint) return;
        System.out.printf("%n[RESOLVING CONFLICTS] Defeat chip (-1 conflict point) added to player %d %n", playerId);
    }

    public void resolvedConflicts(Inventory invPlayer) {
        if (!booleanPrint) return;
        System.out.printf("%n[RESOLVING CONFLICTS] Conflicts are resolved. Total player %d victory chip score: %d, defeat chip count: %d %n", invPlayer.getPlayerId(), invPlayer.getVictoryChipsScore(), invPlayer.getDefeatChipsCount());
    }

    public void endOfAge(int i) {
        if (!booleanPrint) return;
        System.out.printf("%n==========================================================================%n");
        System.out.printf("================ LAST CARDS ARE DISCARDED. END OF AGE N° %d ===============%n", i);
        System.out.printf("==========================================================================%n%n");
    }

    public void endOfGame() {
        if (!booleanPrint) return;
        System.out.printf("%n==========================================================================%n");
        System.out.printf("====================== END OF GAME. FINAL RESULTS ========================%n");
        System.out.printf("==========================================================================%n%n");
    }

    public void finalGameRanking(List<Inventory> inventoryList) {
        if (!booleanPrint) return;
        inventoryList.forEach(inv -> System.out.printf("[FINAL RESULTS] Player %d is ranked %d with the score of %d and %d coins.%n", inv.getPlayerId(), inv.getRank(), inv.getScore(), inv.getCoins()));
    }
}
