# Statistiques dans Spring

Service REST d'échange de statistiques. À la fin des 1000 parties, le moteur demande au serveur les statistiques de fin de partie.

# Chemins REST

- Les joueurs rejoignent la partie en se connectant au moteur de jeu.
- Le moteur demande au joueur de choisir sa merveille.
- Le moteur demande au joueur de choisir une paire action/carte.

# Docker Compose

Deux conteneurs :

- `malta:server`
- `malta:engine`

Pour la suite : un troisième conteneur `malta:player` avec option `scale`.

# Test d'intégration

***???***

# Travis

Script Travis avec `docker build`, `docker-compose`.