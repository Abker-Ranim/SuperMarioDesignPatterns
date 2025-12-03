package mariopatterns.player.decorator;

import mariopatterns.player.Player;

import java.awt.Graphics2D;

public abstract class PlayerDecorator implements PlayerComponent {
    protected final PlayerComponent decoratedPlayer;

    public PlayerDecorator(PlayerComponent decoratedPlayer) {
        this.decoratedPlayer = decoratedPlayer;
    }

    @Override public void update() { decoratedPlayer.update(); }
    @Override public void render(Graphics2D g) { decoratedPlayer.render(g); }
    @Override public int getX() { return decoratedPlayer.getX(); }
    @Override public int getY() { return decoratedPlayer.getY(); }
    @Override public double getSpeedMultiplier() { return decoratedPlayer.getSpeedMultiplier(); }
    @Override public boolean isInvincible() { return decoratedPlayer.isInvincible(); }

    // Ajoutez l'implémentation
    @Override
    public Player getPlayer() {
        return decoratedPlayer.getPlayer();
    }

    // Ajoutez updateDecorators()
    @Override
    public void updateDecorators() {
        decoratedPlayer.updateDecorators();
    }
}
