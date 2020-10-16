package gameelements.cards;

import gameelements.Effect;
import gameelements.effects.*;
import gameelements.enums.Category;
import gameelements.enums.Neighbor;
import gameelements.enums.Resource;
import gameelements.enums.Symbol;

public class CardsSet {
    //=========================================================================AGE I=========================================================================
    //MATIERE_PREMIERE(Brown)
    public static final Card CHANTIER = new Card(CardIds.CHANTIER_ID, "CHANTIER", new ResourceEffect(Resource.BOIS, 1), null, Category.MATIERE_PREMIERE);
    public static final Card CAVITE = new Card(CardIds.CAVITE_ID, "CAVITE", new ResourceEffect(Resource.PIERRE, 1), null, Category.MATIERE_PREMIERE);
    public static final Card BASSIN_ARGILEUX = new Card(CardIds.BASSIN_ARGILEUX_ID, "BASSIN ARGILEUX", new ResourceEffect(Resource.ARGILE, 1), null, Category.MATIERE_PREMIERE);
    public static final Card FILON = new Card(CardIds.FILON_ID, "FILON", new ResourceEffect(Resource.MINERAI, 1), null, Category.MATIERE_PREMIERE);
    public static final Card FRICHE = new Card(CardIds.FRICHE_ID, "FRICHE", new ChoiceResourceEffect(new Resource[]{Resource.PIERRE, Resource.BOIS}, 1), null, Category.MATIERE_PREMIERE, 1);

    public static final Card EXCAVATION = new Card(CardIds.EXCAVATION_ID, "EXCAVATION", new ChoiceResourceEffect(new Resource[]{Resource.PIERRE, Resource.ARGILE}, 1), null, Category.MATIERE_PREMIERE, 1);
    public static final Card FOSSE_ARGILEUSE = new Card(CardIds.FOSSE_ARGILEUSE_ID, "FOSSE ARGILEUSE", new ChoiceResourceEffect(new Resource[]{Resource.ARGILE, Resource.MINERAI}, 1), null, Category.MATIERE_PREMIERE, 1);
    public static final Card EXPLOITATION_FORESTIERE = new Card(CardIds.EXPLOITATION_FORESTIERE_ID, "EXPLOITATION FORESTIERE", new ChoiceResourceEffect(new Resource[]{Resource.PIERRE, Resource.BOIS}, 1), null, Category.MATIERE_PREMIERE, 1);
    public static final Card GISEMENT = new Card(CardIds.GISEMENT_ID, "GISEMENT", new ChoiceResourceEffect(new Resource[]{Resource.BOIS, Resource.MINERAI}, 1), null, Category.MATIERE_PREMIERE, 1);
    public static final Card MINE = new Card(CardIds.MINE_ID, "MINE", new ChoiceResourceEffect(new Resource[]{Resource.MINERAI, Resource.PIERRE}, 1), null, Category.MATIERE_PREMIERE, 1);

    //PRODUIT_MANUFACTURE(Gray)
    public static final Card METIER_A_TISSER = new Card(CardIds.METIER_A_TISSER_ID, "METIER À TISSER", new ResourceEffect(Resource.TISSU, 1), null, Category.PRODUIT_MANUFACTURE);
    public static final Card VERRERIE = new Card(CardIds.VERRERIE_ID, "VERRERIE", new ResourceEffect(Resource.VERRE, 1), null, Category.PRODUIT_MANUFACTURE);
    public static final Card PRESSE = new Card(CardIds.PRESSE_ID, "PRESSE", new ResourceEffect(Resource.PAPYRUS, 1), null, Category.PRODUIT_MANUFACTURE);

    //BATIMENT_CIVIL(Blue)
    public static final Card PRETEUR_SUR_GAGES = new Card(CardIds.PRETEUR_SUR_GAGES_ID, "PRETEUR SUR GAGES", new ScoreEffect(3), null, Category.BATIMENT_CIVIL, null, new int []{CardIds.STATUE_ID});
    public static final Card BAINS = new Card(CardIds.BAINS_ID,  "BAINS", new ScoreEffect(3), new Resource[]{Resource.PIERRE}, Category.BATIMENT_CIVIL, null, new int[]{CardIds.AQUEDUC_ID});
    public static final Card AUTEL = new Card(CardIds.AUTEL_ID, "AUTEL", new ScoreEffect(2), null, Category.BATIMENT_CIVIL, null, new int []{CardIds.PANTHEON_ID});
    public static final Card THEATRE = new Card(CardIds.THEATRE_ID, "THEATRE", new ScoreEffect(2), null, Category.BATIMENT_CIVIL, null, new int[]{CardIds.JARDINS_ID});

