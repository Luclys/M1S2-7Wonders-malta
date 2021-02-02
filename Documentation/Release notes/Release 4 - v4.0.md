## Release 4 - [`v4.0`](https://github.com/uca-m1informatique-softeng/M1-S1-7W-lamac/tree/v4.0)

## 4<sup>ème</sup> version du jeu

Introduction d'un plateau Merveille avec effet de gain de ressources. 
Tous les joueurs commencent avec le même plateau.

Les étapes du plateau sont disponibles, mais le joueur n'a pas encore le choix de les construire.

Ajout du mode silence pour les sorties textuelles.

Les rotations de cartes sont implémentées: après chaque tour, chaque joueur passe ses cartes au voisin de gauche.

L'inventaire est dissocié du joueur.

Les joueurs ne peuvent pas construire deux fois le même bâtiment.

## Étude des design patterns

On a pas su identifier de patterns dans le code présent. 
Nous avons envisagé l'utilisation du pattern *Factory method* pour la création des cartes mais nous ne l'avons pas
retenu car celle-ci ne justifiait pas l'utilisation de ce pattern, une simple classe abstraite a suffi.

Cependant, nous envisageons d'appliquer le pattern *Strategy* pour implémenter les choix des différents bots
lors des prises de décision.
