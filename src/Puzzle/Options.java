package Puzzle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Options extends JFrame implements ActionListener {

    Window window = new Window();

    private final JFrame parent;
    private JPanel sizePanel = new JPanel();
    JButton decrease = new JButton("-");
    JButton increase = new JButton("+");
    JLabel currentDimension = new JLabel(String.valueOf(window.getDim()));
    private JPanel otherPanel = new JPanel();
    JCheckBox cheatMode = new JCheckBox("Cheat mode");
    JTextField chooseName = new JTextField(12);

    public Options(JFrame parent) {
        this.parent = parent;
        createWindow();
    }

    void createWindow() {
        setLocationRelativeTo(parent);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Options");
        setLayout(new GridLayout(2, 1));

        changeDimension();
        add(sizePanel);
        toggleCheatMode();
        chooseName();
        otherPanel.setLayout(new GridLayout(3, 1));
        add(otherPanel);

        setSize(200, 200);
    }

    void chooseName() {
        chooseName.addActionListener(this);
        otherPanel.add(chooseName);
    }

    void toggleCheatMode() {
        cheatMode.addActionListener(this);
        otherPanel.add(cheatMode);
    }

    void changeDimension() {
        decrease.addActionListener(this);
        increase.addActionListener(this);
        sizePanel.add(decrease);
        sizePanel.add(currentDimension);
        sizePanel.add(increase);
        sizePanel.setVisible(true);

    }

    public static void main(String[] args) {
        new Options(null);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == increase) {
            window.setDim(window.getDim() + 1);
            currentDimension.setText(String.valueOf(window.getDim()));
        } else if (e.getSource() == decrease) {
            window.setDim(window.getDim() - 1);
            currentDimension.setText(String.valueOf(window.getDim()));
        } else if (e.getSource() == cheatMode) {
            window.setCheating(cheatMode.isSelected());
        } else if (e.getSource() == chooseName) {
            //TODO
        }

    }
}
