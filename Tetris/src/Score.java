import javax.swing.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

@SuppressWarnings("serial")
public class Score extends JPanel {
    private JLabel nextLabel;
    private JLabel scoreLabel;
    private NextPanel next;
    private JLabel score;

    private class NextPanel extends JPanel {
        public NextPanel() {
            super();
            setPreferredSize(new Dimension(75, 75));
            setBackground(Color.WHITE);
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.BLACK);
            g.fillRect(15, 15, 45, 45);
            System.out.println("Update Next"); //TODO: println
            /* Handle next piece update */
        }
    }

    public Score() {
        nextLabel = new JLabel("Next:");
        nextLabel.setHorizontalAlignment(JLabel.CENTER);
        nextLabel.setVerticalAlignment(JLabel.BOTTOM);

        scoreLabel = new JLabel("Score:");
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);
        scoreLabel.setVerticalAlignment(JLabel.BOTTOM);

        next = new NextPanel();
        
        score = new JLabel("XXXX");
        score.setHorizontalAlignment(JLabel.CENTER);
        score.setVerticalAlignment(JLabel.TOP);

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        add(nextLabel, c);

        c.gridx = 1;
        c.gridy = 0;
        add(scoreLabel, c);

        c.gridx = 0;
        c.gridy = 1;
        add(next, c);

        c.gridx = 1;
        c.gridy = 1;
        add(score, c);
    }

    public void updateNext() {
        next.repaint();
    }
}