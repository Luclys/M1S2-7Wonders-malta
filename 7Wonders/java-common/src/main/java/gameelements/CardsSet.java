package gameelements;

import gameelements.effects.*;
import gameelements.enums.Category;
import gameelements.enums.Neighbor;
import gameelements.enums.Resource;
import gameelements.enums.Symbol;

public class CardsSet {
    //=========================================================================MATIERE_PREMIERE(Brown)=========================================================================
    //Age I
    public final static Card CHANTIER = new Card("CHANTIER", new ResourceEffect(Resource.BOIS, 1), null, Category.MATIERE_PREMIERE);
    public final static Card CAVITÉ = new Card("CAVITÉ", new ResourceEffect(Resource.PIERRE, 1), null, Category.MATIERE_PREMIERE);
    public final static Card BASSIN_ARGILEUX = new Card("BASSIN ARGILEUX", new ResourceEffect(Resource.ARGILE, 1), null, Category.MATIERE_PREMIERE);
    public final static Card FILON = new Card("FILON", new ResourceEffect(Resource.MINERAI, 1), null, Category.MATIERE_PREMIERE);
    public final static Card FRICHE = new Card("FRICHE", new ChoiceResourceEffect(new Resource[]{Resource.PIERRE, Resource.BOIS}, 1), null, Category.MATIERE_PREMIERE, 1);

    public final static Card EXCAVATION = new Card("EXCAVATION", new ChoiceResourceEffect(new Resource[]{Resource.PIERRE, Resource.ARGILE}, 1), null, Category.MATIERE_PREMIERE, 1);
    public final static Card FOSSE_ARGILEUSE = new Card("FOSSE ARGILEUSE", new ChoiceResourceEffect(new Resource[]{Resource.ARGILE, Resource.MINERAI}, 1), null, Category.MATIERE_PREMIERE, 1);
    public final static Card EXPLOITATION_FORESTIÈRE = new Card("EXPLOITATION FORESTIÈRE", new ChoiceResourceEffect(new Resource[]{Resource.PIERRE, Resource.BOIS}, 1), null, Category.MATIERE_PREMIERE, 1);
    public final static Card GISEMENT = new Card("GISEMENT", new ChoiceResourceEffect(new Resource[]{Resource.BOIS, Resource.MINERAI}, 1), null, Category.MATIERE_PREMIERE, 1);
    public final static Card MINE = new Card("MINE", new ChoiceResourceEffect(new Resource[]{Resource.MINERAI, Resource.PIERRE}, 1), null, Category.MATIERE_PREMIERE, 1);

    //Age II
    public final static Card SCIERIE = new Card("SCIERIE", new ResourceEffect(Resource.BOIS, 2), null, Category.MATIERE_PREMIERE, 1);
    public final static Card CARRIÈRE = new Card("CARRIÈRE", new ResourceEffect(Resource.PIERRE, 2), null, Category.MATIERE_PREMIERE, 1);
    public final static Card BRIQUETERIE = new Card("BRIQUETERIE", new ResourceEffect(Resource.ARGILE, 2), null, Category.MATIERE_PREMIERE, 1);
    public final static Card FONDERIE = new Card("FONDERIE", new ResourceEffect(Resource.MINERAI, 2), null, Category.MATIERE_PREMIERE, 1);

    //=========================================================================PRODUIT_MANUFACTURE(Gray)=========================================================================
    public final static Card MÉTIER_A_TISSER = new Card("MÉTIER À TISSER", new ResourceEffect(Resource.TISSU, 1), null, Category.PRODUIT_MANUFACTURE);
    public final static Card VERRERIE = new Card("VERRERIE", new ResourceEffect(Resource.VERRE, 1), null, Category.PRODUIT_MANUFACTURE);
    public final static Card PRESSE = new Card("PRESSE", new ResourceEffect(Resource.PAPYRUS, 1), null, Category.PRODUIT_MANUFACTURE);

