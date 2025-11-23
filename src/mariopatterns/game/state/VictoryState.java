package mariopatterns.game.state;

import mariopatterns.game.GameContext;

public class VictoryState extends AbstractGameState {
    @Override public void enter(GameContext context) {
        logger.logInfo("Victoire ! Score final : " + context.getScore());
    }
    @Override public void update(GameContext context) {}
    @Override public void render(GameContext context) {}
    @Override public void handleInput(GameContext context) {}
    @Override protected String getStateName() { return "VICTORY"; }
}
