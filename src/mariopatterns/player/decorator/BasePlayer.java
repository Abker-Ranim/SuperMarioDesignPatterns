package mariopatterns.player.decorator;

import mariopatterns.player.Player;
import mariopatterns.utils.LoggerManager;

import java.awt.*;

public class BasePlayer implements PlayerComponent {
    private final Player player;
    private final LoggerManager logger = LoggerManager.getInstance();

    public BasePlayer(Player player) {
        this.player = player;
    }

    @Override public void update() { player.update(); }
    @Override public void render(Graphics2D g) {
        g.setColor(Color.BLUE);
        g.fillRect(player.x, player.y, 40, 60);
    }

    @Override public int getX() { return player.x; }
    @Override public int getY() { return player.y; }
    @Override public double getSpeedMultiplier() { return 1.0; }
    @Override public boolean isInvincible() { return false; }
}
