package view;

import javax.swing.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

@SuppressWarnings("serial")
public class Score extends JPanel {
    private JLabel rapidIndicator;
    private JLabel destroyedLabel1;
    private JLabel destroyedLabel2;
    private JLabel destroyedValue;

    public Score() {
        rapidIndicator = new JLabel("Rapid Fire");
        destroyedLabel1 = new JLabel("Asteroids");
        destroyedLabel2 = new JLabel("Destroyed:");
        destroyedValue = new JLabel("0");

        rapidIndicator.setAlignmentX(CENTER_ALIGNMENT);
        rapidIndicator.setFont(rapidIndicator.getFont().deriveFont(16f));
        rapidIndicator.setForeground(Color.GRAY);

        destroyedLabel1.setAlignmentX(CENTER_ALIGNMENT);
        destroyedLabel1.setForeground(Color.WHITE);
        destroyedLabel2.setAlignmentX(CENTER_ALIGNMENT);
        destroyedLabel2.setForeground(Color.WHITE);

        destroyedValue.setAlignmentX(CENTER_ALIGNMENT);
        destroyedValue.setForeground(Color.WHITE);

        setBackground(Color.DARK_GRAY);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(Box.createVerticalGlue());
        add(rapidIndicator);
        add(Box.createRigidArea(new Dimension(0, 15)));
        add(destroyedLabel1);
        add(destroyedLabel2);
        add(destroyedValue);
        add(Box.createVerticalGlue());
    }
    
}