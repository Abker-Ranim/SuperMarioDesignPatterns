package mariopatterns.player.state;

import mariopatterns.game.state.VictoryState;
import mariopatterns.gameobject.GameObject;
import mariopatterns.gameobject.Platform;
import mariopatterns.player.Player;
import mariopatterns.utils.SoundManager;

import java.awt.event.KeyEvent;

public class JumpingState extends AbstractPlayerState {
    private int jumpTimer = 0;
    private boolean canDoubleJump = true;  // pour le double jump

    @Override
    public void enter(Player player) {
        logger.logState("Player: JUMPING");
        player.velocityY = -10;  // saut initial
        jumpTimer = 0;
        canDoubleJump = true;  // permet le double jump
    }

    @Override
    public void update(Player player) {
        // Si on est en Victory → Mario ne tombe plus jamais
        if (player.getGameContext().getCurrentState() instanceof VictoryState) {
            return;
        }

        // Gravité
        player.y += player.velocityY;
        player.velocityY += 0.42;
        jumpTimer++;

        // Atterrissage
        if (player.velocityY > 0 && player.isOnGround()) {
            player.velocityY = 0;
            changeState(player, new IdleState(), "IDLE");
            return;
        }

        // Mort si tombe trop bas → MAIS PAS EN VICTORY !
        if (player.y > 700) {
            changeState(player, new DeadState(), "DEAD");
        }
    }

    @Override
    public void handleInput(Player player) {
        // Récupération du multiplicateur de vitesse (1.0 par défaut, 2.0 avec SpeedBoost)
        double speedMultiplier = player.renderablePlayer.getSpeedMultiplier();

        // === MOUVEMENT HORIZONTAL EN L'AIR ===
        if (player.isKeyPressed(KeyEvent.VK_LEFT)) {
            player.x -= (int) (2 * speedMultiplier); // vitesse réduite en l’air
        }
        if (player.isKeyPressed(KeyEvent.VK_RIGHT)) {
            player.x += (int) (2 * speedMultiplier);
        }

        // === DOUBLE JUMP (seulement si on n’a pas encore utilisé le 2ème saut) ===
        if (player.isKeyPressed(KeyEvent.VK_SPACE) && canDoubleJump && jumpTimer > 8) {
            player.velocityY = -10;           // impulsion du double saut
            canDoubleJump = false;            // on ne peut plus double-jumper
            SoundManager.getInstance().playJump();
            logger.logState("Player: DOUBLE JUMP activé !");
        }

        // === SAUT NORMAL QUAND ON EST AU SOL ===
        // (même si on est déjà en JumpingState, ça permet de sauter immédiatement après atterrissage)
        if (player.isKeyPressed(KeyEvent.VK_SPACE) && player.isOnGround()) {
            player.velocityY = -10;
            SoundManager.getInstance().playJump();
            jumpTimer = 0;           // reset du timer pour le prochain double jump
            canDoubleJump = true;    // on réactive le double jump après chaque atterrissage
            logger.logState("Player: Saut normal depuis le sol");
            // On reste dans JumpingState (ou on y passe si on était ailleurs)
            player.setState(this);   // this = JumpingState actuel
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