package mariopatterns.level;

import mariopatterns.game.state.VictoryState;
import mariopatterns.gameobject.CompositeGameObject;
import mariopatterns.gameobject.Enemy;
import mariopatterns.player.Player;
import mariopatterns.utils.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LevelManager {
    private int currentLevel = 1;
    private final Player player;
    private CompositeGameObject currentLevelObjects;

    private final BufferedImage[] backgrounds = new BufferedImage[3];

    public LevelManager(Player player) {
        this.player = player;
        backgrounds[1] = ImageLoader.load("/resources/backgrounds/level1-forest.png");
        backgrounds[2] = ImageLoader.load("/resources/backgrounds/level2-desert.png");
        loadLevel(1);
    }

    private void loadLevel(int level) {
        currentLevel = level;
        currentLevelObjects = new CompositeGameObject();

        int enemyCount = level == 1 ? 4 : 7;
        for (int i = 0; i < enemyCount; i++) {
            currentLevelObjects.add(new Enemy(250 + i * 110, 400, player));
        }
    }

    public void update() {
        currentLevelObjects.update();

        // Passage au niveau suivant
        if (player.x > 750) {
            if (currentLevel == 1) {
                loadLevel(2);
                player.x = 50;
                player.y = 400;
            } else {
                player.getGameContext().setState(new VictoryState());
            }
        }
    }

    public void render(Graphics2D g) {
        g.drawImage(backgrounds[currentLevel], 0, 0, 800, 600, null);
        currentLevelObjects.render(g);
    }

    public int getCurrentLevel() { return currentLevel; }
}
