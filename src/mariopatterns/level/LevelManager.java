package mariopatterns.level;

import mariopatterns.game.GameContext;
import mariopatterns.game.state.VictoryState;
import mariopatterns.gameobject.CompositeGameObject;
import mariopatterns.gameobject.Enemy;
import mariopatterns.gameobject.Platform;
import mariopatterns.gameobject.SpeedItem;
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

        if (level == 1) { // Niveau 1 - Forêt
            platforms.add(new Platform(0, 500, 200, 140));        // sol principal (invisible mais important)*
            platforms.add(new Platform(300, 460, 320, 140));        // sol principal (invisible mais important)*
            platforms.add(new Platform(10, 120, 180, 90));         // grande plateforme haut gauche*
            platforms.add(new Platform(300, 210, 110, 30));       // petite plateforme flottante*
            platforms.add(new Platform(450, 140, 100, 30));       // petite plateforme flottante
            platforms.add(new Platform(120, 320, 140, 30));       // plateforme sous les caisses*
            platforms.add(new Platform(650, 120, 250, 150));       // grande plateforme haut droite*
            platforms.add(new Platform(380, 350, 180, 110));      // plateforme centrale droite*
            platforms.add(new Platform(630, 500, 90, 60));        // petite plateforme bas droite

            // 1. Sur la grande plateforme haut gauche
            Enemy enemyLeft = new Enemy(80, 80, player);           // x=80, y=120 → bien sur la plateforme
            enemyLeft.setPlatform(platforms.get(2));
            currentLevelObjects.add(enemyLeft);

            // 2. Sur la plateforme centrale droite (la plus grande du milieu)
            Enemy enemyMiddle = new Enemy(460, 310, player);        // parfait au centre
            enemyMiddle.setPlatform(platforms.get(7));
            currentLevelObjects.add(enemyMiddle);

            // 3. Sur la grande plateforme haut droite
            Enemy enemyRight = new Enemy(780, 80, player);         // bien posé sur la plateforme haute droite
            enemyRight.setPlatform(platforms.get(6));
            currentLevelObjects.add(enemyRight);

            SpeedItem speedItem = new SpeedItem(530, 400, player); // Exemple de position (ajustez : x=200, y=80, sur une plateforme)
            currentLevelObjects.add(speedItem);





        } else { // Niveau 2 - Désert
            platforms.add(new Platform(0, 480, 800, 120));     // sol
            platforms.add(new Platform(50, 400, 180, 20));
            platforms.add(new Platform(300, 350, 200, 20));
            platforms.add(new Platform(600, 300, 150, 20));
        }

        // Ajouter les plateformes
        platforms.forEach(currentLevelObjects::add);

    }



    public void update() {
        // Si on est déjà en état Victory → ON NE FAIT PLUS RIEN ICI !
        GameContext gc = player.getGameContext();
        if (gc.getCurrentState() instanceof VictoryState) {
            return; // On bloque tout : plus de détection, plus de chute, plus rien
        }

        currentLevelObjects.update();

        // Détection de fin de niveau (seulement si on n'est PAS déjà en Victory)
        if (player.x > 750 && !victoryTriggered) {
            victoryTriggered = true;
            gc.setState(new VictoryState());
        }
    }

    public void render(Graphics2D g) {
        g.drawImage(backgrounds[currentLevel], 0, 0, 800, 600, null);
        currentLevelObjects.render(g);
    }

    // AJOUTÉ – INDISPENSABLE POUR LE SAUT
    public CompositeGameObject getCurrentLevelObjects() {
        return currentLevelObjects;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }
}