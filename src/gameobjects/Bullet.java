package gameobjects;

import utils.Vector3d;

import java.awt.geom.Rectangle2D;
import java.io.Serializable;

public class Bullet extends GameObject implements Serializable {

    private int ttl = 120;

    public Bullet(Vector3d pos, int size, double vecX, double vecY, double accY) {
        super(new Vector3d(pos), size, 0, accY);
        vel = new Vector3d(vecX, vecY, 0);
        collisionBox = new Rectangle2D.Double(pos.getX(), pos.getY(), size, size);
    }

    public void update() {
        vel.y -= (vel.y > 3) ? 3 : accY;
        pos.x += vel.x;
        pos.y += vel.y;
        collisionBox = new Rectangle2D.Double(pos.getX(), pos.getY(), size, size);
        ttl--;
    }

    public boolean timeOut() {
        return ttl <= 0;
    }
}
