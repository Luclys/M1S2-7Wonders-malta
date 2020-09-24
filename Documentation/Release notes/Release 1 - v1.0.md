## Release 1 - [`v1.0`](https://github.com/uca-m1informatique-softeng/M1-S1-7W-lamac/tree/v1.0)

> Première version du jeu

Nous avons choisi de commencer par un âge et un joueur comme base. 

Le jeu est représenté par 3 classes : une classe Player, une classe Card et une classe Board. 

Le Board est la classe qui gère le jeu ; il permet d'ajouter un joueur, de lui attribuer des cartes et il lance également le jeu.

La classe Player permet de décrire un joueur et celui-ci peut jouer en lançant une carte ; dans cette version, il n'a pas le choix : il lance la première carte de la liste; il reçoit un point qui permet d’incrémenter son score à chaque fois qu'il joue. 

La classe Carte représente une carte de jeu ; dans cette version, nous n'avons qu'un seul type de carte.
