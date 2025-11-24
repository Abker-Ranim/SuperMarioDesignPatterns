// mariopatterns/ui/VictoryPanel.java
package mariopatterns.ui;

import mariopatterns.game.GameContext;
import mariopatterns.game.state.PlayingState;
import mariopatterns.player.Player;
import mariopatterns.player.state.IdleState;
import mariopatterns.utils.MenuPanel;
import mariopatterns.utils.SoundManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VictoryPanel extends JPanel {
    private final CardLayout cardLayout;
    private final JPanel mainPanel;
    private final GamePanel gamePanel;
    private final JLabel messageLabel;
    private final JLabel scoreLabel;
    private final JButton nextButton;

    public VictoryPanel(CardLayout cardLayout, JPanel mainPanel, GamePanel gamePanel) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        this.gamePanel = gamePanel;

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 600));

        JLabel background = new JLabel();
        background.setOpaque(true);
        background.setBackground(new Color(0, 80, 0, 220));
        background.setLayout(new GridBagLayout());
        add(background, BorderLayout.CENTER);

        JPanel box = new JPanel(new GridBagLayout());
        box.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(20, 0, 30, 0);

        JLabel title = new JLabel("VICTORY!");
        title.setFont(new Font("Arial Black", Font.BOLD, 80));
        title.setForeground(new Color(255, 215, 0));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        box.add(title, gbc);

        messageLabel = new JLabel();
        messageLabel.setFont(new Font("Arial Black", Font.BOLD, 40));
        messageLabel.setForeground(Color.WHITE);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        box.add(messageLabel, gbc);

        scoreLabel = new JLabel();
        scoreLabel.setFont(new Font("Arial Black", Font.BOLD, 36));
        scoreLabel.setForeground(Color.YELLOW);
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        box.add(scoreLabel, gbc);

        nextButton = createButton("NEXT LEVEL", new Color(0, 180, 0), e -> nextLevel());
        box.add(nextButton, gbc);

        addButton(box, "EXIT", Color.RED, e -> System.exit(0));

        background.add(box);
    }

    // MÉTHODES DÉPLACÉES ICI (avant refresh)
    private void nextLevel() {
        Player p = gamePanel.player;
        p.x = 50;
        p.y = 400;
        p.velocityY = 0;
        p.pressedKeys.clear();  // Empêche la marche auto
        p.setState(new IdleState());

        gamePanel.getLevelManager().loadLevel(2);
        gamePanel.getGameContext().setState(new PlayingState());

        cardLayout.show(mainPanel, "GAME");
        gamePanel.requestFocusInWindow();
        SoundManager.getInstance().play1UP();

    }

    private void replayFromStart() {
        Player p = gamePanel.player;
        p.x = 50;
        p.y = 430;
        p.velocityY = 0;
        p.pressedKeys.clear();
        p.setState(new IdleState());

        gamePanel.getGameContext().resetScore();
        gamePanel.getLevelManager().loadLevel(1);
        gamePanel.getGameContext().setState(new PlayingState());

        cardLayout.show(mainPanel, "GAME");
        gamePanel.requestFocusInWindow();
    }


    // refresh() maintenant en dernier
    public void refresh() {
        int level = gamePanel.getLevelManager().getCurrentLevel();
        int score = gamePanel.getGameContext().getScore();

        if (level >= 2) {
            messageLabel.setText("GAME COMPLETED!");
            nextButton.setText("REPLAY");
            nextButton.setBackground(Color.ORANGE);
            // Change l'action du bouton
            for (ActionListener al : nextButton.getActionListeners()) {
                nextButton.removeActionListener(al);
            }
            nextButton.addActionListener(e -> replayFromStart());
        } else {
            messageLabel.setText("LEVEL " + level + " COMPLETE!");
            nextButton.setText("NEXT LEVEL");
            nextButton.setBackground(new Color(0, 180, 0));
            for (ActionListener al : nextButton.getActionListeners()) {
                nextButton.removeActionListener(al);
            }
            nextButton.addActionListener(e -> nextLevel());
        }

        scoreLabel.setText("Score : " + score);
    }

    private JButton createButton(String text, Color bg, ActionListener action) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial Black", Font.BOLD, 32));
        btn.setForeground(Color.WHITE);
        btn.setBackground(bg);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(380, 80));
        btn.addActionListener(action);
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(bg.brighter()); }
            public void mouseExited(MouseEvent e) { btn.setBackground(bg); }
        });
        return btn;
    }

    private void addButton(JPanel panel, String text, Color color, ActionListener action) {
        JButton btn = createButton(text, color, action);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(15, 0, 15, 0);
        panel.add(btn, gbc);
    }
}