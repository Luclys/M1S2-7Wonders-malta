package gameelements.effects;

import gameelements.Effect;
import gameelements.Inventory;
import gameelements.Player;
import gameelements.enums.EffectDelay;
import gameelements.enums.EffectFrequency;
import gameelements.enums.Symbol;

public class SymbolEffect extends Effect {
    Symbol symbol;
    int nb;

    public SymbolEffect(Symbol symbol, int nb) {
        super(EffectDelay.INSTANTANEOUS, EffectFrequency.ONCE);
        this.symbol = symbol;
        this.nb = nb;
    }

    public void activateEffect(Player player, Inventory inv, Inventory leftNeighborInv, Inventory rightNeighborInv) {
        super.activateEffect(player, inv, leftNeighborInv, rightNeighborInv);
        inv.getAvailableSymbols()[symbol.getIndex()] += nb;
    }
}
