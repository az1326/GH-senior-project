package model;

import java.awt.Point;

/**
 * Class represents an explosion with a location and a counter for time active.
 */
public class Explosion {
    private final int DURATION = 10;

    private Point location;
    private int time;

    /**
     * Constructs an explosion at the given location
     * @param p the location
     */
    public Explosion(Point p) {
        location = p;
        time = 0;
    }

    /**
     * Increments the time active for the explosion.
     * @return {@code true} if the explosion has lasted longer than the desired duration,
     * {@code false} otherwise.
     */
    public boolean tick() {
        time++;
        return time > DURATION;
    }
    
    /**
     * Returns a {@code Point} representing the location of the explosion
     * @return the location of the explosion
     */
    public Point getLocation() {
        return location;
    }
}