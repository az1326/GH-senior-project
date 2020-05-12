import javax.swing.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

/**
 * Class extends JPanel. Represents the portion of the view that indicates the
 * next piece to spawn and the score.
 */
@SuppressWarnings("serial")
public class Score extends JPanel {
    private JLabel nextLabel;
    private JLabel scoreLabel;
    private NextPanel next;
    private JLabel score;

    private int scoreVal;

    /**
     * Panel represents only the portion that draws the next piece to spawn
     */
    private class NextPanel extends JPanel {
        private String nextPiece;

        /**
         * Constructor for NextPanel. Sets panel to a white square.
         */
        public NextPanel() {
            super();
            nextPiece = null;
            setPreferredSize(new Dimension(75, 75));
            setBackground(Color.WHITE);
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.GRAY);
            if (nextPiece != null) { //Proceed only if there is a valid next piece
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

        /**
         * Updates the string representing the type of piece
         * @param nextPiece the String representing the next piece
         */
        public void updateNextPiece(String nextPiece) {
            this.nextPiece = nextPiece;
        }
    }

    /**
     * Constructor for Score. Creates and adds each component to the panel.
     */
    public Score() {
        scoreVal = 0;

        nextLabel = new JLabel("Next:");
        nextLabel.setHorizontalAlignment(JLabel.CENTER);
        nextLabel.setVerticalAlignment(JLabel.BOTTOM);

        scoreLabel = new JLabel("Score:");
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);
        scoreLabel.setVerticalAlignment(JLabel.BOTTOM);

        next = new NextPanel();
        
        score = new JLabel("0000");
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

    /**
     * Formats the score to include at least 4 characters.
     * @return the formatted score as a String
     */
    private String formatScore() {
        String prelim = String.valueOf(scoreVal);
        if (scoreVal < 1000) {
            int index = prelim.length();
            for (int i = 4; i > index; i--) {
                prelim = "0" + prelim;
            }
        }
        return prelim;
    }

    /**
     * Updates the score if needed.
     * @param scoreVal the current score
     */
    public void updateScore(int scoreVal) {
        if (this.scoreVal != scoreVal) {
            this.scoreVal = scoreVal;
            score.setText(formatScore());
        }
    }

    /**
     * Updates the type of piece next to spawn
     * @param nextPiece the String representing the next piece
     */
    public void updateNext(String nextPiece) {
        next.updateNextPiece(nextPiece);
        next.repaint();
    }
}