package gameobjects;

import utils.Vector3d;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;

public class Ghost extends GameObject{

    private static Image[] frames;

    public Ghost(Vector3d pos, double vel, int size) {
        super(pos, new Vector3d(vel, vel,0), size);
        collisionBox = new Rectangle2D.Double(pos.getX(), pos.getY(), size, size);

        try {
            // TODO find image
            image = imageToBuffered(ImageIO.read(new File("./assets/ghost.png"))
                    .getScaledInstance((size), size, Image.SCALE_SMOOTH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
