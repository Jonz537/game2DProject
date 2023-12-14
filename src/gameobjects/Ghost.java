package gameobjects;

import utils.Vector;

import java.awt.geom.Rectangle2D;
import java.io.Serializable;

public class Ghost extends GameObject implements Serializable {

    public Ghost(Vector pos, double vel, int size) {
        super(pos, new Vector(vel, vel,0), size);
        collisionBox = new Rectangle2D.Double(pos.getX(), pos.getY(), size, size);

        imageRef = "./assets/ghost.png";
    }

    public Ghost(Ghost ghost) {
        super(ghost);
    }

    public void followPlayer(Player player) {
        Vector playerDirection = new Vector(player.getX() - pos.getX(), player.getY() - pos.getY(), 0);
        playerDirection = new Vector(playerDirection.getX() / playerDirection.getNorma() * getVelX(),
                playerDirection.getY() / playerDirection.getNorma() * getVelX(), 0);

        setX(getX() + playerDirection.getX());
        setY(getY() + playerDirection.getY());

        collisionBox = new Rectangle2D.Double(getX(), getY(), size, size);

    }

    @Override
    public void animate() {
        super.animate();
    }
}
