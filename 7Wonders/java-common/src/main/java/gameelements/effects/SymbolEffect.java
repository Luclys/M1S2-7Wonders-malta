package gameelements.effects;

import gameelements.Effect;
import gameelements.Inventory;
import gameelements.Player;
import gameelements.enums.EffectDelay;
import gameelements.enums.Symbol;

public class SymbolEffect extends Effect {
    private Symbol symbol;
    private int nb;

    public SymbolEffect(Symbol symbol, int nb) {
        super(EffectDelay.INSTANTANEOUS);
        this.symbol = symbol;
        this.nb = nb;
    }

    @Override
    public void activateEffect(Player player, Inventory inv, Inventory leftNeighborInv, Inventory rightNeighborInv, boolean isEndGame) {
        if ((!isEndGame) && (getDelay() == EffectDelay.END_OF_THE_GAME)) {
            inv.addEndGameEffect(this);
            return;
        }
        inv.getAvailableSymbols()[symbol.getIndex()] += nb;
    }

    public Symbol getSymbol() {
        return symbol;
    }
}
