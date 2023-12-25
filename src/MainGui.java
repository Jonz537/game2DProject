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
        setUndecorated(true);
        setContentPane(contentPane);
        setModal(true);

        model = new Game();
        controller = new ClientController(model);
        controller.connect();

        RenderPanel renderPanel = new RenderPanel(controller);
        contentPane.add(renderPanel);

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(this);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            MainGui dialog = new MainGui();

            dialog.setVisible(true);
        });
    }
}
