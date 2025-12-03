package mariopatterns.player.decorator;

import mariopatterns.utils.LoggerManager;

import java.awt.*;

public class SpeedBoostDecorator extends PlayerDecorator {
    private final long startTime;
    private final long duration = 7000; // 8 secondes
    private final LoggerManager logger = LoggerManager.getInstance();

    public SpeedBoostDecorator(PlayerComponent decoratedPlayer) {
        super(decoratedPlayer);
        this.startTime = System.currentTimeMillis();
        logger.logDecorator("SpeedBoost applied to Player");
    }

    @Override
    public void updateDecorators() {
        super.updateDecorators();
        if (System.currentTimeMillis() - startTime > duration) {
            logger.logDecorator("SpeedBoost expiré et retiré du Player");
            getPlayer().renderablePlayer = this.decoratedPlayer; // Unwrap proprement
        }
    }

    @Override
    public void render(Graphics2D g) {
        super.render(g);
        g.setColor(new Color(0, 255, 0, 100));
        g.fillOval(getX() - 10, getY() - 10, 60, 80);
    }

    @Override
    public double getSpeedMultiplier() {
        return System.currentTimeMillis() - startTime < duration ? 2.0 : super.getSpeedMultiplier();
    }
}
