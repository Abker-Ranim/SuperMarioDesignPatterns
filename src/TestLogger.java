import enicar.utils.LoggerManager;

public class TestLogger {
    public static void main(String[] args) {
        LoggerManager log = LoggerManager.getInstance();

        log.logInfo("Game started");
        log.logState("Game: MENU -> PLAYING");
        log.logState("Player: IDLE -> RUNNING");
        log.logDecorator("SpeedBoost applied to Player");
        log.logInfo("Test terminé avec succès !");

        System.out.println("Regarde le fichier game.log à la racine du projet !");
    }
}