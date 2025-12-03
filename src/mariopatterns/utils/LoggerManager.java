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
        logger.setUseParentHandlers(false); // Désactive la console par défaut

        try {
            FileHandler fileHandler = new FileHandler("game.log", true); // append = true
            fileHandler.setFormatter(new SimpleFormatter() {
                @Override
                public String format(LogRecord record) {
                    String timestamp = LocalDateTime.now().format(dtf);
                    return String.format("[%s] [%s] %s%n", timestamp, record.getLevel(), record.getMessage());
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

    // États du joueur
    public void logState(String message) {
        logger.info("[STATE] " + message);
    }

    // Décorateurs : application
    public void logDecoratorApplied(String decoratorType, Object target, long durationMs) {
        String duration = durationMs > 0 ? " (duration: " + durationMs + "ms)" : "";
        logger.info(String.format("[DECORATOR] %s applied to %s%s", decoratorType, target.getClass().getSimpleName(), duration));
    }

    // Décorateurs : retrait
    public void logDecoratorRemoved(String decoratorType, Object target) {
        logger.info(String.format("[DECORATOR] %s removed from %s", decoratorType, target.getClass().getSimpleName()));
    }

    // Info générale
    public void logInfo(String message) {
        logger.info("[INFO] " + message);
    }

    // Collisions
    public void logCollision(String message) {
        logger.info("[COLLISION] " + message);
    }
}