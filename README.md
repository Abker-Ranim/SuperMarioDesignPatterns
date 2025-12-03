# Super Mario – Design Patterns Edition

Un **clone ultra-fidèle de Super Mario Bros** développé en **Java 17 pur (Swing/AWT)** dans le seul but de démontrer une **architecture logicielle avancée, propre et extensible** grâce à **8 vrais Design Patterns** pleinement fonctionnels**.

Le jeu est entièrement jouable : 2 niveaux complets, score persistant, sons authentiques, pièces qui tournent en 3D, power-up Speed Boost, victoire, game over… et surtout une architecture que même un architecte senior validerait.

## Aperçu du jeu

<img src="https://github.com/user-attachments/assets/eb0f1d56-d798-48be-a242-ae2d00ccd388" width="800" />

## Fonctionnalités jouables
- 2 niveaux complets (Forêt + Désert) avec fonds différents
- Speed Boost temporaire (x2 pendant 7 secondes + effet visuel vert)
- Pièces avec rotation 3D autour de l’axe Y (comme dans les vrais Mario)
- Score + HUD avec messages temporaires ("SPEED UP !", "COIN +100 !")
- Sons authentiques (coin, jump, 1UP, victory, game over)
- Écrans stylisés : Menu principal, Victory, Game Over
- Bouton EXIT en jeu + nom du joueur personnalisé
- Victoire immédiate en touchant le drapeau

## Design Patterns Implémentés (et vraiment utilisés !)

| Pattern              | Utilisation concrète dans le projet                                                                                         | Classe(s) principales                                      |
|----------------------|------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------|
| **State**            | États du joueur (Idle, Running, Jumping) + États du jeu (Playing, Victory, GameOver)                                              | `PlayerState`, `IdleState`, `RunningState`, `JumpingState`, `GameState` |
| **Decorator**        | Power-ups temporaires (SpeedBoost → vitesse x2 + effet visuel + désactivation automatique)                                      | `PlayerComponent`, `BasePlayer`, `SpeedBoostDecorator`     |
| **Composite**        | Tous les objets du niveau (plateformes, ennemis, items, coins, drapeau) gérés dans une seule structure                              | `CompositeGameObject`, `LevelManager`                      |
| **Factory Method**   | Création centralisée de tous les objets (ennemis, items, plateformes, goal)                                                       | `GameObjectFactory`                                        |
| **Abstract Factory** | Chaque niveau créé par sa propre factory → ajoute un niveau en 5 minutes                                                           | `LevelFactory`, `ForestLevelFactory`, `DesertLevelFactory` |
| **Observer**         | Ramassage d’item/coin → notification automatique du score + HUD (zéro couplage)                                                   | `PlayerObserver`, `ScoreAndHudObserver`                    |
| **Prototype**        | Clonage rapide des pièces (9 pièces → 1 prototype + boucle                                                                       | `Coin.clone()`, `ForestLevelFactory`                       |
| **Singleton**        | Instances uniques pour sons, logs, sauvegarde                                                                                     | `SoundManager`, `LoggerManager`                            |

8 Design Patterns → tous fonctionnels, tous nécessaires, zéro sur-engineering.

## Technologies utilisées
- **Java 17** (Swing + AWT uniquement)
- **Aucune dépendance externe** (tout fait main)
- Ressources embarquées (sprites + sons .wav)

## Structure du projet

```plaintext
src/
└── mariopatterns/
    ├─ factory/
    │   ├─ GameObjectFactory.java
    │   └─ level/
    │       ├─ LevelFactory.java
    │       ├─ ForestLevelFactory.java
    │       └─ DesertLevelFactory.java
    │
    ├─ game/
    │   ├─ GameContext.java          ← Facade du jeu
    │   └─ state/                     ← États du jeu (Playing, Victory, GameOver...)
    │
    ├─ gameobject/
    │   ├─ Coin.java                  ← Prototype + rotation 3D
    │   ├─ SpeedItem.java
    │   ├─ GoalFlag.java
    │   ├─ Enemy.java
    │   └─ CompositeGameObject.java
    │
    ├─ level/
    │   └─ LevelManager.java          ← Utilise Abstract Factory
    │
    ├─ observer/
    │   ├─ PlayerObserver.java
    │   └─ ScoreAndHudObserver.java
    │
    ├─ player/
    │   ├─ Player.java                ← State + Decorator + Observer
    │   └─ decorator/                 ← BasePlayer, SpeedBoostDecorator
    │
    ├─ ui/                            ← ÉCRANS (Menu, Victory, Game Over)
    │   ├─ MenuPanel.java
    │   ├─ VictoryPanel.java
    │   └─ GameOverPanel.java
    │
    └─ utils/
        ├─ SoundManager.java        ← Singleton
        ├─ LoggerManager.java        ← Singleton
        └─ ImageLoader.java

resources/
├─ images/
└─ sounds/
```
## Installation & Lancement

### Prérequis
- JDK 17 ou supérieur
- IDE (IntelliJ IDEA recommandé) ou ligne de commande

### Étapes
```bash
git clone https://github.com/ton-pseudo/SuperMario-DesignPatterns.git
cd SuperMario-DesignPatterns




