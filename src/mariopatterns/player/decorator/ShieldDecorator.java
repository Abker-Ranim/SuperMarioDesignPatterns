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
        logger.logDecorator("Shield applied to Player");
    }

    @Override
    public void updateDecorators() {
        super.updateDecorators();
        if (System.currentTimeMillis() - startTime > duration) {
            logger.logDecorator("Shield expiré et retiré du Player");
            getPlayer().renderablePlayer = this.decoratedPlayer;
        }
    }

    @Override
    public void render(Graphics2D g) {
        super.render(g);
        if (System.currentTimeMillis() - startTime < duration) {
            g.setColor(new Color(255, 255, 0, 120));
            g.fillOval(getX() - 15, getY() - 15, 70, 90);
        }
    }

    @Override
    public boolean isInvincible() {
        return System.currentTimeMillis() - startTime < duration;
    }
}
