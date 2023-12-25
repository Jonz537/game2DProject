package gameobjects;

import utils.Facing;
import utils.Vector;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class Player extends GameObject implements Serializable {

    private boolean touchingFloor = true;
    public int jumpToken = 0;
    public final double jumpSpeed = 30;

    private Facing facing = Facing.RIGHT;
    private int currentIdleFrame = 0, currentWalkingFrame = 0;

    public Player(Player player) {
        super(player);
        this.touchingFloor = player.touchingFloor;
        this.jumpToken = player.jumpToken;
        this.facing = player.facing;
    }

    public Player(Vector pos, int size) {
        super(new Vector(pos), size);
        vel = new Vector(0,0,0);
        collisionBox = new Rectangle2D.Double(pos.getX(), pos.getY(), size, size);
        this.size = size;
        accX = 0.5;

        imageRef = "./assets/player/player_idle_" + currentIdleFrame + ".png";
    }

    public void setTouchingFloor(boolean touchingFloor) {
        this.touchingFloor = touchingFloor;
    }

    public void die(Vector spawnPoint) {
        setVelY(0);
        pos = new Vector(spawnPoint);
    }

    public void outOfBounds(Vector spawnPoint) {
        if (getY() < -600) {
            setVelY(0);
            pos = new Vector(spawnPoint);
        }
    }

    @Override
    public void update() {
        vel.setX((Math.abs(vel.getX()) < 10) ? (vel.getX()) * 0.95 : 10 * vel.getX() / Math.abs(vel.getX()) * 0.98);
        vel.setX((Math.abs(vel.getX()) > 0.3) ? vel.getX(): 0);
        vel.addY(-((vel.getY() > 3) ? 3 - jumpToken * jumpSpeed : accY - jumpToken * jumpSpeed));

        pos.addX(vel.getX());
        pos.addY(vel.getY());
        jumpToken = (jumpToken != 0) ? ((jumpToken > 0) ? jumpToken - 1 : jumpToken + 1) : 0;
        collisionBox = new Rectangle2D.Double(pos.getX() - (double) size / 2, pos.getY() - (double) size / 2, size, size);

        accY = touchingFloor? 0: 0.06;

    }

    @Override
    public void animate() {
        if (vel.getX() == 0 && vel.getY() == 0) {
            currentIdleFrame = (currentIdleFrame + 1) % 4;
            imageRef = "./assets/player/player_idle_" + currentIdleFrame + ".png";
        } else {
            currentWalkingFrame = (currentWalkingFrame + 1) % 4;
            imageRef = "./assets/player/player_walk_" + currentWalkingFrame + ".png";
        }
    }

    public void collidePlatform(Rectangle2D.Double platoform) {
        if (getCollisionBox().intersects(platoform)){
            if (getVelY() <= 0) {
                setVelY(0);
                setTouchingFloor(true);
            } else {
                setY(getY() - getVelY());
            }
        }
    }

    public int signDirection() {
        return facing.signDirection();
    }

    public void accelerate() {
        vel.addX(1);
        facing = Facing.RIGHT;
    }

    public void decelerate() {
        vel.addX(-1);
        facing = Facing.LEFT;
    }

    public void jump() {
        if (touchingFloor) {
            jumpToken++;
        }
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
