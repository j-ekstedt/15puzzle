package Puzzle;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Consumer;

public class Features {

    private Instant startTime = Instant.now();
    private int stepCounter;
    private String userName;
    private ArrayList<HighScore> highScores; // Lista för att lagra högsta poäng
    private final HighScoreHandler highScoreHandler; // Instans av HighScoreHandler för att hantera högsta poäng
    private Timer gameTimer;
    private Consumer<String> timerListener;


    // Konstruktor som initierar HighScoreHandler och laddar högsta poäng från filen
    public Features() {
        this.highScoreHandler = new HighScoreHandler();
        this.highScores = highScoreHandler.loadHighScores();
        userName = getUserName();

        gameTimer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (startTime != null) {
                    Duration elapsedTime = Duration.between(startTime, Instant.now());
                    double seconds = elapsedTime.toMillis() / 1000.0;
                    if (timerListener != null) {
                        // Pass the formatted time to the listener
                        timerListener.accept(String.format("Tid: %.2f sekunder", seconds));
                    }
                }
            }
        });
    }
    // Metod för att starta timern när spelet börjar
    public void startTimer() {
        this.startTime = Instant.now(); // Sparar nuvarande tid som starttid
        gameTimer.start();
    }
    // Metod för att stoppa timern och returnera den förflutna tiden i sekunder
    public Duration stopTimer() {
        gameTimer.stop();
        Instant stopTime = Instant.now();
        Duration elapsedTime = Duration.between(startTime, stopTime);
        return elapsedTime;
    }

    public void setTimerListener(Consumer<String> listener) {
        this.timerListener = listener; //
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

    // Metod för att låta spelaren välja ett användarnamn via en dialogruta
    public void chooseUserName() {
        String input = JOptionPane.showInputDialog(null, "Välj användarnamn:");
        if (input != null && !input.trim().isEmpty()) {
            userName = input;
        }
    }

    public String getUserName() {
        if (userName == null || userName.isEmpty()) {
            userName = "John Doe";
        }
        return userName;
    }

    // Higshscore
    // Metod för att uppdatera högsta poäng med en ny spelpoäng
    public void updateHighScores(String userName, Duration elapsedTime, int stepCounter) {
        HighScore newScore = new HighScore(userName, elapsedTime, stepCounter);
        highScores.add(newScore);
        // Om det finns mer än 20 klarade omgångar, behåll bara de 20 bästa
        if (highScores.size() > 20) {
            highScores.sort(Collections.reverseOrder()); // Sortera poängen i fallande ordning
            highScores = new ArrayList<>(highScores.subList(0, 20));
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
                double seconds = score.getTime().toMillis() / 1000.0;
                String formattedTime = String.format("%.2f", seconds);
                // Skriv ut varje poäng med användarnamn, tid och drag
                System.out.println(score.getUserName() + ": " + formattedTime + " sekunder, " + score.getSteps() + " drag");
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
            Duration elapsedTime = stopTimer();
            int totalSteps = stepCounter;
            updateHighScores(userName, elapsedTime, totalSteps);
            showHighScores();
        }
    }

    public int getStepCounter() {
        return this.stepCounter;
    }
}