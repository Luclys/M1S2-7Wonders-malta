package gameelements.enums;

public enum Symbol {
    BOUCLIER(0),
    COMPAS(1),
    ROUAGE(2),
    STELE(3);
    private final int index;


    Symbol(int index) {
        this.index = index;
    }


    public int getIndex() {
        return index;
    }
}
