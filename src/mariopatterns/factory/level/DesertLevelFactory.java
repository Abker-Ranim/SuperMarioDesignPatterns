package mariopatterns.factory.level;

import mariopatterns.factory.GameObjectFactory;
import mariopatterns.gameobject.*;
import mariopatterns.player.Player;
import java.util.List;

public class DesertLevelFactory implements LevelFactory {

    @Override
    public CompositeGameObject createLevelObjects(Player player) {
        CompositeGameObject levelObjects = new CompositeGameObject();
        List<Platform> platforms = List.of(
                GameObjectFactory.createPlatform(0, 480, 800, 120),
                GameObjectFactory.createPlatform(50, 400, 180, 20),
                GameObjectFactory.createPlatform(300, 350, 200, 20),
                GameObjectFactory.createPlatform(600, 300, 150, 20)
        );

        // Tu peux ajouter des ennemis ou items différents ici plus tard
        levelObjects.add(GameObjectFactory.createItem("speed", 400, 300, player));
        levelObjects.add(GameObjectFactory.createGoal(720, 380, player));

        platforms.forEach(levelObjects::add);
        return levelObjects;
    }
}