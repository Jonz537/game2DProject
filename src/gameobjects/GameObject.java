package gameobjects;

import utils.Vector;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;

public class GameObject implements Serializable {

    protected Vector pos, vel;
    protected Rectangle2D.Double collisionBox;
    protected int size;
    public double accX = 0., accY = 0.3;

    protected String imageRef;
//    public transient BufferedImage image;

    public GameObject(Player player) {
        pos = new Vector(player.getPos());
        vel = new Vector(player.getVel());
        collisionBox = new Rectangle2D.Double(player.getCollisionBox().getX(), player.getCollisionBox().getY(),
                player.getCollisionBox().width, player.getCollisionBox().height);
        size = player.getSize();
        accX = player.accX;
        accY = player.accY;
    }

    public GameObject(Ghost player) {
        pos = new Vector(player.getPos());
        vel = new Vector(player.getVel());
        collisionBox = new Rectangle2D.Double(player.getCollisionBox().getX(), player.getCollisionBox().getY(),
                player.getCollisionBox().width, player.getCollisionBox().height);
        size = player.getSize();
        accX = player.accX;
        accY = player.accY;
    }


    public GameObject(Vector pos, int size) {
        this.pos = pos;
        this.size = size;
    }

    public GameObject(Vector pos, int size, double accX, double accY) {
        this.pos = pos;
        this.size = size;
        this.accX = accX;
        this.accY = accY;
    }

    public GameObject(Vector pos, Vector vel, int size) {
        this.pos = pos;
        this.vel = vel;
        this.size = size;
    }

    public GameObject(Vector pos, Rectangle2D.Double collisionBox, int size) {
        this.pos = pos;
        this.collisionBox = collisionBox;
        this.size = size;
    }

    public GameObject(Vector pos, Vector vel, Rectangle2D.Double collisionBox, int size) {
        this.pos = pos;
        this.vel = vel;
        this.collisionBox = collisionBox;
        this.size = size;
    }

    public GameObject(Vector pos, Rectangle2D.Double collisionBox, int size, double accX, double accY) {
        this.pos = pos;
        this.collisionBox = collisionBox;
        this.size = size;
        this.accX = accX;
        this.accY = accY;
    }

    public GameObject(Vector pos, Vector vel, Rectangle2D.Double collisionBox, int size, double accX, double accY) {
        this.pos = pos;
        this.vel = vel;
        this.collisionBox = collisionBox;
        this.size = size;
        this.accX = accX;
        this.accY = accY;
    }

    public Vector getPos() {
        return pos;
    }

    public Vector getVel() {
        return vel;
    }

    public double getX() {
        return pos.getX();
    }

    public double getY() {
        return pos.getY();
    }

    public double getVelX() {
        return vel.getX();
    }

    public double getVelY() {
        return vel.getY();
    }

    public int getSize() {
        return size;
    }

    public void setX(double newPos) {
        pos.setX(newPos);
    }

    public void setY(double newPos) {
        pos.setY(newPos);
    }

    public void setPos(Vector pos) {
        this.pos = new Vector(pos);
    }

    public void setVelX(double newVel) {
        vel.setX(newVel);
    }

    public void setVelY(double newVel) {
        vel.setY(newVel);
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Rectangle2D.Double getCollisionBox() {
        return collisionBox;
    }

    public void update() {

    }

    public void animate() {

    }

    public BufferedImage imageToBuffered(Image imageToTransform) {

        Image anotherImage = imageToTransform.getScaledInstance(size, size, Image.SCALE_SMOOTH);

        AffineTransform transform = new AffineTransform();
        transform.translate(0, size);
        transform.scale(1, -1);

        BufferedImage bImage = new BufferedImage(size, 2 * size, BufferedImage.TYPE_INT_ARGB);

        Graphics2D bGr = bImage.createGraphics();
        bGr.drawImage(anotherImage, transform, null);
        bGr.dispose();

        return bImage;
    }

    public String getImageRef() {
        return imageRef;
    }

    public Image getImage() {
        try {
            return ImageIO.read(new File(imageRef));
        } catch (IOException e) {
            System.out.println(this);
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "GameObject{" +
                "pos=" + pos +
                ", vel=" + vel +
                ", collisionBox=" + collisionBox +
                ", size=" + size +
                ", accX=" + accX +
                ", accY=" + accY +
                ", imageRef='" + imageRef + '\'' +
                '}';
    }
}
