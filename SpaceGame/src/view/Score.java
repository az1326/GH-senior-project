package view;

import javax.swing.*;

import java.awt.Color;
import java.awt.Dimension;

@SuppressWarnings("serial")
public class Score extends JPanel {
    private JLabel rapidIndicator;
    private JLabel destroyedLabel1;
    private JLabel destroyedLabel2;
    private JLabel destroyedValue;

    private int destroyed;
    private int indicatorCount;

    public Score() {
        destroyed = 0;

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

    public void updateScore(int asteroidsDestroyed, boolean isRapidFire) {
        if (destroyed != asteroidsDestroyed) {
            destroyed = asteroidsDestroyed;
            destroyedValue.setText(destroyed + "");
        }
        if (!isRapidFire) {
            rapidIndicator.setForeground(Color.GRAY);
        } else {
            indicatorCount++;
            if (indicatorCount % 10 < 5) {rapidIndicator.setForeground(Color.WHITE);} 
            else {rapidIndicator.setForeground(Color.RED);}
        }
    }

    public void updateGameOver() {
        rapidIndicator.setForeground(Color.GRAY);
    }
    
    public void updateReset() {
        destroyed = 0;
        indicatorCount = 0;
        destroyedValue.setText("0");
        rapidIndicator.setForeground(Color.GRAY);
    }
}