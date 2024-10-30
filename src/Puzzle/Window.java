package Puzzle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Window extends JFrame {

    int dim = 4; //dimension på spelplanens sida
    int size = dim * dim; //totala storleken
    int whiteTile = size - 1; //Håller koll på vita rutans position, uppdaterad i moveTile()
    JPanel grid = new JPanel();
    JLabel[] labels = new JLabel[size];

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

    void isGameWon() {

    }

    void shuffle() {
        //gör grejer med labels arrayen
    }

    void moveTile(int tileClicked) {

        if (isConnected(tileClicked, whiteTile)) {
            labels[whiteTile].setText(labels[tileClicked].getText());
            labels[whiteTile].setBackground(Color.RED);
            labels[whiteTile].setVisible(true);

            labels[tileClicked].setVisible(false);

            whiteTile = tileClicked;
        }
    }


    public static void main(String[] args) {
        Window window = new Window();
        window.window();
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
            moveTile(index);
        }
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
    boolean isConnected(int tileClicked, int whiteTile) {

        int startOfRow = whiteTile % dim;
        int endOfRow = (whiteTile + 1) % dim;

        if (0 != startOfRow && tileClicked == whiteTile - 1) {
            return true;
        }
        if (0 != endOfRow && tileClicked == whiteTile + 1) {
            return true;
        }

        return tileClicked == whiteTile - dim || tileClicked == whiteTile + dim;
    }

}