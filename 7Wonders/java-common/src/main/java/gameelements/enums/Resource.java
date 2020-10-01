package gameelements.enums;

public enum Resource {
    ARGILE(0),
    PIERRE(1),
    MINERAI(2),
    BOIS(3),
    VERRE(4),
    TISSU(5),
    PAPYRUS(6),
    ARGENT(12);

    private final int index;


    Resource(int index) {
        this.index = index;
    }


    public int getIndex() {
        return index;
    }
}