    //=========================================================================BATIMENT_CIVIL(Blue)=========================================================================
    //Age I
    public final static Card PRÊTEUR_SUR_GAGES = new Card("PRÊTEUR SUR GAGES", new ScoreEffect(3), null, Category.BATIMENT_CIVIL);
    public final static Card BAINS = new Card("BAINS", new ScoreEffect(3), new Resource[]{Resource.PIERRE}, Category.BATIMENT_CIVIL);
    public final static Card AUTEL = new Card("AUTEL", new ScoreEffect(2), null, Category.BATIMENT_CIVIL);
    public final static Card THÉÂTRE = new Card("THÉÂTRE", new ScoreEffect(2), null, Category.BATIMENT_CIVIL);

    //Age II
    public final static Card AQUEDUC = new Card("AQUEDUC", new ScoreEffect(5), new Resource[]{Resource.PIERRE, Resource.PIERRE, Resource.PIERRE}, Category.BATIMENT_CIVIL);
    public final static Card TEMPLE = new Card("TEMPLE", new ScoreEffect(3), new Resource[]{Resource.BOIS, Resource.ARGILE, Resource.VERRE}, Category.BATIMENT_CIVIL);
    public final static Card STATUE = new Card("STATUE", new ScoreEffect(4), new Resource[]{Resource.BOIS, Resource.PIERRE, Resource.PIERRE}, Category.BATIMENT_CIVIL);
    public final static Card TRIBUNAL = new Card("TRIBUNAL", new ScoreEffect(4), new Resource[]{Resource.ARGILE, Resource.ARGILE, Resource.TISSU}, Category.BATIMENT_CIVIL);

    //Age III
    public final static Card PANTHÉON = new Card("PANTHÉON", new ScoreEffect(7), new Resource[]{Resource.ARGILE, Resource.ARGILE, Resource.PIERRE, Resource.PAPYRUS, Resource.TISSU, Resource.VERRE}, Category.BATIMENT_CIVIL);
    public final static Card JARDINS = new Card("JARDINS", new ScoreEffect(5), new Resource[]{Resource.BOIS, Resource.ARGILE, Resource.ARGILE}, Category.BATIMENT_CIVIL);
    public final static Card HÔTEL_DE_VILLE = new Card("HÔTEL DE VILLE", new ScoreEffect(6), new Resource[]{Resource.VERRE, Resource.MINERAI, Resource.PIERRE, Resource.PIERRE}, Category.BATIMENT_CIVIL);
    public final static Card PALACE = new Card("PALACE", new ScoreEffect(8), new Resource[]{Resource.VERRE, Resource.PAPYRUS, Resource.TISSU, Resource.ARGILE, Resource.BOIS, Resource.MINERAI, Resource.PIERRE}, Category.BATIMENT_CIVIL);
    public final static Card SÉNAT = new Card("SÉNAT", new ScoreEffect(8), new Resource[]{Resource.MINERAI, Resource.PIERRE, Resource.BOIS, Resource.BOIS}, Category.BATIMENT_CIVIL);

    //=========================================================================BATIMENT_COMMERCIEAU(Yellow)=========================================================================
    //Age I
    public final static Card TAVERNE = new Card("TAVERNE", new CoinEffect(5), null, gameelements.enums.Category.BATIMENT_COMMERCIEAU);
    public final static Card COMPTOIR_EST = new Card("COMPTOIR EST", new ReductCommerceEffect(Neighbor.RIGHT, true), null, Category.BATIMENT_COMMERCIEAU);
    public final static Card COMPTOIR_OUEST = new Card("COMPTOIR OUEST", new ReductCommerceEffect(Neighbor.LEFT, true), null, Category.BATIMENT_COMMERCIEAU);
    public final static Card MARCHÉ = new Card("MARCHÉ", new ReductCommerceEffect(Neighbor.BOTH, false), null, Category.BATIMENT_COMMERCIEAU);

