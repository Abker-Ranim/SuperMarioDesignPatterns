package mariopatterns.player.decorator;

import mariopatterns.utils.LoggerManager;

import java.awt.*;

public class ShieldDecorator extends PlayerDecorator {

    private final long startTime;
    private final long duration = 10000; // 10 secondes
    private final LoggerManager logger = LoggerManager.getInstance();

    public ShieldDecorator(PlayerComponent decoratedPlayer) {
        super(decoratedPlayer);
        this.startTime = System.currentTimeMillis();

        // LOG APPLICATION (format identique au SpeedBoost)
        logger.logDecoratorApplied("Shield", decoratedPlayer, duration);
    }

    @Override
    public double getSpeedMultiplier() {
        return super.getSpeedMultiplier(); // le bouclier n’affecte pas la vitesse
    }

    @Override
    public boolean isInvincible() {
        long elapsed = System.currentTimeMillis() - startTime;
        if (elapsed >= duration) {
            // L’effet est terminé → log + on se retire automatiquement
            logger.logDecoratorRemoved("Shield", this);
            return false;
        }
        return true;
    }

    @Override
    public void render(Graphics2D g) {
        super.render(g);

        long elapsed = System.currentTimeMillis() - startTime;
        if (elapsed < duration) {
            // Effet visuel : bouclier jaune/orange qui pulse légèrement
            float alpha = 80 + 40 * (float) Math.sin(elapsed / 150.0); // pulse
            g.setColor(new Color(255, 215, 0, (int) alpha));
            g.fillOval(getX() - 18, getY() - 18, 86, 106);

            // Contour brillant
            g.setColor(new Color(255, 255, 100, 180));
            g.setStroke(new BasicStroke(3));
            g.drawOval(getX() - 18, getY() - 18, 86, 106);
        }
    }

    // Méthode pour vérifier si le bouclier est encore actif (utilisée dans Player.updateDecorators())
    public boolean isActive() {
        return System.currentTimeMillis() - startTime < duration;
    }
}