    //BATIMENT_COMMERCIEAU(Yellow)
    public static final Card TAVERNE = new Card(CardIds.TAVERNE_ID, "TAVERNE", new CoinEffect(5), null, gameelements.enums.Category.BATIMENT_COMMERCIAL);
    public static final Card COMPTOIR_EST = new Card(CardIds.COMPTOIR_OUEST_ID, "COMPTOIR EST", new ReductCommerceEffect(Neighbor.RIGHT, true), null, Category.BATIMENT_COMMERCIAL, null, new int[]{CardIds.FORUM_ID});
    public static final Card COMPTOIR_OUEST = new Card(CardIds.COMPTOIR_EST_ID, "COMPTOIR OUEST", new ReductCommerceEffect(Neighbor.LEFT, true), null, Category.BATIMENT_COMMERCIAL,null, new int[]{CardIds.FORUM_ID});
    public static final Card MARCHE = new Card(CardIds.MARCHE_ID, "MARCHE", new ReductCommerceEffect(Neighbor.BOTH, false), null, Category.BATIMENT_COMMERCIAL, null, new int[]{CardIds.CARAVANSERAIL_ID});

    //BATIMENT_SCIENTIFIQUE(Green)
    public static final Card OFFICINE = new Card(CardIds.OFFICINE_ID, "OFFICINE", new SymbolEffect(Symbol.COMPAS, 1), new Resource[]{Resource.TISSU}, Category.BATIMENT_SCIENTIFIQUE, null, new int[]{CardIds.ECURIES_ID, CardIds.DISPENSAIRE_ID});
    public static final Card ATELIER = new Card(CardIds.ATELIER_ID, "ATELIER", new SymbolEffect(Symbol.ROUAGE, 1), new Resource[]{Resource.VERRE}, Category.BATIMENT_SCIENTIFIQUE, null, new int[]{CardIds.CHAMPS_DE_TIR_ID, CardIds.LABORATOIRE_ID});
    public static final Card SCRIPTORIUM = new Card(CardIds.SCRIPTORIUM_ID, "SCRIPTORIUM", new SymbolEffect(Symbol.STELE, 1), new Resource[]{Resource.PAPYRUS}, Category.BATIMENT_SCIENTIFIQUE, null, new int[]{CardIds.TRIBUNAL_ID, CardIds.BIBLIOTHEQUE_ID});

    //BATIMENT_MILITAIRE(Red)
    public static final Card PALISSADE = new Card(CardIds.PALISSADE_ID, "PALISSADE", new SymbolEffect(Symbol.BOUCLIER, 1), new Resource[]{Resource.BOIS}, Category.BATIMENT_MILITAIRE);
    public static final Card CASERNE = new Card(CardIds.CASERNE_ID, "CASERNE", new SymbolEffect(Symbol.BOUCLIER, 1), new Resource[]{Resource.MINERAI}, Category.BATIMENT_MILITAIRE);
    public static final Card TOUR_DE_GARDE = new Card(CardIds.TOUR_DE_GARDE_ID, "TOUR DE GARDE", new SymbolEffect(Symbol.BOUCLIER, 1), new Resource[]{Resource.ARGILE}, Category.BATIMENT_MILITAIRE);

    //=========================================================================AGE II=========================================================================
    //MATIERE_PREMIERE(Brown)
    public static final Card SCIERIE = new Card(CardIds.SCIERIE_ID, "SCIERIE", new ResourceEffect(Resource.BOIS, 2), null, Category.MATIERE_PREMIERE, 1);
    public static final Card CARRIERE = new Card(CardIds.CARRIERE_ID,"CARRIERE", new ResourceEffect(Resource.PIERRE, 2), null, Category.MATIERE_PREMIERE, 1);
    public static final Card BRIQUETERIE = new Card(CardIds.BRIQUETERIE_ID,"BRIQUETERIE", new ResourceEffect(Resource.ARGILE, 2), null, Category.MATIERE_PREMIERE, 1);
    public static final Card FONDERIE = new Card(CardIds.FONDERIE_ID,"FONDERIE", new ResourceEffect(Resource.MINERAI, 2), null, Category.MATIERE_PREMIERE, 1);