    //Age II
    public final static Card FORUM = new Card("FORUM", new ChoiceAllTypeResourceEffect(false), new Resource[]{Resource.ARGILE, Resource.ARGILE}, Category.BATIMENT_COMMERCIEAU);
    public final static Card CARAVANSÉRAIL = new Card("CARAVANSÉRAIL", new ChoiceAllTypeResourceEffect(true), new Resource[]{Resource.BOIS, Resource.BOIS}, Category.BATIMENT_COMMERCIEAU);
    public final static Card VIGNOBLE = new Card("VIGNOBLE", new CoinsForOwnAndNeighborsCardsEffect(1, Category.MATIERE_PREMIERE), null, Category.BATIMENT_COMMERCIEAU);
    public final static Card BAZAR = new Card("BAZAR", new CoinsForOwnAndNeighborsCardsEffect(1, Category.PRODUIT_MANUFACTURE), null, Category.BATIMENT_COMMERCIEAU);

    //Age III
    public final static Card PORT = new Card("PORT", new Effect[]{new ScoreForCategoryEffet(1, Category.MATIERE_PREMIERE), new CoinsForCategoryEffet(1, Category.MATIERE_PREMIERE)}, new Resource[]{Resource.TISSU, Resource.MINERAI, Resource.BOIS}, Category.BATIMENT_COMMERCIEAU);
    public final static Card PHARE = new Card("PHARE", new Effect[]{new ScoreForCategoryEffet(1, Category.BATIMENT_COMMERCIEAU), new CoinsForCategoryEffet(1, Category.BATIMENT_COMMERCIEAU)}, new Resource[]{Resource.VERRE, Resource.PIERRE}, Category.BATIMENT_COMMERCIEAU);
    public final static Card CHAMBRE_DE_COMMERCE = new Card("CHAMBRE DE COMMERCE", new Effect[]{new ScoreForCategoryEffet(2, Category.PRODUIT_MANUFACTURE), new CoinsForCategoryEffet(2, Category.PRODUIT_MANUFACTURE)}, new Resource[]{Resource.ARGILE, Resource.ARGILE, Resource.PAPYRUS}, Category.BATIMENT_COMMERCIEAU);
    public final static Card ARÈNE = new Card("ARÈNE", new Effect[]{new ScoreForMerveilleEffect(1), new CoinsForMerveilleEffect(3)}, new Resource[]{Resource.MINERAI, Resource.PIERRE, Resource.PIERRE}, Category.BATIMENT_COMMERCIEAU);

    //=========================================================================BATIMENT_MILITAIRE(Red)=========================================================================
    //Age I
    public final static Card PALISSADE = new Card("PALISSADE", new SymbolEffect(Symbol.BOUCLIER, 1), new Resource[]{Resource.BOIS}, Category.BATIMENT_MILITAIRE);
    public final static Card CASERNE = new Card("CASERNE", new SymbolEffect(Symbol.BOUCLIER, 1), new Resource[]{Resource.MINERAI}, Category.BATIMENT_MILITAIRE);
    public final static Card TOUR_DE_GARDE = new Card("TOUR DE GARDE", new SymbolEffect(Symbol.BOUCLIER, 1), new Resource[]{Resource.ARGILE}, Category.BATIMENT_MILITAIRE);

    //Age II
    public final static Card MURAILLE = new Card("MURAILLE", new SymbolEffect(Symbol.BOUCLIER, 2), new Resource[]{Resource.PIERRE, Resource.PIERRE, Resource.PIERRE}, Category.BATIMENT_MILITAIRE);
    public final static Card PLACE_D_ARMES = new Card("PLACE D’ARMES", new SymbolEffect(Symbol.BOUCLIER, 2), new Resource[]{Resource.BOIS, Resource.MINERAI, Resource.MINERAI}, Category.BATIMENT_MILITAIRE);
    public final static Card ÉCURIES = new Card("ÉCURIES", new SymbolEffect(Symbol.BOUCLIER, 2), new Resource[]{Resource.MINERAI, Resource.ARGILE, Resource.BOIS}, Category.BATIMENT_MILITAIRE);
    public final static Card CHAMPS_DE_TIR = new Card("CHAMPS DE TIR", new SymbolEffect(Symbol.BOUCLIER, 2), new Resource[]{Resource.BOIS, Resource.BOIS, Resource.MINERAI}, Category.BATIMENT_MILITAIRE);

