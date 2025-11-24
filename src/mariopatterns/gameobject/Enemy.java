package mariopatterns.gameobject;

import mariopatterns.player.Player;
import mariopatterns.utils.LoggerManager;

import java.awt.*;

public class Enemy implements GameObject {
    public int x, y;
    private int dir = 1;
    private final Player player;
    private final LoggerManager logger = LoggerManager.getInstance();

    public Enemy(int x, int y, Player player) {
        this.x = x; this.y = y; this.player = player;
    }

    @Override
    public void update() {
        x += dir * 2;
        if (x < 0 || x > 800) dir *= -1;

        // Collision simple
        if (Math.abs(x - player.x) < 40 && Math.abs(y - player.y) < 60) {
            if (!player.renderablePlayer.isInvincible()) {
                player.onCollisionWithEnemy();
                logger.logCollision("Enemy hit player !");
            } else {
                logger.logInfo("Enemy hit player but SHIELD active → ignored");
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, 40, 40);
    }

    @Override
    public boolean isAlive() { return true; }
}
