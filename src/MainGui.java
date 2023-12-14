import client_stuff.ClientController;
import client_stuff.RenderPanel;
import utils.Game;

import javax.swing.*;
import java.awt.*;

public class MainGui extends JDialog {
    private JPanel contentPane;

    Game model;
    ClientController controller;

    public MainGui() {
        setContentPane(contentPane);
        setModal(true);

        model = new Game();
        controller = new ClientController(model);
        controller.connect();

        RenderPanel renderPanel = new RenderPanel(controller);
        contentPane.add(renderPanel);
    }

    public static void main(String[] args) {

        //TODO fullscreen mode
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();

        MainGui dialog = new MainGui();
        dialog.pack();
        dialog.setVisible(true);

        System.exit(0);
    }
}
