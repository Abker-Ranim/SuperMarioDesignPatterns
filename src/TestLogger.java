import mariopatterns.game.GameContext;
import mariopatterns.utils.LoggerManager;

import java.awt.event.KeyEvent;

public class TestLogger {
    public static void main(String[] args) throws InterruptedException {
        GameContext game = new GameContext();

        System.out.println("TEST STATE PATTERN - Regarde game.log dans 15 secondes !");

        for (int i = 0; i < 50; i++) {
            game.update();
            Thread.sleep(300);

            // === Simulation des appuis de touche ===
            if (i == 10) {
                System.out.println("→ Simule appui ENTRÉE");
                game.keyPressed(10);   // 10 = VK_ENTER
            }
            if (i == 12) game.keyReleased(10);

            if (i == 20) {
                System.out.println("→ Simule appui P (pause)");
                game.keyPressed(80);   // 80 = VK_P
            }
            if (i == 25) {
                System.out.println("→ Relâche P → reprend");
                game.keyReleased(80);
                game.keyPressed(80);
            }
            if (i == 27) game.keyReleased(80);

            if (i == 35) {
                System.out.println("→ Simule appui Q → Game Over");
                game.keyPressed(81);   // 81 = VK_Q
            }
        }
    }
}
