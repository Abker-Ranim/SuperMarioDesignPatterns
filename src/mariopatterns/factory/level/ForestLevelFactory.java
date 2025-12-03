package mariopatterns.factory.level;

import mariopatterns.factory.GameObjectFactory;
import mariopatterns.gameobject.*;
import mariopatterns.player.Player;

import java.util.List;

public class ForestLevelFactory implements LevelFactory {

    @Override
    public CompositeGameObject createLevelObjects(Player player) {
        CompositeGameObject levelObjects = new CompositeGameObject();

        // === PLATEFORMES ===
        List<Platform> platforms = List.of(
                GameObjectFactory.createPlatform(0, 500, 200, 140),
                GameObjectFactory.createPlatform(300, 460, 320, 140),
                GameObjectFactory.createPlatform(10, 120, 180, 90),
                GameObjectFactory.createPlatform(300, 210, 110, 30),
                GameObjectFactory.createPlatform(450, 140, 100, 30),
                GameObjectFactory.createPlatform(120, 320, 140, 30),
                GameObjectFactory.createPlatform(650, 120, 250, 150),
                GameObjectFactory.createPlatform(380, 350, 180, 110),
                GameObjectFactory.createPlatform(630, 500, 90, 60)
        );

        // === ENNEMIS (Spiny) ===
        Enemy enemy1 = (Enemy) GameObjectFactory.createEnemy("spiny", 80, 80, player);
        enemy1.setPlatform(platforms.get(2));
        levelObjects.add(enemy1);

        Enemy enemy2 = (Enemy) GameObjectFactory.createEnemy("spiny", 460, 310, player);
        enemy2.setPlatform(platforms.get(7));
        levelObjects.add(enemy2);

        Enemy enemy3 = (Enemy) GameObjectFactory.createEnemy("spiny", 780, 80, player);
        enemy3.setPlatform(platforms.get(6));
        levelObjects.add(enemy3);

        // === ITEMS ===
        levelObjects.add(GameObjectFactory.createItem("speed", 480, 80, player));

        // === PROTOTYPE PATTERN : 1 SEUL COIN + CLONAGE ! ===
        Coin coinPrototype = new Coin(0, 0, player); // position temporaire

        // Liste de positions (x, y) pour les pièces
        int[][] coinPositions = {
                {420, 400}, {360, 400}, {480, 400}, {540, 400},
                {700,  70}, {650,  70}, {150,  70}, {100,  70}, {50,  70}
        };

        for (int[] pos : coinPositions) {
            Coin clonedCoin = coinPrototype.clone();  // PROTOTYPE EN ACTION !
            clonedCoin.setPosition(pos[0], pos[1]);   // on change juste la position
            levelObjects.add(clonedCoin);
        }

        // === DRAPEAU DE FIN ===
        levelObjects.add(GameObjectFactory.createGoal(740, 34, player));

        // === AJOUT DES PLATEFORMES ===
        platforms.forEach(levelObjects::add);
        return levelObjects;
    }
}