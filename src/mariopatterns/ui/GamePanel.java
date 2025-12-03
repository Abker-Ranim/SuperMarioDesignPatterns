package mariopatterns.ui;

import mariopatterns.game.GameContext;
import mariopatterns.level.Level;
import mariopatterns.level.LevelManager;
import mariopatterns.observer.ScoreAndHudObserver;
import mariopatterns.player.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;



public class GamePanel extends JPanel {
    private final GameContext gameContext;
    final Player player;
    private final LevelManager levelManager;
    private String playerName = "MARIO";
    private final JLabel messageLabel = new JLabel();

    public void setPlayerName(String name) {
        this.playerName = name.toUpperCase();
    }

    public String getPlayerName() { return playerName; }
    public GameContext getGameContext() { return gameContext; }
    public LevelManager getLevelManager() {
        return levelManager;
    }
    public GamePanel() {
        gameContext = new GameContext();
        player = new Player(gameContext);
        levelManager = new LevelManager(player);
// ABONNEMENT DE L'OBSERVER UNIQUE
        player.addObserver(new ScoreAndHudObserver(gameContext, messageLabel));

        gameContext.setGamePanel(this);

        setPreferredSize(new Dimension(800, 600));
        setDoubleBuffered(true);  // fluidité max
        setFocusable(true);
        // AJOUT DU BOUTON EXIT EN BAS À DROITE
        JButton exitButton = new JButton("EXIT");
        exitButton.setFont(new Font("Arial Black", Font.BOLD, 18));
        exitButton.setForeground(Color.WHITE);
        exitButton.setBackground(new Color(180, 0, 0));
        exitButton.setOpaque(true);
        exitButton.setBorderPainted(false);
        exitButton.setFocusPainted(false);
        exitButton.setBounds(700, 540, 90, 40);  // Position bas droite

        // Effet hover
        exitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                exitButton.setBackground(new Color(255, 50, 50));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                exitButton.setBackground(new Color(180, 0, 0));
            }
        });

        // Action : quitte le jeu
        exitButton.addActionListener(e -> System.exit(0));

        // Important : on utilise un layout null pour positionner manuellement
        setLayout(null);
        // Message temporaire (Speed Up / Coin)
        messageLabel.setFont(new Font("Arial Black", Font.BOLD, 40));
        messageLabel.setBounds(200, 150, 400, 80);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setVisible(false);
        add(messageLabel);
        add(exitButton);
        requestFocusInWindow();

        addKeyListener(new KeyAdapter() {
            @Override public void keyPressed(KeyEvent e) {
                player.keyPressed(e.getKeyCode());
                gameContext.keyPressed(e.getKeyCode());
            }
            @Override public void keyReleased(KeyEvent e) {
                player.keyReleased(e.getKeyCode());
                gameContext.keyReleased(e.getKeyCode());
            }
        });

        // GAME LOOP 60 FPS
        new Timer(16, e -> {
            gameContext.update();
            player.update();
            levelManager.update();
            repaint();
        }).start();

        // Score +10/sec en jeu
        new Timer(1000, e -> {
            if (gameContext.getCurrentState() instanceof mariopatterns.game.state.PlayingState) {
                gameContext.addScore(10);
            }
        }).start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();


        levelManager.render(g2d);

        player.render(g2d);

        g2d.setColor(Color.black);
        g2d.setFont(new Font("Arial Black", Font.BOLD, 22));
        g2d.drawString(playerName, 30, 20);
        g2d.drawString("SCORE: " + gameContext.getScore(), 30, 60);
        g2d.drawString("LEVEL " + levelManager.getCurrentLevel(), 550, 60);

        g2d.dispose();  // libère les ressources
    }
}