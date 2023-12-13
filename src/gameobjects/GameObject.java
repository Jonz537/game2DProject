package gameobjects;

import utils.Vector3d;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;

public class GameObject implements Serializable {

    protected Vector3d pos, vel;
    protected Rectangle2D.Double collisionBox;
    protected int size;
    public double accX = 0., accY = 0.3;
    public transient BufferedImage image;

    public GameObject(Vector3d pos, int size) {
        this.pos = pos;
        this.size = size;
    }

    public GameObject(Vector3d pos, int size, double accX, double accY) {
        this.pos = pos;
        this.size = size;
        this.accX = accX;
        this.accY = accY;
    }

    public GameObject(Vector3d pos, Vector3d vel, int size) {
        this.pos = pos;
        this.vel = vel;
        this.size = size;
    }

    public GameObject(Vector3d pos, Rectangle2D.Double collisionBox, int size) {
        this.pos = pos;
        this.collisionBox = collisionBox;
        this.size = size;
    }

    public GameObject(Vector3d pos, Vector3d vel, Rectangle2D.Double collisionBox, int size) {
        this.pos = pos;
        this.vel = vel;
        this.collisionBox = collisionBox;
        this.size = size;
    }

    public GameObject(Vector3d pos, Rectangle2D.Double collisionBox, int size, double accX, double accY) {
        this.pos = pos;
        this.collisionBox = collisionBox;
        this.size = size;
        this.accX = accX;
        this.accY = accY;
    }

    public GameObject(Vector3d pos, Vector3d vel, Rectangle2D.Double collisionBox, int size, double accX, double accY) {
        this.pos = pos;
        this.vel = vel;
        this.collisionBox = collisionBox;
        this.size = size;
        this.accX = accX;
        this.accY = accY;
    }

    public Vector3d getPos() {
        return pos;
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

    public void setPos(Vector3d pos) {
        this.pos = new Vector3d(pos);
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

    @Serial
    private void writeObject(ObjectOutputStream out) {
        // Write the image as bytes
        if (image != null) {
            try {
                out.defaultWriteObject();
                ImageIO.write(image, "png", out);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Serial
    private void readObject(ObjectInputStream in) {
        // Read the image bytes and reconstruct the BufferedImage
        try {
            in.defaultReadObject();
            image = ImageIO.read(in);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public BufferedImage imageToBuffered(Image imageToTransform) {
        AffineTransform transform = new AffineTransform();
        transform.translate(0, size);
        transform.scale(1, -1);

        BufferedImage bimage = new BufferedImage(size, 2 * size, BufferedImage.TYPE_INT_ARGB);

        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(imageToTransform, transform, null);
        bGr.dispose();

        return bimage;
    }

    public Image getImage() {
        return image;
    }



}
