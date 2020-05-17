package model;

import java.awt.Point;

/**
 * A class representing a laser in game, with a location. Statically counts number of standard lasers active.
 */
public class Laser {
    private Point location;
    private boolean standard;
    private static int count;

    /**
     * Constructs a laser with the given location and given default/rapid-fire status.
     * @param spawnLocation the {@code Point} to spawn the laser
     * @param isDefault {@code true} if the laser is a standard laser and should increment laser count,
     * {@code false} if the laser is fired during rapid fire mode and should not.
     */
    public Laser(Point spawnLocation, boolean isDefault) {
        location = spawnLocation;
        standard = isDefault;
        if(isDefault)
            count++;
    }

    /**
     * Moves the laser to the right with given speed.
     * @param speed the distance to move to the right
     */
    public void move(int speed) {
        location.x += speed;
    }

    /**
     * Reduces the count of active lasers by one if this laser was standard. Should only be called if
     * laser is about to be removed from model.
     */
    public void destroy() {
        if (standard)
            count--;
    }

    /**
     * Returns the {@code Point} representing the location of the laser
     * @return the location of the laser
     */
    public Point getLocation() {
        return location;
    }

    /**
     * Returns the number of standard active lasers.
     * @return the number of lasers
     */
    public static int getCount() {
        return count;
    }
}