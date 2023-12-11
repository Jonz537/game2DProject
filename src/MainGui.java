import renderer.RenderPanel;
import utils.Game;
import utils.GameController;

import javax.swing.*;
import java.awt.*;

public class MainGui extends JDialog {
    private JPanel contentPane;

    Game model;
    GameController controller;

    public MainGui() {
        setContentPane(contentPane);
        setModal(true);

        model = new Game();
        controller = new GameController(model);

        RenderPanel renderPanel = new RenderPanel(controller);
        contentPane.add(renderPanel);
    }

    public static void main(String[] args) {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();

        MainGui dialog = new MainGui();
        dialog.pack();
        dialog.setVisible(true);

//        gd.setFullScreenWindow(dialog);

        System.exit(0);
    }
}
