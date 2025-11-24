package mariopatterns.player.state;

import mariopatterns.player.Player;

import java.awt.event.KeyEvent;

public class RunningState extends AbstractPlayerState {
    @Override public void enter(Player player) { logger.logState("Player: RUNNING"); }

    @Override
    public void handleInput(Player player) {
        boolean left = player.isKeyPressed(KeyEvent.VK_LEFT);
        boolean right = player.isKeyPressed(KeyEvent.VK_RIGHT);

        if (!left && !right) {
            changeState(player, new IdleState(), "IDLE");
        }
        if (player.isKeyPressed(KeyEvent.VK_SPACE) && player.isOnGround()) {
            changeState(player, new JumpingState(), "JUMPING");
        }
    }

    @Override
    public void update(Player player) {
        if (player.isKeyPressed(KeyEvent.VK_LEFT)) player.x -= 5;
        if (player.isKeyPressed(KeyEvent.VK_RIGHT)) player.x += 5;

        if (!player.isOnGround()) {
            changeState(player, new JumpingState(), "FALLING");
        }
    }

    @Override public void onCollisionWithEnemy(Player player) {
        changeState(player, new DeadState(), "DEAD");
    }
    @Override protected String getStateName() { return "RUNNING"; }
}
