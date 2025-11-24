package mariopatterns.gameobject;

import mariopatterns.player.Player;
import mariopatterns.utils.ImageLoader;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Enemy implements GameObject {
    public int x, y;
    private int dir = 1;
    private final Player player;
    private static final BufferedImage SPINY = ImageLoader.load("/resources/enemies/spiny.png");

    public Enemy(int x, int y, Player player) {
        this.x = x;
        this.y = y;
        this.player = player;
    }

    @Override
    public void update() {
        x += dir * 2;
        if (x < 50 || x > 750) dir *= -1;

        // Collision = Game Over
        if (Math.abs(x - player.x) < 45 && Math.abs(y - player.y) < 60) {
            player.onCollisionWithEnemy();
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(SPINY, x, y, 50, 50, null);
    }

    @Override
    public boolean isAlive() { return true; }
}