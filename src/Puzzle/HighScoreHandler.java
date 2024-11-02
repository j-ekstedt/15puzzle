package Puzzle;

import java.io.*;
import java.time.Duration;
import java.util.ArrayList;

public class HighScoreHandler {
    private final String filepath = "src/Puzzle/HighScoreList.txt";


    // Metod för att ladda högsta poäng från filen
    public ArrayList<HighScore> loadHighScores() {
        ArrayList<HighScore> loadedScores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String name = parts[0];
                    double seconds = Double.parseDouble(parts[1]);
                    Duration time = Duration.ofMillis((long) (seconds * 1000));
                    int steps = Integer.parseInt(parts[2]);
                    loadedScores.add(new HighScore(name , time, steps));
                    //TODO                                    ^ detta behöver fixas, skrev bara in så för tillfället, just nu sparas endast sista tiden
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error ladda fil " + e.getMessage());
        }
        return loadedScores;
    }

    public void saveHighScores(ArrayList<HighScore> highScores) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepath, true))) {
            for (HighScore score : highScores) {
                //String formattedTime = score.getTime().toString();
                //formattedTime = formattedTime.substring(2, formattedTime.indexOf('.') + 1);
                //double seconds = score.getTime().getSeconds() + score.getTime().getNano() / 1_000_000_000.0;
                //TODO Fix med utskrift av tiden
                double seconds = score.getTime().toMillis() / 1000.0;
                String formattedTime = String.format("%.2f", seconds);
                //TODO byt ut score.getTime() i writern med formatted time för att få den i sekunder.
                writer.write("Användare -" +score.getUserName() + ", tid -" + formattedTime + ", antal steg -" + score.getSteps() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error spara fil" + e.getMessage());
        }
    }
}