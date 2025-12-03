import mariopatterns.game.GameContext;
import mariopatterns.player.Player;
import mariopatterns.player.decorator.ShieldDecorator;
import mariopatterns.player.decorator.SpeedBoostDecorator;
import mariopatterns.utils.LoggerManager;

import java.awt.event.KeyEvent;

public class TestLogger {
    public static void main(String[] args) throws InterruptedException {
        GameContext game = new GameContext();
        Player player = new Player(game);

        System.out.println("TEST DECORATOR PATTERN – regarde game.log !");

        for (int i = 0; i < 200; i++) {
            player.update();
            game.update();
            Thread.sleep(100);

            if (i == 20) player.keyPressed(KeyEvent.VK_RIGHT);
            if (i == 50) {
                System.out.println("Power-up SPEED !");
                player.renderablePlayer = new SpeedBoostDecorator(player.renderablePlayer);
            }
            if (i == 80) {
                System.out.println("Power-up SHIELD !");
                player.renderablePlayer = new ShieldDecorator(player.renderablePlayer);
            }
            if (i == 120) player.onCollisionWithEnemy(); // Test bouclier
        }
    }
}