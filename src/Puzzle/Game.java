package Puzzle;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Game {

    /*
    Byter plats på rutan man klickade på med vita rutan.
     */
    public static int moveTile(int tileClicked, JLabel[] labels, int whiteTile, int dimension, Features features) {

        if (isConnected(tileClicked, whiteTile, dimension)) {
            //byter text
            String temp = labels[whiteTile].getText();
            labels[whiteTile].setText(labels[tileClicked].getText());
            labels[tileClicked].setText(temp);

            labels[whiteTile].setBackground(Color.LIGHT_GRAY);
            labels[whiteTile].setVisible(true);

            labels[tileClicked].setVisible(false);

            features.tileStepCounter(); //increment steps
            return tileClicked;
        }

        return whiteTile;
    }
    /*
    tileClicked är rutan man klickade på, whiteTile är vita rutan,
    dimension är dimensionen på rutnätet, det är antaget att rutnätet är kvadratiskt.
    Metoden returnerar true om rutorna står sida mot sida vertikalt eller horisontellt.
     */
    public static boolean isConnected(int tileClicked, int whiteTile, int dimension) {

        int startOfRow = whiteTile % dimension;
        int endOfRow = (whiteTile + 1) % dimension;

        if (0 != startOfRow && tileClicked == whiteTile - 1) {
            return true;
        }
        if (0 != endOfRow && tileClicked == whiteTile + 1) {
            return true;
        }

        return tileClicked == whiteTile - dimension || tileClicked == whiteTile + dimension;
    }
    /*
    Shufflear runt alla element i arrayen förutom sista
    vilket är whitetile som för enkelhetens skull
    alltid är längst ner till höger.
    Metoden är kopierat från Collections.shuffle()
    fast för arrays istället, den borde funka likadant.
     */
    public static void shuffle(JLabel[] labels) {

        for (int i = labels.length - 1; i > 0; i--) {
            Random rand  = new Random();
            int randomIndex = rand.nextInt(i);
            String temp = labels[randomIndex].getText();
            labels[randomIndex].setText(labels[i - 1].getText());
            labels[i - 1].setText(temp);
        }
    }

    /*
    https://en.wikipedia.org/wiki/15_puzzle
    Brute force algoritm för att räkna antal inversions.
    Om inversions är jämn är blandingen lösbar* oavsett dimensioner på rutnätet.
    *om tomma rutan är längst ner till höger.
     */
    public static boolean isSolveable(JLabel[] labels) {
        int inversions = 0;
        for (int i = 0; i < labels.length; i++) {
            int num = Integer.parseInt(labels[i].getText());
            for (int j = i + 1; j < labels.length; j++) {
                int next = Integer.parseInt(labels[j].getText());
                if (num > next) {
                    inversions++;
                }
            }
        }

//      return inversions % 2 == 0;
        return (inversions & 1) == 0;
    }
}