    //BATIMENT_CIVIL(Blue)
    public static final Card AQUEDUC = new Card(CardIds.AQUEDUC_ID, "AQUEDUC", new ScoreEffect(5), new Resource[]{Resource.PIERRE, Resource.PIERRE, Resource.PIERRE}, Category.BATIMENT_CIVIL, new int[]{CardIds.BAINS_ID}, null);
    public static final Card TEMPLE = new Card(CardIds.TEMPLE_ID, "TEMPLE", new ScoreEffect(3), new Resource[]{Resource.BOIS, Resource.ARGILE, Resource.VERRE}, Category.BATIMENT_CIVIL);
    public static final Card STATUE = new Card(CardIds.STATUE_ID, "STATUE", new ScoreEffect(4), new Resource[]{Resource.BOIS, Resource.PIERRE, Resource.PIERRE}, Category.BATIMENT_CIVIL, new int[]{CardIds.PRETEUR_SUR_GAGES_ID}, null);
    public static final Card TRIBUNAL = new Card(CardIds.TRIBUNAL_ID, "TRIBUNAL", new ScoreEffect(4), new Resource[]{Resource.ARGILE, Resource.ARGILE, Resource.TISSU}, Category.BATIMENT_CIVIL, new int[]{CardIds.SCRIPTORIUM_ID}, null);

    //BATIMENT_COMMERCIEAU(Yellow)
    public static final Card FORUM = new Card(CardIds.FORUM_ID, "FORUM", new ChoiceAllTypeResourceEffect(false), new Resource[]{Resource.ARGILE, Resource.ARGILE}, Category.BATIMENT_COMMERCIAL, new int[]{CardIds.COMPTOIR_EST_ID, CardIds.COMPTOIR_OUEST_ID}, new int[]{CardIds.PORT_ID});
    public static final Card CARAVANSERAIL = new Card(CardIds.CARAVANSERAIL_ID, "CARAVANSERAIL", new ChoiceAllTypeResourceEffect(true), new Resource[]{Resource.BOIS, Resource.BOIS}, Category.BATIMENT_COMMERCIAL, new int[]{CardIds.MARCHE_ID}, new int[]{CardIds.PHARE_ID});
    public static final Card VIGNOBLE = new Card(CardIds.VIGNOBLE_ID, "VIGNOBLE", new CoinsForOwnAndNeighborsCardsEffect(1, Category.MATIERE_PREMIERE), null, Category.BATIMENT_COMMERCIAL);
    public static final Card BAZAR = new Card(CardIds.BAZAR_ID, "BAZAR", new CoinsForOwnAndNeighborsCardsEffect(1, Category.PRODUIT_MANUFACTURE), null, Category.BATIMENT_COMMERCIAL);

    //BATIMENT_SCIENTIFIQUE(Green)
    public static final Card DISPENSAIRE = new Card(CardIds.DISPENSAIRE_ID, "DISPENSAIRE", new SymbolEffect(Symbol.COMPAS, 1), new Resource[]{Resource.MINERAI, Resource.MINERAI, Resource.VERRE}, Category.BATIMENT_SCIENTIFIQUE, new int[]{CardIds.OFFICINE_ID}, new int[]{CardIds.ARENE_ID, CardIds.LOGE_ID});
    public static final Card LABORATOIRE = new Card(CardIds.LABORATOIRE_ID, "LABORATOIRE", new SymbolEffect(Symbol.ROUAGE, 1), new Resource[]{Resource.ARGILE, Resource.ARGILE, Resource.PAPYRUS}, Category.BATIMENT_SCIENTIFIQUE, new int[]{CardIds.ATELIER_ID}, new int[]{CardIds.ATELIER_DE_SIEGE_ID, CardIds.OBSERVATOIRE_ID});
    public static final Card BIBLIOTHEQUE = new Card(CardIds.BIBLIOTHEQUE_ID, "BIBLIOTHEQUE", new SymbolEffect(Symbol.STELE, 1), new Resource[]{Resource.PIERRE, Resource.PIERRE, Resource.TISSU}, Category.BATIMENT_SCIENTIFIQUE, new int[]{CardIds.SCRIPTORIUM_ID}, new int[]{CardIds.SENAT_ID, CardIds.UNIVERSITE_ID});
    public static final Card ECOLE = new Card(CardIds.ECOLE_ID, "ECOLE", new SymbolEffect(Symbol.STELE, 1), new Resource[]{Resource.BOIS, Resource.PAPYRUS}, Category.BATIMENT_SCIENTIFIQUE, null, new int[]{CardIds.ACADEMIE_ID});

