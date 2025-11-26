package mariopatterns.gameobject;

import java.awt.*;

public class Platform implements GameObject {
    public int x, y, width, height;

    public Platform(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean isOnPlatform(int playerX, int playerY, int playerHeight) {
        return playerX + 50 > x && playerX < x + width &&
                playerY + playerHeight >= y && playerY + playerHeight <= y + height + 10;
    }

    @Override
    public void update() {}

    // VERSION DEBUG : LIGNES ROUGES VISIBLES !
    @Override
    public void render(Graphics2D g) {

    }

    @Override
    public boolean isAlive() { return true; }
}