package mariopatterns.player.state;

import mariopatterns.game.GameContext;
import mariopatterns.player.Player;
import mariopatterns.ui.GameOverPanel;
import mariopatterns.utils.SoundManager;

import java.awt.*;

public class DeadState extends AbstractPlayerState {

    @Override
    public void enter(Player player) {
        logger.logState("Player: DEAD");
        SoundManager.getInstance().playGameOver();
        GameContext gc = player.getGameContext();
        String name = gc.getGamePanel().getPlayerName();
        gc.saveFinalScore(name);

        // Passage à l'écran Game Over
        Container parent = gc.getGamePanel().getParent();
        CardLayout cl = (CardLayout) parent.getLayout();
        cl.show(parent, "GAMEOVER");

        // Mise à jour du score affiché dans GameOverPanel
        for (Component comp : parent.getComponents()) {
            if (comp instanceof GameOverPanel gameOverPanel) {
                gameOverPanel.refreshScore();
                break;
            }
        }
    }

    @Override public void update(Player player) {}
    @Override public void handleInput(Player player) {}
    @Override public void onCollisionWithEnemy(Player player) {}

    @Override
    protected String getStateName() {
        return "DEAD"; // Corrigé !
    }
}