package mariopatterns.ui;

import mariopatterns.game.GameContext;
import mariopatterns.level.Level;
import mariopatterns.level.LevelManager;
import mariopatterns.player.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;



public class GamePanel extends JPanel {
    private final GameContext gameContext;
    private final Player player;
    private final LevelManager levelManager;
    private String playerName = "MARIO";

    public void setPlayerName(String name) {
        this.playerName = name.toUpperCase();
    }

    public String getPlayerName() { return playerName; }
    public GameContext getGameContext() { return gameContext; }

    public GamePanel() {
        gameContext = new GameContext();
        player = new Player(gameContext);
        levelManager = new LevelManager(player);

        gameContext.setGamePanel(this);

        setPreferredSize(new Dimension(800, 600));
        setDoubleBuffered(true);  // fluidité max
        setFocusable(true);
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
        // ON FORCE L'EFFACEMENT DU FOND SANS COULEUR PAR DÉFAUT
        g.clearRect(0, 0, getWidth(), getHeight());

        Graphics2D g2d = (Graphics2D) g;

        // 1. Fond du niveau (forêt ou désert) → dessiné en premier
        levelManager.render(g2d);

        // 2. Mario → par-dessus
        player.render(g2d);

        // 3. HUD → toujours visible
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial Black", Font.BOLD, 28));
        g2d.drawString(playerName, 30, 60);
        g2d.drawString("SCORE: " + gameContext.getScore(), 30, 100);
        g2d.drawString("LEVEL " + levelManager.getCurrentLevel(), 550, 60);
    }
}