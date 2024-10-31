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
    private ArrayList<HighScore> highScores;
    private final String highScoreFilepath;

    public void startTimer() {
        startTime = System.currentTimeMillis();
    }

    public double stopTimer() {
        double stopTime = System.currentTimeMillis();
        double elapsedTime = (stopTime - startTime) / 1000.0;
        return elapsedTime;
    }

    public void tileStepCounter() {
        stepCounter++;
        System.out.println("Antal drag: " + stepCounter);
    }

    public void resetStepCounter() {
        stepCounter = 0;
    }

    // username
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
            userName = newName.isEmpty() ? "John Doe" : newName;
            nameDialog.dispose();
        });

        namePanel.add(nameOkButton);
        nameDialog.add(namePanel);
        nameDialog.pack();
        nameDialog.setLocationRelativeTo(parentframe);
        nameDialog.setVisible(true);
    }

    // Higshscore
    public Features(String highScoreFilepath) {
        this.highScoreFilepath = highScoreFilepath;
        highScores = loadHighScores();
    }

    public void updateHighScores(String userName, double elapsedTime, int stepCounter) {
        HighScore newScore = new HighScore(userName, elapsedTime, stepCounter);
        highScores.add(newScore);
        saveHighScores();
    }

    public void showHighScores() {
        if (highScores.isEmpty()) {
            System.out.println("Finns ännu ingen vinnare");
        } else {
            System.out.println("Highscore: ");
            for (HighScore score : highScores) {
                System.out.println("- " + score.getUserName() + ": " + score.getTime() + " sekunder, " + score.getSteps() + " drag");
            }

            HighScore bestScore = Collections.min(highScores);
            System.out.println("Ledaren är: " + bestScore.getUserName() + " med " + bestScore.getTime() + " sekunder och " + bestScore.getSteps() + " drag");
        }
    }

    // Ladda highscore från txtfil
    private ArrayList<HighScore> loadHighScores() {
        ArrayList<HighScore> loadedScores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(highScoreFilepath))) {
            String line;
            while ((line = reader.readLine()) != null){

                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String name = parts[0];
                    double time = Double.parseDouble(parts[1]);
                    int steps = Integer.parseInt(parts[2]);
                    loadedScores.add(new HighScore(name, time, steps));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading highscore: " + e.getMessage());
        }
        return loadedScores;
    }

    // Sparar highscore till txtfil
    private void saveHighScores() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(highScoreFilepath))) {
            for (HighScore score : highScores) {
                writer.write(score.getUserName() + "," + score.getTime() + "," + score.getSteps() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        // Privat inre klass för att lagra tid och drag för en spelare
        // Och implementerar interfacet Comparable
        private class HighScore implements Comparable<HighScore> {
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

            public HighScore getBestScore() {
                return highScores.isEmpty() ? null : Collections.min(highScores);
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
    public HighScore getBestScore() {
        return highScores.isEmpty() ? null : Collections.min(highScores);
    }
    public void result (boolean gameWon) {
        if (gameWon) {

            double elapsedTime = stopTimer();
            int totalSteps = stepCounter;

            updateHighScores(userName, elapsedTime, totalSteps);

            showHighScores();

            HighScore bestScore = getBestScore();
            if (bestScore != null) {
                System.out.println("Ledaren är: " + bestScore.getUserName() + " med "
                        + bestScore.getTime() + " sekunder och " + bestScore.getSteps() + " drag");
            }
        }
    }


}
        // implemitera
        // infoknapp
        // byta färg på brickor
        // byta färg på spel




