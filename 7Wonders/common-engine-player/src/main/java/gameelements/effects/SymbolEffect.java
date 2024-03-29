package gameelements.effects;

import gameelements.Inventory;
import gameelements.enums.EffectDelay;
import gameelements.enums.Symbol;

public class SymbolEffect extends Effect {
    private final Symbol symbol;
    private final int nb;

    public SymbolEffect(Symbol symbol, int nb) {
        super(EffectDelay.INSTANTANEOUS);
        this.symbol = symbol;
        this.nb = nb;
    }

    @Override
    public void activateEffect(Inventory inv, Inventory leftNeighborInv, Inventory rightNeighborInv, boolean isEndGame) {
        if ((!isEndGame) && (getDelay() == EffectDelay.END_OF_THE_GAME)) {
            inv.addEndGameEffect(this);
            return;
        }
        inv.getAvailableSymbols()[symbol.getIndex()] += nb;
    }

    @Override
    public int getConstantlyAddedItem() {
        return nb;
    }

    public Symbol getSymbol() {
        return symbol;
    }
}