    //BATIMENT_MILITAIRE(Red)
    public static final Card MURAILLE = new Card(CardIds.MURAILLE_ID, "MURAILLE", new SymbolEffect(Symbol.BOUCLIER, 2), new Resource[]{Resource.PIERRE, Resource.PIERRE, Resource.PIERRE}, Category.BATIMENT_MILITAIRE, null, new int[]{CardIds.FORTIFICATIONS_ID});
    public static final Card PLACE_D_ARMES = new Card(CardIds.PLACE_D_ARMES_ID, "PLACE D’ARMES", new SymbolEffect(Symbol.BOUCLIER, 2), new Resource[]{Resource.BOIS, Resource.MINERAI, Resource.MINERAI}, Category.BATIMENT_MILITAIRE, null, new int[]{CardIds.CIRQUE_ID});
    public static final Card ECURIES = new Card(CardIds.ECURIES_ID, "ECURIES", new SymbolEffect(Symbol.BOUCLIER, 2), new Resource[]{Resource.MINERAI, Resource.ARGILE, Resource.BOIS}, Category.BATIMENT_MILITAIRE, new int[]{CardIds.OFFICINE_ID}, null);
    public static final Card CHAMPS_DE_TIR = new Card(CardIds.CHAMPS_DE_TIR_ID, "CHAMPS DE TIR", new SymbolEffect(Symbol.BOUCLIER, 2), new Resource[]{Resource.BOIS, Resource.BOIS, Resource.MINERAI}, Category.BATIMENT_MILITAIRE, new int[]{CardIds.ATELIER_ID}, null);

    //=========================================================================AGE III=========================================================================
    //BATIMENT_CIVIL(Blue)
    public static final Card PANTHEON = new Card(CardIds.PANTHEON_ID, "PANTHEON", new ScoreEffect(7), new Resource[]{Resource.ARGILE, Resource.ARGILE, Resource.PIERRE, Resource.PAPYRUS, Resource.TISSU, Resource.VERRE}, Category.BATIMENT_CIVIL, new int[]{CardIds.AUTEL_ID}, null);
    public static final Card JARDINS = new Card(CardIds.JARDINS_ID, "JARDINS", new ScoreEffect(5), new Resource[]{Resource.BOIS, Resource.ARGILE, Resource.ARGILE}, Category.BATIMENT_CIVIL, new int[]{CardIds.THEATRE_ID}, null);
    public static final Card HOTEL_DE_VILLE = new Card(CardIds.HOTEL_DE_VILLE_ID, "HÔTEL DE VILLE", new ScoreEffect(6), new Resource[]{Resource.VERRE, Resource.MINERAI, Resource.PIERRE, Resource.PIERRE}, Category.BATIMENT_CIVIL);
    public static final Card PALACE = new Card(CardIds.PALACE_ID, "PALACE", new ScoreEffect(8), new Resource[]{Resource.VERRE, Resource.PAPYRUS, Resource.TISSU, Resource.ARGILE, Resource.BOIS, Resource.MINERAI, Resource.PIERRE}, Category.BATIMENT_CIVIL);
    public static final Card SENAT = new Card(CardIds.SENAT_ID, "SENAT", new ScoreEffect(8), new Resource[]{Resource.MINERAI, Resource.PIERRE, Resource.BOIS, Resource.BOIS}, Category.BATIMENT_CIVIL, new int[]{CardIds.BIBLIOTHEQUE_ID}, null);

