package mariopatterns.game.state;

import mariopatterns.game.GameContext;
import mariopatterns.ui.VictoryPanel;
import java.awt.*;

public class VictoryState extends AbstractGameState {

    private boolean bonusGiven = false;  // AJOUTÉ : évite le bonus infini

    @Override
    public void enter(GameContext context) {
        logger.logInfo("VICTORY STATE ENTERED");

        // On donne le bonus QU'UNE SEULE FOIS
        if (!bonusGiven) {
            int currentLevel = context.getGamePanel().getLevelManager().getCurrentLevel();
            int bonus = 1000 * currentLevel;
            context.addScore(bonus);
            logger.logInfo("Bonus level " + currentLevel + " : +" + bonus + " points");
            bonusGiven = true;
        }

        // Affichage écran victoire
        Container parent = context.getGamePanel().getParent();
        CardLayout cl = (CardLayout) parent.getLayout();
        cl.show(parent, "VICTORY");

        // Refresh du panel
        for (Component c : parent.getComponents()) {
            if (c instanceof VictoryPanel vp) {
                vp.refresh();
                break;
            }
        }
    }

    // IMPORTANT : reset du flag quand on quitte l'état
    @Override
    public void update(GameContext context) {
        // rien, mais on garde pour pouvoir reset si besoin
    }

    @Override
    public void render(GameContext context) {}

    @Override
    public void handleInput(GameContext context) {}

    @Override
    protected String getStateName() {
        return "VICTORY";
    }

    // AJOUTÉ : méthode pour reset quand on recommence
    public void reset() {
        bonusGiven = false;
    }
}