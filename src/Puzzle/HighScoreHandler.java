package Puzzle;

import java.io.*;
import java.time.LocalTime;
import java.util.ArrayList;

    public class HighScoreHandler {
        private final String filepath = "src/Puzzle/HighScoreList.txt";

        // Metod för att ladda högsta poäng från filen
        public ArrayList<Features.HighScore> loadHighScores() {
            ArrayList<Features.HighScore> loadedScores = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 3) {
                        String name = parts[0];
                        double time = Double.parseDouble(parts[1]);
                        int steps = Integer.parseInt(parts[2]);
                        loadedScores.add(new Features.HighScore(name, time, steps));
                    }
                }
            } catch (IOException | NumberFormatException e) {
                System.out.println("Error ladda fil " + e.getMessage());
            }
            return loadedScores;
        }

        public void saveHighScores(ArrayList<Features.HighScore> highScores) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepath, false))) {
                for (Features.HighScore score : highScores) {
                    writer.write(score.getUserName() + "," + score.getTime() + "," + score.getSteps() + "\n");
                }
            } catch (IOException e) {
                System.out.println("Error spara fil" + e.getMessage());
            }
        }
    }
