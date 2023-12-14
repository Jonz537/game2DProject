package gameobjects;

import utils.Vector;

import java.awt.geom.Rectangle2D;
import java.io.Serializable;

public class Player extends GameObject implements Serializable {

    private boolean touchingFloor = true;
    public int jumpToken = 0;
    public final double jumpSpeed = 35;

    public Player(Vector pos, int size) {
        super(new Vector(pos), size);
        vel = new Vector(0,0,0);
        collisionBox = new Rectangle2D.Double(pos.getX(), pos.getY(), size, size);
        this.size = size;
        accX = 0.5;

        imageRef = "./assets/player.png";
    }

    public Player(Player player) {
        super(player);
    }

    @Override
    public void update() {
        // TODO fix value for better movement
        vel.setX((Math.abs(vel.getX()) < 10) ? (vel.getX()) * 0.95 : 10 * vel.getX() / Math.abs(vel.getX()) * 0.98);
        vel.setX((Math.abs(vel.getX()) > 0.3) ? vel.getX(): 0);
        vel.addY(-((vel.getY() > 3) ? 3 - jumpToken * jumpSpeed : accY - jumpToken * jumpSpeed));

        pos.addX(vel.getX());
        pos.addY(vel.getY());
        jumpToken = (jumpToken != 0) ? ((jumpToken > 0) ? jumpToken - 1 : jumpToken + 1) : 0;
        collisionBox = new Rectangle2D.Double(pos.getX() - (double) size / 2, pos.getY() - (double) size / 2, size, size);

        accY = touchingFloor? 0: 0.3;

    }

    public void setTouchingFloor(boolean touchingFloor) {
        this.touchingFloor = touchingFloor;
    }


    public void accelerate() {
        vel.addX(3);
    }

    public void decelerate() {
        vel.addX(-3);
    }

    public void jump() {
        // TODO fix jump for better movement
        if (touchingFloor) {
            jumpToken++;
        }
    }

    public void die(Vector spawnPoint) {
        pos = new Vector(spawnPoint);
    }

}
