import javax.swing.*;

import java.awt.Graphics;

/**
 * Class extends JPanel. Includes a label providing information about the app.
 */
@SuppressWarnings("serial")
public class Rules extends JPanel{
    private JLabel rules;

    public Rules() {
        rules = new JLabel("<html><div style='text-align: center;'>Tetris<br><br>Left/Right Arrow keys"
        + "<br>to shift the piece.<br><br>Down Arrow key<br>to drop piece.<br><br>z/x keys to rotate piece."
        + "<br><br>All pieces will<br>spawn above red line.<br><br>Game Over when a piece"
        + "<br>locks above the red line.<br><br>Clear lines to gain points.<br><br>Game speeds up as"
        + "<br>more points are earned<br><br>Survive as long as you can!</div></html>");
        add(rules);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}