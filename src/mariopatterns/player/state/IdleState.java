package mariopatterns.player.state;

import mariopatterns.player.Player;

import java.awt.event.KeyEvent;

public class IdleState extends AbstractPlayerState {
    @Override public void enter(Player player) { logger.logState("Player: IDLE"); }

    @Override
    public void handleInput(Player player) {
        if (player.isKeyPressed(KeyEvent.VK_LEFT) || player.isKeyPressed(KeyEvent.VK_RIGHT)) {
            changeState(player, new RunningState(), "RUNNING");
        }
        if (player.isKeyPressed(KeyEvent.VK_SPACE) && player.isOnGround()) {
            changeState(player, new JumpingState(), "JUMPING");
        }
    }

    @Override
    public void update(Player player) {
        if (!player.isOnGround()) {
            changeState(player, new JumpingState(), "FALLING");
        }
    }


    @Override public void onCollisionWithEnemy(Player player) {
        changeState(player, new DeadState(), "DEAD");
    }
    @Override protected String getStateName() { return "IDLE"; }
}