package gameobjects;

import utils.Facing;
import utils.Vector;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Ghost extends GameObject implements Serializable {

    private Player target;

    private int currentFrame = 0;
    private Facing facing = Facing.RIGHT;


    public Ghost(Ghost ghost) {
        super(ghost);
        this.target = ghost.target;
        this.facing = ghost.facing;
    }

    public Ghost(Vector pos, double vel, int size, Player target) {
        super(pos, new Vector(vel, vel,0), size);
        collisionBox = new Rectangle2D.Double(pos.getX(), pos.getY(), size, size);

        imageRef = "./assets/ghost/ghost_" + currentFrame + ".png";
        this.target = target;
    }

    @Override
    public void update() {
        Vector playerDirection = new Vector(target.getX() - pos.getX(), target.getY() - pos.getY(), 0);
        playerDirection = new Vector(playerDirection.getX() / playerDirection.getNorma() * getVelX(),
                playerDirection.getY() / playerDirection.getNorma() * getVelX(), 0);

        setX(getX() + playerDirection.getX());
        setY(getY() + playerDirection.getY());

        if (playerDirection.getX() < 0) {
            facing = Facing.RIGHT;
        } else {
            facing = Facing.LEFT;
        }

        collisionBox = new Rectangle2D.Double(getX(), getY(), size, size);

    }

    @Override
    public void animate() {
        currentFrame = (currentFrame + 1) % 3;
        imageRef = "./assets/ghost/ghost_" + currentFrame + ".png";
    }

    @Override
    public BufferedImage imageToBuffered(Image imageToTransform) {

        Image anotherImage = imageToTransform.getScaledInstance(size, size, Image.SCALE_SMOOTH);

        AffineTransform transform = new AffineTransform();
        if (facing.equals(Facing.RIGHT)) {
            transform.translate(0, size);
            transform.scale(1, -1);
        } else {
            transform.translate(size, size);
            transform.scale(-1, -1);
        }

        BufferedImage bImage = new BufferedImage(size, 2 * size, BufferedImage.TYPE_INT_ARGB);

        Graphics2D bGr = bImage.createGraphics();
        bGr.drawImage(anotherImage, transform, null);
        bGr.dispose();

        return bImage;
    }

}
