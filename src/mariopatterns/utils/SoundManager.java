package mariopatterns.utils;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class SoundManager {
    private static final SoundManager instance = new SoundManager();
    private final Map<String, Clip> clips = new HashMap<>();

    private SoundManager() {
        // Chargement des sons au démarrage
        loadSound("jump", "/resources/sounds/jump.wav");
        loadSound("up", "/resources/sounds/up.wav");
        loadSound("victory", "/resources/sounds/victory.wav");
        loadSound("gameover", "/resources/sounds/gameover.wav");
        loadSound("coin", "/resources/sounds/coin.wav"); // mets ton son ici
    }

    public static SoundManager getInstance() {
        return instance;
    }

    private void loadSound(String name, String path) {
        try {
            InputStream audioSrc = getClass().getResourceAsStream(path);
            if (audioSrc == null) {
                System.err.println("Son introuvable : " + path);
                return;
            }
            BufferedInputStream bufferedIn = new BufferedInputStream(audioSrc);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clips.put(name, clip);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play(String name) {
        Clip clip = clips.get(name);
        if (clip != null) {
            if (clip.isRunning()) clip.stop();
            clip.setFramePosition(0);
            clip.start();
        }
    }

    public void playVictory() { play("victory"); }
    public void playGameOver() { play("gameover"); }
    public void playJump() { play("jump"); }
    public void play1UP() { play("up"); }
}