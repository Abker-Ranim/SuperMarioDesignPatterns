package mariopatterns.player.decorator;

import mariopatterns.utils.LoggerManager;

import java.awt.*;

public class SpeedBoostDecorator extends PlayerDecorator {

    private final long startTime;
    private final long duration = 7000; // 7 secondes
    private final LoggerManager logger = LoggerManager.getInstance();

    public SpeedBoostDecorator(PlayerComponent decoratedPlayer) {
        super(decoratedPlayer);
        this.startTime = System.currentTimeMillis();

        // LOG APPLICATION
        logger.logDecoratorApplied("SpeedBoost", decoratedPlayer, duration);
    }

    @Override
    public double getSpeedMultiplier() {
        long elapsed = System.currentTimeMillis() - startTime;
        if (elapsed < duration) {
            return 2.0;
        } else {
            // LOG RETRAIT AUTOMATIQUE (quand Player.updateDecorators() nous retire)
            logger.logDecoratorRemoved("SpeedBoost", this);
            return super.getSpeedMultiplier(); // revient à 1.0
        }
    }

    @Override
    public void render(Graphics2D g) {
        long elapsed = System.currentTimeMillis() - startTime;
        if (elapsed < duration) {
            g.setColor(new Color(0, 255, 0, 100));
            g.fillOval(getX() - 10, getY() - 10, 70, 90);
        }
        super.render(g);
    }

    // Méthode utilisée par Player pour savoir si on est encore actif
    public boolean isActive() {
        return System.currentTimeMillis() - startTime < duration;
    }
}