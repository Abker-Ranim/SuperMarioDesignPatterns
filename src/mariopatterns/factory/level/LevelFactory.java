package mariopatterns.factory.level;

import mariopatterns.gameobject.CompositeGameObject;
import mariopatterns.player.Player;

public interface LevelFactory {
    CompositeGameObject createLevelObjects(Player player);
}