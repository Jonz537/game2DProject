package gameobjects;

import utils.Vector;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Tent extends GameObject implements Serializable {

    public Tent(Tent tent) {
        super(tent);
    }

    public Tent(Vector pos, int height) {
        super(pos, height);

        collisionBox = new Rectangle2D.Double(pos.getX(), pos.getY(), 2 * size, size);
        imageRef = "./assets/checkpoint.png";

    }

    @Override
    public BufferedImage imageToBuffered(Image imageToTransform) {

        Image anotherImage = imageToTransform.getScaledInstance(size, size, Image.SCALE_SMOOTH);

        AffineTransform transform = new AffineTransform();
        transform.translate(0, size);
        transform.scale(2, -1);

        BufferedImage bImage = new BufferedImage(2 * size, 2 * size, BufferedImage.TYPE_INT_ARGB);

        Graphics2D bGr = bImage.createGraphics();
        bGr.drawImage(anotherImage, transform, null);
        bGr.dispose();

        return bImage;
    }

}
