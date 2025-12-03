package mariopatterns.game.state;

import mariopatterns.game.GameContext;

public interface GameState {
    void enter(GameContext context);
    void update(GameContext context);
    void render(GameContext context);
    void handleInput(GameContext context);
}
