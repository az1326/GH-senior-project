package app;

import javax.swing.*;
import java.awt.Graphics;

public class View extends JPanel{
    //private JPanel mainPanel = new JPanel();
    private Controller cont;

    private JTextArea ta;

    public View() {
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
        
    }
}