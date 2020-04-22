import javax.swing.*;
import java.awt.Graphics;

public class Rules extends JPanel{
    private JLabel rules;

    public Rules() {
        rules = new JLabel("test");
        add(rules);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}