package view;

import javax.swing.*;

import java.awt.Color;
import java.awt.Graphics;

@SuppressWarnings("serial")
public class Field extends JPanel {
    // Data Fields

    public Field() {
        setBackground(Color.BLACK);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //Draw base
        g.drawImage(Images.BASE, 0, 0, 77, 400, 0, 0, 135, 700, null);
    }
}