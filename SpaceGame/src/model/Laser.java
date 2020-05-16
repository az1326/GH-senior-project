package model;

import java.awt.Point;

public class Laser {
    private Point location;
    private boolean standard;
    private static int count;

    public Laser(Point spawnLocation, boolean isDefault) {
        location = spawnLocation;
        standard = isDefault;
        if(isDefault)
            count++;
    }

    public void move(int speed) {
        location.x += speed;
    }

    public void destroy() {
        if (standard)
            count--;
    }

    public Point getLocation() {
        return location;
    }

    public static int getCount() {
        return count;
    }
}