    //Age III
    public final static Card FORTIFICATIONS = new Card("FORTIFICATIONS", new SymbolEffect(Symbol.BOUCLIER, 3), new Resource[]{Resource.PIERRE, Resource.MINERAI, Resource.MINERAI, Resource.MINERAI}, Category.BATIMENT_MILITAIRE);
    public final static Card CIRQUE = new Card("FORTIFICATIONS", new SymbolEffect(Symbol.BOUCLIER, 3), new Resource[]{Resource.PIERRE, Resource.PIERRE, Resource.PIERRE, Resource.MINERAI}, Category.BATIMENT_MILITAIRE);
    public final static Card ARSENAL = new Card("ARSENAL", new SymbolEffect(Symbol.BOUCLIER, 3), new Resource[]{Resource.MINERAI, Resource.BOIS, Resource.BOIS, Resource.TISSU}, Category.BATIMENT_MILITAIRE);
    public final static Card ATELIER_DE_SIÈGE = new Card("ATELIER_DE_SIÈGE", new SymbolEffect(Symbol.BOUCLIER, 3), new Resource[]{Resource.BOIS, Resource.ARGILE, Resource.ARGILE, Resource.ARGILE}, Category.BATIMENT_MILITAIRE);

    //=========================================================================BATIMENT_SCIENTIFIQUE(Green)=========================================================================
    //Age I
    public final static Card OFFICINE = new Card("OFFICINE", new SymbolEffect(Symbol.COMPAS, 1), new Resource[]{Resource.TISSU}, Category.BATIMENT_SCIENTIFIQUE);
    public final static Card ATELIER = new Card("ATELIER", new SymbolEffect(Symbol.ROUAGE, 1), new Resource[]{Resource.VERRE}, Category.BATIMENT_SCIENTIFIQUE);
    public final static Card SCRIPTORIUM = new Card("SCRIPTORIUM", new SymbolEffect(Symbol.STELE, 1), new Resource[]{Resource.PAPYRUS}, Category.BATIMENT_SCIENTIFIQUE);

    //Age II
    public final static Card DISPENSAIRE = new Card("DISPENSAIRE", new SymbolEffect(Symbol.COMPAS, 1), new Resource[]{Resource.MINERAI, Resource.MINERAI, Resource.VERRE}, Category.BATIMENT_SCIENTIFIQUE);
    public final static Card LABORATOIRE = new Card("LABORATOIRE", new SymbolEffect(Symbol.ROUAGE, 1), new Resource[]{Resource.ARGILE, Resource.ARGILE, Resource.PAPYRUS}, Category.BATIMENT_SCIENTIFIQUE);
    public final static Card BIBLIOTHÈQUE = new Card("BIBLIOTHÈQUE", new SymbolEffect(Symbol.STELE, 1), new Resource[]{Resource.PIERRE, Resource.PIERRE, Resource.TISSU}, Category.BATIMENT_SCIENTIFIQUE);
    public final static Card ÉCOLE = new Card("ÉCOLE", new SymbolEffect(Symbol.STELE, 1), new Resource[]{Resource.BOIS, Resource.PAPYRUS}, Category.BATIMENT_SCIENTIFIQUE);

    //Age III
    public final static Card LOGE = new Card("LOGE", new SymbolEffect(Symbol.COMPAS, 1), new Resource[]{Resource.ARGILE, Resource.ARGILE, Resource.TISSU, Resource.PAPYRUS}, Category.BATIMENT_SCIENTIFIQUE);
    public final static Card OBSERVATOIRE = new Card("OBSERVATOIRE", new SymbolEffect(Symbol.ROUAGE, 1), new Resource[]{Resource.MINERAI, Resource.MINERAI, Resource.VERRE, Resource.TISSU}, Category.BATIMENT_SCIENTIFIQUE);
    public final static Card UNIVERSITÉ = new Card("UNIVERSITÉ", new SymbolEffect(Symbol.STELE, 1), new Resource[]{Resource.BOIS, Resource.BOIS, Resource.PAPYRUS, Resource.VERRE}, Category.BATIMENT_SCIENTIFIQUE);
    public final static Card ACADEMIE = new Card("ACADEMIE", new SymbolEffect(Symbol.COMPAS, 1), new Resource[]{Resource.PIERRE, Resource.PIERRE, Resource.PIERRE, Resource.VERRE}, Category.BATIMENT_SCIENTIFIQUE);
    public final static Card ÉTUDE = new Card("ÉTUDE", new SymbolEffect(Symbol.ROUAGE, 1), new Resource[]{Resource.BOIS, Resource.PAPYRUS, Resource.TISSU}, Category.BATIMENT_SCIENTIFIQUE);

