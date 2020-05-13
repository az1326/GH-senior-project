import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class View extends JPanel {
    private blankPanel blank;
    private JLabel coordinateCont;
    private JLabel coordinateModel;

    private class blankPanel extends JPanel {
        private Point location;
        private boolean toggle;
        BufferedImage cueball;

        public blankPanel() {
            super();
            setPreferredSize(new Dimension(200,200));
            location = new Point(0,0);
            toggle = false;
            setBackground(Color.WHITE);
            cueball = null;
            try {
                cueball = ImageIO.read(getClass().getResourceAsStream("/images/Cueball.png"));
            } catch (IOException e) {

            }
        }

        public void updateLocation(Point p) {
            location = p;
        }

        public void setToggle(boolean t) {
            toggle = t;
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (toggle)
                setBackground(Color.BLACK);
            else   
                setBackground(Color.WHITE);
            g.drawImage(cueball, location.x, location.y, location.x + 20, location.y + 20, 0, 0, 190, 190, null);
        }
    }

    public View() {
        blank = new blankPanel();
        coordinateCont = new JLabel();
        coordinateModel = new JLabel();

        blank.setPreferredSize(new Dimension(200, 200));
        coordinateCont.setPreferredSize(new Dimension(200, 20));
        coordinateModel.setPreferredSize(new Dimension(200, 20));
        setPreferredSize(new Dimension(200, 240));

        coordinateCont.setText("coordinateCont");
        coordinateModel.setText("coordinateModel");

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(blank);
        add(coordinateCont);
        add(coordinateModel);
    }

    public void setCoordinateCont(Point p) {
        coordinateCont.setText("Mouse: (" + p.x + ", " + p.y + ")");
    }

    public void updateView(Point p, boolean toggle) {
        coordinateModel.setText("Mouse: (" + p.x + ", " + p.y + ")");
        blank.updateLocation(p);
        blank.setToggle(toggle);
        blank.repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}