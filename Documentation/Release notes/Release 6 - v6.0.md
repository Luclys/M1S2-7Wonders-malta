## Release 6 - [`v6.0`](https://github.com/uca-m1informatique-softeng/M1-S1-7W-lamac/tree/v6.0)

## 6<sup>ème</sup> version de 7Wonders

Le pattern Strategy a été appliqué sur les comportements des joueurs.  
Nous avons commencé par implémenter une stratégie qui se focalise sur la construction des étapes de Merveille.

Une IA peut choisir de construire une étape de merveille ou bien jouer n'importe quelle carte, celle-ci joue les cartes 
en fonction de ses ressources disponibles.

Le Client-Server est opérationnel. Pour l'instant, le client ne fait qu'envoyer l'identifiant du gagnant
(on ne considère pas les égalités sur les points) au serveur pour qu'il l'affiche pour chaque partie jouée.

Le prix d'achat des ressources est réduit lors de l'obtempsion de l'effet adéquat.

L'achat d'une carte grâce aux chaînages est désormais possible.

L'effet de "carte défaussée gratuite" a été implémenté. 

Amélioration de la lisibilité de la sortie textuelle.

Le score final est calculé selon les règles.

Correction de bogue critiques.