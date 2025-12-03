import mariopatterns.ui.GameOverPanel;
import mariopatterns.ui.GamePanel;
import mariopatterns.ui.VictoryPanel;
import mariopatterns.utils.MenuPanel;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Super Mario - Design Patterns Project");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel mainPanel = new JPanel();
            CardLayout cardLayout = new CardLayout();
            mainPanel.setLayout(cardLayout);

            MenuPanel menuPanel = new MenuPanel(cardLayout, mainPanel);
            GamePanel gamePanel = new GamePanel();
            GameOverPanel gameOverPanel = new GameOverPanel(cardLayout, mainPanel, gamePanel);  // NOUVEAU

            mainPanel.add(menuPanel, "MENU");
            mainPanel.add(gamePanel, "GAME");
            mainPanel.add(gameOverPanel, "GAMEOVER");  // NOUVEAU

            VictoryPanel victoryPanel = new VictoryPanel(cardLayout, mainPanel, gamePanel);
            mainPanel.add(victoryPanel, "VICTORY");

            frame.add(mainPanel);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            cardLayout.show(mainPanel, "MENU");
        });
    }
}