    //BATIMENT_COMMERCIEAU(Yellow)
    public static final Card PORT = new Card(CardIds.PORT_ID, "PORT", new Effect[]{new ScoreForCategoryEffet(1, Category.MATIERE_PREMIERE), new CoinsForCategoryEffet(1, Category.MATIERE_PREMIERE)}, new Resource[]{Resource.TISSU, Resource.MINERAI, Resource.BOIS}, Category.BATIMENT_COMMERCIAL, new int[CardIds.PORT_ID], null);
    public static final Card PHARE = new Card(CardIds.PHARE_ID, "PHARE", new Effect[]{new ScoreForCategoryEffet(1, Category.BATIMENT_COMMERCIAL), new CoinsForCategoryEffet(1, Category.BATIMENT_COMMERCIAL)}, new Resource[]{Resource.VERRE, Resource.PIERRE}, Category.BATIMENT_COMMERCIAL, new int[]{CardIds.CARAVANSERAIL_ID}, null);
    public static final Card CHAMBRE_DE_COMMERCE = new Card(CardIds.CHAMBRE_DE_COMMERCE_ID, "CHAMBRE DE COMMERCE", new Effect[]{new ScoreForCategoryEffet(2, Category.PRODUIT_MANUFACTURE), new CoinsForCategoryEffet(2, Category.PRODUIT_MANUFACTURE)}, new Resource[]{Resource.ARGILE, Resource.ARGILE, Resource.PAPYRUS}, Category.BATIMENT_COMMERCIAL);
    public static final Card ARENE = new Card(CardIds.ARENE_ID, "ARENE", new Effect[]{new ScoreForMerveilleEffect(1), new CoinsForMerveilleEffect(3)}, new Resource[]{Resource.MINERAI, Resource.PIERRE, Resource.PIERRE}, Category.BATIMENT_COMMERCIAL, new int[]{CardIds.DISPENSAIRE_ID}, null);

    //BATIMENT_SCIENTIFIQUE(Green)
    public static final Card LOGE = new Card(CardIds.LOGE_ID, "LOGE", new SymbolEffect(Symbol.COMPAS, 1), new Resource[]{Resource.ARGILE, Resource.ARGILE, Resource.TISSU, Resource.PAPYRUS}, Category.BATIMENT_SCIENTIFIQUE, new int[]{CardIds.DISPENSAIRE_ID}, null);
    public static final Card OBSERVATOIRE = new Card(CardIds.OBSERVATOIRE_ID, "OBSERVATOIRE", new SymbolEffect(Symbol.ROUAGE, 1), new Resource[]{Resource.MINERAI, Resource.MINERAI, Resource.VERRE, Resource.TISSU}, Category.BATIMENT_SCIENTIFIQUE, new int[]{CardIds.LABORATOIRE_ID}, null);
    public static final Card UNIVERSITE = new Card(CardIds.UNIVERSITE_ID, "UNIVERSITE", new SymbolEffect(Symbol.STELE, 1), new Resource[]{Resource.BOIS, Resource.BOIS, Resource.PAPYRUS, Resource.VERRE}, Category.BATIMENT_SCIENTIFIQUE, new int[]{CardIds.BIBLIOTHEQUE_ID}, null);
    public static final Card ACADEMIE = new Card(CardIds.ACADEMIE_ID, "ACADEMIE", new SymbolEffect(Symbol.COMPAS, 1), new Resource[]{Resource.PIERRE, Resource.PIERRE, Resource.PIERRE, Resource.VERRE}, Category.BATIMENT_SCIENTIFIQUE, new int[]{CardIds.ECOLE_ID}, null);
    public static final Card ETUDE = new Card(CardIds.ETUDE_ID, "ETUDE", new SymbolEffect(Symbol.ROUAGE, 1), new Resource[]{Resource.BOIS, Resource.PAPYRUS, Resource.TISSU}, Category.BATIMENT_SCIENTIFIQUE, new int[]{CardIds.ECOLE_ID}, null);

    //BATIMENT_MILITAIRE(Red)
    public static final Card FORTIFICATIONS = new Card(CardIds.FORTIFICATIONS_ID, "FORTIFICATIONS", new SymbolEffect(Symbol.BOUCLIER, 3), new Resource[]{Resource.PIERRE, Resource.MINERAI, Resource.MINERAI, Resource.MINERAI}, Category.BATIMENT_MILITAIRE, new int[]{CardIds.MURAILLE_ID}, null);
    public static final Card CIRQUE = new Card(CardIds.CIRQUE_ID, "CIRQUE", new SymbolEffect(Symbol.BOUCLIER, 3), new Resource[]{Resource.PIERRE, Resource.PIERRE, Resource.PIERRE, Resource.MINERAI}, Category.BATIMENT_MILITAIRE, new int[]{CardIds.PLACE_D_ARMES_ID}, null);
    public static final Card ARSENAL = new Card(CardIds.ARSENAL_ID, "ARSENAL", new SymbolEffect(Symbol.BOUCLIER, 3), new Resource[]{Resource.MINERAI, Resource.BOIS, Resource.BOIS, Resource.TISSU}, Category.BATIMENT_MILITAIRE);
    public static final Card ATELIER_DE_SIEGE = new Card(CardIds.ATELIER_DE_SIEGE_ID, "ATELIER DE SIEGE", new SymbolEffect(Symbol.BOUCLIER, 3), new Resource[]{Resource.BOIS, Resource.ARGILE, Resource.ARGILE, Resource.ARGILE}, Category.BATIMENT_MILITAIRE, new int[]{CardIds.LABORATOIRE_ID}, null);

