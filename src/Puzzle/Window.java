package Puzzle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Window extends JFrame {

    int dim = 4;
    int size = dim * dim;
    JPanel grid = new JPanel();

    void window() {
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        grid.setLayout(new GridLayout(dim, dim));
        grid.setOpaque(false);
        JPanel[] panelArray = new JPanel[size];

        //Skapa en array med paneler som sen ska hålla labelsen
        for (int i = 0 ; i < size ; i++) {
            JPanel panel = new JPanel();
            grid.add(panel);
            panelArray[i] = panel;
        }

        JLabel[] labels = new JLabel[size];
        for (int i = 0; i < size; i++) {
            //saker som alla labels har gemensamt
            int oneIndexed = i + 1;
            labels[i] = new JLabel(oneIndexed + "");
            labels[i].setPreferredSize(new Dimension(60, 60)); //ändra storlek

            //saker som bara har med tomma rutan att göra
            if (i == size - 1) {
                labels[i].setText("");
                labels[i].setBackground(Color.WHITE);
                continue;
            }

            //saker som bara har med spelknapparna att göra
            labels[i].setBackground(Color.BLUE);
            labels[i].setHorizontalAlignment(JLabel.CENTER);
            labels[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            labels[i].setOpaque(true);

            labels[i].addMouseListener(new LabelMouseListener(oneIndexed));
            panelArray[i].add(labels[i]);

        }

        add(grid);
        pack();

    }

    public static void main(String[] args) {
        Window window = new Window();
        window.window();
    }

    private class LabelMouseListener extends MouseAdapter {
        private final int labelNumber;

        public LabelMouseListener(int labelNumber) {
            this.labelNumber = labelNumber;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println("Label " + labelNumber + " clicked.");
        }
    }

}
