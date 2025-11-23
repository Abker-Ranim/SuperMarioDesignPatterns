import mariopatterns.game.GameContext;
import mariopatterns.player.Player;
import mariopatterns.utils.LoggerManager;

import java.awt.event.KeyEvent;

public class TestLogger {
    public static void main(String[] args) throws InterruptedException {
        GameContext game = new GameContext();
        Player player = new Player(game);

        System.out.println("TEST PLAYER STATES – regarde game.log !");

        for (int i = 0; i < 80; i++) {
            player.update();
            game.update();
            Thread.sleep(100);

            // Simulation inputs
            if (i == 20) { player.keyPressed(KeyEvent.VK_RIGHT); System.out.println("→ DROITE"); }
            if (i == 40) { player.keyReleased(KeyEvent.VK_RIGHT); System.out.println("→ Relâche"); }
            if (i == 50) { player.keyPressed(KeyEvent.VK_SPACE); System.out.println("→ SAUT"); }
            if (i == 70) { player.onCollisionWithEnemy(); System.out.println("→ Collision ennemi !"); }
        }
    }
}