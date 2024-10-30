package Puzzle;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Features {

    private long startTime;
    private long endTime;
    private int stepCounter;
    private String userName;
    private List<HighScore> highScores;

    public Features() {
        highScores = new ArrayList<>();
    }
    public void setStartTimer() {
        startTime = System.currentTimeMillis();
    }

    public void setEndTimer() {
        endTime = System.currentTimeMillis();
        double elapsedTime = (endTime - startTime) / 1000.0;
        System.out.println("Tid: " + elapsedTime + " sekunder");
    }

    public void incrementStepCounter() {
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

    /** I Windows
     * private Features name;
     * public Windoes(){
     *     name = new Features ();
     *     String userName = name.chooseUserName(this);
     * }
     */
    // Higshscore
    public void updateHighScores(String userName, long elapsedTime, int stepCounter) {
        HighScore newScore = new HighScore(userName, elapsedTime, stepCounter);
        highScores.add(newScore);
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

    // Privat inre klass för att lagra tid och drag för en spelare, implementerar Comparable
    private class HighScore implements Comparable<HighScore> {
        private String userName;
        private double time;
        private int steps;
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


    // implemitera

    // infoknapp

    // byta färg på brickor

    // byta färg på spel



}
