package gameelements.enums;

public enum Category {
    MATIERE_PREMIERE(0),
    PRODUIT_MANUFACTURE(1),
    BATIMENT_CIVIL(2),
    BATIMENT_SCIENTIFIQUE(3),
    BATIMENT_COMMERCIAL(4),
    BATIMENT_MILITAIRE(5),
    GUILDE(6);

    private final int index;

    Category(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}