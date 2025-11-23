package mariopatterns.player.decorator;

import java.awt.Graphics2D;

public interface PlayerComponent {
    void update();
    void render(Graphics2D g);

    // Accès aux propriétés modifiables
    int getX();
    int getY();
    double getSpeedMultiplier();
    boolean isInvincible();
}
