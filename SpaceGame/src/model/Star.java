package model;

import java.awt.Point;

/**
 * A class representing a backgroundstar in game, with a location and a movement speed.
 */
public class Star {
    private final int MAX_SPEED = 17;

    private Point location;
    private int speed;

    /**
     * Constructor class for {@code Star}. Creates a {@code Star} at the given y-coordinate, barely offscreen in the
     * positive-x direction, and with a random speed between 1 and 17.
     * @param y the y-coordinate of the star
     */
    public Star (int y) {
        location = new Point(803, y);
        speed = (int) Math.floor(Math.random() * MAX_SPEED) + 1;
    }

    /**
     * Returns the {@code Point} representing the location of the star
     * @return the location of the star
     */
    public Point getLocation() {
        return location;
    }

    /**
     * Moves the star to the left depending on its speed.
     * @return {@code true} if the star is now offscreen and can be removed, {@code false} otherwise
     */
    public boolean tick() {
        location.x -= speed;
        return location.x < 0;
    }
}