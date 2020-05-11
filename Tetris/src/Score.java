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
        private String nextPiece;

        public NextPanel() {
            super();
            nextPiece = null;
            setPreferredSize(new Dimension(75, 75));
            setBackground(Color.WHITE);
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.GRAY);
            if (nextPiece != null) {
                switch (nextPiece) {
                    case "O":
                        g.fillRect(21, 21, 15, 15);
                        g.fillRect(21, 39, 15, 15);
                        g.fillRect(39, 21, 15, 15);
                        g.fillRect(39, 39, 15, 15);
                        break;
                    case "I":
                        g.fillRect(3, 30, 15, 15);
                        g.fillRect(21, 30, 15, 15);
                        g.fillRect(39, 30, 15, 15);
                        g.fillRect(57, 30, 15, 15);
                        break;
                    case "S":
                        g.fillRect(12, 39, 15, 15);
                        g.fillRect(30, 39, 15, 15);
                        g.fillRect(30, 21, 15, 15);
                        g.fillRect(48, 21, 15, 15);
                        break;
                    case "Z":
                        g.fillRect(12, 21, 15, 15);
                        g.fillRect(30, 39, 15, 15);
                        g.fillRect(30, 21, 15, 15);
                        g.fillRect(48, 39, 15, 15);
                        break;
                    case "T":
                        g.fillRect(12, 21, 15, 15);
                        g.fillRect(30, 39, 15, 15);
                        g.fillRect(30, 21, 15, 15);
                        g.fillRect(48, 21, 15, 15);
                        break;
                    case "J":
                        g.fillRect(12, 21, 15, 15);
                        g.fillRect(30, 21, 15, 15);
                        g.fillRect(48, 21, 15, 15);
                        g.fillRect(48, 39, 15, 15);
                        break;
                    case "L":
                        g.fillRect(12, 21, 15, 15);
                        g.fillRect(12, 39, 15, 15);
                        g.fillRect(30, 21, 15, 15);
                        g.fillRect(48, 21, 15, 15);
                        break;
                    default:
                        break;
                }
            }
        }

        public void updateNextPiece(String nextPiece) {
            this.nextPiece = nextPiece;
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

    public void updateNext(String nextPiece) {
        next.updateNextPiece(nextPiece);
        next.repaint();
    }
}