package gameobjects;

import utils.Vector3d;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Tent extends GameObject{

    public Tent(Vector3d pos, int height) {
        super(pos, height);

        collisionBox = new Rectangle2D.Double(pos.getX(), pos.getY(), 2 * size, size);

        try {
            image = imageToBuffered(ImageIO.read(new File("./assets/checkpoint.png"))
                    .getScaledInstance((size), size, Image.SCALE_SMOOTH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public BufferedImage imageToBuffered(Image imageToTransform) {
        AffineTransform transform = new AffineTransform();
        transform.translate(0, size);
        transform.scale(2, -1);

        BufferedImage bimage = new BufferedImage(2 * size, 2 * size, BufferedImage.TYPE_INT_ARGB);

        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(imageToTransform, transform, null);
        bGr.dispose();

        return bimage;
    }

}
