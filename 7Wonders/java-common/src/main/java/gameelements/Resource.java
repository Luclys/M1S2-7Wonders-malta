package gameelements;

public enum Resource {
    ARGILE(0),
    PIERRE(1),
    MINERAI(2),
    BOIS(3),
    VERRE(4),
    TISSU(5),
    PAPYRUS(6),
    BOUCLIER(7),
    COMPAS(8),
    ROUAGE(9),
    STELE(10),
    ARGENT(12);

    private final int index;


    Resource(int index) {
        this.index = index;
    }


    public int getIndex() {
        return index;
    }
}
