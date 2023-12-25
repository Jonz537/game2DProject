package gameobjects;

import utils.Vector;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;

public class GameObject implements Serializable {

    protected Vector pos, vel = new Vector(0,0,0);
    protected int size;
    public double accX = 0., accY = 0.06;

    private boolean renderSound = false;
    private double distaceVolume = 0;

    protected Rectangle2D.Double collisionBox;
    protected String imageRef;

    public GameObject(GameObject go) {
        pos = new Vector(go.getPos());
        vel = new Vector(go.getVel());
        collisionBox = new Rectangle2D.Double(go.getCollisionBox().getX(), go.getCollisionBox().getY(),
                go.getCollisionBox().width, go.getCollisionBox().height);
        size = go.getSize();
        accX = go.accX;
        accY = go.accY;
        imageRef = go.getImageRef();
    }


    public GameObject(Vector pos, int size) {
        this.pos = pos;
        this.size = size;
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

    public GameObject(Vector pos, int size, double accX, double accY) {
        this.pos = pos;
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

    public double getZ() {
        return pos.getZ();
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

    public void setVelY(double newVel) {
        vel.setY(newVel);
    }

    public Rectangle2D.Double getCollisionBox() {
        return collisionBox;
    }

    public String getImageRef() {
        return imageRef;
    }

    public Double distance(GameObject go) {
        return Point.distance(pos.getX(), pos.getY(), go.getX(), go.getY());
    }

    public boolean isRenderSound() {
        return renderSound;
    }

    public void setRenderSound(boolean renderSound) {
        this.renderSound = renderSound;
    }

    public void update() {

    }

    public void animate() {

    }

    public Image getImage() {
        try {
            return ImageIO.read(new File(imageRef));
        } catch (IOException e) {
            System.out.println(this);
            throw new RuntimeException(e);
        }
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

    public double getDistaceVolume() {
        return distaceVolume;
    }

    public void setDistaceVolume(double distaceVolume) {
        this.distaceVolume = distaceVolume;
    }
}
