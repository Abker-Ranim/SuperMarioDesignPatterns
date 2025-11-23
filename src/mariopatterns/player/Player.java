package mariopatterns.player;

import mariopatterns.game.GameContext;
import mariopatterns.player.decorator.BasePlayer;
import mariopatterns.player.decorator.PlayerComponent;
import mariopatterns.player.state.IdleState;
import mariopatterns.player.state.PlayerState;

import java.util.HashSet;
import java.util.Set;

public class Player {
    public int x = 100, y = 400;
    public double velocityY = 0;
    private PlayerState currentState = new IdleState();
    private final GameContext gameContext;
    private final Set<Integer> pressedKeys = new HashSet<>();
    public PlayerComponent renderablePlayer;

    public void applyPowerUp(PlayerComponent decorator) {
        this.renderablePlayer = decorator;
    }
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
    }

    public void onCollisionWithEnemy() {
        currentState.onCollisionWithEnemy(this);
    }

    // Input
    public void keyPressed(int keyCode) { pressedKeys.add(keyCode); }
    public void keyReleased(int keyCode) { pressedKeys.remove(keyCode); }
    public boolean isKeyPressed(int keyCode) { return pressedKeys.contains(keyCode); }

    public GameContext getGameContext() { return gameContext; }
}