package model;

import java.util.ArrayList;
import java.awt.Point;

public class Model {
    private final int MAX_LASERS = 5;
    private final int MAX_SPEED = 15;
    private final int LASER_SPEED = 5;
    private final int PICKUP_SPEED = 4;
    private final int PICKUP_SPAWN_RATE = 500;

    private Point shipLocation;
    private ArrayList<Point> asteroidLocations;
    private ArrayList<Laser> laserLocations;
    private Point pickupLocation;
    private PickupType pickup;

    private int shipHealth;
    private int baseHealth;
    private int asteroidsDestroyed;

    private int gameTime;
    private int asteroidSpeed;
    private int asteroidSpawnRate;

    private GameState gameStatus;
    private FireState rapidStatus;

    private Point mouseTarget;
    
    public enum GameState {
        RESET, IN_PROGRESS, GAME_OVER;
    }

    public enum FireState {
        DEFAULT, RAPID;
    }

    public enum PickupType {
        HEALTH, RAPID_FIRE, NONE;
    }

    public Model() {
        shipLocation = null;
        asteroidLocations = new ArrayList<Point>();
        laserLocations = new ArrayList<Laser>();
        pickupLocation = null;

        shipHealth = 100;
        baseHealth = 100;
        asteroidsDestroyed = 0;

        gameTime = 1;
        asteroidSpeed = 3;
        asteroidSpawnRate = 25;

        gameStatus = GameState.RESET;
        rapidStatus = FireState.DEFAULT;
        pickup = PickupType.NONE;

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

        gameTime = 1;
        asteroidSpeed = 3;

        gameStatus = GameState.RESET;
        rapidStatus = FireState.DEFAULT;
        pickup = PickupType.NONE;

        mouseTarget = new Point(0,200);
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
            if (gameTime % PICKUP_SPAWN_RATE == 0)
                spawnPickup();
            despawn();
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
            pickupLocation.x -= PICKUP_SPEED;
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
        Point p = new Point(800 + 20, 20 + (int) Math.floor(Math.random() * 360));
        asteroidLocations.add(p);
    }

    private void spawnPickup() {
        pickupLocation = new Point(800 + 15, 15 + (int) Math.floor(Math.random() * 370));
        if (Math.random() < 0.5) {pickup = PickupType.HEALTH;}
        else {pickup = PickupType.RAPID_FIRE;}
    }

    public void fireLaser() {
        if (rapidStatus == FireState.DEFAULT) {
            if (Laser.getCount() >= MAX_LASERS) {return;}
            else {laserLocations.add(new Laser(new Point(shipLocation.x + 21, shipLocation.y), true));}
        } else {
            laserLocations.add(new Laser(new Point(shipLocation.x + 21, shipLocation.y), false));
        }
    }

    //Collision Detection


    private void despawn() {
        ArrayList<Point> asteroidToDespawn = new ArrayList<Point>();
        for (Point p : asteroidLocations) {
            if (p.x < -20) {asteroidToDespawn.add(p);}
        }
        asteroidLocations.removeAll(asteroidToDespawn);

        ArrayList<Laser> laserToDespawn = new ArrayList<Laser>();
        for (Laser l : laserLocations) {
            if (l.getLocation().x > 819) {
                l.destroy();
                laserToDespawn.add(l);
            }
        }
        laserLocations.removeAll(laserToDespawn);

        if (pickupLocation != null && pickupLocation.x < -15) {
            pickupLocation = null;
            pickup = PickupType.NONE;
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

    public Point getPickupLocation() {
        if (pickupLocation == null) {
            return null;
        }
        return pickupLocation;
    }

    public boolean getPickupType() {
        return pickup == PickupType.HEALTH;
    }
}