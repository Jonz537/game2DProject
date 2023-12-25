package client_stuff;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class OptionPanel extends JPanel implements MouseListener {

    Image image;

    public OptionPanel() {
        addMouseListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // antialiasing
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int size = 25;

        setMinimumSize(new Dimension(size, size));
        setMaximumSize(new Dimension(size, size));
        setPreferredSize(new Dimension(size, size));

        try {
            image = ImageIO.read(new File("./assets/settings.png")).getScaledInstance(size, size, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            System.out.println(this);
            throw new RuntimeException(e);
        }

        graphics.drawImage(image, 0, 0, this);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO show exit
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
