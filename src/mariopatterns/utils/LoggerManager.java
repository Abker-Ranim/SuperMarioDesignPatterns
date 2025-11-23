package mariopatterns.utils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.*;

public class LoggerManager {

    private static LoggerManager instance;
    private final Logger logger;
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private LoggerManager() {
        logger = Logger.getLogger("MarioGameLogger");
        logger.setLevel(Level.ALL);
        logger.setUseParentHandlers(false); // désactive la console par défaut

        try {
            // Crée (ou ouvre) le fichier game.log à la racine du projet
            FileHandler fileHandler = new FileHandler("game.log", true);
            fileHandler.setFormatter(new SimpleFormatter() {
                @Override
                public String format(LogRecord record) {
                    String timestamp = LocalDateTime.now().format(dtf);
                    return String.format("[%s] [%s] %s%n",
                            timestamp,
                            record.getLevel(),
                            record.getMessage());
                }
            });
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static LoggerManager getInstance() {
        if (instance == null) {
            instance = new LoggerManager();
        }
        return instance;
    }

    public void logState(String message) {
        logger.info("[STATE] " + message);
    }

    public void logDecorator(String message) {
        logger.info("[DECORATOR] " + message);
    }

    public void logInfo(String message) {
        logger.info("[INFO] " + message);
    }

    public void logCollision(String message) {
        logger.info("[COLLISION] " + message);
    }
}
