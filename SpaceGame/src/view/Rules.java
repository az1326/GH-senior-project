package view;

import javax.swing.*;

import java.awt.Color;
import java.awt.BorderLayout;

@SuppressWarnings("serial")
public class Rules extends JPanel{
    private JLabel rules;

    public Rules() {
        rules = new JLabel("<html><div style='text-align: center;'>Welcome to SpaceGame! Defend your base from "
        + "asteroids.<br>Use the mouse to move your ship and click to fire a laser.<br>Be economical, you can only " 
        + "have 5 lasers active at any time.<br>Pick up powerups to repair damage or activate rapid fire." 
        + "<br>The game is over when your base health or ship health is<br>reduced to 0. Survive as long as you can!" 
        + "<div></html>");
        rules.setForeground(Color.WHITE);
        rules.setHorizontalAlignment(SwingConstants.CENTER);

        setBackground(Color.DARK_GRAY);
        setLayout(new BorderLayout());
        add(rules, BorderLayout.CENTER);
    }

    
}