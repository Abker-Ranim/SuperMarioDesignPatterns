package mariopatterns.level;

import mariopatterns.factory.GameObjectFactory;
import mariopatterns.factory.level.DesertLevelFactory;
import mariopatterns.factory.level.ForestLevelFactory;
import mariopatterns.factory.level.LevelFactory;
import mariopatterns.game.GameContext;
import mariopatterns.game.state.VictoryState;
import mariopatterns.gameobject.*;
import mariopatterns.player.Player;
import mariopatterns.utils.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class LevelManager {

    private int currentLevel = 1;
    private final Player player;
    private CompositeGameObject currentLevelObjects;
    private final BufferedImage[] backgrounds = new BufferedImage[3];
    private boolean victoryTriggered = false;

    public LevelManager(Player player) {
        this.player = player;
        backgrounds[1] = ImageLoader.load("/resources/backgrounds/level1-forest.png");
        backgrounds[2] = ImageLoader.load("/resources/backgrounds/level2-desert.png");
        loadLevel(1);
    }

    public void loadLevel(int level) {
        currentLevel = level;
        victoryTriggered = false;
        currentLevelObjects = new CompositeGameObject();

        LevelFactory factory = switch (level) {
            case 1 -> new ForestLevelFactory();
            case 2 -> new DesertLevelFactory();
            default -> new ForestLevelFactory(); // par défaut
        };

        currentLevelObjects = factory.createLevelObjects(player);
    }

    public void update() {
        GameContext gc = player.getGameContext();
        if (gc.getCurrentState() instanceof VictoryState) {
            return; // Tout est bloqué en victoire
        }

        // Mise à jour des objets (ennemis, items, drapeau...)
        currentLevelObjects.update();

        // PLUS DE DÉTECTION AUTOMATIQUE PAR X → victoire UNIQUEMENT via GoalFlag !
    }

    public void render(Graphics2D g) {
        g.drawImage(backgrounds[currentLevel], 0, 0, 800, 600, null);
        currentLevelObjects.render(g);
    }

    public CompositeGameObject getCurrentLevelObjects() {
        return currentLevelObjects;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    // Méthode appelée par GoalFlag quand Mario touche le drapeau
    public void triggerVictory() {
        if (!victoryTriggered) {
            victoryTriggered = true;
            player.getGameContext().setState(new VictoryState());
        }
    }
}