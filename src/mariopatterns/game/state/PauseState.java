package mariopatterns.game.state;

import mariopatterns.game.GameContext;

public class PauseState extends AbstractGameState {
    @Override public void enter(GameContext context) { logger.logInfo("Jeu en pause"); }
    @Override public void update(GameContext context) {}
    @Override public void render(GameContext context) {}
    @Override public void handleInput(GameContext context) {
        if (context.isKeyPressedOnce(80)) { // P pour reprendre
            changeState(context, new PlayingState(), "PLAYING");
        }
    }
    @Override protected String getStateName() { return "PAUSE"; }
}
