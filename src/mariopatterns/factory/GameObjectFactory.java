package mariopatterns.factory;

import mariopatterns.gameobject.*;
import mariopatterns.player.Player;

public class GameObjectFactory {

    // Ennemi (seulement Spiny pour l'instant)
    public static GameObject createEnemy(String type, int x, int y, Player player) {
        if ("spiny".equalsIgnoreCase(type) || "enemy".equalsIgnoreCase(type)) {
            return new Enemy(x, y, player);
        }
        throw new IllegalArgumentException("Type d'ennemi inconnu : " + type + " (seul 'spiny' est supporté)");
    }

    // Item (seulement SpeedItem pour l'instant)
    public static GameObject createItem(String type, int x, int y, Player player) {
        if ("speed".equalsIgnoreCase(type) || "boost".equalsIgnoreCase(type)) {
            return new SpeedItem(x, y, player);
        }
        throw new IllegalArgumentException("Type d'item inconnu : " + type + " (seul 'speed' est supporté)");
    }

    // Drapeau de fin
    public static GameObject createGoal(int x, int y, Player player) {
        return new GoalFlag(x, y, player);
    }

    // Plateforme — renvoie Platform (corrigé pour éviter l'erreur de type)
    public static Platform createPlatform(int x, int y, int width, int height) {
        return new Platform(x, y, width, height);
    }
}