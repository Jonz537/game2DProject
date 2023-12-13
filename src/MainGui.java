import client_stuff.ClientController;
import client_stuff.RenderPanel;
import utils.Game;
import utils.GameController;

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
        //TODO connection
//        controller.connect();

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
