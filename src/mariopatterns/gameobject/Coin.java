package mariopatterns.gameobject;

import mariopatterns.player.Player;
import mariopatterns.utils.ImageLoader;
import mariopatterns.utils.SoundManager;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Coin implements GameObject , Cloneable{
    private int x;
    private int y;
    private boolean collected = false;
    private final Player player;
    private static final BufferedImage IMAGE = ImageLoader.load("/resources/enemies/Coin.png");

    // Animation simple de rotation/flottement
    private double animPhase = Math.random() * Math.PI * 2;

    public Coin(int x, int y, Player player) {
        this.x = x;
        this.y = y;
        this.player = player;
    }

    @Override
    public void update() {
        if (collected) return;

        animPhase += 0.06;
        if (animPhase > Math.PI * 2) animPhase -= Math.PI * 2;

        // Collision
        if (Math.abs(x + 16 - player.x - 25) < 40 && Math.abs(y + 16 - player.y - 35) < 50) {
            collected = true;
            SoundManager.getInstance().play("coin");
            player.collectCoin();
        }
    }

    @Override
    public void render(Graphics2D g) {
        if (collected) return;

        int centerX = x + 16;
        int centerY = y + 16 + (int)(Math.sin(animPhase * 2) * 4); // flottement léger

        AffineTransform old = g.getTransform();
        g.translate(centerX, centerY);

        // Échelle X = cos(phase) → simule le flip sur l'axe Y
        double scaleX = Math.cos(animPhase * 2);  // passe de 1 → 0 → -1 → 0 → 1
        double absScale = Math.abs(scaleX);

        // Appliquer l'échelle horizontale
        g.scale(scaleX, 1.0);

        // Dessiner l'image (on dessine toujours la face avant)
        g.drawImage(IMAGE, -16, -16, 32, 32, null);

        // Optionnel : effet "plat" quand de côté (plus fin)
        if (absScale < 0.3) {
            g.setColor(new Color(255, 215, 0, 180)); // doré transparent
            g.fillOval(-14, -14, 28, 28);
        }

        g.setTransform(old);
    }

    @Override
    public boolean isAlive() {
        return !collected;
    }

    // AJOUTE ÇA DANS LA CLASSE Coin.java (en bas, avant la dernière accolade)

    @Override
    public Coin clone() {
        try {
            Coin cloned = (Coin) super.clone();
            cloned.animPhase = Math.random() * Math.PI * 2; // chaque pièce tourne différemment
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Clone non supporté", e);
        }
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}