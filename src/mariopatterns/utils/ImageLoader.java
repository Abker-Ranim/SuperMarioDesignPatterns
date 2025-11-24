package mariopatterns.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class ImageLoader {
    public static BufferedImage load(String path) {
        try {
            return ImageIO.read(Objects.requireNonNull(ImageLoader.class.getResourceAsStream(path)));
        } catch (IOException e) {
            System.err.println("Impossible de charger : " + path);
            e.printStackTrace();
            return null;
        }
    }
}