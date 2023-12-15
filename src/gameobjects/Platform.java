package gameobjects;

import utils.Vector;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Platform extends GameObject implements Serializable {

    private static final int height = 4 * 20;
    public static final int IMG_LENGTH = 4 * 46;

    public Platform(Vector pos, int length) {
        super(pos, new Vector(0,0,0), null, length, 0, 0);

        collisionBox = new Rectangle2D.Double(pos.getX(), pos.getY() + (double) (3 * height) / 4 - 4, size, (double) height / 4);
        imageRef = "./assets/platform.png";
    }

    public Platform(Platform platform) {
        super(platform);
    }


    @Override
    public BufferedImage imageToBuffered(Image imageToTransform) {
        int imageTimes = (size / IMG_LENGTH);
        BufferedImage bImage = new BufferedImage(size, 2 * height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = bImage.createGraphics();

        Image anotherImage = imageToTransform.getScaledInstance(IMG_LENGTH, height, Image.SCALE_SMOOTH);

        AffineTransform transform = new AffineTransform();
        transform.translate(0, height);
        transform.scale(1, -1);

        for (int i = 0 ; i < imageTimes; i++) {
            bGr.drawImage(anotherImage, transform, null);
            transform.translate(IMG_LENGTH, 0);
        }

        bGr.dispose();

        return bImage;
    }
}
