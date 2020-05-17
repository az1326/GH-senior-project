package model;

import java.awt.Point;

public class Explosion {
    private final int DURATION = 5;

    private Point location;
    private int time;

    public Explosion(Point p) {
        location = p;
        time = 0;
    }

    public boolean tick() {
        time++;
        return time > DURATION;
    }
    
    public Point getLocation() {
        return location;
    }
}