package view;

import javax.swing.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

@SuppressWarnings("serial")
public class Info extends JPanel {
    private JLabel healthText;
    private JLabel rapidText;
    private Icons icons;
    
    private class Icons extends JPanel {
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            setBackground(Color.DARK_GRAY);
            g.drawImage(Images.HEALTH, 5, 5, 45, 45, 0, 0, 220, 220, null);
            g.drawImage(Images.RAPID, 5, 55, 45, 95, 0, 0, 220, 220, null);
        }
    }

    public Info() {
        healthText = new JLabel("<html>Pick up to<br>recover health.</html>");
        rapidText = new JLabel("<html>Pick up to<br>fire more lasers.</html>");
        icons = new Icons();

        healthText.setForeground(Color.WHITE);
        rapidText.setForeground(Color.WHITE);

        JPanel tempPanel = new JPanel();
        tempPanel.setLayout(new BoxLayout(tempPanel, BoxLayout.PAGE_AXIS));
        tempPanel.add(Box.createVerticalGlue());
        tempPanel.add(healthText);
        tempPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        tempPanel.add(rapidText);
        tempPanel.add(Box.createVerticalGlue());
        tempPanel.setPreferredSize(new Dimension(120, 100));
        tempPanel.setBackground(Color.DARK_GRAY);

        icons.setPreferredSize(new Dimension(50, 100));

        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        setBackground(Color.DARK_GRAY);
        add(icons);
        add(tempPanel);
    }
}