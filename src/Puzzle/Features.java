package Puzzle;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class Features {

    private double startTime;
    private double stopTime;
    private int stepCounter;
    private String userName;
    private ArrayList<HighScore> highScores; // Lista för att lagra högsta poäng
    private final String highScoreFilepath = "src/Puzzle/HighScoreList.txt"; // Filväg för att spara högsta poäng
    private final HighScoreHandler highScoreHandler; // Instans av HighScoreHandler för att hantera högsta poäng

    // Konstruktor som initierar HighScoreHandler och laddar högsta poäng från filen
    public Features(String highScoreFilepath) {
        this.highScoreHandler = new HighScoreHandler(highScoreFilepath);
        this.highScores = highScoreHandler.loadHighScores();
    }
    // Metod för att starta timern när spelet börjar
    public void startTimer() {
        startTime = System.currentTimeMillis(); // Sparar nuvarande tid som starttid
    }
    // Metod för att stoppa timern och returnera den förflutna tiden i sekunder
    public double stopTimer() {
        double stopTime = System.currentTimeMillis();
        double elapsedTime = (stopTime - startTime) / 1000.0;
        return elapsedTime;
    }
    // Metod för att öka steg räknaren med 1 varje gång spelaren gör ett drag
    public void tileStepCounter() {
        stepCounter++;
        System.out.println("Antal drag: " + stepCounter);
    }
    // Metod för att återställa steg räknaren till 0
    public void resetStepCounter() {
        stepCounter = 0;
    }

    // username
    // Metod för att låta spelaren välja ett användarnamn via en dialogruta
    public void chooseUserName(JFrame parentframe) {
        JDialog nameDialog = new JDialog(parentframe, "Välj användarnamn", true);
        JPanel namePanel = new JPanel(new FlowLayout());
        JLabel nameLabel = new JLabel("Välj ett användarnamn: ");
        JTextField nameTextField = new JTextField(25);

        namePanel.add(nameLabel);
        namePanel.add(nameTextField);
        JButton nameOkButton = new JButton("OK");
        nameOkButton.addActionListener(e -> {
            String newName = nameTextField.getText().trim();
            userName = newName.isEmpty() ? "John Doe" : newName; // Sätter defaultnamn om fältet är tomt
            nameDialog.dispose();
        });

        namePanel.add(nameOkButton);
        nameDialog.add(namePanel);
        nameDialog.pack();
        nameDialog.setLocationRelativeTo(parentframe); // Centrera dialogrutan i förhållande till föräldra fönstret
        nameDialog.setVisible(true);
    }

    // Higshscore
    // Metod för att uppdatera högsta poäng med en ny spelpoäng
    public void updateHighScores(String userName, double elapsedTime, int stepCounter) {
        HighScore newScore = new HighScore(userName, elapsedTime, stepCounter);
        highScores.add(newScore);
        // Om det finns mer än 3 klarade omgångar, behåll bara de 3 bästa
        if (highScores.size() > 3) {
            highScores.sort(Collections.reverseOrder()); // Sortera poängen i fallande ordning
            highScores = new ArrayList<>(highScores.subList(0, 3));
        }

        highScoreHandler.saveHighScores(highScores); // Spara högsta poängen till filen
    }
    // Metod för att visa alla högsta poäng
    public void showHighScores() {
        if (highScores.isEmpty()) {
            System.out.println("Finns ännu ingen vinnare");
        } else {
            System.out.println("Highscore: ");
            for (HighScore score : highScores) {
                // Skriv ut varje poäng med användarnamn, tid och drag
                System.out.println("- " + score.getUserName() + ": " + score.getTime() + " sekunder, " + score.getSteps() + " drag");
            }
        }
    }
    // Metod för att hämta den bästa poängen (minst tid)
    public HighScore getBestScore() {
        return highScores.isEmpty() ? null : Collections.min(highScores);
    }
    // Metod för att hantera resultatet av spelet när det är slut
    public void result(boolean gameWon) {
        if (gameWon) {
            double elapsedTime = stopTimer();
            int totalSteps = stepCounter;
            updateHighScores(userName, elapsedTime, totalSteps);
            showHighScores();
        }
    }

    // Inre klass för att lagra tid och drag för en spelare
    // Implementerar interfacet Comparable för att jämföra poäng
    public static class HighScore implements Comparable<HighScore> {
         private String userName;
         private double time;
         private int steps;

        public HighScore(String userName, double time, int steps) {
            this.userName = userName;
            this.time = time;
            this.steps = steps;
        }

        public String getUserName() {
            return userName;
        }

        public double getTime() {
            return time;
        }

        public int getSteps() {
            return steps;
        }


        // Jämför två poäng och returnerar resultatet
        @Override
        public int compareTo(HighScore otherScore) {
            if (this.time < otherScore.getTime()) {
                return -1;
            } else if (this.time > otherScore.getTime()) {
                return 1;
            } else {
                return Integer.compare(this.steps, otherScore.getSteps());
            }
        }
    }
}


 /*          String highScoreFilePath = "src/Puzzle/HighScoreList.txt";
/*          Features features = new Features(filePath);
 *         // Välj användarnamn
 *         features.chooseUserName(this());
 *         // Starta tid
 *         features.startTimer();
 *         // Starta stegräknare vid ett drag
 *         features.tileStepCounter();
 *         // När spelet är slut
 *         boolean gameWon = true;
 *         features.result(gameWon);
 */
        // implemitera
        // infoknapp
        // byta färg på brickor
        // byta färg på spel




