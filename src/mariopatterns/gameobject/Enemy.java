package mariopatterns.gameobject;

import mariopatterns.player.Player;
import mariopatterns.utils.ImageLoader;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Enemy implements GameObject {
    public int x, y;
    private int dir = 1;
    private final Player player;
    private Platform platform;
    private static final BufferedImage SPINY = ImageLoader.load("/resources/enemies/spiny.png");

    public Enemy(int x, int y, Player player) {
        this.x = x;
        this.y = y;
        this.player = player;
    }
    public void setPlatform(Platform platform) { this.platform = platform; }
    @Override
    public void update() {
        x += dir * 2;

        // Inverse direction aux bords de la plateforme
        if (platform != null) {
            if (x <= platform.x || x >= platform.x + platform.width - 50) {
                dir *= -1;
            }
        } else if (x < 50 || x > 750) {
            dir *= -1;
        }

        // Collision avec Mario
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