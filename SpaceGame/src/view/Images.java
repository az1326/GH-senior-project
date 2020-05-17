package view;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Static class used to access images.
 */
public class Images {
    public static final BufferedImage BASE;
    public static final BufferedImage SHIP;
    public static final BufferedImage ASTEROID;
    public static final BufferedImage EXPLODE;
    public static final BufferedImage HEALTH;
    public static final BufferedImage RAPID;
    public static final BufferedImage LASER;

    //Load images into static variables;
    static {
        BufferedImage temp = null;
        try {
            temp = ImageIO.read(Images.class.getResourceAsStream("/images/Base.png"));
        } catch (IOException e) {}
        BASE = temp;

        temp = null;
        try {
            temp = ImageIO.read(Images.class.getResourceAsStream("/images/Spaceship.png"));
        } catch (IOException e) {}
        SHIP = temp;

        temp = null;
        try {
            temp = ImageIO.read(Images.class.getResourceAsStream("/images/Asteroid.png"));
        } catch (IOException e) {}
        ASTEROID = temp;

        temp = null;
        try {
            temp = ImageIO.read(Images.class.getResourceAsStream("/images/Explosion.png"));
        } catch (IOException e) {}
        EXPLODE = temp;

        temp = null;
        try {
            temp = ImageIO.read(Images.class.getResourceAsStream("/images/Health.png"));
        } catch (IOException e) {}
        HEALTH = temp;

        temp = null;
        try {
            temp = ImageIO.read(Images.class.getResourceAsStream("/images/Rapid.png"));
        } catch (IOException e) {}
        RAPID = temp;

        temp = null;
        try {
            temp = ImageIO.read(Images.class.getResourceAsStream("/images/Laser.png"));
        } catch (IOException e) {}
        LASER = temp;
    }
}