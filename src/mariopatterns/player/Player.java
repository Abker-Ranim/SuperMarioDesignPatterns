package mariopatterns.player;

import mariopatterns.game.GameContext;
import mariopatterns.gameobject.GameObject;
import mariopatterns.gameobject.Platform;
import mariopatterns.observer.PlayerObserver;
import mariopatterns.player.decorator.BasePlayer;
import mariopatterns.player.decorator.PlayerComponent;
import mariopatterns.player.decorator.ShieldDecorator;
import mariopatterns.player.decorator.SpeedBoostDecorator;
import mariopatterns.player.state.IdleState;
import mariopatterns.player.state.JumpingState;
import mariopatterns.player.state.PlayerState;
import mariopatterns.player.state.RunningState;
import mariopatterns.utils.ImageLoader;
import mariopatterns.utils.LoggerManager;
import mariopatterns.player.decorator.SpeedBoostDecorator;
import mariopatterns.player.decorator.ShieldDecorator;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

public class Player {
    public int x = 50, y = 430;
    public double velocityY = 0;
    private PlayerState currentState = new IdleState();
    private final GameContext gameContext;
    public final Set<Integer> pressedKeys = new HashSet<>();
    public PlayerComponent renderablePlayer;

    // TES SPRITES
    private static final BufferedImage MARIO_STAND = ImageLoader.load("/resources/player/mario_stand.png");
    private static final BufferedImage MARIO_RUN   = ImageLoader.load("/resources/player/mario_run.png");   // ton seul sprite run
    private static final BufferedImage MARIO_JUMP  = ImageLoader.load("/resources/player/mario_jump.png");

    private int animTimer = 0;  // pour faire "bouger" le sprite run

    public Player(GameContext gameContext) {
        this.gameContext = gameContext;
        currentState.enter(this);
        this.renderablePlayer = new BasePlayer(this);
    }

    public void setState(PlayerState newState) {
        this.currentState = newState;
        newState.enter(this);
    }

    public void update() {
        currentState.update(this);
        currentState.handleInput(this);
        animTimer++; // pour l'animation
        renderablePlayer.updateDecorators();

    }
    public void applySpeedBoost() {
        renderablePlayer = new SpeedBoostDecorator(renderablePlayer);
    }
    public void render(Graphics2D g) {
        BufferedImage currentSprite;

        // ON UTILISE LES IMAGES DÉJÀ CHARGÉES (static) → AUCUN ImageLoader.load() ici !
        if (currentState instanceof mariopatterns.player.state.JumpingState) {
            currentSprite = MARIO_JUMP;
        } else if (currentState instanceof mariopatterns.player.state.RunningState) {
            currentSprite = MARIO_RUN;
        } else {
            currentSprite = MARIO_STAND;
        }

        // Si l'image est null → on dessine un rectangle rouge pour debug
        if (currentSprite == null) {
            g.setColor(Color.RED);
            g.fillRect(x, y, 50, 70);
            g.setColor(Color.WHITE);
            g.drawString("IMG NULL", x, y + 35);
            return;
        }

        // Dessin avec flip si on va à gauche
        if (isKeyPressed(KeyEvent.VK_LEFT)) {
            g.drawImage(currentSprite, x + 50, y, -50, 70, null);  // flip horizontal
        } else {
            g.drawImage(currentSprite, x, y, 50, 70, null);        // normal
        }

        // Power-up (Decorator)
        if (renderablePlayer != null) {
            renderablePlayer.render(g);
        }
    }

    public void onCollisionWithEnemy() {

        if (!renderablePlayer.isInvincible()) {
            currentState.onCollisionWithEnemy(this);
        } else {
            LoggerManager.getInstance().logCollision("Collision ennemie ignorée (bouclier actif) !");
        }
    }

    public void keyPressed(int keyCode) { pressedKeys.add(keyCode); }
    public void keyReleased(int keyCode) { pressedKeys.remove(keyCode); }
    public boolean isKeyPressed(int keyCode) { return pressedKeys.contains(keyCode); }
    public GameContext getGameContext() { return gameContext; }
    // AJOUTE CETTE MÉTHODE À LA FIN DE TA CLASSE Player.java (juste avant la dernière accolade)
// Dans Player.java → ajoute cette méthode pour l'atterrissage
    public boolean isOnGround() {
        var gamePanel = gameContext.getGamePanel();
        if (gamePanel == null) return y >= 480;

        var levelManager = gamePanel.getLevelManager();
        if (levelManager == null) return y >= 480;

        var objects = levelManager.getCurrentLevelObjects();
        if (objects == null) return y >= 480;

        int marioBottom = y + 70;

        for (GameObject obj : objects.getChildren()) {
            if (obj instanceof Platform p) {
                if (marioBottom >= p.y - 10 && marioBottom <= p.y + 30 &&
                        x + 50 > p.x && x < p.x + p.width) {
                    return true;
                }
            }
        }
        return false;
    }


    // Dans Player.java → ajoute ces méthodes

    public void collectCoin() {
        notifyCoinCollected();  // → déclenche tous les observers
    }

    private final List<PlayerObserver> observers = new ArrayList<>();

    public void addObserver(PlayerObserver observer) {
        observers.add(observer);
    }

    private void notifySpeedBoost() {
        observers.forEach(PlayerObserver::onSpeedBoostActivated);
    }

    private void notifyCoinCollected() {
        observers.forEach(PlayerObserver::onCoinCollected);
    }


    public void updateDecorators() {
        // SpeedBoost expiré ?
        if (renderablePlayer instanceof SpeedBoostDecorator speedDec && !speedDec.isActive()) {
            renderablePlayer = speedDec.getDecoratedPlayer();
        }

        // Shield expiré ? (quand tu l'ajouteras)
        if (renderablePlayer instanceof ShieldDecorator shieldDec && !shieldDec.isActive()) {
            renderablePlayer = shieldDec.getDecoratedPlayer();
        }
    }
}