    //GUILDE(Purple)
    public static final Card GUILDE_DES_TRAVAILLEURS = new Card(CardIds.GUILDE_DES_TRAVAILLEURS_ID, "GUILDE DES TRAVAILLEURS", new ScoreForNeighborsCardsEffect(1, Category.MATIERE_PREMIERE), new Resource[]{Resource.MINERAI, Resource.MINERAI, Resource.ARGILE, Resource.PIERRE, Resource.BOIS}, Category.GUILDE);
    public static final Card GUILDE_DES_ARTISANS = new Card(CardIds.GUILDE_DES_ARTISANS_ID, "GUILDE DES ARTISANS", new ScoreForNeighborsCardsEffect(2, Category.PRODUIT_MANUFACTURE), new Resource[]{Resource.MINERAI, Resource.MINERAI, Resource.PIERRE, Resource.PIERRE}, Category.GUILDE);
    public static final Card GUILDE_DES_COMMERCANTS = new Card(CardIds.GUILDE_DES_COMMERCANTS_ID, "GUILDE DES COMMERÇANTS", new ScoreForNeighborsCardsEffect(1, Category.BATIMENT_COMMERCIAL), new Resource[]{Resource.TISSU, Resource.PAPYRUS, Resource.VERRE}, Category.GUILDE);
    public static final Card GUILDE_DES_PHILOSOPHES = new Card(CardIds.GUILDE_DES_PHILOSOPHES_ID, "GUILDE DES PHILOSOPHES", new ScoreForNeighborsCardsEffect(1, Category.BATIMENT_SCIENTIFIQUE), new Resource[]{Resource.ARGILE, Resource.ARGILE, Resource.ARGILE, Resource.TISSU, Resource.PAPYRUS}, Category.GUILDE);
    public static final Card GUILDE_DES_ESPIONS = new Card(CardIds.GUILDE_DES_ESPIONS_ID, "GUILDE DES ESPIONS", new ScoreForNeighborsCardsEffect(1, Category.BATIMENT_MILITAIRE), new Resource[]{Resource.ARGILE, Resource.ARGILE, Resource.ARGILE, Resource.VERRE}, Category.GUILDE);
    public static final Card GUILDE_DES_STRATEGES = new Card(CardIds.GUILDE_DES_STRATEGES_ID, "GUILDE DES STRATEGES", new ScoreForNeighborsDefeatJetonsEffet(1), new Resource[]{Resource.MINERAI, Resource.MINERAI, Resource.PIERRE, Resource.TISSU}, Category.GUILDE);
    public static final Card GUILDE_DES_ARMATEURS = new Card(CardIds.GUILDE_DES_ARMATEURS_ID, "GUILDE DES ARMATEURS", new ScoreForCategoriesEffet(1), new Resource[]{Resource.BOIS, Resource.BOIS, Resource.BOIS, Resource.PAPYRUS, Resource.VERRE}, Category.GUILDE);
    public static final Card GUILDE_DES_SCIENTIFIQUES = new Card(CardIds.GUILDE_DES_SCIENTIFIQUES_ID, "GUILDE DES SCIENTIFIQUES", new ChoiceScientificEffect(), new Resource[]{Resource.BOIS, Resource.BOIS, Resource.MINERAI, Resource.MINERAI, Resource.PAPYRUS}, Category.GUILDE);
    public static final Card GUILDE_DES_MAGISTRATS = new Card(CardIds.GUILDE_DES_MAGISTRATS_ID, "GUILDE DES MAGISTRATS", new ScoreForNeighborsCardsEffect(1, Category.BATIMENT_CIVIL), new Resource[]{Resource.BOIS, Resource.BOIS, Resource.BOIS, Resource.PIERRE, Resource.TISSU}, Category.GUILDE);
    public static final Card GUILDE_DES_BATISSEURS = new Card(CardIds.GUILDE_DES_BATISSEURS_ID, "GUILDE DES BATISSEURS", new ScoreForOwnAndNeighborMerveillesEffet(1), new Resource[]{Resource.PIERRE, Resource.PIERRE, Resource.ARGILE, Resource.ARGILE, Resource.VERRE}, Category.GUILDE);
 }
