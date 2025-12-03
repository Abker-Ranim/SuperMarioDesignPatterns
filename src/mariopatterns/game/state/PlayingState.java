package mariopatterns.game.state;

import mariopatterns.game.GameContext;

public class PlayingState extends AbstractGameState {
    @Override public void enter(GameContext context) {
        logger.logInfo("Partie commencée !");
    }
    @Override public void update(GameContext context) {
        // Ici viendra la logique du jeu
    }
    @Override public void render(GameContext context) {}
    @Override public void handleInput(GameContext context) {
        if (context.isKeyPressedOnce(80)) { // 80 = P
            changeState(context, new PauseState(), "PAUSE");
        }
        if (context.isKeyPressedOnce(81)) { // 81 = Q (test rapide Game Over)
            changeState(context, new GameOverState(), "GAME_OVER");
        }
    }
    @Override protected String getStateName() { return "PLAYING"; }
}