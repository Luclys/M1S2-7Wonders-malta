package gameelements.enums;

public enum Neighbor {
    LEFT(0),
    RIGHT(1),
    BOTH(2);

    private final int index;

    public int getIndex() {
        return index;
    }

    Neighbor(int index) {
        this.index = index;
    }
}