package mariopatterns.game.state;

import mariopatterns.game.GameContext;
import mariopatterns.ui.GamePanel;

public class GameOverState extends AbstractGameState {
    @Override public void enter(GameContext context) {
        logger.logInfo("Game Over ! Score final : " + context.getScore());
        // Récupère le nom du joueur depuis GamePanel
        String playerName = ((GamePanel)context.getGamePanel()).getPlayerName(); // on ajoutera ça
        context.saveFinalScore(playerName);
    }

    @Override public void update(GameContext context) {}
    @Override public void render(GameContext context) {}
    @Override public void handleInput(GameContext context) {}
    @Override protected String getStateName() { return "GAME_OVER"; }
}