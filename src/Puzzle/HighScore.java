package Puzzle;

import java.time.Duration;

public class HighScore implements Comparable<HighScore> {
    private String userName;
    private Duration time;
    private int steps;

    public HighScore(String userName, Duration time, int steps) {
        this.userName = userName;
        this.time = time;
        this.steps = steps;
    }

    public String getUserName() {
        return userName;
    }

    public Duration getTime() {
        return time;
    }

    public int getSteps() {
        return steps;
    }


    // Jämför två poäng och returnerar resultatet
    @Override
    public int compareTo(HighScore otherScore) {
        int timeComparison = this.time.compareTo(otherScore.getTime());
        if (timeComparison != 0) {
            return timeComparison;
        }

        return Integer.compare(this.steps, otherScore.getSteps());
    }


}
