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
    private boolean gameOver;
    private boolean shipDestroyed;
    private int secondsLasted;
    

    public Field() {
        setBackground(Color.BLACK);
        asteroids = new ArrayList<Point>();
        lasers = new ArrayList<Point>();
        ship = null;
        gameOver = false;
        shipDestroyed = false;
        pickup = null;
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

        if (ship != null && !gameOver)
            g.drawImage(Images.SHIP, ship.x - 35, ship.y - 20, ship.x + 35, ship.y + 20, 0, 0, 2400, 1372, null);
        else if (gameOver) {
            if (shipDestroyed){
                g.drawImage(Images.EXPLODE, ship.x - 44, ship.y - 39, ship.x + 44, ship.y + 39, 0, 0, 440, 390, null);
            } else {
                g.drawImage(Images.EXPLODE, 0, 100 - 100, 88, 100 + 100, 0, 0, 440, 390, null);
                g.drawImage(Images.EXPLODE, 0, 300 - 100, 88, 300 + 100, 0, 0, 440, 390, null);
                g.drawImage(Images.EXPLODE, 0, 200 -  39, 88, 200 +  39, 0, 0, 440, 390, null);
                g.drawImage(Images.SHIP, ship.x - 35, ship.y - 20, ship.x + 35, ship.y + 20, 0, 0, 2400, 1372, null);
            }
            Color translucent = new Color(Color.LIGHT_GRAY.getRed(), Color.LIGHT_GRAY.getGreen(),
                Color.LIGHT_GRAY.getBlue(), 127);
            g.setColor(translucent);
            g.fillRect(0, 0, 800, 400);
            g.setColor(Color.WHITE);
            g.setFont(g.getFont().deriveFont(g.getFont().getSize() * 2f));
            String gt = "You survived for " + secondsLasted + " seconds.";
            g.drawString(gt, (800 - g.getFontMetrics().stringWidth(gt)) / 2, 220 + g.getFontMetrics().getAscent());
            g.setColor(Color.RED);
            g.setFont(g.getFont().deriveFont(g.getFont().getSize() * 3f));
            String gg = "GAME OVER";
            g.drawString(gg, (800 - g.getFontMetrics().stringWidth(gg)) / 2, 200);
        }
    }

    public void updateField(ArrayList<Point> asteroidLocations, ArrayList<Point> laserLocations,
            Point shipLocation, Point pickupLocation, boolean health) {
        gameOver = false;
        asteroids = asteroidLocations;
        lasers = laserLocations;
        ship = shipLocation;
        pickup = pickupLocation;
        isHealth = health;
        repaint();
    }

    public void updateGameOver(boolean isShipDead, int gameSeconds) {
        shipDestroyed = isShipDead;
        gameOver = true;
        secondsLasted = gameSeconds;
        repaint();
    }

    public void updateReset() {
        asteroids = new ArrayList<Point>();
        lasers = new ArrayList<Point>();
        ship = null;
        gameOver = false;
        shipDestroyed = false;
        pickup = null;
        repaint();
    }
}