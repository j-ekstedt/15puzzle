package Puzzle;

public class Features {

    private long startTime;
    private long endTime;
    private int stepCounter;

    public void setStartTimer() {
        startTime = System.currentTimeMillis();
    }
    public void setEndTimer() {
        endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        System.out.println("Tid: " + elapsedTime / 1000.0 + "sekunder " );
    }

    public void incrementStepCounter() {
        stepCounter++;
        System.out.println("Antal drag: " + stepCounter);
    }

    public void resetStepCounter() {
        stepCounter = 0;
    }

}
