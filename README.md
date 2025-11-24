# Super Mario - Design Patterns Edition

Un clone fidèle de **Super Mario Bros** développé en **Java pur (Swing)** dans le but de démontrer une **architecture propre et avancée** basée sur les **Design Patterns**.

Fonctionnalités complètes : 2 niveaux, système de score sauvegardé, écran Game Over / Victory, sons, bouton EXIT en jeu, et surtout une architecture exemplaire.

## Aperçu du jeu

<img width="586" height="420" alt="Capture d&#39;écran 2025-11-25 001956" src="https://github.com/user-attachments/assets/eb0f1d56-d798-48be-a242-ae2d00ccd388" />


## Fonctionnalités

- 2 niveaux jouables (Level 1 & Level 2)
- Score persistant (sauvegarde du meilleur score par joueur)
- Écrans Game Over et Victory stylisés
- Sons authentiques Mario (jump, coin, victory, stage start, game over)
- Bouton EXIT en bas à droite pendant le jeu
- Nom du joueur personnalisé
- Architecture 100 % orientée objets et Design Patterns

## Design Patterns Implémentés (et vraiment utilisés !)

| Pattern               | Utilisation concrète dans le projet                                      | Classe(s) principale(s)                     |
|-----------------------|--------------------------------------------------------------------------|---------------------------------------------|
| **State**             | États du joueur (Idle, Running, Jumping, Dead) + états du jeu (Playing, Victory, GameOver) | `PlayerState`, `GameState`, `PlayingState`, `VictoryState`, `DeadState` |
| **Singleton**         | Gestionnaires uniques : sons, logs, sauvegarde, images                   | `SoundManager`, `LoggerManager`, `SaveData` |
| **Composite**         | Gestion hiérarchique de tous les objets du niveau (blocks, enemies, player, coins) | `CompositeGameObject`, `LevelManager`       |
| **Facade**            | Point d’entrée unique pour toute la logique du jeu                     | `GameContext`                               |
| **Strategy** (via State) | Chaque état du joueur définit un comportement différent               | Tous les `PlayerState`                      |
| **MVC** (quasi-complet) | Séparation claire Model / View / Controller                           | `Player` + `LevelManager` / `GamePanel` / `GameContext` + States |

## Technologies utilisées

- **Java 17** (Swing pour l’interface)
- **Aucune dépendance externe** (tout est fait main)
- Ressources embarquées (images + sons)
- Architecture modulaire et extensible

## Structure du projet
