package effects;

import gameelements.Effect;
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

    public void activateEffect() {
        super.activateEffect();
        // Request the add of nb resource to the player
        //collectedSymbols[symbol.getIndex()]++;
        changeStatus();
    }
}
