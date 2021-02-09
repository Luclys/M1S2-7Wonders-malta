package gameelements.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ResourceTest {

    @Test
    void getCategoryTest() {
        for (Resource r : Resource.values()) {
            if (r.equals(Resource.ARGILE) || r.equals(Resource.PIERRE) || r.equals(Resource.MINERAI) || r.equals(Resource.BOIS)) {
                assertEquals(Category.MATIERE_PREMIERE, r.getCategory());
            } else {
                assertEquals(Category.PRODUIT_MANUFACTURE, r.getCategory());
            }
        }
    }
}
