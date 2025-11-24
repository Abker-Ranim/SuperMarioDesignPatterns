package mariopatterns.ui;

import mariopatterns.game.GameContext;
import mariopatterns.player.Player;
import mariopatterns.player.state.IdleState;
import mariopatterns.game.state.PlayingState;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameOverPanel extends JPanel {
    private final CardLayout cardLayout;
    private final JPanel mainPanel;
    private final GamePanel gamePanel;
    private final JLabel scoreLabel;

    public GameOverPanel(CardLayout cardLayout, JPanel mainPanel, GamePanel gamePanel) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        this.gamePanel = gamePanel;

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 600));

        JLabel background = new JLabel();
        background.setOpaque(true);
        background.setBackground(new Color(0, 0, 0, 210));
        background.setLayout(new GridBagLayout());
        add(background, BorderLayout.CENTER);

        JPanel gameOverBox = new JPanel();
        gameOverBox.setLayout(new GridBagLayout());
        gameOverBox.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(20, 0, 30, 0);

        JLabel title = new JLabel("GAME OVER");
        title.setFont(new Font("Arial Black", Font.BOLD, 70));
        title.setForeground(Color.RED);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        gameOverBox.add(title, gbc);

        scoreLabel = new JLabel();
        scoreLabel.setFont(new Font("Arial Black", Font.BOLD, 36));
        scoreLabel.setForeground(Color.YELLOW);
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        refreshScore();
        gameOverBox.add(scoreLabel, gbc);

        addButton(gameOverBox, "REPLAY", new Color(0, 180, 0), e -> replay());
        addButton(gameOverBox, "MAIN MENU", new Color(0, 120, 200), e -> mainMenu());
        addButton(gameOverBox, "EXIT", Color.RED, e -> System.exit(0));

        background.add(gameOverBox);
    }

    public void refreshScore() {
        scoreLabel.setText("Score final : " + gamePanel.getGameContext().getScore());
    }

    private void addButton(JPanel panel, String text, Color color, ActionListener action) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial Black", Font.BOLD, 32));
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(380, 80));
        btn.addActionListener(action);
        btn.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { btn.setBackground(color.brighter()); }
            @Override public void mouseExited(MouseEvent e) { btn.setBackground(color); }
        });
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(15, 0, 15, 0);
        panel.add(btn, gbc);
    }

    private void replay() {
        GameContext gc = gamePanel.getGameContext();

        // Reset propre
        gc.resetScore();

        // Reset joueur
        Player p = gamePanel.player;
        p.x = 50;
        p.y = 430;
        p.velocityY = 0;
        p.setState(new IdleState());
        p.pressedKeys.clear();  // ← AJOUT : nettoie les touches coincées

        // Reset niveau
        gamePanel.getLevelManager().loadLevel(1);

        // État jeu
        gc.setState(new PlayingState());

        // Switch + FOCUS (LA CLÉ !)
        cardLayout.show(mainPanel, "GAME");
        gamePanel.requestFocusInWindow();  // ← ÇA RÉPARE TOUT !

        // Petit repaint pour fluidité
        gamePanel.repaint();
    }

    private void mainMenu() {
        cardLayout.show(mainPanel, "MENU");
    }
}