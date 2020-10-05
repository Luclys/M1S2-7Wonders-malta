package gameelements.effects;

import gameelements.Effect;
import gameelements.Inventory;
import gameelements.enums.Category;
import gameelements.enums.EffectDelay;
import gameelements.enums.EffectFrequency;

public class ScoreAndCoinsForCategoryEffect extends Effect  {
    int nbCoins;
    int score;
    Category category;

    public ScoreAndCoinsForCategoryEffect(int nbCoins, int score, Category category) {
        super(EffectDelay.END_OF_THE_GAME, EffectFrequency.ONCE);
        this.nbCoins = nbCoins;
        this.score = score;
        this.category = category;
    }

    public void activateEffect(Inventory playersInv) {
        super.activateEffect(playersInv);
        int cardsCount = (int) playersInv.getPlayedCards().stream().filter(card -> card.getCategory().equals(category)).count();
        playersInv.addScore(cardsCount*score);
        playersInv.addCoins(cardsCount*nbCoins);
    }
}
