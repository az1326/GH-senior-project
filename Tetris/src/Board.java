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
            setBackground(Color.GRAY);
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
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
            g.setColor(Color.BLACK);
            if (tickState) {g.fillRect(10, 10, 50, 50);}
            System.out.println("Update Active"); //TODO: println
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