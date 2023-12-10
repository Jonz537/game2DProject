import gameobjects.*;
import utils.GameController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class RenderPanel extends JPanel implements KeyListener, MouseMotionListener {

    protected GameController gameController;
    protected Image sceneBackground;

    //TODO debug mode
    int lightLevel = 25, darkLevel = 255;
    protected double worldSize = 1000., scaler = Math.min(getWidth(), getHeight());
    double torchRadius = worldSize / 3.5;
    protected Point2D mousePos = new Point(0, 0);

    public RenderPanel(GameController controller) {
        gameController = controller;
        setFocusable(true);
        addKeyListener(this);
        addMouseMotionListener(this);
        initialize();
    }

    protected void initialize() {
        // remove cursor from scene
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg, new Point(0, 0), "blank cursor");
        this.setCursor(blankCursor);

        // background image
        try {
            sceneBackground = ImageIO.read(new File("./assets/background.jpg"))
                    .getScaledInstance((int) ((worldSize) * 4.), (int) worldSize, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // start update timer
        Timer gameTimer = new Timer(16, (e) -> {
            gameController.update();
            gameController.checkCollision();
            repaint();
        });
        Timer animationTimer = new Timer(100, (e -> gameController.updateImages()));

        gameTimer.start();
        animationTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // antialiasing
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // world scaling
        worldSize = 1000.;
        scaler = Math.min(getWidth(), getHeight());
        graphics.translate(getWidth() / 2. - gameController.getPlayer().getX() * scaler / worldSize,
                getHeight() / 2.);
        graphics.scale(scaler / worldSize, scaler / worldSize);

        // draw background stuff
        int chunkXPos = (int) Math.floor((gameController.getPlayer().getX() + (worldSize / 2)) / 4 / worldSize);
        graphics.drawImage(sceneBackground, (int) (chunkXPos * 4 * worldSize + (-9 * worldSize / 2)), (int) (-worldSize / 2), this);
        graphics.drawImage(sceneBackground, (int) (chunkXPos * 4 * worldSize + (-worldSize / 2)), (int) (-worldSize / 2), this);
        graphics.drawImage(sceneBackground, (int) (chunkXPos  * 4 * worldSize + (7 * worldSize / 2)), (int) (-worldSize / 2), this);
        graphics.scale(1, -1);


        // drawing entities
        for (GameObject go: gameController.getEntities()) {
            drawGameObject(graphics, go);
        }
        // drawing torchlight
        lightRender(graphics);
    }

    private void drawGameObject(Graphics2D graphics, GameObject go) {
        if (go instanceof Player) {
            graphics.drawImage(go.getImage(), (int) go.getX() - go.getSize() / 2, (int) go.getY() - go.getSize() / 2, this);
        } else if (go instanceof Platform) {
            graphics.drawImage(go.getImage(), (int) go.getX(), (int) go.getY(), this);
        } else {
            graphics.drawImage(go.getImage(), (int) go.getX(), (int) go.getY(), this);
        }

        // TODO debug mode
//        Area a = new Area(go.getCollisionBox());
//        graphics.setColor(new Color(255,0,0,128));
//        graphics.fill(a);
//        graphics.drawLine(0, (int) - worldSize, 0, (int) worldSize);
//        graphics.drawLine((int) - worldSize,0, (int) worldSize, 0);
    }

    private void lightRender(Graphics2D graphics) {
        // TODO fix this again
        Rectangle2D.Double screenDark = new Rectangle2D.Double((-worldSize / 2) + gameController.getPlayer().getX(),
                (-worldSize / 2), worldSize, worldSize);

        Point2D mousePosScaled = pixelsToPos(mousePos);

        Ellipse2D.Double mouseTorch = new Ellipse2D.Double(mousePosScaled.getX() - torchRadius / 2.,
                mousePosScaled.getY() - torchRadius / 2., torchRadius, torchRadius);

        Area square = new Area(screenDark), torchCircle = new Area(mouseTorch);
        square.subtract(torchCircle);

        boolean drawTorchAlone = true;
        // random test torch
        for (GameObject fire: gameController.getEntities()) {
            if (!(fire instanceof Fire)) {
                continue;
            }
            square.subtract(((Fire) fire).getLightArea());

            torchCircle = new Area(mouseTorch);
            torchCircle.intersect(((Fire) fire).getLightArea());

            if (torchCircle.isEmpty()) {
                Point fireCenter = new Point((int) (fire.getX() + fire.getSize() / 2), (int) (fire.getY() + fire.getSize() / 2));

                graphics.setPaint(createRadialLight(fireCenter, ((Fire) fire).getLightRadius()));
                graphics.fill(((Fire) fire).getLightArea());
            } else {
                drawTorchAlone = false;
//                Point averageCenter = new Point((int) ((mousePosScaled.getX() + fire.getX() + (fire.getX() + fire.getSize() / 2)) / 2),
//                        (int) ((mousePosScaled.getY() + fire.getY() + (fire.getY() + fire.getSize() / 2)) / 2));
                Point averageCenter = new Point((int) ((mousePosScaled.getX() + (fire.getX() + fire.getSize() / 2)) / 2),
                        (int) ((mousePosScaled.getY() + (fire.getY() + fire.getSize() / 2)) / 2));
                double combinedRadius = (2 * torchRadius) + (((Fire) fire).getLightRadius());

                graphics.setPaint(createRadialLight(averageCenter, combinedRadius));
                graphics.fillOval((int) (((averageCenter.getX() - (combinedRadius) + fire.getX()) / 2)),
                        (int) (((averageCenter.getY() - combinedRadius + fire.getY()) / 2)),
                        (int) combinedRadius, (int) combinedRadius);
            }
        }

        if (drawTorchAlone) {
            graphics.setPaint(createRadialLight((Point) mousePosScaled, torchRadius));
            graphics.fillOval((int) (mousePosScaled.getX() - torchRadius / 2.),
                    (int) (mousePosScaled.getY() - torchRadius / 2.),
                    (int) torchRadius, (int) torchRadius);
        }

//        square.subtract(new Area(new Ellipse2D.Double(gameController.getPlayer().getX() - gameController.getPlayer().getSize(),
//                gameController.getPlayer().getY() - gameController.getPlayer().getSize(),
//                 2 * gameController.getPlayer().getSize(), 2 * gameController.getPlayer().getSize())));

        graphics.setColor(Color.BLACK);
        graphics.fill(square);
    }

    private RadialGradientPaint createRadialLight(Point center, double radius) {
        return new RadialGradientPaint(
                (float) (center.getX()),
                (float) (center.getY()),
                (float) radius,// Radius
                new float[]{0.0f, 1.0f},   // Fractions
                new Color[]{new Color(0, 0, 0, lightLevel),
                        new Color(50, 0, 0, darkLevel)});
    }


    // world coordinate to pixel coordinate
    public Point2D posToPixels(Point2D point) {
        return null;
    }

    // pixel coordinate to world coordinate
    public Point2D pixelsToPos(Point2D point2D) {

        int x = (int) ((point2D.getX() - getWidth() / 2 + gameController.getPlayer().getX() * scaler / worldSize) * worldSize / scaler);
        int y = (int) ((point2D.getY() - getHeight() / 2) * -worldSize / scaler);

        return new Point(x, y);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    // player movement
    @Override
    public void keyPressed(KeyEvent e) {
        // TODO make all the inputs + parallel inputs
        switch (e.getKeyCode()) {
            case KeyEvent.VK_D -> gameController.getPlayer().accelerate();
            case KeyEvent.VK_A -> gameController.getPlayer().decelerate();
            case KeyEvent.VK_SPACE -> gameController.getPlayer().jump();
            case KeyEvent.VK_ENTER -> gameController.addEntity(new Bullet(gameController.getPlayer().getPos(), 25, 10, 10, 0.05));
            case KeyEvent.VK_Q -> printDebugInfo();
        }
    }

    private void printDebugInfo() {
        System.out.println(gameController.getSpawnPoint());
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mousePos = e.getPoint();
    }
}
