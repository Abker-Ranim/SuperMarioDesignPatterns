package mariopatterns.gameobject;

import mariopatterns.player.Player;
import mariopatterns.utils.ImageLoader;
import mariopatterns.utils.LoggerManager;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class SpeedItem implements GameObject {
    private final int x, baseY;
    private boolean alive = true;
    private final Player player;
    private static final BufferedImage IMAGE = ImageLoader.load("/resources/enemies/speed.png"); // Add your photo here
    private final LoggerManager logger = LoggerManager.getInstance();

    private double bobPhase = 0.0;
    private static final double BOB_SPEED = 0.1;  // vitesse lente et fluide
    private static final double BOB_AMPLITUDE = 8.0; // monte/descend de 8 pixels

    public SpeedItem(int x, int y, Player player) {
        this.x = x;
        this.baseY = y;  // position de base pour l'animation
        this.player = player;
        // Phase aléatoire pour que plusieurs items ne bougent pas synchro
        this.bobPhase = Math.random() * Math.PI * 2;
    }

    @Override
    public void update() {
        if (!alive) return;

        // === ANIMATION FLOTTANTE : mise à jour de la phase ===
        bobPhase += BOB_SPEED;
        if (bobPhase > Math.PI * 2) bobPhase -= Math.PI * 2;

        // Collision avec Mario (zone un peu plus large pour faciliter la collecte)
        if (Math.abs(x - player.x) < 50 && Math.abs(baseY - player.y) < 65) {
            player.applySpeedBoost();
            alive = false;
            logger.logInfo("🚀 SpeedItem mangé ! Mario boosté x2 pendant 7s.");
        }
    }

    @Override
    public void render(Graphics2D g) {
        if (!alive) return;

        // Position Y animée : sinusoïdale douce (monte/descend)
        int animatedY = baseY + (int) (Math.sin(bobPhase) * BOB_AMPLITUDE);

        // Rendu de l'item avec rotation légère pour plus de style (optionnel)
        g.drawImage(IMAGE, x, animatedY, 50, 50, null);

        // Debug : ligne pointillée pour voir la zone de collision (enlève en prod)
        // g.setColor(Color.BLUE);
        // g.drawOval(x - 25, baseY - 32, 100, 130);
    }

    @Override
    public boolean isAlive() {
        return alive;
    }
}