    //=========================================================================GUILDE(Purple)=========================================================================
    //Age III
    public final static Card GUILDE_DES_TRAVAILLEURS = new Card("GUILDE DES TRAVAILLEURS", new ScoreForNeighborsCardsEffect(1, Category.MATIERE_PREMIERE), new Resource[]{Resource.MINERAI, Resource.MINERAI, Resource.ARGILE, Resource.PIERRE, Resource.BOIS}, Category.GUILDE);
    public final static Card GUILDE_DES_ARTISANS = new Card("GUILDE DES ARTISANS", new ScoreForNeighborsCardsEffect(2, Category.PRODUIT_MANUFACTURE), new Resource[]{Resource.MINERAI, Resource.MINERAI, Resource.PIERRE, Resource.PIERRE}, Category.GUILDE);
    public final static Card GUILDE_DES_COMMERÇANTS = new Card("GUILDE DES COMMERÇANTS", new ScoreForNeighborsCardsEffect(1, Category.BATIMENT_COMMERCIEAU), new Resource[]{Resource.TISSU, Resource.PAPYRUS, Resource.VERRE}, Category.GUILDE);
    public final static Card GUILDE_DES_PHILOSOPHES = new Card("GUILDE DES PHILOSOPHES", new ScoreForNeighborsCardsEffect(1, Category.BATIMENT_SCIENTIFIQUE), new Resource[]{Resource.ARGILE, Resource.ARGILE, Resource.ARGILE, Resource.TISSU, Resource.PAPYRUS}, Category.GUILDE);
    public final static Card GUILDE_DES_ESPIONS = new Card("GUILDE DES ESPIONS", new ScoreForNeighborsCardsEffect(1, Category.BATIMENT_MILITAIRE), new Resource[]{Resource.ARGILE, Resource.ARGILE, Resource.ARGILE, Resource.VERRE}, Category.GUILDE);
    public final static Card GUILDE_DES_STRATÈGES = new Card("GUILDE DES STRATÈGES", new ScoreForNeighborsDefeatJetonsEffet(1), new Resource[]{Resource.MINERAI, Resource.MINERAI, Resource.PIERRE, Resource.TISSU}, Category.GUILDE);
    public final static Card GUILDE_DES_ARMATEURS = new Card("GUILDE DES ARMATEURS", new ScoreForCategoriesEffet(1), new Resource[]{Resource.BOIS, Resource.BOIS, Resource.BOIS, Resource.PAPYRUS, Resource.VERRE}, Category.GUILDE);
    public final static Card GUILDE_DES_SCIENTIFIQUES = new Card("GUILDE DESSCIENTIFIQUES", new ChoiceScientificEffect(), new Resource[]{Resource.BOIS, Resource.BOIS, Resource.MINERAI, Resource.MINERAI, Resource.PAPYRUS}, Category.GUILDE);
    public final static Card GUILDE_DES_MAGISTRATS = new Card("GUILDE DES MAGISTRATS", new ScoreForNeighborsCardsEffect(1, Category.BATIMENT_CIVIL), new Resource[]{Resource.BOIS, Resource.BOIS, Resource.BOIS, Resource.PIERRE, Resource.TISSU}, Category.GUILDE);
    public final static Card GUILDE_DES_BÂTISSEURS = new Card("GUILDE DES BÂTISSEURS", new ScoreForOwnAndNeighborMerveillesEffet(1), new Resource[]{Resource.PIERRE, Resource.PIERRE, Resource.ARGILE, Resource.ARGILE, Resource.VERRE}, Category.GUILDE);
}
