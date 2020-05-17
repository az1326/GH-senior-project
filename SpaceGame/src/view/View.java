package view;

import javax.swing.*;

import java.util.ArrayList;

import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Point;

@SuppressWarnings("serial")
public class View extends JPanel{
    private Field field;
    private Health health;
    private Info info;
    private Score score;
    private Rules rules;

    /**
     * Creates the content pane for the frame.
     */
    public View() {
        //Instantiate panels
        field = new Field();
        health = new Health();
        info = new Info();
        score = new Score();
        rules = new Rules();

        //Set preferred sizes
        field.setPreferredSize(new Dimension(800, 400));
        health.setPreferredSize(new Dimension(100, 100));
        info.setPreferredSize(new Dimension(170, 100));
        score.setPreferredSize(new Dimension(100, 100));
        rules.setPreferredSize(new Dimension(400, 100));

        //Instantiate formatting panels
        JPanel hPanel1 = new JPanel();
        JPanel hPanel2 = new JPanel();

        //First horizontal panel
        hPanel1.setLayout(new BoxLayout(hPanel1, BoxLayout.LINE_AXIS));
        hPanel1.add(Box.createRigidArea(new Dimension(10, 400)));
        hPanel1.add(field);
        hPanel1.add(Box.createRigidArea(new Dimension(10, 400)));

        //Second horizontal panel
        hPanel2.setLayout(new BoxLayout(hPanel2, BoxLayout.LINE_AXIS));
        hPanel2.add(Box.createRigidArea(new Dimension(10, 100)));
        hPanel2.add(health);
        hPanel2.add(Box.createRigidArea(new Dimension(10, 100)));
        hPanel2.add(info);
        hPanel2.add(Box.createRigidArea(new Dimension(10, 100)));
        hPanel2.add(score);
        hPanel2.add(Box.createRigidArea(new Dimension(10, 100)));
        hPanel2.add(rules);
        hPanel2.add(Box.createRigidArea(new Dimension(10, 100)));

        //Combine all
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(Box.createRigidArea(new Dimension(820, 10)));
        add(hPanel1);
        add(Box.createRigidArea(new Dimension(820, 10)));
        add(hPanel2);
        add(Box.createRigidArea(new Dimension(820, 10)));
    }

    /**
     * Returns the field panel for mouse listener purposes.
     * @return the field panel
     */
    public JPanel getField() {
        return field;
    }

    /**
     * Updates all parts of the view from given data.
     * @param asteroidLocations an {@code ArrayList<Point>} representing the location of each asteroid
     * @param laserLocations an {@code ArrayList<Point>} representing the location of each laser
     * @param explosionLocations an {@code ArrayList<Point>} representing the location of each explosion
     * @param starLocations an {@code ArrayList<Point>} representing the location of each star
     * @param shipLocation a {@code Point} representing the location of the spaceship
     * @param pickupLocation a {@code Point} representing the location of the power-up. {@code null} if none
     * @param isHealth {@code true} if the power-up is a repair power-up, {@code false} otherwise
     * @param shipHealth the health of the ship
     * @param baseHealth the health of the base
     * @param asteroidsDestroyed the number of asteroids destroyed this game
     * @param isRapidFire {@code true} if rapid fire is active, {@code false} otherwise
     */
    public void updateViewInProgress(ArrayList<Point> asteroidLocations, ArrayList<Point> laserLocations,
            ArrayList<Point> explosionLocations, ArrayList<Point> starLocations, Point shipLocation,
            Point pickupLocation, boolean isHealth, int shipHealth, int baseHealth, int asteroidsDestroyed,
            boolean isRapidFire) {
        field.updateField(asteroidLocations, laserLocations, explosionLocations, starLocations, shipLocation,
            pickupLocation, isHealth);
        health.updateHealth(shipHealth, baseHealth);
        score.updateScore(asteroidsDestroyed, isRapidFire);
    }

    /**
     * Updates all parts of the view to game over status.
     * @param shipHealth the health of the ship
     * @param baseHealth the health of the base
     * @param gameSeconds how long the game lasted in seconds
     */
    public void updateViewGameOver(int shipHealth, int baseHealth, int gameSeconds) {
        field.updateGameOver(shipHealth <= 0, gameSeconds);
        health.updateHealth(shipHealth, baseHealth);
        score.updateGameOver();
    }

    /**
     * Resets all parts of the view.
     */
    public void updateViewReset() {
        field.updateReset();
        score.updateReset();
        health.updateReset();
    }
}