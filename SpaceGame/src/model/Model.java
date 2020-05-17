package model;

import java.util.ArrayList;
import java.awt.Point;

public class Model {
    //Constant fields
    private final int MAX_LASERS = 5;
    private final int MAX_SPEED = 15;
    private final int LASER_SPEED = 5;
    private final int PICKUP_SPEED = 4;
    private final int PICKUP_SPAWN_RATE = 500;
    private final int RAPID_MAX_TIME = 200;
    private final int STAR_SPAWN_RATE = 3;

    //Location fields
    private Point shipLocation;
    private ArrayList<Point> asteroidLocations;
    private ArrayList<Laser> laserLocations;
    private ArrayList<Explosion> explosionLocations;
    private ArrayList<Star> starLocations;
    private Point pickupLocation;
    private PickupType pickup;

    //Game data fields
    private int shipHealth;
    private int baseHealth;
    private int asteroidsDestroyed;

    //Game control fields
    private int gameTime;
    private int asteroidSpeed;
    private int asteroidSpawnRate;
    private int rapidTime;

    //Game status fields
    private GameState gameStatus;
    private FireState rapidStatus;

    //Input field
    private Point mouseTarget;
    
    /**
     * Possible game states
     */
    public enum GameState {
        RESET, IN_PROGRESS, GAME_OVER;
    }

    /**
     * Possible rapid fire states
     */
    public enum FireState {
        DEFAULT, RAPID;
    }

    /**
     * Possible Power-up types
     */
    private enum PickupType {
        HEALTH, RAPID_FIRE, NONE;
    }

    /**
     * Creates a model
     */
    public Model() {
        //Instantiate ArrayLists
        asteroidLocations = new ArrayList<Point>();
        laserLocations = new ArrayList<Laser>();
        explosionLocations = new ArrayList<Explosion>();
        starLocations = new ArrayList<Star>();

        reset();
    }

    /**
     * Indicates a game is now in progress
     */
    public void start() {
        gameStatus = GameState.IN_PROGRESS;
        shipLocation = new Point(0,200);
    }

    /**
     * Resets the value of all fields to default
     */
    public void reset() {
        shipLocation = null;
        asteroidLocations.clear();
        laserLocations.clear();
        explosionLocations.clear();
        starLocations.clear();
        pickupLocation = null;

        shipHealth = 100;
        baseHealth = 100;
        asteroidsDestroyed = 0;

        gameTime = 1;
        asteroidSpeed = 3;
        asteroidSpawnRate = 40;

        rapidTime = 0;

        gameStatus = GameState.RESET;
        rapidStatus = FireState.DEFAULT;
        pickup = PickupType.NONE;

        mouseTarget = new Point(0,200);
    }

    /**
     * Indicates the game is over
     */
    private void gameOver() {
        gameStatus = GameState.GAME_OVER;
    }

    /**
     * Handles a tick of the game. Moves objects and spawns objects if needed. Checks for collisions
     * and despawns. Checks for game over. Also handles other relevant game logic.
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
            if (gameTime % STAR_SPAWN_RATE == 0)
                spawnStar();
            checkCollision();
            despawn();
            tickExplosions();
            tickStars();
            handleRapid();
            adjustSpawns();
            if (shipHealth <= 0 || baseHealth <= 0)
                gameOver();
            gameTime++;
        }
    }

    /**
     * Moves each asteroid according to current asteroid speed.
     */
    private void moveAsteroids() {
        for (Point p : asteroidLocations) {
            p.x -= asteroidSpeed;
        }
    }

    /**
     * Moves each laser according to laser speed.
     */
    private void moveLasers() {
        for (Laser l : laserLocations) {
            l.move(LASER_SPEED);
        }
    }

    /**
     * Moves the power-up (if it exists) according to its speed
     */
    private void movePickup() {
        if (pickupLocation != null) {
            pickupLocation.x -= PICKUP_SPEED;
        }
    }

    /**
     * Moves the spaceship towards its target with a cap on max distance
     */
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

    /**
     * Spawns an asteroid at a random y-coordinate offscreen in the positive-x direction
     */
    private void spawnAsteroid() {
        Point p = new Point(800 + 20, 20 + (int) Math.floor(Math.random() * 360));
        asteroidLocations.add(p);
    }

    /**
     * Spawns a random power-up at a random y-coordinate offscreen in the positive-x direction
     */
    private void spawnPickup() {
        pickupLocation = new Point(800 + 15, 15 + (int) Math.floor(Math.random() * 370));
        if (Math.random() < 0.5) {pickup = PickupType.HEALTH;}
        else {pickup = PickupType.RAPID_FIRE;}
    }

