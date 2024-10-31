package Puzzle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static Puzzle.Game.*;

public class Window extends JFrame {

    int dim = 4; //dimension på spelplanens sida
    int size = dim * dim; //totala storleken
    int whiteTile = size - 1; //Håller koll på vita rutans position, uppdaterad i moveTile()
    JPanel grid = new JPanel();
    JLabel[] labels = new JLabel[size];
    boolean cheating = false;
    JPanel stats = new JPanel();
    JPanel options = new JPanel();
    private Features features; // Instans av Features-klassen för spelstatistik

    void startGame() {
        if (cheating) {
            cheat(labels);
        } else {
            shuffle(labels);
            if (!isSolveable(labels)) {
                startGame();
            }
        }
    }

    public Window() {
        features = new Features("highscore.txt");
        features.chooseUserName(this);
        features.startTimer();
        features.resetStepCounter();
    }


    void window() {
        setSize(800, 800);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        grid.setLayout(new GridLayout(dim, dim));
        grid.setOpaque(false);
        JPanel[] panelArray = new JPanel[size];

        createPanelArray(panelArray);
        createLabelArr(panelArray);

        add(grid, BorderLayout.CENTER);
        pack();

        stats.setLayout(new BorderLayout());
        options.setLayout(new FlowLayout());
        add(stats, BorderLayout.NORTH);
        add(options, BorderLayout.SOUTH);

    }


    void cheat(JLabel[] labels) {
        labels[whiteTile].setVisible(true);
        whiteTile = size - 2;
        labels[size - 1].setText(15 + "");
        labels[size - 2].setVisible(true);
        labels[whiteTile].setText(16 + "");
        labels[whiteTile].setVisible(false);
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
        window.startGame();
    }

    private void createLabelArr(JPanel[] panelArray) {
        for (int i = 0; i < size; i++) {
            //saker som alla labels har gemensamt
            int oneIndexed = i + 1; //faktiska nummret
            labels[i] = new JLabel(oneIndexed + "");

            labels[i].setFont(new Font("Arial", Font.BOLD, getHeight() / dim / 2));
            labels[i].setPreferredSize(new Dimension(getWidth() / dim, getHeight() / dim)); //ändra storlek

            labels[i].setHorizontalAlignment(JLabel.CENTER);
            labels[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            labels[i].setOpaque(true);
            labels[i].setBackground(Color.GREEN);
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

    private class LabelMouseListener extends MouseAdapter {
        private final int index;

        public LabelMouseListener(int index) {
            this.index = index;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            whiteTile = moveTile(index, labels, whiteTile, dim);
            if (isGameWon()) {
                System.out.println("you won");
            }
        }
    }
}
