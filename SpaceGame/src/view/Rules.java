package view;

import javax.swing.*;

import java.awt.Color;
import java.awt.BorderLayout;

@SuppressWarnings("serial")
public class Rules extends JPanel{
    private JLabel rules;

    public Rules() {
        rules = new JLabel("<html><div style='text-align: center;'>test1<br>test2<br>test3<div></html>");
        rules.setForeground(Color.WHITE);
        rules.setHorizontalAlignment(SwingConstants.CENTER);

        setBackground(Color.DARK_GRAY);
        setLayout(new BorderLayout());
        add(rules, BorderLayout.CENTER);
    }

    
}