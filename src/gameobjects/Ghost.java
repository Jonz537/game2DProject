package gameobjects;

import utils.Vector;

import java.awt.geom.Rectangle2D;
import java.io.Serializable;

public class Ghost extends GameObject implements Serializable {

    private Player target;

    public Ghost(Ghost ghost) {
        super(ghost);
        this.target = ghost.target;
    }

    public Ghost(Vector pos, double vel, int size, Player target) {
        super(pos, new Vector(vel, vel,0), size);
        collisionBox = new Rectangle2D.Double(pos.getX(), pos.getY(), size, size);

        imageRef = "./assets/ghost.png";
        this.target = target;
    }

    public void followPlayer() {
        //TODO fix this
        Vector playerDirection = new Vector(target.getX() - pos.getX(), target.getY() - pos.getY(), 0);
        playerDirection = new Vector(playerDirection.getX() / playerDirection.getNorma() * getVelX(),
                playerDirection.getY() / playerDirection.getNorma() * getVelX(), 0);

        setX(getX() + playerDirection.getX());
        setY(getY() + playerDirection.getY());

        collisionBox = new Rectangle2D.Double(getX(), getY(), size, size);

    }
}
