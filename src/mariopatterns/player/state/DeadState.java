package mariopatterns.player.state;

import mariopatterns.game.state.GameOverState;
import mariopatterns.player.Player;

public class DeadState extends AbstractPlayerState {
    @Override public void enter(Player player) {
        logger.logState("Player: DEAD");
        // On passe le jeu en Game Over
        player.getGameContext().setState(new GameOverState());
    }
    @Override public void update(Player player) {}
    @Override public void handleInput(Player player) {}
    @Override public void onCollisionWithEnemy(Player player) {}
    @Override protected String getStateName() { return "DEAD"; }
}