    /**
     * Spawns a star at a random y-coordinate offscreen in the positive-x direction
     */
    private void spawnStar() {
        Star s = new Star((int) Math.floor(Math.random() * 400));
        starLocations.add(s);
    }

    /**
     * Handles rapid-fire logic. Automatically fires lasers periodically and ends rapid-fire
     * after time has elapsed
     */
    private void handleRapid() {
        if (rapidStatus == FireState.RAPID) {
            rapidTime++;
            if (rapidTime % 5 == 0) {
                fireLaser();
            }
            if (rapidTime > RAPID_MAX_TIME) {
                rapidTime = 0;
                rapidStatus = FireState.DEFAULT;
            }
        }
    }

    /**
     * Ticks each explosion, removing those that have expired.
     */
    private void tickExplosions() {
        ArrayList<Explosion> toDestroy = new ArrayList<Explosion>();
        for (Explosion e : explosionLocations) {
            if (e.tick())
                toDestroy.add(e);
        }
        explosionLocations.removeAll(toDestroy);
    }

    /**
     * Ticks each star, removing those that have moved offscreen.
     */
    private void tickStars() {
        ArrayList<Star> toDestroy = new ArrayList<Star>();
        for (Star s : starLocations) {
            if (s.tick())
                toDestroy.add(s);
        }
        starLocations.removeAll(toDestroy);
    }

    /**
     * Adjusts asteroid spawn rate and speed based on current elapsed game time
     */
    private void adjustSpawns() {
        if (gameTime > 15000) {
            asteroidSpawnRate = 12;
            asteroidSpeed = 10;
        } else if (gameTime > 6000) {
            asteroidSpawnRate = 15;
            asteroidSpeed = 8;
        } else if (gameTime > 3000) {
            asteroidSpawnRate = 20;
            asteroidSpeed = 6;
        } else if (gameTime > 1500) {
            asteroidSpawnRate = 24;
            asteroidSpeed = 5;
        } else if (gameTime > 500) {
            asteroidSpawnRate = 30;
            asteroidSpeed = 4;
        }
    }

    /**
     * Attempts to spawn a laser on the spaceship. Will not spawn if {@code MAX_LASERS} is met and rapid-fire
     * mode is off
     */
    public void fireLaser() {
        if (rapidStatus == FireState.DEFAULT) {
            if (Laser.getCount() >= MAX_LASERS) {return;}
            else {laserLocations.add(new Laser(new Point(shipLocation.x + 21, shipLocation.y), true));}
        } else {
            laserLocations.add(new Laser(new Point(shipLocation.x + 21, shipLocation.y), false));
        }
    }

