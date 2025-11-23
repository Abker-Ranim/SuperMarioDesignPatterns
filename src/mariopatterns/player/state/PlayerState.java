package mariopatterns.player.state;

import mariopatterns.player.Player;

public interface PlayerState {
    void enter(Player player);
    void update(Player player);
    void handleInput(Player player);
    void onCollisionWithEnemy(Player player);
}