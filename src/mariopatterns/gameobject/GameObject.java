package mariopatterns.gameobject;

import java.awt.Graphics2D;

public interface GameObject {
    void update();
    void render(Graphics2D g);
    boolean isAlive();
}
