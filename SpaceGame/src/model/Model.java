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
            checkCollision();
            despawn();
            if (shipHealth <= 0 || baseHealth <= 0)
                gameStatus = GameState.GAME_OVER;
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
    private void checkCollision() {
        ArrayList<Laser> lasersToDestroy = new ArrayList<Laser>();
        ArrayList<Point> asteroidsToDestroy = new ArrayList<Point>();
        Point s = shipLocation;

        //Asteroid check
        for (Point p : asteroidLocations) {
            boolean destroyed = false;

            //Asteroid-Laser check
            for (Laser laser : laserLocations) {
                Point l = laser.getLocation();
                if (checkCircleBoxCollision(p, new Point(l.x - 19, l.y - 2), new Point(l.x + 19, l.y - 2))) {
                    destroyed = true;
                    asteroidsDestroyed++;
                    asteroidsToDestroy.add(p);
                    lasersToDestroy.add(laser);
                    laser.destroy();
                    break;
                }
            }

            //Asteroid-Ship check
            if (!destroyed) {
                if (checkCircleBoxCollision(p, new Point(s.x - 22, s.y - 12), new Point(s.x + 32, s.y + 12))) {
                        asteroidsToDestroy.add(p);
                        shipHealth -= 20;
                    }
            }
        }

        //Ship-Pickup check
        if (pickupLocation != null) {
            boolean xLeft = (pickupLocation.x - 15) >= (s.x - 22) && (pickupLocation.x - 15) <= (s.x + 32);
            boolean xRight = (pickupLocation.x + 15) >= (s.x - 22) && (pickupLocation.x + 15) <= (s.x + 32);
            boolean yUp = (pickupLocation.y - 15) >= (s.y - 12) && (pickupLocation.y - 15) <= (s.y + 12);
            boolean yDown = (pickupLocation.y + 15) >= (s.y - 12) && (pickupLocation.y + 15) <= (s.y + 12);
            if ((xLeft && (yUp || yDown)) || (xRight && (yUp || yDown))) {
                pickupLocation = null;
                pickup = PickupType.NONE;
            }
        }

        asteroidLocations.removeAll(asteroidsToDestroy);
        laserLocations.removeAll(lasersToDestroy);
    }


    private void despawn() {
        ArrayList<Point> asteroidToDespawn = new ArrayList<Point>();
        for (Point p : asteroidLocations) {
            if (p.x < 30) {
                asteroidToDespawn.add(p);
                baseHealth -= 5;
            }
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

    public int getAsteroidsDestroyed() {
        return asteroidsDestroyed;
    }

    public int getShipHealth() {
        return shipHealth;
    }

    public int getBaseHealth() {
        return baseHealth;
    }

    private static boolean checkCircleBoxCollision(Point circle, Point boxUL, Point boxLR) {
        //Check left vertical segment
        if (Math.abs(circle.x - boxUL.x) < 20) {
            if (circle.y > boxUL.y && circle.y < boxLR.y)
                return true;
            else if (Math.sqrt(Math.pow(circle.x - boxUL.x, 2) + Math.pow(circle.y - boxUL.y, 2)) < 20)
                return true;
            else if (Math.sqrt(Math.pow(circle.x - boxUL.x, 2) + Math.pow(circle.y - boxLR.y, 2)) < 20)
                return true;
        }
        //Check right vertical segment
        if (Math.abs(circle.x - boxLR.x) < 20) {
            if (circle.y > boxUL.y && circle.y < boxLR.y)
                return true;
            else if (Math.sqrt(Math.pow(circle.x - boxLR.x, 2) + Math.pow(circle.y - boxLR.y, 2)) < 20)
                return true;
            else if (Math.sqrt(Math.pow(circle.x - boxLR.x, 2) + Math.pow(circle.y - boxUL.y, 2)) < 20)
                return true;
        }
        //Check upper horizontal segment
        if (Math.abs(circle.y - boxUL.y) < 20) {
            if (circle.x > boxUL.x && circle.x < boxLR.x)
                return true;
            else if (Math.sqrt(Math.pow(circle.x - boxUL.x, 2) + Math.pow(circle.y - boxUL.y, 2)) < 20)
                return true;
            else if (Math.sqrt(Math.pow(circle.x - boxLR.x, 2) + Math.pow(circle.y - boxUL.y, 2)) < 20)
                return true;
        }
        //Check lower horizontal segment
        if (Math.abs(circle.y - boxLR.y) < 20) {
            if (circle.x > boxUL.x && circle.x < boxLR.x)
                return true;
            else if (Math.sqrt(Math.pow(circle.x - boxLR.x, 2) + Math.pow(circle.y - boxLR.y, 2)) < 20)
                return true;
            else if (Math.sqrt(Math.pow(circle.x - boxUL.x, 2) + Math.pow(circle.y - boxLR.y, 2)) < 20)
                return true;
        }
        return false;
    }
}