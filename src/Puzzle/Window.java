package Puzzle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


import static Puzzle.Game.*;

public class Window extends JFrame {

    private int dim = 4; //dimension på spelplanens sida
    private int size = dim * dim; //totala storleken
    private int whiteTile = size - 1; //Håller koll på vita rutans position, uppdaterad i moveTile()
    private Timer gameTimer;
    private boolean cheating = false;

    JPanel grid = new JPanel();
    JPanel[] panelArray;
    JLabel[] labels = new JLabel[size];
    JPanel stats = new JPanel();
    private Features features = new Features(); // Instans av Features-klassen för spelstatistik
    private JLabel stepsLabel;
    private JLabel timeLabel;

    void newGame() {
        features.stopTimer();
        features.chooseUserName(); // test
        features.resetStepCounter();
        features.startTimer();
        timeLabel.setText("Tid: 0 sekunder");

        do {
            shuffle(labels);
        } while (!isSolveable(labels));
    }
    void endGame() {
        JOptionPane.showMessageDialog(null,"Grattis, du vann efter " + features.getStepCounter() + " drag!");
        features.resetStepCounter();
        features.stopTimer();
        features.result(true);
    }

    void window() {
        setSize(800, 800);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        grid.setLayout(new GridLayout(dim, dim));
        grid.setOpaque(false);
        panelArray = new JPanel[size];
        createLabelButtons();
        add(grid, BorderLayout.CENTER);
        createPanelArray(panelArray);
        createLabelArr(panelArray);
        pack();
        stats.setLayout(new GridLayout(1,4,10,0));

        add(stats, BorderLayout.NORTH);

    }

    boolean isGameWon() {
        for (int i = size - 2; i >= 0; i--) {
            if (!labels[i].getText().equals(String.valueOf(i + 1))) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Window window = new Window();
        window.window();
    }

    private void createLabelArr(JPanel[] panelArray) {
        for (int i = 0; i < size; i++) {
            //saker som alla labels har gemensamt
            int oneIndexed = i + 1; //faktiska nummret
            labels[i] = new JLabel(oneIndexed + "");

            labels[i].setFont(new Font("Arial", Font.BOLD, getHeight() / dim / 2));
            labels[i].setPreferredSize(new Dimension(getWidth() / dim, getHeight() / dim)); //ändra storlek

            labels[i].setHorizontalAlignment(JLabel.CENTER);
            labels[i].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3));
            labels[i].setOpaque(true);
            labels[i].setBackground(Color.LIGHT_GRAY);
            labels[i].addMouseListener(new LabelMouseListener(i));
            panelArray[i].add(labels[i]);
            //saker som bara har med tomma rutan att göra
            if (i == whiteTile) {
                labels[i].setVisible(false);
            }
        }
    }
    private void createPanelArray(JPanel[] panelArray) {
        for (int i = 0; i < size; i++) {
            JPanel panel = new JPanel();
            grid.add(panel);
            panelArray[i] = panel;
        }
    }
    private void createLabelButtons(){
        JLabel newGameLabel = new JLabel ("Nytt spel");
        newGameLabel.setFont(new Font("Arial Black", Font.BOLD, 16));
        newGameLabel.setHorizontalAlignment(JLabel.CENTER);
        newGameLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 3));
        newGameLabel.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                newGame();
                System.out.println("NU nu spelar " + features.getUserName());
            }
        });

        JLabel optionsLabel = new JLabel("Byt namn");
        optionsLabel.setFont(new Font("Arial Black", Font.BOLD, 16));
        optionsLabel.setHorizontalAlignment(JLabel.CENTER);
        optionsLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 3));
        optionsLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                features.chooseUserName();
                System.out.println("Du bytte namn till " + features.getUserName());
            }

        });

        timeLabel = new JLabel("Tid: 0:00");
        timeLabel.setFont(new Font("Arial Black", Font.BOLD, 16));
        timeLabel.setHorizontalAlignment(JLabel.CENTER);
        timeLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 3));

        stepsLabel = new JLabel("Antal drag: " + features.getStepCounter() );
        stepsLabel.setFont(new Font("Arial Black", Font.BOLD, 16));
        stepsLabel.setHorizontalAlignment(JLabel.CENTER);
        stepsLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 3));

        stats.add(newGameLabel);
        stats.add(optionsLabel);
        stats.add(timeLabel);
        stats.add(stepsLabel);
        features.setTimerListener((formattedTime) -> timeLabel.setText(formattedTime));

    }

    private class LabelMouseListener extends MouseAdapter {
        private final int index;

        public LabelMouseListener(int index) {
            this.index = index;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            whiteTile = moveTile(index, labels, whiteTile, dim, features, Window.this);
            if (isGameWon()) {
                endGame();
            }
        }
    }
    public void setStepLabelText(){
        stepsLabel.setText("Antal drag: " + features.getStepCounter());
    }
}
//TODO Försök till timer -rad 16, -rad 143