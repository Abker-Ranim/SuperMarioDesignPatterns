package mariopatterns.level;

import mariopatterns.factory.GameObjectFactory;
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
        List<Platform> platforms = new ArrayList<>();

        if (level == 1) {
            // === PLATEFORMES via Factory ===
            platforms.add(GameObjectFactory.createPlatform(0, 500, 200, 140));
            platforms.add(GameObjectFactory.createPlatform(300, 460, 320, 140));
            platforms.add(GameObjectFactory.createPlatform(10, 120, 180, 90));
            platforms.add(GameObjectFactory.createPlatform(300, 210, 110, 30));
            platforms.add(GameObjectFactory.createPlatform(450, 140, 100, 30));
            platforms.add(GameObjectFactory.createPlatform(120, 320, 140, 30));
            platforms.add(GameObjectFactory.createPlatform(650, 120, 250, 150));
            platforms.add(GameObjectFactory.createPlatform(380, 350, 180, 110));
            platforms.add(GameObjectFactory.createPlatform(630, 500, 90, 60));

            // === ENNEMIS via Factory ===
            Enemy enemy1 = (Enemy) GameObjectFactory.createEnemy("spiny", 80, 80, player);
            enemy1.setPlatform(platforms.get(2));
            currentLevelObjects.add(enemy1);

            Enemy enemy2 = (Enemy) GameObjectFactory.createEnemy("spiny", 460, 310, player);
            enemy2.setPlatform(platforms.get(7));
            currentLevelObjects.add(enemy2);

            Enemy enemy3 = (Enemy) GameObjectFactory.createEnemy("spiny", 780, 80, player);
            enemy3.setPlatform(platforms.get(6));
            currentLevelObjects.add(enemy3);

            // === ITEM via Factory ===
            currentLevelObjects.add(GameObjectFactory.createItem("speed", 530, 400, player));

            // === DRAPEAU DE FIN via Factory ===
            currentLevelObjects.add(GameObjectFactory.createGoal(740, 36, player));

        } else if (level == 2) {
            // Niveau 2 - Désert
            platforms.add(GameObjectFactory.createPlatform(0, 480, 800, 120));
            platforms.add(GameObjectFactory.createPlatform(50, 400, 180, 20));
            platforms.add(GameObjectFactory.createPlatform(300, 350, 200, 20));
            platforms.add(GameObjectFactory.createPlatform(600, 300, 150, 20));

            // Exemple : un autre item + drapeau
            currentLevelObjects.add(GameObjectFactory.createItem("speed", 400, 300, player));
            currentLevelObjects.add(GameObjectFactory.createGoal(720, 380, player));
        }

        // Ajout des plateformes dans le Composite
        platforms.forEach(currentLevelObjects::add);
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