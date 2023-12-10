package gameobjects;

import utils.Vector3d;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Fire extends GameObject {

    private double lightRadius;
    private Area lightArea;
    private int currentFrame = 0;

    private static Image[] frames;

    {
        try {
            frames = new Image[]{ImageIO.read(new File("./assets/fire_0.png"))
                    .getScaledInstance((size), size, Image.SCALE_SMOOTH),
                    ImageIO.read(new File("./assets/fire_1.png"))
                            .getScaledInstance((size), size, Image.SCALE_SMOOTH),
                    ImageIO.read(new File("./assets/fire_2.png"))
                            .getScaledInstance((size), size, Image.SCALE_SMOOTH),
                    ImageIO.read(new File("./assets/fire_3.png"))
                            .getScaledInstance((size), size, Image.SCALE_SMOOTH),};
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Fire(Vector3d pos, double lightRadius) {
        super(pos, 50);
        lightArea = new Area(new Ellipse2D.Double(getX() + (getSize() - lightRadius) / 2,
                getY() + (getSize() - lightRadius) / 2,
                lightRadius, lightRadius));
        this.lightRadius = lightRadius;
        this.collisionBox = new Rectangle2D.Double(pos.x, pos.y, 50, 50);
        currentFrame = new Random().nextInt(0, 4);
        image = frames[currentFrame];
    }

    @Override
    public void animate() {
        currentFrame = (currentFrame + 1) % 4;
        image = frames[currentFrame];
    }

    public double getLightRadius() {
        return lightRadius;
    }

    public Area getLightArea() {
        return lightArea;
    }
}
