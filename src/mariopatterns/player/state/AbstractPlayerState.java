package mariopatterns.player.state;

import mariopatterns.player.Player;
import mariopatterns.utils.LoggerManager;

public abstract class AbstractPlayerState implements PlayerState {
    protected final LoggerManager logger = LoggerManager.getInstance();

    protected void changeState(Player player, PlayerState newState, String stateName) {
        logger.logState("Player: " + getStateName() + " -> " + stateName);
        player.setState(newState);
    }

    protected abstract String getStateName();
}
