package model;

import java.awt.Point;

public class Star {
    private final int MAX_SPEED = 17;

    private Point location;
    private int speed;

    public Star (int y) {
        location = new Point(803, y);
        speed = (int) Math.floor(Math.random() * MAX_SPEED) + 1;
    }

    public Point getLocation() {
        return location;
    }

    public boolean tick() {
        location.x -= speed;
        return location.x < 0;
    }
}