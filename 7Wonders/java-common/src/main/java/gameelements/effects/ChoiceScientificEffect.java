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

    public void activateEffect(Player player, Inventory inv, Inventory leftNeighborInv, Inventory rightNeighborInv) {
        super.activateEffect(player, inv, leftNeighborInv, rightNeighborInv);
        Symbol symbol = player.chooseScientific(inv.getAvailableSymbols().clone());
        inv.getAvailableSymbols()[symbol.getIndex()]++;
    }
}
