package mariopatterns.player.decorator;

import mariopatterns.player.Player;

import java.awt.Graphics2D;

public interface PlayerComponent {
    void update();
    void render(Graphics2D g);
    Player getPlayer();
    // Accès aux propriétés modifiables
    int getX();
    int getY();
    double getSpeedMultiplier();
    boolean isInvincible();
    void updateDecorators();
}
