package mariopatterns.player;

import mariopatterns.game.GameContext;
import mariopatterns.player.decorator.BasePlayer;
import mariopatterns.player.decorator.PlayerComponent;
import mariopatterns.player.state.IdleState;
import mariopatterns.player.state.JumpingState;
import mariopatterns.player.state.PlayerState;
import mariopatterns.player.state.RunningState;
import mariopatterns.utils.ImageLoader;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

public class Player {
    public int x = 100, y = 400;
    public double velocityY = 0;
    private PlayerState currentState = new IdleState();
    private final GameContext gameContext;
    private final Set<Integer> pressedKeys = new HashSet<>();
    public PlayerComponent renderablePlayer;

    // TES SPRITES
    private static final BufferedImage MARIO_STAND = ImageLoader.load("/resources/player/mario_stand.png");
    private static final BufferedImage MARIO_RUN   = ImageLoader.load("/resources/player/mario_run.png");   // ton seul sprite run
    private static final BufferedImage MARIO_JUMP  = ImageLoader.load("/resources/player/mario_jump.png");

    private int animTimer = 0;  // pour faire "bouger" le sprite run

    public Player(GameContext gameContext) {
        this.gameContext = gameContext;
        currentState.enter(this);
        this.renderablePlayer = new BasePlayer(this);
    }

    public void setState(PlayerState newState) {
        this.currentState = newState;
        newState.enter(this);
    }

    public void update() {
        currentState.update(this);
        currentState.handleInput(this);
        animTimer++; // pour l'animation
    }

    // Ajoute cette méthode dans ta classe Player.java
    public void render(Graphics2D g) {
        BufferedImage currentSprite;

        if (currentState instanceof mariopatterns.player.state.JumpingState) {
            currentSprite = ImageLoader.load("/resources/player/mario_jump.png");
        } else if (currentState instanceof mariopatterns.player.state.RunningState) {
            currentSprite = ImageLoader.load("/resources/player/mario_run.png");
        } else {
            currentSprite = ImageLoader.load("/resources/player/mario_stand.png");
        }

        if (isKeyPressed(KeyEvent.VK_LEFT)) {
            g.drawImage(currentSprite, x + 50, y, -50, 70, null);
        } else {
            g.drawImage(currentSprite, x, y, 50, 70, null);
        }

        // Effets power-up
        if (renderablePlayer != null) {
            renderablePlayer.render(g);
        }
    }

    public void onCollisionWithEnemy() {
        currentState.onCollisionWithEnemy(this);
    }

    public void keyPressed(int keyCode) { pressedKeys.add(keyCode); }
    public void keyReleased(int keyCode) { pressedKeys.remove(keyCode); }
    public boolean isKeyPressed(int keyCode) { return pressedKeys.contains(keyCode); }
    public GameContext getGameContext() { return gameContext; }
}