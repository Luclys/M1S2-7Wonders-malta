package statistic;

public class DetailedResults {
    String strategyName = "";
    String wbName = "";

    int rank = 0;
    int totalScore = 0;

    int scoreFromVictoryConflict = 0;
    int nbDefeatConflict = 0;
    int scoreFromEndGameCoins = 0;
    int scoreFromScientificBuildings = 0;
    int scoreFromGuilds = 0;
    int nbStepBuilt = 0;
    int nbSoldCard = 0;
    int nbCoinsSpentInTrade = 0;
    int nbCoinsAcquiredInTrade = 0;
    int nbShield = 0;

    public DetailedResults() {
    }

    public void addNbCoinsAcquiredInTrade(int coins) {
        this.nbCoinsAcquiredInTrade += coins;
    }

    public void addNbCoinsSpentInTrade(int coins) {
        this.nbCoinsSpentInTrade += coins;
    }

    public void incNbSoldCard() {
        this.nbSoldCard++;
    }

    public String getStrategyName() {
        return strategyName;
    }

    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName;
    }

    public String getWbName() {
        return wbName;
    }

    public void setWbName(String wbName) {
        this.wbName = wbName;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getScoreFromVictoryConflict() {
        return scoreFromVictoryConflict;
    }

    public void setScoreFromVictoryConflict(int scoreFromVictoryConflict) {
        this.scoreFromVictoryConflict = scoreFromVictoryConflict;
    }

    public int getNbDefeatConflict() {
        return nbDefeatConflict;
    }

    public void setNbDefeatConflict(int nbDefeatConflict) {
        this.nbDefeatConflict = nbDefeatConflict;
    }

    public int getScoreFromEndGameCoins() {
        return scoreFromEndGameCoins;
    }

    public void setScoreFromEndGameCoins(int scoreFromEndGameCoins) {
        this.scoreFromEndGameCoins = scoreFromEndGameCoins;
    }

    public int getScoreFromScientificBuildings() {
        return scoreFromScientificBuildings;
    }

    public void setScoreFromScientificBuildings(int scoreFromScientificBuildings) {
        this.scoreFromScientificBuildings = scoreFromScientificBuildings;
    }

    public int getScoreFromGuilds() {
        return scoreFromGuilds;
    }

    public void setScoreFromGuilds(int scoreFromGuilds) {
        this.scoreFromGuilds = scoreFromGuilds;
    }

    public int getNbStepBuilt() {
        return nbStepBuilt;
    }

    public void setNbStepBuilt(int nbStepBuilt) {
        this.nbStepBuilt = nbStepBuilt;
    }

    public int getNbSoldCard() {
        return nbSoldCard;
    }

    public void setNbSoldCard(int nbSoldCard) {
        this.nbSoldCard = nbSoldCard;
    }

    public int getNbCoinsSpentInTrade() {
        return nbCoinsSpentInTrade;
    }

    public void setNbCoinsSpentInTrade(int nbCoinsSpentInTrade) {
        this.nbCoinsSpentInTrade = nbCoinsSpentInTrade;
    }

    public int getNbCoinsAcquiredInTrade() {
        return nbCoinsAcquiredInTrade;
    }

    public void setNbCoinsAcquiredInTrade(int nbCoinsAcquiredInTrade) {
        this.nbCoinsAcquiredInTrade = nbCoinsAcquiredInTrade;
    }

    public int getNbShield() {
        return nbShield;
    }

    public void setNbShield(int nbShield) {
        this.nbShield = nbShield;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }
}
