package mariopatterns.gameobject;

import mariopatterns.player.Player;
import mariopatterns.utils.ImageLoader;
import mariopatterns.utils.SoundManager;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GoalFlag implements GameObject {
    private final int x, y;
    private boolean triggered = false;
    private final Player player;
    private static final BufferedImage FLAG = ImageLoader.load("/resources/enemies/flag_goal.png"); // Mets ta photo ici


    public GoalFlag(int x, int y, Player player) {
        this.x = x;
        this.y = y ;  // le mât commence en haut
        this.player = player;
    }

    @Override
    public void update() {
        if (triggered) return;

        // Collision simple avec Mario
        if (player.x + 40 > x && player.x < x + 80 &&
                player.y + 70 > y && player.y < y + 100) {

            triggered = true;
            SoundManager.getInstance().playVictory();

            // Victoire immédiate
            player.getGameContext().setState(new mariopatterns.game.state.VictoryState());
        }
    }

    private void triggerVictory() {
        if (triggered) return;
        triggered = true;
        SoundManager.getInstance().playVictory();
        player.getGameContext().getGamePanel().getLevelManager().triggerVictory(); // on dit au LevelManager que c’est gagné
    }

    @Override
    public void render(Graphics2D g) {
        if (FLAG != null) {
            g.drawImage(FLAG, x, y, 80, 100, null);
        } else {
            // Backup visuel si l'image manque
            g.setColor(Color.MAGENTA);
            g.fillRect(x, y, 80, 100);
            g.setColor(Color.WHITE);
            g.drawString("GOAL", x + 15, y + 50);
        }
    }

    @Override
    public boolean isAlive() {
        return true;
    }
}