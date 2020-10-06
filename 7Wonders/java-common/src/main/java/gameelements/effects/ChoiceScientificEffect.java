package gameelements.effects;

import gameelements.Effect;
import gameelements.Inventory;
import gameelements.Player;
import gameelements.enums.EffectDelay;
import gameelements.enums.EffectFrequency;
import gameelements.enums.Symbol;

public class ChoiceScientificEffect extends Effect {

    public ChoiceScientificEffect() {
        super(EffectDelay.END_OF_THE_GAME, EffectFrequency.ONCE);
    }

    public void activateEffect(Inventory inv, Player player) {
        super.activateEffect(inv);
        Symbol symbol = player.chooseScientific(inv.getAvailableSymbols().clone());
        inv.getAvailableSymbols()[symbol.getIndex()]++;
    }
}
