package engine.board;


import gameelements.Inventory;
import gameelements.cards.Card;
import gameelements.enums.Symbol;
import statistic.DetailedResults;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayersManager {

    List<Inventory> playerInventoryList;

    GameLogger log;

    public PlayersManager(GameLogger logger) {
        this.log = logger;
    }

    public PlayersManager() {
        this.log = new GameLogger(false);
        playerInventoryList = new ArrayList<>();
    }

    public PlayersManager(PlayersManager playersManager) {
        this.log = new GameLogger(false);
        this.playerInventoryList = new ArrayList<>();
        this.playerInventoryList.addAll(playersManager.playerInventoryList);
    }

    protected void updateCoins() {
        for (Inventory inv : playerInventoryList) {
            int coinsFromTrade = inv.getAddedCoins();
            inv.addCoins(coinsFromTrade);
            inv.getDetailedResults().addNbCoinsAcquiredInTrade(coinsFromTrade);
            inv.setAddedCoins(0);
        }
    }

    protected void freeBuildFromDiscarded(List<Card> discardedDeckCardList) {
        for (Inventory inv : playerInventoryList) {
            log.playerInformation(inv);
            if (discardedDeckCardList.isEmpty()) {
                return;
            }
            if (inv.getPossibleFreeDiscardedBuildingsCount() != 0) {
                //TODO MAYBE ?
                //Player player = playerList.get(inv.getPlayerId());
                //Card card = player.chooseDiscardedCardToBuild(new Inventory(inv), discardedDeckCardList);
                Card card = discardedDeckCardList.get(0);
                inv.updateInventoryFreeCard(card, playerInventoryList.get(inv.getLeftNeighborId()), playerInventoryList.get(inv.getRightNeighborId()), discardedDeckCardList);
                inv.addPossibleFreeDiscardedBuildingsCount(-1);
                log.playerBuildsFreeCardFromDiscarded(inv.getPlayerId(), card);
            }
        }
    }

    protected void fightWithNeighbor(Inventory invPlayer, Inventory invNeighbor, int victoryJetonValue) { // victoryJetonValue depends on Age
        int playerBoucliersCount = invPlayer.getSymbolCount(Symbol.BOUCLIER);
        int neighborBoucliersCount = invNeighbor.getSymbolCount(Symbol.BOUCLIER);
        log.conflicts(invPlayer, invNeighbor);
        log.checkShields(playerBoucliersCount, neighborBoucliersCount);
        if (playerBoucliersCount > neighborBoucliersCount) {
            invPlayer.addVictoryJetonsScore(victoryJetonValue);
            log.addConflictsPoint(victoryJetonValue, invPlayer.getPlayerId());
        } else if (playerBoucliersCount < neighborBoucliersCount) {
            invPlayer.addDefeatJeton();
            log.defeatChip(invPlayer.getPlayerId());
        }
        log.resolvedConflicts(invPlayer);
    }

    protected void handlePlayersIds(HashMap<Integer, String> mapPlayerID_URL) {
        playerInventoryList = new ArrayList<>();

        for (Integer k : mapPlayerID_URL.keySet()) {
            Inventory inv = new Inventory(k);
            inv.setPlayerURL(mapPlayerID_URL.get(k));
            // To make a sure we bind the first's left to last id
            if (k == 0) {
                inv.setLeftNeighborId(mapPlayerID_URL.size() - 1);
            } else {
                inv.setLeftNeighborId(k - 1);
            }
            // To make a sure we bind the last's right to first id
            if (k == mapPlayerID_URL.size() - 1) {
                inv.setRightNeighborId(0);
            } else {
                inv.setRightNeighborId(k + 1);
            }

            DetailedResults details = new DetailedResults();
            // TODO : Ask Player for his Strategy name ?
            //details.setStrategyName(player.getStrategyName());
            inv.setDetailedResults(details);

            playerInventoryList.add(inv);
        }
    }

    public List<Inventory> getPlayerInventoryList() {
        return playerInventoryList;
    }
}
