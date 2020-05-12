import javax.swing.*;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Class extends JPanel. Represents the game board.
 */
@SuppressWarnings("serial")
public class Board extends JPanel{
    private boolean[][] baseData;
    private int[][] activeData;
    private boolean gameOver;

    /**
     * Constructor method. Instantiates and sets each field to default.
     */
    public Board() {
        baseData = new boolean[10][20];
        activeData = null;
        gameOver = false;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 20; j++) {
                baseData[i][j] = false;
            }
        }
    }

    /**
     * Updates each field and repaints the board.
     * @param baseInput a 2d boolean array representing the state of each cell
     * @param activeInput a 2d int array representing the location of the current piece
     * @param status {@code true} if the game is over, {@code false} otherwise
     */
    public void update(boolean[][] baseInput, int[][] activeInput, boolean status) {
        baseData = baseInput;
        activeData = activeInput;
        gameOver = status;
        this.repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw Grid
        g.setColor(Color.GRAY);
        g.drawLine(14, 39, 316, 39);
        g.drawLine(316, 39, 316, 641);
        g.drawLine(14, 641, 316, 641);
        g.drawLine(14, 39, 14, 641);
        for (int i = 0; i < 10; i++) {
            g.fillRect(15 + 30 * i, 40, 1, 600);
            g.fillRect(15 + 29 + 30 * i, 40, 1, 600);
        }
        for (int i = 0; i < 20; i++) {
            g.fillRect(15, 40 + 30 * i, 300, 1);
            g.fillRect(15, 40 + 29 + 30 * i, 300, 1);
        }
        //Indicate game over line
        g.setColor(Color.RED);
        g.fillRect(15, 40 + 29 + 30, 300, 2);
        
        //Draw base pieces
        g.setColor(Color.BLACK);
        for (int i = 0; i < baseData.length; i++) {
            for (int j = 0; j < baseData[0].length; j++) {
                if (baseData[i][j]) {
                    g.fillRect(16 + 30 * i, 41 + 30 * j, 28, 28);
                }
            }
        }

        //Draw active piece
        if (activeData != null) {
            g.setColor(Color.BLUE);
            for (int i = 0; i < activeData[0].length; i++) {
                g.fillRect(16 + 30 * activeData[0][i], 41 + 30 * activeData[1][i], 28, 28);
            }
        }

        //Draw game over text if needed
        if (gameOver) {
            g.setColor(Color.WHITE);
            g.fillRect(30, 150, 270, 80);
            g.setColor(Color.BLACK);
            g.setFont(g.getFont().deriveFont(g.getFont().getSize() * 3f));
            String gg = "GAME OVER";
            g.drawString(gg, 30 + (270 - g.getFontMetrics().stringWidth(gg)) / 2,
                230 - (80 - g.getFontMetrics().getAscent()) / 2);
        }
    }//End of paint
}