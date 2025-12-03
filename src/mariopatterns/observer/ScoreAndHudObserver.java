package mariopatterns.observer;

import mariopatterns.game.GameContext;
import mariopatterns.utils.LoggerManager;

import javax.swing.*;
import java.awt.*;

public class ScoreAndHudObserver implements PlayerObserver {
    private final GameContext gameContext;
    private final JLabel messageLabel;
    private final Timer hideTimer;
    private final LoggerManager logger = LoggerManager.getInstance();

    public ScoreAndHudObserver(GameContext gameContext, JLabel messageLabel) {
        this.gameContext = gameContext;
        this.messageLabel = messageLabel;
        this.hideTimer = new Timer(2000, e -> messageLabel.setVisible(false));
        hideTimer.setRepeats(false);
    }

    @Override
    public void onSpeedBoostActivated() {
        gameContext.addScore(200);
        showMessage("SPEED UP ! x2", Color.GREEN, 3000);
        logger.logInfo("Observer : +200 points (Speed Boost)");
    }

    @Override
    public void onCoinCollected() {
        gameContext.addScore(100);
        showMessage("COIN +100 !", Color.YELLOW, 2000);
        logger.logInfo("Observer : +100 points (Coin)");
    }

    private void showMessage(String text, Color color, int duration) {
        messageLabel.setText(text);
        messageLabel.setForeground(color);
        messageLabel.setVisible(true);
        hideTimer.stop();
        hideTimer.setInitialDelay(duration);
        hideTimer.start();
    }
}