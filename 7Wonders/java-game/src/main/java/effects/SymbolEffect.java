package effects;

import gameelements.Effect;
import gameelements.Inventory;
import gameelements.enums.Symbol;

public class SymbolEffect extends Effect {
    Symbol symbol;
    int nb;

    public SymbolEffect(String name, Symbol symbol, int nb) {
        super(name);
        // Instantaneous and only once
        this.symbol = symbol;
        this.nb = nb;
    }

    public void activateEffect(Inventory inv) {
        super.activateEffect(inv);
        inv.getAvailableSymbols()[symbol.getIndex()] += nb;
        changeStatus();
    }
}
