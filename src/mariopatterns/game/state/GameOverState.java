package mariopatterns.game.state;

import mariopatterns.game.GameContext;

public class GameOverState extends AbstractGameState {
    @Override public void enter(GameContext context) {
        logger.logInfo("Game Over ! Score final : " + context.getScore());
    }
    @Override public void update(GameContext context) {}
    @Override public void render(GameContext context) {}
    @Override public void handleInput(GameContext context) {}
    @Override protected String getStateName() { return "GAME_OVER"; }
}