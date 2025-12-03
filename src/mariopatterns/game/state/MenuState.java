package mariopatterns.game.state;

import mariopatterns.game.GameContext;

public class MenuState extends AbstractGameState {
    @Override public void enter(GameContext context) {
        logger.logInfo("Menu affiché");
    }
    @Override public void update(GameContext context) {}
    @Override public void render(GameContext context) {
        // Plus tard on dessinera le menu ici
    }
    @Override public void handleInput(GameContext context) {
        // Simule appui sur Entrée pour démarrer
        if (context.isKeyPressedOnce(10)) { // 10 = Entrée
            changeState(context, new PlayingState(), "PLAYING");
        }
    }
    @Override protected String getStateName() { return "MENU"; }
}