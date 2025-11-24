package mariopatterns.player.state;

import mariopatterns.gameobject.GameObject;
import mariopatterns.gameobject.Platform;
import mariopatterns.player.Player;
import java.awt.event.KeyEvent;

public class JumpingState extends AbstractPlayerState {
    private int jumpTimer = 0;
    private boolean canDoubleJump = true;  // pour le double jump

    @Override
    public void enter(Player player) {
        logger.logState("Player: JUMPING");
        player.velocityY = -12;  // saut initial
        jumpTimer = 0;
        canDoubleJump = true;  // permet le double jump
    }

    @Override
    public void update(Player player) {
        // Gravité
        player.y += player.velocityY;
        player.velocityY += 0.6;
        jumpTimer++;

        // ATTERRISSAGE SUR PLATEFORME
        if (player.velocityY > 0 && player.isOnGround()) {
            player.velocityY = 0;
            changeState(player, new IdleState(), "IDLE");
            return;
        }

        // Mort si tombe trop bas
        if (player.y > 700) {
            changeState(player, new DeadState(), "DEAD");
        }
    }

    @Override
    public void handleInput(Player player) {
        // MARCHE EN L'AIR (contrôle horizontal pendant saut)
        if (player.isKeyPressed(KeyEvent.VK_LEFT)) {
            player.x -= 4;  // vitesse réduite en l'air
        }
        if (player.isKeyPressed(KeyEvent.VK_RIGHT)) {
            player.x += 4;
        }

        // DOUBLE JUMP (2ème saut en l'air)
        if (player.isKeyPressed(KeyEvent.VK_SPACE) && canDoubleJump && jumpTimer < 15) {
            player.velocityY = -12;  // nouveau saut
            canDoubleJump = false;
            logger.logState("Player: DOUBLE JUMP !");
        }
    }

    @Override
    public void onCollisionWithEnemy(Player player) {
        changeState(player, new DeadState(), "DEAD");
    }

    @Override
    protected String getStateName() { return "JUMPING"; }

    private void alignOnPlatform(Player player) {
        var objects = player.getGameContext().getGamePanel().getLevelManager().getCurrentLevelObjects();
        Platform highestPlatform = null;
        int highestY = Integer.MAX_VALUE;

        for (GameObject obj : objects.getChildren()) {
            if (obj instanceof Platform p) {
                if (player.x + 50 > p.x && player.x < p.x + p.width &&
                        p.y < highestY) {
                    highestPlatform = p;
                    highestY = p.y;
                }
            }
        }

        if (highestPlatform != null) {
            player.y = highestPlatform.y - 70;  // alignement parfait
        } else {
            player.y = 480 - 70;  // sol par défaut
        }
    }
}