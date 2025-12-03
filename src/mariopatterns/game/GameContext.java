package mariopatterns.game;

import mariopatterns.game.state.GameState;
import mariopatterns.game.state.MenuState;
import mariopatterns.ui.GamePanel;
import mariopatterns.utils.LoggerManager;
import mariopatterns.utils.SaveData;

import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public class GameContext {
    private GameState currentState;
    private int score = 0;
    private final Set<Integer> pressedKeys = new HashSet<>();
    private final Set<Integer> justPressedKeys = new HashSet<>();
    private GamePanel gamePanel;

    public GameContext() {
        setState(new MenuState());
    }

    public void setState(GameState newState) {
        this.currentState = newState;
        newState.enter(this);
    }
    public void setGamePanel(GamePanel panel) {
        this.gamePanel = panel;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }
    public void update() {
        justPressedKeys.clear();
        justPressedKeys.addAll(pressedKeys);
        currentState.update(this);
        currentState.handleInput(this);
    }

    public void render() {
        currentState.render(this);
    }

    // Gestion des touches (on verra plus tard avec Swing)
    public void keyPressed(int keyCode) {
        pressedKeys.add(keyCode);
    }

    public void keyReleased(int keyCode) {
        pressedKeys.remove(keyCode);
    }

    public boolean isKeyPressedOnce(int keyCode) {
        return pressedKeys.contains(keyCode) && !justPressedKeys.contains(keyCode);
    }

    public int getScore() { return score; }

    public void addScore(int points) {
        this.score += points;
        LoggerManager.getInstance().logInfo("Score + " + points + " → Total: " + score);
    }

    public void saveFinalScore(String playerName) {
        SaveData.getInstance().saveScore(playerName, score);
        LoggerManager.getInstance().logInfo("Score final sauvegardé pour " + playerName + " : " + score);
    }
    public GameState getCurrentState() {
        return currentState;
    }
    public void resetScore() {
        this.score = 0;
        LoggerManager.getInstance().logInfo("Score remis à zéro pour une nouvelle partie !");
    }
}
