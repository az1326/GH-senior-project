package app;

import javax.swing.*;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;


public class View extends JPanel{
    //private JPanel mainPanel = new JPanel();
    private Controller cont;

    private JTextArea ta;

    public View() {
        setBackground(Color.GRAY);
        ta = new JTextArea(1, 5);
        ta.setEditable(false);
        add(ta);
    }

    public void registerController(Controller source) {
        cont = source;
    }

    public void updateDirection(Model.Dir direction) {
        ta.setText(direction.toString());
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.GRAY);
        g.fillRect(50,50,50,50);
        g.setColor(Color.WHITE);
        if (ta.getText().equalsIgnoreCase("RIGHT")) g.fillRect(90,70,10,10);
        if (ta.getText().equalsIgnoreCase("LEFT")) g.fillRect(50,70,10,10);
        if (ta.getText().equalsIgnoreCase("UP")) g.fillRect(70,50,10,10);
        if (ta.getText().equalsIgnoreCase("DOWN")) g.fillRect(70,90,10,10);
    }
}