    /**
     * Checks for collisions between game objects, removes objects that should be destroyed
     * as a result of collision, and handles other logic related to collisions. Spawns an
     * explosion where asteroids are destroyed.
     * Checks for:
     * <ul>
     * <li>Asteroid-Laser collisions
     * <li>Asteroid-Spaceship collisions
     * <li>Spaceship-Powerup collisions
     * </ul>
     * Asteroid-Base collisions are handled when asteroids despawn.
     */
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
                    explosionLocations.add(new Explosion(p));
                    asteroidsToDestroy.add(p);
                    lasersToDestroy.add(laser);
                    laser.destroy();
                    break;
                }
            }

            //Asteroid-Ship check
            if (!destroyed) {
                if (checkCircleBoxCollision(p, new Point(s.x - 22, s.y - 12), new Point(s.x + 32, s.y + 12))) {
                        explosionLocations.add(new Explosion(p));
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
                if (pickup == PickupType.HEALTH) {
                    shipHealth = Math.min(100, shipHealth + 20);
                    baseHealth = Math.min(100, baseHealth + 10);
                } else if (pickup == PickupType.RAPID_FIRE) {
                    rapidStatus = FireState.RAPID;
                }
                pickupLocation = null;
                pickup = PickupType.NONE;
            }
        }

        asteroidLocations.removeAll(asteroidsToDestroy);
        laserLocations.removeAll(lasersToDestroy);
    }


    /**
     * Despawns asteroids, lasers, and power-ups once they impact the base or move offscreen.
     * Spawns an explosion where asteroid impacts base.
     */
    private void despawn() {
        //Despawn asteroids
        ArrayList<Point> asteroidToDespawn = new ArrayList<Point>();
        for (Point p : asteroidLocations) {
            if (p.x < 30) {
                explosionLocations.add(new Explosion(p));
                asteroidToDespawn.add(p);
                baseHealth -= 5;
            }
        }
        asteroidLocations.removeAll(asteroidToDespawn);

        //Despawn lasers
        ArrayList<Laser> laserToDespawn = new ArrayList<Laser>();
        for (Laser l : laserLocations) {
            if (l.getLocation().x > 819) {
                l.destroy();
                laserToDespawn.add(l);
            }
        }
        laserLocations.removeAll(laserToDespawn);

        //Despawn power-up
        if (pickupLocation != null && pickupLocation.x < -15) {
            pickupLocation = null;
            pickup = PickupType.NONE;
        }
    }

    /**
     * Updates the target location based on given input.
     * @param location the location of the mouse
     */
    public void updateMouseLocation(Point location) {
        mouseTarget = location;
    }

    /**
     * Returns an {@code ArrayList<Point>} representing the locations of the asteroids.
     * @return the locations of the asteroids
     */
    public ArrayList<Point> getAsteroids() {
        return asteroidLocations;
    }

    /**
     * Returns an {@code Point} representing the location of the spaceship.
     * @return the location of the spaceship
     */
    public Point getShip() {
        return shipLocation;
    }

    /**
     * Returns an {@code ArrayList<Point>} representing the locations of the lasers.
     * @return the locations of the lasers
     */
    public ArrayList<Point> getLasers() {
        ArrayList<Point> temp = new ArrayList<Point>();
        for (Laser l : laserLocations) {
            temp.add(l.getLocation());
        }
        return temp;
    }

    /**
     * Returns an {@code ArrayList<Point>} representing the locations of the explosions.
     * @return the locations of the explosions
     */
    public ArrayList<Point> getExplosions() {
        ArrayList<Point> temp = new ArrayList<Point>();
        for (Explosion e : explosionLocations) {
            temp.add(e.getLocation());
        }
        return temp;
    }

    /**
     * Returns an {@code ArrayList<Point>} representing the locations of the background stars
     * @return the locations of the stars
     */
    public ArrayList<Point> getStars() {
        ArrayList<Point> temp = new ArrayList<Point>();
        for (Star s : starLocations) {
            temp.add(s.getLocation());
        }
        return temp;
    }

    /**
     * Returns a {@code Point} representing the location of the powerup, if it exists.
     * @return the location of the powerup if it exists, {@code null} otherwise
     */
    public Point getPickupLocation() {
        if (pickupLocation == null) {
            return null;
        }
        return pickupLocation;
    }

    /**
     * Returns a {@code boolean} representing the type of the powerup
     * @return {@code true} if the powerup is a repair powerup, {@code false} otherwise
     */
    public boolean getPickupType() {
        return pickup == PickupType.HEALTH;
    }

    /**
     * Returns the number of asteroids destroyed this game.
     * @return the number of asteroids destroyed
     */
    public int getAsteroidsDestroyed() {
        return asteroidsDestroyed;
    }

    /**
     * Returns the health of the ship
     * @return the ship's health
     */
    public int getShipHealth() {
        return shipHealth;
    }

    /**
     * Returns the health of the base
     * @return the base's health
     */
    public int getBaseHealth() {
        return baseHealth;
    }

    /**
     * Returns the duration of the game in ticks
     * @return the duration of the game
     */
    public int getGameTicks() {
        return gameTime;
    }

    /**
     * Returns the {@code GameState} of the model
     * @return the state of the game
     */
    public GameState getGameStatus() {
        return gameStatus;
    }

    /**
     * Returns the {@code FireState} of the model
     * @return the status of rapid fire
     */
    public FireState getFireStatus() {
        return rapidStatus;
    }

    /**
     * Checks to see if a circle centered at a given point with radius 19 and a rectangle defined by its
     * upper-left and lower-right points intersect. Method does so by checking each segment of the rectangle
     * individually. If the line extension of the segment is within 19 units of the center of the circle,
     * check to see if:
     * <ul>
     * <li> the center of the circle can be project perpendicularly on to the segment;
     * <li> one endpoint of the segment is within the circle;
     * <li> the other endpoint of the segment is within the circle.
     * </ul>
     * If any of those conditions are true, then an intersection exists. If none of the conditions are
     * true for every segment, then no intersection exists.
     * @param circle the {@code Point} representing the center of the circle
     * @param boxUL the {@code Point} representing the upper-left point of the rectangle
     * @param boxLR the {@code Point} representing the lower-right point of the rectangle
     * @return {@code true} if the rectangle and circle intersect, {@code false} otherwise
     */
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