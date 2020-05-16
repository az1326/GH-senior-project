package view;

import javax.swing.*;

import java.awt.Point;
import java.util.ArrayList;

import java.awt.Color;
import java.awt.Graphics;

@SuppressWarnings("serial")
public class Field extends JPanel {
    private ArrayList<Point> asteroids;
    private ArrayList<Point> lasers;
    private Point ship;
    private Point pickup;
    private boolean isHealth;
    

    public Field() {
        setBackground(Color.BLACK);
        asteroids = new ArrayList<Point>();
        lasers = new ArrayList<Point>();
        ship = null;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //Draw base
        g.drawImage(Images.BASE, 0, 0, 77, 400, 0, 0, 135, 700, null);

        if (pickup != null) {
            if (isHealth) {
                g.drawImage(Images.HEALTH, pickup.x - 15, pickup.y - 15, pickup.x + 15, pickup.y + 15,
                    0, 0, 220, 220, null);
            } else {
                g.drawImage(Images.RAPID, pickup.x - 15, pickup.y - 15, pickup.x + 15, pickup.y + 15,
                    0, 0, 220, 220, null);
            }
        }

        for (Point p : asteroids) {
            g.drawImage(Images.ASTEROID, p.x - 20, p.y - 20, p.x + 20, p.y + 20, 0, 0, 320, 320, null);
        }

        for (Point p : lasers) {
            g.drawImage(Images.LASER, p.x - 19, p.y - 2, p.x + 19, p.y + 2, 0, 0, 380, 40, null);
        }

        if (ship != null)
            g.drawImage(Images.SHIP, ship.x - 35, ship.y - 20, ship.x + 35, ship.y + 20, 0, 0, 2400, 1372, null);
    }

    public void updateField(ArrayList<Point> asteroidLocations, ArrayList<Point> laserLocations,
            Point shipLocation, Point pickupLocation, boolean health) {
        asteroids = asteroidLocations;
        lasers = laserLocations;
        ship = shipLocation;
        pickup = pickupLocation;
        isHealth = health;
        repaint();
    }
}