package mariopatterns.ui;

import mariopatterns.game.GameContext;
import mariopatterns.level.Level;
import mariopatterns.player.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel {
    private final GameContext gameContext;
    private final Player player;
    private final Level level;
    private String playerName = "MARIO";

    public void setPlayerName(String name) {
        this.playerName = name.toUpperCase();
    }

    public GamePanel() {
        gameContext = new GameContext();
        player = new Player(gameContext);
        level = new Level(player);

        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.CYAN);

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
        setFocusable(true);

        // Game loop 60 FPS
        new Timer(16, e -> {
            gameContext.update();
            player.update();
            level.update();
            repaint();
        }).start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Fond + sol
        g2d.setColor(Color.GREEN.darker());
        g2d.fillRect(0, 460, 800, 140);

        // Render tout
        level.render(g2d);
        player.renderablePlayer.render(g2d);

        // HUD
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        g2d.drawString("Score: " + gameContext.getScore(), 10, 30);
        g2d.drawString("État jeu: " + gameContext.getClass().getSimpleName().replace("State", ""), 10, 60);
        g2d.drawString(playerName, 20, 40);
    }

}
