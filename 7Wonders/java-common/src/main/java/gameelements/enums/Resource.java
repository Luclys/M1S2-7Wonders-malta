package gameelements.enums;

public enum Resource {
    ARGILE(0),
    PIERRE(1),
    MINERAI(2),
    BOIS(3),
    VERRE(4),
    TISSU(5),
    PAPYRUS(6);
    private final int index;

    Resource(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public Category getCategory() {
        if (this.equals(ARGILE) || this.equals(PIERRE) || this.equals(MINERAI) || this.equals(BOIS)) {
            return Category.MATIERE_PREMIERE;
        } else {
            return Category.PRODUIT_MANUFACTURE;
        }
    }
}
