package mariopatterns.player.state;

import mariopatterns.player.Player;

public class JumpingState extends AbstractPlayerState {
    private int jumpTimer = 0;
    private final int JUMP_DURATION = 20;

    @Override public void enter(Player player) {
        logger.logState("Player: JUMPING");
        player.velocityY = -12;
        jumpTimer = 0;
    }
    @Override public void update(Player player) {
        player.y += player.velocityY;
        player.velocityY += 0.6; // gravité

        jumpTimer++;
        if (jumpTimer > JUMP_DURATION || player.y >= 400) { // sol à y=400
            player.y = 400;
            changeState(player, new IdleState(), "IDLE");
        }
    }
    @Override public void handleInput(Player player) {}
    @Override public void onCollisionWithEnemy(Player player) {
        changeState(player, new DeadState(), "DEAD");
    }
    @Override protected String getStateName() { return "JUMPING"; }
}