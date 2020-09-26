package gameelements;

public enum Resource {
    ARGILE(0),
    PIERRE(1),
    MINERAI(2),
    BOIS(3);

    private final int index;


    Resource(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
