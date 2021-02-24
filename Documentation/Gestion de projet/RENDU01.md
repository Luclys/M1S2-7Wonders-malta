# Rendu du 9 février

## Description des modules

- `java-player` : le joueur et les stratégies.
- `java-engine` : le moteur du jeu et le launcher.
- `java-server` : le serveur
- `java-engine-server` : en commun entre engine et server
- `java-engine-player` :  en commun entre engine et player

## Découpage

On a décidé de procéder à un premier découpage en laissant la dépendance entre `Engine` et `Player`.

C’est au moment où le joueur choisit la carte qu’il va jouer que `Engine` dépend de `Player`.  
Cette dépendance sera par la suite totalement supprimée par l’ajout de *web services*.

Les autres références à `Player` dans `Engine` peuvent être remplacées par `Inventory` qui gère la plupart des tâches de `Player` (le commerce et les effets).
