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

    void startGame() {
        shuffle(labels);
        if (isSolveable(labels)) {
            System.out.println("solveable");
        } else {
            System.out.println("not solveable");
            startGame();
        }
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

        add(grid);
        pack();
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
        for (int i = 0 ; i < size ; i++) {
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
        public void mouseClicked(MouseEvent e) {
            System.out.println(index + " actual index");
            System.out.println(labels[index].getText());
            System.out.println("----");
            whiteTile = moveTile(index, labels, whiteTile, dim);
        }
    }
}
