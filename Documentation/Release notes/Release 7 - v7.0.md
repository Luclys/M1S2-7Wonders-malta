## Release 7 - [`v7.0`](https://github.com/uca-m1informatique-softeng/M1-S1-7W-lamac/tree/v7.0)

## 7<sup>ème</sup> et dernière version de 7Wonders

A la fin de la partie, chaque joueur se voit assigné son rang 
([dense ranking](https://en.wikipedia.org/wiki/Ranking#Dense_ranking_(%221223%22_ranking))).

Les derniers effets manquants ont été implémentés au jeu.

Les joueurs peuvent choisir un plateau de merveille parmi la liste des plateaux disponibles.  
Les deux faces du plateau choisis sont retirés de la liste.

Une nouvelle classe DetailedResults a été créé, elle compile les statistiques associées au joueur les plus importantes.
C'est cet objet qui est envoyé au serveur.

Le serveur reçoit tous les DetailedResults des joueurs et affiche la moyenne des scores finaux des joueurs.

Tests Unitaires.

Réduction de code Smells.

Correction de bogue critiques.