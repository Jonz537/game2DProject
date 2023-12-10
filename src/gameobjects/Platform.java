package gameobjects;

import utils.Vector3d;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Platform extends GameObject {

    private static final int height = 4 * 20;
    public static final int IMG_LENGHT = 4 * 46;

    public Platform(Vector3d pos, int lenght) {
        super(pos, new Vector3d(0,0,0), null, lenght, 0, 0);

        collisionBox = new Rectangle2D.Double(pos.x, pos.y + (double) (3 * height) / 4 - 4, size, (double) height / 4);
        try {
            // TODO find image
            image = ImageIO.read(new File("./assets/platform.png"))
                    .getScaledInstance(IMG_LENGHT, height, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Image getImage() {
        int imageTimes = (size / IMG_LENGHT);
        BufferedImage bimage = new BufferedImage(size, 2 * height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = bimage.createGraphics();

        AffineTransform transform = new AffineTransform();
        transform.translate(0, height);
        transform.scale(1, -1);

        for (int i = 0 ; i < imageTimes; i++) {
            bGr.drawImage(image, transform, null);
            transform.translate(IMG_LENGHT, 0);
        }

        bGr.dispose();

        return bimage;
    }
}
