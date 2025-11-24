package mariopatterns.level;

import mariopatterns.gameobject.CompositeGameObject;
import mariopatterns.gameobject.Enemy;
import mariopatterns.player.Player;

public class Level extends CompositeGameObject {
    public Level(Player player) {
        // 3 ennemis bien espacés
        add(new Enemy(300, 400, player));
        add(new Enemy(500, 400, player));
        add(new Enemy(700, 400, player));
    }
}
