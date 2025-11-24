package mariopatterns.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SaveData {
    private static final String FILE_PATH = "data.txt";
    private static final SaveData instance = new SaveData();

    private SaveData() {
        createFileIfNotExists();
    }

    public static SaveData getInstance() {
        return instance;
    }

    private void createFileIfNotExists() {
        if (!Files.exists(Paths.get(FILE_PATH))) {
            try (PrintWriter writer = new PrintWriter(FILE_PATH)) {
                writer.println("# Super Mario - High Scores");
                writer.println("# Format: NOM:SCORE");
                writer.println("MARIO:5000");
                writer.println("LUIGI:3200");
                writer.println("PEACH:1800");
            } catch (IOException e) {
                LoggerManager.getInstance().logInfo("Erreur création data.txt");
            }
        }
    }

    public void saveScore(String playerName, int score) {
        playerName = playerName.toUpperCase().trim();
        List<String> lines = new ArrayList<>();
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().startsWith(playerName + ":")) {
                    // Remplace par le nouveau score seulement s'il est plus haut
                    if (score > extractScore(line)) {
                        lines.add(playerName + ":" + score);
                        LoggerManager.getInstance().logInfo("Record battu ! " + playerName + " → " + score);
                    } else {
                        lines.add(line); // garde l'ancien meilleur
                    }
                    found = true;
                } else {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!found) {
            lines.add(playerName + ":" + score);
        }

        // Réécrit proprement (sans les anciens commentaires si tu veux, mais on les garde)
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (String line : lines) {
                writer.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int extractScore(String line) {
        try {
            return Integer.parseInt(line.split(":")[1].trim());
        } catch (Exception e) {
            return 0;
        }
    }

    public List<String> getTopScores(int limit) {
        List<String> validScores = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();

                // Ignore les lignes vides et les commentaires
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                // Vérifie que la ligne contient bien ":" et un nombre après
                if (line.contains(":")) {
                    String[] parts = line.split(":", 2); // 2 = max 2 parties
                    if (parts.length == 2) {
                        String name = parts[0].trim();
                        String scoreStr = parts[1].trim();

                        try {
                            Integer.parseInt(scoreStr); // teste si c'est un nombre
                            validScores.add(name + ":" + scoreStr);
                        } catch (NumberFormatException e) {
                            // ignore les lignes corrompues
                            LoggerManager.getInstance().logInfo("Ligne ignorée (score invalide) : " + line);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Tri par score décroissant
        validScores.sort((a, b) -> {
            int scoreA = Integer.parseInt(a.split(":")[1]);
            int scoreB = Integer.parseInt(b.split(":")[1]);
            return Integer.compare(scoreB, scoreA); // décroissant
        });

        return validScores.size() > limit ? validScores.subList(0, limit) : validScores;
    }
}