import javax.swing.*;
import java.awt.Graphics;
import java.awt.Dimension;

public class State extends JPanel{
    private JLabel gameState;
    private JButton restart;

    public State() {
        gameState = new JLabel("Click to Start");
        gameState.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        restart = new JButton("Start");
        restart.setAlignmentX(JButton.CENTER_ALIGNMENT);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(gameState);
        add(Box.createRigidArea(new Dimension(175,0)));
        add(restart);
    }
}