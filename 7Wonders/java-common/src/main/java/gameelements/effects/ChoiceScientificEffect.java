package gameelements.effects;

import gameelements.Effect;
import gameelements.Inventory;
import gameelements.Player;
import gameelements.enums.EffectDelay;
import gameelements.enums.Symbol;

public class ChoiceScientificEffect extends Effect {

    public ChoiceScientificEffect() {
        super(EffectDelay.END_OF_THE_GAME);
    }

    @Override
    public void activateEffect(Player player, Inventory inv, Inventory leftNeighborInv, Inventory rightNeighborInv, boolean isEndGame) {
        if ((!isEndGame) && (getDelay() == EffectDelay.END_OF_THE_GAME)) {
            inv.addEndGameEffect(this);
            return;
        }

        Symbol symbol = player.chooseScientific();
        inv.getAvailableSymbols()[symbol.getIndex()]++;
    }
}
