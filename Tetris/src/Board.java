import javax.swing.*;

import java.awt.Color;
import java.awt.Graphics;

@SuppressWarnings("serial")
public class Board extends JPanel{
    private BasePanel base;
    private ActivePanel active;
    private boolean tickState;

    private class BasePanel extends JPanel {
        public BasePanel() {
            super();
            //setBackground(Color.GRAY);
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            //Draw Grid
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
            
            /* Handle base updates */
        }
    }

    private class ActivePanel extends JPanel {
        public ActivePanel() {
            super();
            setOpaque(false);
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            /* Handle active updates */
        }
    }

    public Board() {
        tickState = true;

        base = new BasePanel();

        active = new ActivePanel();

        setLayout(null);
        add(active);
        active.setBounds(0,0,325,700);
        add(base);
        base.setBounds(0,0,325,700);
    }

    public void updateBase() {
        base.repaint();
    }

    public void updateActive() {
        active.repaint();
        tickState = !tickState;
    }
}