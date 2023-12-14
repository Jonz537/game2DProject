package gameobjects;

import utils.Vector3d;

import java.awt.geom.Rectangle2D;

public class Ghost extends GameObject{

    public Ghost(Vector3d pos, double vel, int size) {
        super(pos, new Vector3d(vel, vel,0), size);
        collisionBox = new Rectangle2D.Double(pos.getX(), pos.getY(), size, size);

        imageRef = "./assets/ghost.png";
    }

    public void followPlayer(Player player) {
        Vector3d playerDirection = new Vector3d(player.getX() - pos.getX(), player.getY() - pos.getY(), 0);
        playerDirection = new Vector3d(playerDirection.getX() / playerDirection.getNorma() * getVelX(),
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
