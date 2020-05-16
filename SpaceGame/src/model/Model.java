package model;

import java.util.ArrayList;
import java.awt.Point;

public class Model {
    private final int MAX_LASERS = 5;
    private final int MAX_SPEED = 15;
    private final int LASER_SPEED = 5;

    private Point shipLocation;
    private ArrayList<Point> asteroidLocations;
    private ArrayList<Laser> laserLocations;
    private Point pickupLocation;

    private int shipHealth;
    private int baseHealth;
    private int asteroidsDestroyed;

    private int gameTime;
    private int asteroidSpeed;
    private int asteroidSpawnRate;
    private int pickupSpawnRate;

    private GameState gameStatus;
    private FireState rapidStatus;

    private Point mouseTarget;
    
    public enum GameState {
        RESET, IN_PROGRESS, GAME_OVER;
    }

    public enum FireState {
        DEFAULT, RAPID;
    }

    public Model() {
        shipLocation = null;
        asteroidLocations = new ArrayList<Point>();
        laserLocations = new ArrayList<Laser>();
        pickupLocation = null;

        shipHealth = 100;
        baseHealth = 100;
        asteroidsDestroyed = 0;

        gameTime = 0;
        asteroidSpeed = 3;
        asteroidSpawnRate = 25;
        pickupSpawnRate = 250;

        gameStatus = GameState.RESET;
        rapidStatus = FireState.DEFAULT;

        mouseTarget = new Point(0,200);
    }

    public void start() {
        gameStatus = GameState.IN_PROGRESS;
        shipLocation = new Point(0,200);
    }

    public void reset() {
        shipLocation = null;
        asteroidLocations.clear();
        laserLocations.clear();
        pickupLocation = null;

        shipHealth = 100;
        baseHealth = 100;
        asteroidsDestroyed = 0;

        gameTime = 0;
        asteroidSpeed = 3;

        gameStatus = GameState.RESET;
        rapidStatus = FireState.DEFAULT;

        mouseTarget = new Point(0,0);
    }

    private void gameOver() {
        gameStatus = GameState.GAME_OVER;
    }

    /**
     * Move Asteroids
     * Move Lasers
     * Move Pickup
     * Move Ship
     * Spawn Asteroid if needed
     * Check Collisions
     * Check despawn
     */
    public void tick() {
        if (gameStatus == GameState.IN_PROGRESS) {
            moveAsteroids();
            moveLasers();
            movePickup();
            moveShip();
            if (gameTime % asteroidSpawnRate == 0)
                spawnAsteroid();
            gameTime++;
        }

    }

    private void moveAsteroids() {
        for (Point p : asteroidLocations) {
            p.x -= asteroidSpeed;
        }
    }

    private void moveLasers() {
        for (Laser l : laserLocations) {
            l.move(LASER_SPEED);
        }
    }

    private void movePickup() {
        if (pickupLocation != null) {
            pickupLocation.x -= asteroidSpeed;
        }
    }

    private void moveShip() {
        int xDiff = mouseTarget.x - shipLocation.x;
        int yDiff = mouseTarget.y - shipLocation.y;
        double mag = Math.sqrt(xDiff * xDiff + yDiff * yDiff);

        if (mag > MAX_SPEED) {
            shipLocation.x += Math.round(xDiff * (MAX_SPEED / mag));
            shipLocation.y += Math.round(yDiff * (MAX_SPEED / mag));
        } else {
            shipLocation.x += xDiff;
            shipLocation.y += yDiff;
        }
    }

    private void spawnAsteroid() {
        Point p = new Point(800 + 40,40 + (int) Math.floor(Math.random() * 320));
        asteroidLocations.add(p);
    }

    public void fireLaser() {
        if (rapidStatus == FireState.DEFAULT) {
            if (Laser.getCount() >= 5) {return;}
            else {laserLocations.add(new Laser(new Point(shipLocation.x + 21, shipLocation.y), true));}
        } else {
            laserLocations.add(new Laser(new Point(shipLocation.x + 21, shipLocation.y), false));
        }
    }

    public void updateMouseLocation(Point location) {
        mouseTarget = location;
    }

    public ArrayList<Point> getAsteroids() {
        return asteroidLocations;
    }

    public Point getShip() {
        return shipLocation;
    }

    public ArrayList<Point> getLasers() {
        ArrayList<Point> temp = new ArrayList<Point>();
        for (Laser l : laserLocations) {
            temp.add(l.getLocation());
        }
        return temp;
    }
}