package mariopatterns.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class ImageLoader {
    public static BufferedImage load(String path) {
        try {
            BufferedImage img = ImageIO.read(ImageLoader.class.getResourceAsStream(path));
            if (img == null) {
                System.err.println("ERREUR : Image non trouvée → " + path);
                System.err.println("Vérifie que le fichier existe dans src/resources" + path);
                return createPlaceholder(path);
            }
            System.out.println("Image chargée : " + path);
            return img;
        } catch (Exception e) {
            System.err.println("ERREUR CRITIQUE : Impossible de charger " + path);
            e.printStackTrace();
            return createPlaceholder(path);
        }
    }

    // Image de secours si tout plante
    private static BufferedImage createPlaceholder(String path) {
        BufferedImage placeholder = new BufferedImage(64, 64, BufferedImage.TYPE_INT_RGB);
        var g = placeholder.createGraphics();
        g.setColor(java.awt.Color.MAGENTA);
        g.fillRect(0, 0, 64, 64);
        g.setColor(java.awt.Color.BLACK);
        g.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 10));
        g.drawString("MISSING", 5, 20);
        g.drawString(path.substring(path.lastIndexOf("/") + 1), 5, 40);
        g.dispose();
        return placeholder;
    }
}