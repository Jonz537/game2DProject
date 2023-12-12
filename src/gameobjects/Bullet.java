package gameobjects;

import utils.Vector3d;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;

public class Bullet extends GameObject {

    private int ttl = 120;

    public Bullet(Vector3d pos, int size, double vecX, double vecY, double accY) {
        super(new Vector3d(pos), size, 0, accY);
        vel = new Vector3d(vecX, vecY, 0);
        collisionBox = new Rectangle2D.Double(pos.x - (double) size / 2, pos.y - (double) size / 2, size, size);

        try {
            image = ImageIO.read(new File("./assets/test.jpg"))
                    .getScaledInstance((size), size, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void update() {
        vel.y -= (vel.y > 3) ? 3 : accY;
        pos.x += vel.x;
        pos.y += vel.y;
        collisionBox = new Rectangle2D.Double(pos.x - (double) size / 2, pos.y - (double) size / 2, size, size);
        ttl--;
    }

    public boolean timeOut() {
        return ttl <= 0;
    }
}
