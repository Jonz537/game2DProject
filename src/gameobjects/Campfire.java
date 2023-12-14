package gameobjects;

import utils.Vector3d;

import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class Campfire extends GameObject {

    private final double lightRadius;
    private int currentFrame;

    public Campfire(Vector3d pos, double lightRadius) {
        super(pos, 50);
        this.lightRadius = lightRadius;
        this.collisionBox = new Rectangle2D.Double(pos.getX(), pos.getY(), 50, 50);
        currentFrame = new Random().nextInt(0, 4);

        imageRef = "./assets/fire_" + currentFrame + ".png";
    }

    @Override
    public void animate() {
        currentFrame = (currentFrame + 1) % 4;
        imageRef = "./assets/fire_" + currentFrame + ".png";
    }

    public double getLightRadius() {
        return lightRadius;
    }

    public Area getLightArea() {
        return new Area(new Ellipse2D.Double(getX() + (getSize() - lightRadius) / 2,
                getY() + (getSize() - lightRadius) / 2,
                lightRadius, lightRadius));
    }
}
