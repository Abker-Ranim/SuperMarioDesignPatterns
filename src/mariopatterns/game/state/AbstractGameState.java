package mariopatterns.game.state;

import mariopatterns.game.GameContext;
import mariopatterns.utils.LoggerManager;

public abstract class AbstractGameState implements GameState {
    protected final LoggerManager logger = LoggerManager.getInstance();

    protected void changeState(GameContext context, GameState newState, String stateName) {
        logger.logState("Game: " + getStateName() + " -> " + stateName);
        context.setState(newState);
    }

    protected abstract String getStateName();
}
